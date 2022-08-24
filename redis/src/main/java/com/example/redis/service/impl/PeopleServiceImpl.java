package com.example.redis.service.impl;

import com.example.redis.mapper.PeopleMapper;
import com.example.redis.pojo.People;
import com.example.redis.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 金喆
 */
@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public People findPeopleById(Integer id) {

        String key = "product:" +id;
        //先从redis中获取数据
        if(redisTemplate.hasKey(key))
        {
            System.out.println("执行缓存");
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<People>(People.class));
            People product = (People) redisTemplate.opsForValue().get(key);
            return product;
        }
        System.out.println("执行mysql");

        People product = peopleMapper.findPeopleById(id);
        redisTemplate.opsForValue().set(key, product);
        return product;
    }
}
