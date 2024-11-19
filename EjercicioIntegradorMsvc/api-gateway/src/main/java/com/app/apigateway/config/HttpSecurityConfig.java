package com.app.apigateway.config;

import com.app.apigateway.config.filter.JwtFilter;
import com.app.apigateway.constant.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .securityMatcher("/api/**")
                .authorizeHttpRequests(req -> req
                        // Paradas
                        .requestMatchers(HttpMethod.POST, "/api/parada/**").hasRole(Rol.ADMINISTRADOR.toString())
                        .requestMatchers(HttpMethod.DELETE, "/api/parada/**").hasRole(Rol.ADMINISTRADOR.toString())
                        .requestMatchers(HttpMethod.GET, "/api/parada/**").authenticated()

                        // Tarifas
                        .requestMatchers(HttpMethod.GET, "api/tarifa/actual").authenticated()
                        .requestMatchers("/api/tarifa/**").hasRole(Rol.ADMINISTRADOR.toString())

                        // Pagos
                        .requestMatchers("/api/pago/total-facturado").hasRole(Rol.ADMINISTRADOR.toString())
                        .requestMatchers(HttpMethod.POST, "/api/pago").hasRole(Rol.USUARIO.toString())

                        // Mantenimiento
                        .requestMatchers("/api/mantenimiento/**").hasRole(Rol.ENCARGADO_MANTENIMIENTO.toString())

                        // Monopatines
                        .requestMatchers("/api/monopatin/reporte").hasRole(Rol.ENCARGADO_MANTENIMIENTO.toString())
                        .requestMatchers("/api/monopatin/minimo-viajes/**").hasRole(Rol.ADMINISTRADOR.toString())
                        .requestMatchers("/api/monopatin/disponibles-vs-mantenimiento").hasRole(Rol.ADMINISTRADOR.toString())
                        .requestMatchers(HttpMethod.POST, "/api/monopatin/**").hasRole(Rol.ADMINISTRADOR.toString())
                        .requestMatchers(HttpMethod.DELETE, "/api/monopatin/**").hasRole(Rol.ADMINISTRADOR.toString())
                        .requestMatchers("/api/monopatin/*/reset-estadisticas").hasRole(Rol.ENCARGADO_MANTENIMIENTO.toString())
                        .requestMatchers("api/monopatin/*/ubicar-en-parada/**").authenticated()
                        .requestMatchers("/api/monopatin/parada/**").authenticated()
                        .requestMatchers("/api/monopatin/ubicacion/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/monopatin/**").authenticated()

                        // Viajes
                        .requestMatchers("/api/viaje/monopatines/minimo-viajes/**").hasRole(Rol.ADMINISTRADOR.toString())
                        .requestMatchers("/api/viaje/**").hasRole(Rol.USUARIO.toString())

                        // Autenticación
                        .requestMatchers("/api/auth/**").permitAll()

                        // Cuentas
                        .requestMatchers("/api/usuario/*/anular").hasRole(Rol.ADMINISTRADOR.toString())
                        .requestMatchers("/api/usuario").hasRole(Rol.ADMINISTRADOR.toString())

                        // Mercado pago
                        .requestMatchers("/api/mercado-pago/*/cargar-saldo/**").hasRole(Rol.USUARIO.toString())

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
