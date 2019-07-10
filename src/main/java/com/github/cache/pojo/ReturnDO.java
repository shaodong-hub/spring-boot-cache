package com.github.cache.pojo;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 创建时间为 19:46 2019-07-07
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

@Getter
@Setter
@Builder
public class ReturnDO<T> {

    private T data;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
