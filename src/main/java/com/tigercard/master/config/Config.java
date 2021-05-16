package com.tigercard.master.config;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class Config extends CachingConfigurerSupport {

    @Bean
    public net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration fifteenMinCacheConfig = new CacheConfiguration();
        fifteenMinCacheConfig.setName("fifteen-min-cache");
        fifteenMinCacheConfig.setMemoryStoreEvictionPolicy("LRU");
        fifteenMinCacheConfig.setMaxEntriesLocalHeap(500);
        fifteenMinCacheConfig.setTimeToLiveSeconds(900);

        net.sf.ehcache.config.Configuration ehCacheConfig = new net.sf.ehcache.config.Configuration();
        ehCacheConfig.addCache(fifteenMinCacheConfig);

        return net.sf.ehcache.CacheManager.newInstance(ehCacheConfig);
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }
}
