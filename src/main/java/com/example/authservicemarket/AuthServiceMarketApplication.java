package com.example.authservicemarket;

import com.example.authservicemarket.entry.MarketUser;
import com.example.authservicemarket.repository.MarketUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Log4j2
public class AuthServiceMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceMarketApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(MarketUserRepository repository, PasswordEncoder encoder){
		return (args) -> {
			MarketUser admin = new MarketUser();
			admin.setId("admin");
			admin.setPassword(encoder.encode("1234"));
			admin.setAuthorities(List.of("USER", "ADMIN", "SELLER"));
		};
	}

}
