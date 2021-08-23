package br.com.temvaga.config;

import br.com.temvaga.security.AuthenticationFilter;
import br.com.temvaga.security.AuthorizationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static br.com.temvaga.constants.SecurityConstants.SIGN_UP_URL;
import static br.com.temvaga.constants.SecurityConstants.AUTH_LIST;
import static br.com.temvaga.constants.SecurityConstants.URLS;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, URLS).permitAll()
                .antMatchers(AUTH_LIST).authenticated()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager()))
                .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }



    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration conf  = new CorsConfiguration().applyPermitDefaultValues();
        conf.setAllowedOriginPatterns(Collections.singletonList("https://temvaga.vercel.app"));
        conf.setAllowedOrigins(Collections.singletonList("https://temvaga.vercel.app"));
        conf.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        conf.setExposedHeaders(Arrays.asList("Authorization", "content-type"));
        conf.setExposedHeaders(Arrays.asList("token", "content-type"));
        conf.setAllowedHeaders(Arrays.asList("Authorization", "content-type"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", conf);
        return source;
    }
}
