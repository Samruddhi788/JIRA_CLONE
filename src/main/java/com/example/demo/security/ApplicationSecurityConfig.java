package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
//modern method to work on the same and deal with the same 

@Configuration
public class ApplicationSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
        .csrf(csrf -> csrf.disable())
       
			.authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
				.requestMatchers("/users").hasRole("USER")
                .requestMatchers(HttpMethod.DELETE, "/delete/**").hasRole("ADMIN")
                .requestMatchers("/").hasRole("ADMIN")
                .anyRequest().authenticated()
                
			)
        
			.httpBasic(org.springframework.security.config.Customizer.withDefaults());
		return http.build();
	}
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //so we are making users with spring so that they are in memory and not in database
    //so that we can easily work and deal with the same
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("user123"))
            .roles("USER")
            .build();

        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin123"))
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(user,admin);
    }
}