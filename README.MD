# 一.Spring Cache
## 1.概述
Spring框架支持透明地向应用程序添加缓存.抽象的核心是将缓存应用于方法,从而根据缓存中可用的信息减少数据库访问的次数.
缓存逻辑是透明地应用的,不会对调用程序造成任何干扰.
只要通过@EnableCaching注释启用缓存支持,Spring Boot就会自动配置缓存基础设施.
## 2.支持的缓存
缓存抽象不提供实际的存储，而是依赖于org.springframework.cache 和 org.springframework.cache.CacheManager 接口
* Generic
* JCache (JSR-107) (EhCache 3, Hazelcast, Infinispan, and others)
* EhCache 2.x
* Hazelcast
* Infinispan
* Couchbase
* Redis
* Caffeine
* Simple


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

# 四.几个重要参数:
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


# 五.缓存SpEL可用元数据
序号|名称|位置|说明|举例
:--:|:--|:--|:--|:--
01|methodName|Root object|当前被调用的方法名|#root.methodName
02|method|Root object|当前被调用的方法|#root.method.name
03|target|Root object|当前被调用的目标对象|#root.target
04|targetClass|Root object|当前被调用的目标对象类|#root.targetClass
05|args|Root object|当前被调用的方法的参数列表|#root.args[0]
06|caches|Root object|当前方法调用使用的缓存列表(如@Cacheable(value={“cache1”, “cache2”}))，则有两个cache|#root.caches[0].name
07|Argument name|Evaluation context|当前被调用的方法的参数，如findById(Long id)，我们可以通过#id拿到参数|#iban or #a0 (you can also use #p0 or #p<#arg> notation as an alias).
08|result|Evaluation context|方法执行后的返回值(仅当方法执行之后的判断有效,如‘unless','cache evict'的beforeInvocation=false)|#result





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
1.方法运行事前, 先去查询 Cache(缓存组件),按照 cacheNames 指定的名字获取:
(CacheManager 先获取相应的缓存),第一次获取缓存如果没有 Cache 组件会自动创建.
2.去 cache 中查找缓存的内容,使用一个 key ,默认就是方法的参数:
key 是按照某种策略生成的,默认使用的 keyGenerator生成的,默认使用 SimpleKeyGenerator 生成 key
SimpleKeyGenerator 生成 key 的默认策略:
如果没有参数, key = new SimpleKey()
如果只有一个参数, key = 参数的值
如果有多个参数, key = new SimpleKey(params)
3.没有查询到缓存就调用目标方法:
4.将目标方法返回的结果放进缓存中.
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
运行时机:
1.先调用目标方法;
2.将目标方法的结果缓存起来;
```
@CachePut(key = "#result.data.name")
public ReturnDO<UserDetailDO> update(UserDetailDO userCacheDTO) {
    UserDetailDO userDetailDO = repository.save(userCacheDTO);
    return ReturnDO.<UserDetailDO>builder().data(userDetailDO).build();
}
```
## 5.使用 @CacheEvict
@CacheEvict 的作用是根据一定的条件对缓存进行清空(DELETE),该方法也是每次调用均会执行,删除数据同时删除缓存中的数据
key:指定要清除的数据
allEntries = true:指定清除这个缓存中所有的数据
beforeInvocation = true: 缓存的清除是否在方法之前执行
默认代表缓存清除操作是在方法执行之后执行:如果出现异常缓存就不会清除
beforeInvocation = false: 缓存的清除是否在方法之后执行
无论方法是否出现异常,缓存都清除
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
@Caching 复杂缓存配置
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














