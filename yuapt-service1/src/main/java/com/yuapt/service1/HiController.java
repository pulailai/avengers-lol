package com.yuapt.service1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jimmy on 16/12/27.
 */
@RestController
public class HiController {


    private static final Log log = LogFactory.getLog(HiController.class);

    @RequestMapping("/hello")
    public String hi() throws Exception {
        log.info("helloservice");
        return "helloservice";
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@RequestParam Integer a, @RequestParam Integer b) {
        System.out.println("server1啊飒飒拉丝机的啦:" + a + b);
        return "ok";
    }
}
