package org.example.test1.config;

import org.example.test1.config.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan("org.example.test1")

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя
    @Autowired
    private LoginSuccessHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/admin").hasAnyRole("ADMIN")
                .antMatchers("/hello").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(successUserHandler)
                .failureUrl("/login?error=true")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/exception");

                http.logout()
                        .permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        // указываем URL при удачном логауте
                        .logoutSuccessUrl("/login?login")
                        .and().csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}