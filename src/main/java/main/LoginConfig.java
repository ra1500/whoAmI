package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class LoginConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationProvider customAuthProvider;  //ryan

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new UrlAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(customAuthProvider);
        //auth.inMemoryAuthentication()
        //.withUser("admin").password(encoder().encode("admin")).roles("ADMIN");
        //.and()
        //.withUser("user").password(encoder().encode("user")).roles("USER");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // cross-site request forgery. disabled for now. need to generate a code to feed into the html login form.
                //.exceptionHandling()  // ?
                //.authenticationEntryPoint(restAuthenticationEntryPoint)  this is handled via the successHandler below
                //.and()
                .authorizeRequests()
                //.antMatchers("/anonymous*").anonymous()     //not needed at this time
                //.antMatchers("/foo.html").authenticated()   //not needed at this time
                //.antMatchers("/foo.html").hasRole("ADMIN")  //not needed at this time
                .antMatchers("/login*").permitAll()  //excludes 'login.html' page from requiring to be logged in
                .antMatchers("/createlogin.html").permitAll()
                .antMatchers("/user*").permitAll()  // Need to set this up so that only users created from the createlogin.html page can actually POST via this url path
                .anyRequest().authenticated()
                .and()
                .formLogin()  // indicating the need for a login via a form
                .loginPage("/login.html") // not index.html since site is now login required except for /login as specified in 'permitAll()" above.
                .loginProcessingUrl("/do_login")  //in html form 'action="/do_login" as well in order to match here. This avoids the default of "/login" which may expose fact that I'm using Spring Security.
                .successHandler(myAuthenticationSuccessHandler())
                //.failureHandler(myFailureHandler)  //add a failure landing page/url
                .and()
                .logout();  // .indicated in html via href to '/logout' unless otherwise specified in this method to a different url such as '/foo'
                // .logoutSuccessUrl("/afterlogout.html");  //default reverts to '/login'  otherwise set it here

        http
                .sessionManagement()
                .maximumSessions(2);  //maximum count of sessions
    }
}