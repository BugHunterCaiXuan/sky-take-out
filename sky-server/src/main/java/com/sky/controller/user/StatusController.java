package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.StatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("UserStatusController")
@Slf4j
@RequestMapping("/user/shop")
@Api(tags = "店铺状态相关接口")
public class StatusController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取店铺营业状态为: {}", status == 1 ? "启用" : "禁用");
        return Result.success(status);
    }
}
