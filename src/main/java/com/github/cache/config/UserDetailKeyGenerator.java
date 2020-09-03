package com.github.cache.config;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Joiner.on(",").skipNulls().join(s)
 *
 * <p>
 * 创建时间为 19:02 2019-07-07
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
@Configuration
public class UserDetailKeyGenerator {

    /**
     * 创建默认
     *
     * @return KeyGenerator
     */
    @Bean("DefaultGenerator")
    public KeyGenerator defaultGenerator() {
        log.info("创建 DefaultGenerator!");
        return (target, method, params) -> Arrays.asList(params).toString();
    }


    /**
     * 创建默认
     *
     * @return KeyGenerator
     */
    @Bean("RedisGenerator")
    public KeyGenerator redisGenerator() {
        log.info("创建 DefaultGenerator!");
        return (target, method, params) -> Joiner.on(":").skipNulls().join(params);
    }


    /**
     * UserKeyGenerator
     *
     * @return KeyGenerator
     */
    @Bean("UserKeyGenerator")
    public KeyGenerator keyGenerator() {
        log.info("创建 UserKeyGenerator!");
        return (target, method, params) -> method.getName() + ":" + Arrays.asList(params).toString();
    }

}
