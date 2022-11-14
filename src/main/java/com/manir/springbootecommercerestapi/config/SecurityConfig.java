package com.manir.springbootecommercerestapi.config;

import com.manir.springbootecommercerestapi.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.annotation.Resource;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //to permit all get request and secure post put and delete methods
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

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
}
