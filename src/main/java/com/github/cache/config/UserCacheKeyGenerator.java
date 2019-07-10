package com.github.cache.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * <p>
 * 创建时间为 19:02 2019-07-07
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class UserCacheKeyGenerator {

    @Bean("DefaultGenerator")
    public KeyGenerator getDefaultGenerator() {
        System.out.println("getDefaultGenerator");
        return (target, method, params) -> params;
    }

    @Bean("UserKeyGenerator")
    public KeyGenerator getKeyGenerator() {
        System.out.println("getKeyGenerator");
        return (target, method, params) -> method.getName() + ":" + Arrays.asList(params).toString();
    }

}
