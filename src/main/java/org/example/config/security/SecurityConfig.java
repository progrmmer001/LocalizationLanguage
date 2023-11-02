package org.example.config.security;

import org.example.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {
    private final String[] WHITE_PAGE = {"/index", "/auth/login", "/auth/signIn", "/auth/sign","/home","/image/.."};
    private UserService userService;
    private CustomAuthentication authentication;

    public SecurityConfig(UserService userService, CustomAuthentication authentication) {
        this.userService = userService;
        this.authentication = authentication;
    }

    @Bean
    public SecurityFilterChain security(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf().disable();
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers(WHITE_PAGE)
                .permitAll()
                .anyRequest()
                .authenticated();
        httpSecurity.userDetailsService(userService);
        httpSecurity.formLogin()
                .loginPage("/auth/login")
                .usernameParameter("uname")
                .passwordParameter("pswd")
                .defaultSuccessUrl("/index", true)
                .failureHandler(authentication);
        httpSecurity
                .logout()
                .deleteCookies("JSESSION", "JSESSIONID")
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"));
        httpSecurity.rememberMe()
                .rememberMeParameter("rememberMe")
                .rememberMeCookieName("rememberMe")
                .tokenValiditySeconds(24 * 60 * 60)
                .key("secretKey")
                .alwaysRemember(true)
                .userDetailsService(userService);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails build = User.withDefaultPasswordEncoder().username("abbos").password("1234").roles("ADMIN").build();
//        UserDetails build1 = User.withDefaultPasswordEncoder().username("Jasmina").password("12345").roles("USER").build();
//        return new InMemoryUserDetailsManager(build, build1);
//    }
}
