package com.test.first.conf;

import com.test.first.filter.JwtAuthenticationFilter;
import com.test.first.filter.LoginFilter;
import com.test.first.handler.FailuerHandler;
import com.test.first.handler.LogoutHandler;
import com.test.first.handler.SuccessHandler;
import com.test.first.service.UserDetailService;
import com.test.first.service.imp.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SuccessHandler successHandler;

    @Autowired
    FailuerHandler failuerHandler;

    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    LogoutHandler logoutHandler;

    /**
     * 注入 LoginFilter 时候需要，注入 authenticationManager
     */
    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationManager(authenticationManager());
        return loginFilter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new UserDetailService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/login")
//                .permitAll()
//                .successHandler(successHandler)
//                .failureHandler(failuerHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/guest/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .anyRequest()
                .authenticated()
//                .and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutHandler)
                .and()
                .addFilter(loginFilter())
                .addFilter(jwtAuthenticationFilter());

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
//                    .authenticationProvider(authenticationProvider())
                    .userDetailsService(userDetailsService())
                    .passwordEncoder(passwordEncoder());


//        // 在内存中配置2个用户
//        auth.inMemoryAuthentication()
//                .withUser("user").password("123456").roles("user");// 密码不加密
//        System.out.println("auth");
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("$2a$10$fB2UU8iJmXsjpdk6T6hGMup8uNcJnOGwo2.QGR.e3qjIsdPYaS4LO").roles("admin")
//                .and()
//                .withUser("user").password("$2a$10$3TQ2HO/Xz1bVHw5nlfYTBON2TDJsQ0FMDwAS81uh7D.i9ax5DR46q").roles("user");// 密码加密
     }
}
