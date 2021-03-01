package com.marvel.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(value = "ehcache.enabled", havingValue = "true")
@Configuration
@EnableCaching
public class CachingConfig {
  public static final String REGION_CHARATER_IDS = "characterIds";
}

