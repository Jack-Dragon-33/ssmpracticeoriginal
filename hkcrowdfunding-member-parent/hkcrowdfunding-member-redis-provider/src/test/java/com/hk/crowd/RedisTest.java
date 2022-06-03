package com.hk.crowd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author Dragon
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    private Logger logger= LoggerFactory.getLogger(RedisTest.class);
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    public void Test1(){
        redisTemplate.opsForValue().set("hello","world");
    }
    @Test
    public void Test2(){
        redisTemplate.opsForValue().set("ui", "nihao", 10, TimeUnit.SECONDS);
    }
}
