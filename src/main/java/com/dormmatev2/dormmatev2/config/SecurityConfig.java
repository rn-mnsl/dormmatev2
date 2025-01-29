package com.dormmatev2.dormmatev2.config;

import com.dormmatev2.dormmatev2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.dormmatev2.dormmatev2.model.User;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
     @Autowired
        private UserRepository userRepository;
     @Bean
       public UserDetailsService userDetailsService(UserRepository userRepository) {
         return username -> {
           User user = userRepository.findByUsername(username);
            if (user != null) {
              return new org.springframework.security.core.userdetails.User(
                  user.getUsername(),
                   user.getPassword(),
                   AuthorityUtils.createAuthorityList(user.getRole())
                );
            } else {
              throw new UsernameNotFoundException("User not found: " + username);
           }
      };
  }
        @Bean
         public AuthenticationProvider authenticationProvider(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(userDetailsService);
            authenticationProvider.setPasswordEncoder(passwordEncoder);
            return authenticationProvider;
      }
      @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
           return authConfig.getAuthenticationManager();
      }
       @Bean
      public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
      }
      @Bean
       public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
               .authorizeHttpRequests(auth -> auth
                   .requestMatchers("/**").permitAll()
                 .anyRequest().authenticated()
            )
               .httpBasic(withDefaults());
           return http.build();
       }
}
       