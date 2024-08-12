package com.usc.ECom.security;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.usc.ECom.handler.AccessDeniedHandlerImpl;
import com.usc.ECom.handler.AuthenticationEntryPointImpl;
import com.usc.ECom.handler.AuthenticationFailureHandlerImpl;
import com.usc.ECom.handler.AuthenticationSuccessHandlerImpl;
import com.usc.ECom.handler.LogoutSuccessHandlerImpl;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPointImpl;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandlerImpl;

    @Autowired
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;

    @Autowired
    private AuthenticationFailureHandlerImpl authenticationFailureHandlerImpl;

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandlerImpl;

    @Autowired
    private UserDetailsService userDetailsService;// never  used?

    @Bean //???
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder(11);
        return encoder;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors-> cors.configurationSource(corsConfigurationSource())).authorizeRequests()
            .requestMatchers("/index.html").hasAnyRole("ROLE_ADMIN").and()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPointImpl).and().exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandlerImpl).and()
           // in  org.springframework.security.config.annotation.web.builders.HttpSecurity; !
            .formLogin(form ->  form
            		.permitAll().loginProcessingUrl("/login")
                    .successHandler(authenticationSuccessHandlerImpl)
                    .failureHandler(authenticationFailureHandlerImpl)
                    .usernameParameter("username").passwordParameter("password")
                    
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(logoutSuccessHandlerImpl)
                    .permitAll()
            );

        return http.build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // You should only set trusted sites here
        configuration.addAllowedOrigin("http://localhost:4200"); // Only this site can access

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
