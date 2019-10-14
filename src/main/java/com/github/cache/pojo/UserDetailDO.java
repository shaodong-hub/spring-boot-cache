package com.github.cache.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

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

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "user_detail",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})},
        indexes = {
                @Index(name = "index_phone", columnList = "phone"),
                @Index(name = "index_age", columnList = "age")
        }

)
@EntityListeners(AuditingEntityListener.class)
public class UserDetailDO implements Serializable {

    private static final long serialVersionUID = -8116446033357952120L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "username", nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String phone;

    @Range(min = 0, max = 125)
    private Integer age;

    @CreatedDate
    private Date createDate;

    @LastModifiedDate
    private Date lastModifiedDate;

}
