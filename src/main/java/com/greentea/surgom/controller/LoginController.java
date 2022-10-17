package com.greentea.surgom.controller;

import com.greentea.surgom.security.SessionMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private HttpSession httpSession;

    @GetMapping("/login-success")
    public String login_success_handler(SessionMember member, Model model) {
        // httpSession에 저장된 user의 정보를 가져옵니다.
        member = (SessionMember) httpSession.getAttribute("member");

        model.addAttribute("name", member.getName());
        model.addAttribute("nickname", member.getNickname());
        model.addAttribute("gender", member.getGender());
        model.addAttribute("age", member.getAge());
        model.addAttribute("phone", member.getPhone());
        model.addAttribute("point", member.getPoint());
        model.addAttribute("authority", member.getAuthority());
        model.addAttribute("identifier", member.getIdentifier());

        return "login/login-success";
    }
}
