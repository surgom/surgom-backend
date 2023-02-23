package com.greentea.surgom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class TestController {
    @GetMapping("/")
    public String index() {
        return "APIExamNaverLogin";
    }

    @ResponseBody
    @RequestMapping("/NaverLoginCallback")
    public String NaverLoginCallback(@RequestParam Map<String, String> resValue){
        return "redirect:/join/naver";
    }
}
