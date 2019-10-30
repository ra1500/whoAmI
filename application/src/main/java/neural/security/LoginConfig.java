package neural.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class LoginConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationProvider customAuthProvider;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthProvider);
    }

    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public PasswordEncoder encoder() {return new BCryptPasswordEncoder();}

    // CORS bean
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        //final CorsConfiguration config = new CorsConfiguration();
        //config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    // CORS filter.
    //@Bean
    //public CorsFilter corsFilter() {
    //    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //    final CorsConfiguration config = new CorsConfiguration();
    //    config.setAllowCredentials(true);
    //    config.setAllowedOrigins(Collections.singletonList("*"));
    //    config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
    //    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
    //    source.registerCorsConfiguration("/**", config);
    //    return new CorsFilter(source);
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // cross-site request forgery. disabled for now. need to generate a code to feed into the html login form.
                .authorizeRequests()
                .antMatchers("/user/*").permitAll()
                .antMatchers("/max").permitAll() //max # questions in QuestionSet
                .antMatchers("/us/scores*").permitAll()
                //.antMatchers("/**").permitAll() // gives all access without authentication
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint); //JSON response instead of full 401 error.
        //http
        //        .addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class); //TODO
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http
                .cors(); // for CORS obviously

    }

    @Component
    public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

        //@Override
        public void commence (HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
                throws IOException, ServletException {
            response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = response.getWriter();
            writer.println("HTTP Status 401 - " + authEx.getMessage());
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            setRealmName("NeuralJuice");
            super.afterPropertiesSet();
        }

    }

    public class CustomFilter extends GenericFilterBean {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            chain.doFilter(request, response);
        }
    }
}