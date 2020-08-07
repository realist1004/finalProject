package com.overwatch.test.controller;


import com.overwatch.test.domain.Member;
import com.overwatch.test.domain.Sort;
import com.overwatch.test.service.KakaoAPI;
import com.overwatch.test.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;

@Controller
public class KakaoController {


    @Autowired
    private MemberService memberService;

    @Autowired
    private KakaoAPI kakao;

    @RequestMapping(value="/")
    public String index() {

        return "index";
    }

    private String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    @RequestMapping(value="/login/kakao")
    public String login(@RequestParam("code") String code, HttpSession session, HttpServletResponse response) throws Exception{
        String access_Token = kakao.getAccessToken(code);

        HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);

        System.out.println("Hello1");

        String id = (String) userInfo.get("email");
        Member member = new Member();
        if(memberService.check(id)==0){
            System.out.println("Hello2");
            member.setId((String) userInfo.get("email"));
            member.setName((String) userInfo.get("nickname"));
            member.setBirthday((String) userInfo.get("birthday"));
            member.setSort(Sort.KAKAO);
            String password = generateRandomString();
            member.setPassword(password);

            memberService.join(member);
        }else if(memberService.check(id)>0){
            member = memberService.findMember(id);

        }

        System.out.println("Hello3");

        if(userInfo.get("email")!=null){
            //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
            System.out.println("Hello4");
//            session.setAttribute("userId", userInfo.get("email"));
//            //  session.setAttribute("access_Token", access_Token);
//            //  access_Token=> loginUser
//            session.setAttribute("access_Token", access_Token);
            System.out.println("****************");
            System.out.println(member.getId()+" "+member.getName()+" "+member.getBirthday() + member.getNum());
            System.out.println(member);
            session.setAttribute("loginName", member.getName());
            session.setAttribute("loginUser", member);
            session.setAttribute("loginSort", member.getSort());
            session.setAttribute("loginNum", member.getNum());
        }else {
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('로그인 실패')");
            out.println("history.back()");
            out.println("</script>");
        }
        return "redirect:/";
    }

   /* @RequestMapping(value="/logout")
    public String logout(HttpSession session) {
        kakao.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        session.removeAttribute("loginUser");

        return "redirect:/";
    }*/

}

