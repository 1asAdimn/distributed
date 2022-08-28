package com.example.springsecurity.config;

import com.example.springsecurity.exception.MyAccessDeniedHandler;
import com.example.springsecurity.security.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/showLogin")
                .loginProcessingUrl("/login")
                //当发现/login时认为是登录，需要执行UserDetailsServiceImpl
//                .successForwardUrl("/showMain")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.sendRedirect("/showMain");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        response.sendRedirect("/showFail");
                    }
                });
//                        .failureForwardUrl("/showFail");

        //此处是post请求
        http.authorizeRequests()
                .antMatchers("/showLogin", "/showFail").permitAll() //login.html不需要被认证
                .antMatchers("/js/**").permitAll()
                .antMatchers("/demo").permitAll()
//                .antMatchers("/abc").denyAll()
//                .antMatchers("/authority").hasAnyAuthority("demo:update")
//                .antMatchers("/role").hasRole("admin3ff")
                .antMatchers("/role").access("@myServiceImpl.hasPermission(request,authentication)")
                .anyRequest().authenticated();//所有的请求都必须被认证。必须登录后才能访问。

        http.exceptionHandling()
//                .accessDeniedHandler(myAccessDeniedHandler);
                .accessDeniedPage("/showAccessDenied");


        http.logout()
                .logoutSuccessUrl("/showLogin");

        http.rememberMe()
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(120)
                .tokenRepository(persistentTokenRepository);
        //关闭csrf防护
        http.csrf().disable();
    }
}
