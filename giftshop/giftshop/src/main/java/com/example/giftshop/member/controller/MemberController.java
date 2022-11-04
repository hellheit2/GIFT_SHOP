package com.example.giftshop.member.controller;

import com.example.giftshop.member.dto.MemberFormDTO;
import com.example.giftshop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/join")
    public String memberJoin(Model model){
        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "member/memberJoinForm";
    }
}
