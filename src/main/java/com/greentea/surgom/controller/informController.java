package com.greentea.surgom.controller;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.security.SessionMember;
import com.greentea.surgom.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
public class informController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/inform/member")
    public ResponseEntity<Object> getMemberInformation(HttpServletRequest request) {
        Optional<Member> member = memberService.getMember(request);

        if (member != null)
            return ResponseEntity.badRequest().body("회원이 존재하지 않습니다");
        else
            return ResponseEntity.ok().body(member);
    }
}
