package sn.fr.samagp.configuration.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final  JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf(t -> t.disable());
        http.authorizeHttpRequests(authorize -> {
            authorize
//                    .requestMatchers(HttpMethod.GET, "/restaurant/public/list").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/restaurant/public/menu/*").permitAll()
                    .requestMatchers(
                            "/api/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/actuator/**",
                            "/annonces/**",
                            "/commentaires/**",
                            "/clients/**",
                            "/zones-geo/**"
                    ).permitAll()
                    .anyRequest().authenticated();
        });
        http.oauth2ResourceServer(t-> {
            t.jwt(config -> config.jwtAuthenticationConverter(jwtAuthConverter));
            //t.jwt(Customizer.withDefaults());
            //t.opaqueToken(Customizer.withDefaults());
        });
        http.sessionManagement(
                t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        return http.build();
    }

    @Bean
    public DefaultMethodSecurityExpressionHandler msecurity() {
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setDefaultRolePrefix("");
        return defaultMethodSecurityExpressionHandler;
    }

//    @Bean
//    public JwtAuthenticationConverter con() {
//        JwtAuthenticationConverter c =new JwtAuthenticationConverter();
//        JwtGrantedAuthoritiesConverter cv = new JwtGrantedAuthoritiesConverter();
//        cv.setAuthorityPrefix(""); // Default "SCOPE_"
//        cv.setAuthoritiesClaimName("roles"); // Default "scope" or "scp"
//        c.setJwtGrantedAuthoritiesConverter(cv);
//        return c;
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}