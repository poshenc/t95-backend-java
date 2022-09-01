package com.t95.t95backend.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.t95.t95backend.entity.UserAccount;
import com.t95.t95backend.repository.UserRepository;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return Args -> {
            UserAccount johnson = new UserAccount(
                    "Johnson",
                    "Chen",
                    "johnson@gmail.com",
                    27
            );
            UserAccount albert = new UserAccount(
                    "Albert",
                    "Leung",
                    "leung@gmail.com",
                    28
            );
            UserAccount till = new UserAccount(
                    "Till",
                    "Lin",
                    "ktl@gmail.com",
                    29
            );
            userRepository.saveAll(List.of(johnson, albert, till));
        };
    }
}
