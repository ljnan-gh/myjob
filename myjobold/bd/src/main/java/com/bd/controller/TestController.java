//package com.bd.controller;
//
//
//import com.core.exception.MyJobException;
//import com.core.exception.Result;
//import com.core.util.lock.RedisUtils;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@RequestMapping("/test")
//@RestController
//@Api(value = "测试分布式锁", tags = {"测试分布式锁"})
//public class TestController {
//
//
//    @Autowired
//    private RedisUtils redisUtils;
//
//    @Resource(name = "redisTemplate")
//    private RedisTemplate<Object, Object> redisTemplate;
//
//    @RequestMapping(value = "/t", method = RequestMethod.GET)
//    public Result getList() throws MyJobException {
//        Result re = new Result();
//        String[] as = {"测试仪", "测试erect"};
//        re.setValue(as);
//        return re;
//    }
//
//    @RequestMapping(value = "/t2", method = RequestMethod.GET)
//    public String t2(String info) throws MyJobException {
//        redisTemplate.boundValueOps("test").set(info);
//        String s = (String) redisTemplate.boundValueOps("test").get();
//        return s;
//    }
//
//    @RequestMapping(value = "/lock1", method = RequestMethod.GET)
//    public Result lock1() throws MyJobException, InterruptedException {
//        boolean result = redisUtils.tryLock("lock1", "1", 3);
//        Thread.sleep(6000);
//        if (result) {
//            return Result.success();
//        }
//        return Result.fail("执行失败");
//    }
//
//    @RequestMapping(value = "/lock2", method = RequestMethod.GET)
//    public Result lock2() throws MyJobException {
//        boolean result = redisUtils.tryLock("lock1", "1", 3);
//        if (result) {
//            return Result.success();
//        }
//        return Result.fail("执行失败");
//    }
//}
