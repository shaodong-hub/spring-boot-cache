# 一.Spring Cache




# 二.几个重要概念:

序号|名词|说明
:--:|:--|:--
01|Cache| 缓存接口，定义缓存操作。实现有：RedisCache、EhCacheCache、ConcurrentMapCache等
02|CacheManager| 缓存管理器，管理各种缓存（cache）组件
03|keyGenerator| 缓存数据时key生成策略
04|serialize| 缓存数据时value序列化策略

# 三.几个重要注解:

序号|注解|说明
:--:|:--|:--
01|@EnableCaching| 开启基于注解的缓存
02|@Cacheable| 主要针对方法配置，能够根据方法的请求参数对其进行缓存
03|@CachePut| 保证方法被调用，又希望结果被缓存。与@Cacheable区别在于是否每次都调用方法，常用于更新.
04|@CacheEvict| 清空缓存
05|@CacheConfig| 统一配置本类的缓存注解的属性
06|@Caching| 混合注解 

# 四.主要的参数:
@Cacheable/@CachePut/@CacheEvict 主要的参数

序号|参数|说明
:--:|:--|:--
01|value|cacheNames的别名.即缓存的名称
02|cacheNames|同value
03|key|缓存的key,支持SPEL表达式.默认为""
04|keyGenerator|key生成器,默认使用SimpleKeyGenerator,即根据Key数组生成一个字符串
05|cacheManager|cache管理器
06|cacheResolver|自定义CacheResolver的bean名称
07|condition|满足某个条件时执行,支持SPEL表达式
08|unless|满足某个条件时不执行,支持SPEL表达式.否定缓存, 当条件为 true 时, 就不会缓存.
09|sync|是否异步执行
10|allEntries|@CacheEvict独有,是否清空所有的缓存
11|beforeInvocation|@CacheEvict独有,是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存


# 五.SPEL支持的参数
序号|参数|说明
:--:|:--|:--


# 六.SpringBoot使用
## 1.加入依赖
使用之前需要加入以下依赖:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

## 2.开启自动配置
开启缓存
```
@EnableCaching
@SpringBootApplication
public class SpringBootCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootCacheApplication.class, args);
    }
}
```

## 3.使用 @Cacheable
使用 @Cacheable 注解后, 调用 findByName 会先从缓存中查找是否有对应的数据, 如果没有才会去数据库查找, 并将查找的结果放入缓存中.如果在缓存中查询到数据则不会访问方法体重的代码.
```
@Cacheable
public ReturnDO<UserDetailDO> findByName(String name) {
    log.info("UserDetailService|findByName|{}", name);
    UserDetailDO userDetailDO = repository.findUserCacheDOByNameIs(name);
    return ReturnDO.<UserDetailDO>builder().data(userDetailDO).build();
}
```

## 4.使用 @CachePut
被标注@CachePut的方法,每次方法被调用均会执行目标方法,并对请求参数对其结果进行缓存,这个注解主要用于新增数据和更新数据上(POST & PUT).
这里的KEY & VALUE必须和 @Cacheable KEY & VALUE 相同.
```
@CachePut(key = "#result.data.name")
public ReturnDO<UserDetailDO> update(UserDetailDO userCacheDTO) {
    UserDetailDO userDetailDO = repository.save(userCacheDTO);
    return ReturnDO.<UserDetailDO>builder().data(userDetailDO).build();
}
```
## 5.使用 @CacheEvict
@CacheEvict 的作用是根据一定的条件对缓存进行清空(DELETE),该方法也是每次调用均会执行,删除数据同时删除缓存中的数据
```
@CacheEvict
public ResponseEntity<Void> delete(String name) {
    repository.deleteUserCacheDOByNameIs(name);
    return new ResponseEntity<>(HttpStatus.OK);
}
```
## 6.使用 @CacheConfig
方法统一注解,一般注解到类上,表示该类方法中相同的注解配置使用值(统一设置配置).
```
@Slf4j
@Service
@CacheConfig(cacheNames = "redis_cache")
public class UserDetailService {
```
## 7.使用 @Caching
```
@Caching(
       cacheable = {
            @Cacheable(key = "#a0.name")
       },
       put = {
            @CachePut(key = "#result.data.name"),
            @CachePut(key = "#result.data.phone")
       }
)
public ReturnDO<UserDetailDO> create(UserDetailDO userCacheDTO) {
    UserDetailDO userDetailDO = repository.save(userCacheDTO);
    return ReturnDO.<UserDetailDO>builder().data(userDetailDO).build();
}
```

# 七.整合 Redis


# 八.个性化Cache
## 1.自定义 KeyGenerator

## 2.自定义 RedisCacheManager 序列化方式

## 3.自定义缓存组件














