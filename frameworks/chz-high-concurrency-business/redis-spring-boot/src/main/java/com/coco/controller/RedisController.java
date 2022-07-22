package com.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/haha")
    public String returnSomesth(){
        return "spring-boot-redis";
    }

    @GetMapping("/ops/{type}/{key}/{value}")
    public String redisOps(@PathVariable("type")String type, @PathVariable("key")String key, @PathVariable("value")String value){
        switch (type){
            case "string":
                ValueOperations<String, Object> string = redisTemplate.opsForValue();
                string.set("string"+key, value);
                break;
            case "list":
                ListOperations<String, Object> list = redisTemplate.opsForList();
                list.leftPush("list"+key, value+"list1");
                break;
            case "set":
                SetOperations<String, Object> set = redisTemplate.opsForSet();
                set.add("set"+key, value+"set", value+"set1", value+"set2");
                break;
            case "zset":
                ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
                zset.add("zset"+key, value+"zset1", 10000d);
                break;
            case "hash":
                HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
                hash.put("user"+key, "name", "value");
                break;
            case "hyperloglog":
                HyperLogLogOperations<String, Object> hyperloglog = redisTemplate.opsForHyperLogLog();
                hyperloglog.add("hyperloglog"+key, value+"hyperloglog1", value+"hyperloglog1", value+"hyperloglog2");
                break;
            case "geo":
                GeoOperations<String, Object> geo = redisTemplate.opsForGeo();
                geo.add("city"+key, new Point(120.00, 30.00), "shanghai");
                break;
            default:
                break;
        }

        return "Success!";
    }
}
