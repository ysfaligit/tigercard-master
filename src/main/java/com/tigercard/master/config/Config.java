package com.tigercard.master.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

@Configuration
@EnableCaching
@EnableSwagger2
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

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo()).select().paths(postPaths()).build();
    }

    private Predicate<String> postPaths() {
        return Predicates.or(regex("/trip.*"),
                regex("/capping.*"),
                regex("/rate.*"),
                regex("/riderules.*"),
                regex("/card.*"),
                regex("/zone.*")
                );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Tiger Card ")
                .description("Tiger Card API records daily trip of user & calculates Daily & Weekly fares.")
                .version("1.0").build();
    }
}
