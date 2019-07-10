

几个重要概念:
Cache 缓存接口，定义缓存操作。实现有：RedisCache、EhCacheCache、ConcurrentMapCache等

CacheManager 缓存管理器，管理各种缓存（cache）组件

keyGenerator 缓存数据时key生成策略

serialize 缓存数据时value序列化策略

注解:

@EnableCaching 开启基于注解的缓存

@Cacheable 主要针对方法配置，能够根据方法的请求参数对其进行缓存

@CachePut 保证方法被调用，又希望结果被缓存。与@Cacheable区别在于是否每次都调用方法，常用于更新.

@CacheEvict 清空缓存

@CacheConfig 统一配置本类的缓存注解的属性

@Caching 混合注解 



@Cacheable/@CachePut/@CacheEvict 主要的参数






















