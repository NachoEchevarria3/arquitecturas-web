package com.app.apigateway.config;

import com.app.apigateway.config.filter.JwtFilter;
import com.app.apigateway.constant.RolEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
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
                        .requestMatchers(HttpMethod.POST, "/api/parada/**").hasAuthority(RolEnum.ADMINISTRADOR.toString())
                        .requestMatchers(HttpMethod.DELETE, "/api/parada/**").hasAuthority(RolEnum.ADMINISTRADOR.toString())
                        .requestMatchers(HttpMethod.GET, "/api/parada/**").authenticated()

                        // Tarifas
                        .requestMatchers(HttpMethod.GET, "api/tarifa/actual").authenticated()
                        .requestMatchers("/api/tarifa/**").hasAuthority(RolEnum.ADMINISTRADOR.toString())

                        // Pagos
                        .requestMatchers("/api/pago/total-facturado").hasAuthority(RolEnum.ADMINISTRADOR.toString())
                        .requestMatchers(HttpMethod.POST, "/api/pago").hasAuthority(RolEnum.USUARIO.toString())

                        // Mantenimiento
                        .requestMatchers("/api/mantenimiento/**").hasAuthority(RolEnum.ENCARGADO_MANTENIMIENTO.toString())

                        // Monopatines
                        .requestMatchers("/api/monopatin/reporte").hasAuthority(RolEnum.ENCARGADO_MANTENIMIENTO.toString())
                        .requestMatchers("/api/monopatin/minimo-viajes/**").hasAuthority(RolEnum.ADMINISTRADOR.toString())
                        .requestMatchers("/api/monopatin/disponibles-vs-mantenimiento").hasAuthority(RolEnum.ADMINISTRADOR.toString())
                        .requestMatchers(HttpMethod.POST, "/api/monopatin/**").hasAuthority(RolEnum.ADMINISTRADOR.toString())
                        .requestMatchers(HttpMethod.DELETE, "/api/monopatin/**").hasAuthority(RolEnum.ADMINISTRADOR.toString())
                        .requestMatchers("/api/monopatin/*/reset-estadisticas").hasAuthority(RolEnum.ENCARGADO_MANTENIMIENTO.toString())
                        .requestMatchers("api/monopatin/*/ubicar-en-parada/**").authenticated()
                        .requestMatchers("/api/monopatin/parada/**").authenticated()
                        .requestMatchers("/api/monopatin/ubicacion/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/monopatin/**").authenticated()

                        // Viajes
                        .requestMatchers("/api/viaje/monopatines/minimo-viajes/**").hasAuthority(RolEnum.ADMINISTRADOR.toString())
                        .requestMatchers("/api/viaje/**").hasAuthority(RolEnum.USUARIO.toString())

                        // Autenticación
                        .requestMatchers("/api/auth/**").permitAll()

                        // Cuentas
                        .requestMatchers("/api/usuario/*/anular").hasAuthority(RolEnum.ADMINISTRADOR.toString())
                        .requestMatchers("/api/usuario").hasAuthority(RolEnum.ADMINISTRADOR.toString())

                        // Mercado pago
                        .requestMatchers("/api/mercado-pago/*/cargar-saldo/**").hasAuthority(RolEnum.USUARIO.toString())

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
