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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/join")
    public String memberJoin(Model model){
        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "member/joinform";
    }

    @PostMapping(value = "/join")
    public String memberJoin(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model){

        System.out.println("post join");
        if(bindingResult.hasErrors()){
            System.out.println("hasErrors() : " + bindingResult.toString());
            return "member/joinform";
        }
        try{
            Member member = Member.createMember(memberFormDTO, passwordEncoder);
            memberService.joinMember(member);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/joinform";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String memberLogin(HttpServletRequest request, Principal principal){
        //로그인 상태에서 접근
        if(principal != null){
            return "redirect:/";
        }
        //이전 페이지
        request.getSession()
                .setAttribute("referer",request.getHeader("Referer"));
        return "member/loginform";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 비밀번호를 확인해주세요");
        return "member/loginform";
    }
}
