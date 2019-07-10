package com.github.cache.pojo;


import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 创建时间为 18:50 2019-07-07
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

@Setter
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "UserDetailDO")
public class UserDetailDO implements Serializable {

    private static final long serialVersionUID = -8116446033357952120L;

    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    private String name;

    @Indexed
    @NotBlank
    private String phone;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
