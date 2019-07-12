package com.github.cache.repository;

import com.github.cache.pojo.UserDetailDO;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * <p>
 * 创建时间为 18:54 2019-07-07
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */
@Repository
public class IUserDetailRepository {

    private static Map<String, UserDetailDO> mapName = Maps.newHashMap();

    private static Map<String, UserDetailDO> mapPhone = Maps.newHashMap();

    @PostConstruct
    public void init() {
        for (int i = 0; i < 12; i++) {
            String name = "name" + i;
            String phone = 1000 + i + "";
            UserDetailDO userDetailDO = UserDetailDO.builder().name(name).phone(phone).build();
            mapName.put(name, userDetailDO);
            mapPhone.put(phone, userDetailDO);
        }
    }

    /**
     * 根据用户名查询单个用户
     *
     * @param name 用户名
     * @return UserDetailDO
     */
    public UserDetailDO findUserCacheDOByNameEquals(String name) {
        return mapName.get(name);
    }

    /**
     * 根据手机号码查询单个用户
     *
     * @param phone 手机号码
     * @return UserDetailDO
     */
    public UserDetailDO findUserCacheDOByPhoneEquals(String phone) {
        return mapPhone.get(phone);
    }


    public UserDetailDO save(UserDetailDO userCacheDTO) {
        mapName.put(userCacheDTO.getName(), userCacheDTO);
        mapPhone.put(userCacheDTO.getPhone(), userCacheDTO);
        return userCacheDTO;
    }

    /**
     * 删除单个用户
     *
     * @param name 用户名
     */
    public void deleteUserCacheDOByNameIs(String name) {
        mapName.remove(name);
    }

    public void deleteAll() {
        mapName.clear();
        mapPhone.clear();
    }
}