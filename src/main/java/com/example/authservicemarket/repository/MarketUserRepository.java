package com.example.authservicemarket.repository;

import com.example.authservicemarket.entry.MarketUser;
import org.springframework.data.repository.CrudRepository;

public interface MarketUserRepository extends CrudRepository<MarketUser, String> {
}
