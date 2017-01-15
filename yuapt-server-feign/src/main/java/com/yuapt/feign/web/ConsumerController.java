package com.yuapt.feign.web;

import com.yuapt.feign.service.ComputeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jimmy on 17/1/6.
 */
@RestController
public class ConsumerController {


    @Autowired
    ComputeClient computeClient;

    @RequestMapping(value = "/add1", method = RequestMethod.GET)
    public String add() {

        String i = computeClient.add(10, 20);
        System.out.println("end ......");

        return i;
    }
}
