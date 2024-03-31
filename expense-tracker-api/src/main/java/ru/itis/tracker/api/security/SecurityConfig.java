package ru.itis.tracker.api.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.itis.tracker.api.security.filter.AuthenticationFilter;
import ru.itis.tracker.api.security.filter.AuthorizationFilter;
import ru.itis.tracker.api.security.filter.CustomLogoutFilter;

import static ru.itis.tracker.api.security.SecurityConstants.AUTHENTICATION_PATH;
import static ru.itis.tracker.api.security.SecurityConstants.LOGOUT_PATH;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsServiceImpl;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http,
                                              AuthenticationFilter authenticationFilter,
                                              AuthorizationFilter authorizationFilter,
                                              CustomLogoutFilter logoutFilter) throws Exception {

        authenticationFilter.setFilterProcessesUrl(AUTHENTICATION_PATH);

        return http
                .csrf(AbstractHttpConfigurer::disable)

                .addFilter(authenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(logoutFilter, LogoutFilter.class)

                .exceptionHandling(configurer ->
                        configurer
                                .authenticationEntryPoint((request, response, authException) -> {
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                }))

                .sessionManagement(configurer ->
                        configurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/user", HttpMethod.POST.name())).permitAll()
                                .requestMatchers(AUTHENTICATION_PATH).permitAll()
                                .requestMatchers(LOGOUT_PATH).authenticated()
                                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                                .requestMatchers("/**").authenticated()
                                .anyRequest().permitAll())

                .logout(configurer ->
                        configurer
                                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_PATH, HttpMethod.POST.name()))
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))

                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/resources/static/**")
                .requestMatchers("/resources/templates/**");
    }

    @Autowired
    public void daoAuthenticationProvider(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);

        authenticationManagerBuilder
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        };
    }

}
