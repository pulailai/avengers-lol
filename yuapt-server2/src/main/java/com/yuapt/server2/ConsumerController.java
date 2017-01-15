package com.yuapt.server2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jimmy on 17/1/4.
 */
@RestController
public class ConsumerController {


    private static final Logger LOG = Logger.getLogger(ConsumerController.class.getName());

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        LOG.log(Level.INFO, "trace demo backend is being called");
        System.out.println("=============== 调用者");
      //  RibbonFilterContextHolder.getCurrentContext().add("version","1.0").add("variant", "A");
        return restTemplate.getForEntity("http://SERVICE1/add?a=10&b=20", String.class).getBody();

        //return restTemplate.getForEntity("http://SERVICE1/add?a=10&b=20", String.class).getBody();
    }


    @RequestMapping("/")
    public String home() {
        LOG.log(Level.INFO, "trace demo backend is being called");
        return "Hello World.";
    }

}
