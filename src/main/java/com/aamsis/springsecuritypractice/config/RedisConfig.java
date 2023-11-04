package com.aamsis.springsecuritypractice.config;

import com.aamsis.springsecuritypractice.cache.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
  @Value("${application.redis.host}")
  private String redisHost;
  @Value("${application.redis.port}")
  private String redisPort;
  @Value("${application.redis.pass}")
  private String redisPass;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(redisHost, Integer.parseInt(redisPort));
  }

  @Bean
  public RedisTemplate<String, Token> redisTemplate() {
    RedisTemplate<String, Token> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory());
    return template;
  }
}
