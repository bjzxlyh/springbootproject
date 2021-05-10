package com.lyh.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    /*
    系统登录页面
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    /*
    系统主页面
     */
    @RequestMapping("/main")
    public String main(){
        return "main";
    }

    /*
    系统欢迎页
     */
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }
}
