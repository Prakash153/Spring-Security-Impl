package com.Prakash.springSecurityApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{

        httpSecurity



                .authorizeHttpRequests(auth ->
                        auth
                                // through this route with /posts will not be authenticated
                                // by adding stars ** we can make all the routes public that comes under posts

                                // anyone can go to /posts
                                .requestMatchers("/posts").permitAll()
                                // Through hasRole we can define that if you are admin then only you can access posts route
                              //  only admins can visit routes under posts
                                .requestMatchers("/posts/**").hasAnyRole("ADMIN")
                                // through this method we are authenticating each routes
                                .anyRequest().authenticated());
        // we won't be using form login instead we would now use JWT token 
//                .formLogin(Customizer.withDefaults());


        return httpSecurity.build();
    }

    // this is bean is basically like adding users manually just like adding in application.properties
    // instead of this we will use UserService repository for this purpose

    // Adding multiple users to our memory through bean
    @Bean
    UserDetailsService inMemoryUserDetailsService(){
        UserDetails normalUser = User
                .withUsername("Prakash")
                .password(passwordEncoder().encode("Prakash123"))
                .roles("USER")
                .build();
        UserDetails adminUser = User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        // registering new users in the memory
        return new InMemoryUserDetailsManager(normalUser, adminUser);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
