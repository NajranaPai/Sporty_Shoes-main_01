package com.sporty_shoes.configuration;

import com.sporty_shoes.service.CustomUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;

    @Autowired
    private CustomUserDetailService customUserDetailService;
 
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                      .authorizeRequests()
                      .antMatchers("/","/shop/**","/register").permitAll()
                      .antMatchers("/admin/**").hasRole("ADMIN")
                      .anyRequest()
                      .authenticated()
                      .and()
                      .formLogin()
                      .loginPage("/login")
                      .permitAll()
                      .failureUrl("/login?error=true")
                      .defaultSuccessUrl("/")
                      .usernameParameter("email")
                      .passwordParameter("password")
                      .and()
                      .oauth2Login()
                      .loginPage("/login")
                      .successHandler(googleOAuth2SuccessHandler)
                      .and()
                      .logout()
                      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                      .logoutSuccessUrl("/login")
                      .invalidateHttpSession(true)
                      .deleteCookies("JSESSIONID")
                      .and()
                      .exceptionHandling()
                      .and()
                      .csrf().disable();

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
            auth.userDetailsService(customUserDetailService);
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception{
        webSecurity.ignoring().antMatchers("/resources/**","/static/**","/images/**"
        ,"/productImages/**","/js/**","/css/**");
    }

}