package com.yuapt.feign.service;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by jimmy on 17/1/6.
 * UP (2) - 10.188.10.141:Service1:9984 , 10.188.10.141:Service1:9981
 */
@FeignClient(name = "Service1")
public interface ComputeClient {


    @RequestMapping(method = RequestMethod.GET, value = "/add")
    String add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);
}
