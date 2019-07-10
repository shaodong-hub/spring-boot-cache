package com.github.cache.provider;

import com.github.cache.pojo.UserDetailDO;
import com.github.cache.repository.IUserDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * <p>
 * 创建时间为 15:26 2019-07-10
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
@Service
public class DataProvider {

    @Resource
    private IUserDetailRepository repository;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void init() {
        redisTemplate.delete(redisTemplate.keys("*"));
        repository.deleteAll();
        for (int i = 0; i < 12; i++) {
            UserDetailDO userDetailDO = UserDetailDO.builder().name("name" + i).phone(1000 + i + "").build();
            repository.save(userDetailDO);
        }
    }

}
