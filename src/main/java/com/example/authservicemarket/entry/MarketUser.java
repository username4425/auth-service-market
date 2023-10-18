package com.example.authservicemarket.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("MarketUser")
public class MarketUser implements Serializable {
    private String id, password;
    private List<String> authorities;
    private static final long serialVersionUID = 1L;
}
