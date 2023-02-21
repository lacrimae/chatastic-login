package io.chatasticauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
//        // temporary disables login page and web security
//        security.csrf().disable().authorizeHttpRequests()
//                .requestMatchers("/swagger", "/swagger-ui/index.html").permitAll();
////                .and()
////                .httpBasic()
////                .disable();
//        return security.build();
//    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
