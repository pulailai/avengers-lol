package com.yuapt.server11;

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
        System.out.println("server1:" + a + b);
        System.out.println("12欧撒打算考了解到卡拉是进口的垃圾是看得见爱死了");
        return "ok";
    }
}
