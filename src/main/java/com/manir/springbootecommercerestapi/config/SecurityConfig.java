package com.manir.springbootecommercerestapi.config;

import com.manir.springbootecommercerestapi.security.CustomUserDetailsService;
import com.manir.springbootecommercerestapi.security.JwtAuthenticationEntryPoint;
import com.manir.springbootecommercerestapi.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
/***
    global security is used for enable security at method level for example permitting get methods
    Ex: PreAuthorize("hasRole('ADMIN')")
 ***/
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                /*-------------------------JWT Starts------------------------------*/
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                /*-------------------------JWT ends------------------------------*/
                .and()
                .authorizeRequests()
                //to permit all get request and secure post put and delete methods
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                //authorize singIn and signUp
                .antMatchers("/api/v1/auth/**").permitAll()
                .anyRequest()
                .authenticated();

                /**
                  Basic auth used before JWT implementation
                 .and()
                .httpBasic();
                 **/
        http
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    //Jwt auth filter method
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    //In memory Auth
    /**
        @Override
        @Bean
        protected UserDetailsService userDetailsService() {
            UserDetails user =  User.builder().username("customer").password(passwordEncoder().encode("customer")).roles("USER").build();
            UserDetails admin =  User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();

            return new InMemoryUserDetailsManager(user, admin);
        }
    **/

    //DB Auth
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //User authentication manager bean
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
