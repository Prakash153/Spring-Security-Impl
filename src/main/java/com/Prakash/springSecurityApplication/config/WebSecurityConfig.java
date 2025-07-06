package com.Prakash.springSecurityApplication.config;

import com.Prakash.springSecurityApplication.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthFilter  jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // session management impt.
                .authorizeHttpRequests(auth ->
                        auth
                                // through this route with /posts will not be authenticated
                                // by adding stars ** we can make all the routes public that comes under posts

                                // anyone can go to /posts
                                .requestMatchers("/posts","/auth/**").permitAll()
                                // Through hasRole we can define that if you are admin then only you can access posts route
                              //  only admins can visit routes under posts
//                                .requestMatchers("/posts/**").authenticated()/
                                // through this method we are authenticating each routes
                                .anyRequest().authenticated());

        // we won't be using form login instead we would now use JWT token
//                .formLogin(Customizer.withDefaults());


        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
      return   config.getAuthenticationManager();
    }

    // this is bean is basically like adding users manually just like adding in application.properties
    // instead of this we will use UserService repository for this purpose

    // Adding multiple users to our memory through bean
//    @Bean
//    UserDetailsService inMemoryUserDetailsService(){
//        UserDetails normalUser = User
//                .withUsername("Prakash")
//                .password(passwordEncoder().encode("Prakash123"))
//                .roles("USER")
//                .build();
//        UserDetails adminUser = User
//                .withUsername("admin")
//                .password(passwordEncoder().encode("admin123"))
//                .roles("ADMIN")
//                .build();
//
//        // registering new users in the memory
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }


}
