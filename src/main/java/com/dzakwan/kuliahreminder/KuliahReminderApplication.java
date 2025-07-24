package com.dzakwan.kuliahreminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan("com.dzakwan.kuliahreminder.model")
@EnableJpaRepositories("com.dzakwan.kuliahreminder.repository")
public class KuliahReminderApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuliahReminderApplication.class, args);
    }
}
