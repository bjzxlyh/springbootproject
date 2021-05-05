package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

//    @RequestMapping("css")
//    public String main(){
//        return "/css/styles.css";
//    }
//
//    @RequestMapping("/weclome")
//    public String weclome(){
//        return "weclome";
//    }
}
