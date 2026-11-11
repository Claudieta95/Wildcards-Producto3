
package com.example.helloworld.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers(
                                "/login",
                                "/registro",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/favicon.ico"
                        ).permitAll()

                        // Administración: solo ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Zona de usuario: USER y ADMIN
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")

                        // Bloquear el resto de rutas
                        .anyRequest().denyAll()
                )

                // Formulario de inicio de sesión
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/user/inicio", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )

                // Cierre de sesión
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
