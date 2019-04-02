package main;

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

    //private RestAuthenticationEntryPoint restAuthenticationEntryPoint = new RestAuthenticationEntryPoint();

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
                auth.inMemoryAuthentication()
                .withUser("admin").password(encoder().encode("admin")).roles("ADMIN")
                .and()
                .withUser("user").password(encoder().encode("user")).roles("USER");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().disable()
                //.exceptionHandling()
                //.authenticationEntryPoint(restAuthenticationEntryPoint)
                //.and()
                .authorizeRequests()
                .antMatchers("/anonymous*").anonymous()
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated()
                //.antMatchers("/log.html").authenticated()
                .antMatchers("/index.html").hasRole("ADMIN")
                .and()
                .formLogin()
                //.loginPage("/login.html")
                //.loginProcessingUrl("/login")
                .successHandler(myAuthenticationSuccessHandler())
                //.failureHandler(myFailureHandler)  //add a failure class to implement or simply allow a re-direct in the successhandler?
                .and()
                .logout();
                // .logoutSuccessUrl("/afterlogout.html");  //default reverts to '/'  otherwise set it here

        http
                .sessionManagement()
                .maximumSessions(2);  //maximum count of sessions
    }
}