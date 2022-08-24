package com.example.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class RedisApplicationTests {

    @Test
    void contextLoads() {
//        Jedis jedis = new Jedis("59.110.233.202",7001);
//        jedis.auth("root");
//        String key = jedis.get("key");
//        System.out.println(key);
        Set<HostAndPort> set = new HashSet<>();
        set.add(new HostAndPort("59.110.233.202",7001));
        set.add(new HostAndPort("59.110.233.202",7002));
        set.add(new HostAndPort("59.110.233.202",7003));
        set.add(new HostAndPort("59.110.233.202",7004));
        set.add(new HostAndPort("59.110.233.202",7005));
        set.add(new HostAndPort("59.110.233.202",7006));
        JedisCluster jedisCluster = new JedisCluster(set);
        jedisCluster.set("name","bjmsb");
        String value = jedisCluster.get("name");
        System.out.println(value);
    }

}
