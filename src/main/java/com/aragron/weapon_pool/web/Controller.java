package com.aragron.weapon_pool.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述:
 *
 * @Author: aragron
 * @Create: 2018/7/26-11:39
 * @Home: http://aragron.com
 */
@RestController
public class Controller {

    @GetMapping("/test")
    public String test() {
        return "哈";
    }
}
