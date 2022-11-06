package com.example.giftshop.member.controller;

import com.example.giftshop.member.dto.MemberFormDTO;
import com.example.giftshop.member.entity.Member;
import com.example.giftshop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/join")
    public String memberJoin(Model model){
        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "member/memberJoinForm";
    }

    @PostMapping(value = "/join")
    public String memberJoin(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "member/memberJoinForm";
        }

        try{
            Member member = Member.createMember(memberFormDTO, passwordEncoder);
            memberService.joinMember(member);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberJoinForm";
        }


        return "redirect:/";
    }
}
