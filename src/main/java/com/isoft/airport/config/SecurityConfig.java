package com.isoft.airport.config;

import com.isoft.airport.models.Passenger;
import com.isoft.airport.models.PassengerDetails;
import com.isoft.airport.repositories.PassengerDetailsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_PASSENGER = "ROLE_PASSENGER";

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/", "/home", "/search", "/search/page")
                .and().authorizeHttpRequests()
                .mvcMatchers("/reserve-flight", "/panel","/admin/**").authenticated()
                .mvcMatchers("/", "/home", "/search/**").permitAll().and()
                .formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/panel")
                .failureUrl("/login?error=true").and().logout().permitAll()
                .logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true);
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Component
    public static class UserPassAuthProvider implements AuthenticationProvider {
        private final PassengerDetailsRepository passengerDetailsRepository;
        private final PasswordEncoder encoder;

        public UserPassAuthProvider(PassengerDetailsRepository passengerDetailsRepository, PasswordEncoder encoder) {
            this.passengerDetailsRepository = passengerDetailsRepository;
            this.encoder = encoder;
        }

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String email = authentication.getName();
            String password = authentication.getCredentials().toString();
            PassengerDetails passengerDetails = passengerDetailsRepository.findByEmailAddress(email);

            if (passengerDetails != null && encoder.matches(password, passengerDetails.getPassword())) {
                Passenger passenger = passengerDetails.getPassenger();
                return new UsernamePasswordAuthenticationToken(passenger.getFirstName() + " " + passenger.getLastName(),
                        null, Collections.singleton(passengerDetails.getRole()));
            }
            return null;
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return UsernamePasswordAuthenticationToken.class.equals(authentication);
        }
    }
}
