package com.overwatch.test.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.scribejava.core.model.Response;
import com.overwatch.test.OAuth2.NaverLoginBO;
import com.overwatch.test.domain.Member;
import com.overwatch.test.domain.Sort;
import com.overwatch.test.service.MemberService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.scribejava.core.model.OAuth2AccessToken;

/**
 * Handles requests for the application home page.
 */
@Controller
public class NaverController {
    /* NaverLoginBO */
    private NaverLoginBO naverLoginBO;
    private String apiResult = null;

    @Autowired
    private MemberService memberService;

    @Autowired
    private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
        this.naverLoginBO = naverLoginBO;
    }

    //로그인 첫 화면 요청 메소드
    @RequestMapping(value = "/naver", method = {RequestMethod.GET, RequestMethod.POST})
    public void login(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        //* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 *//*
        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
//https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
//redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
        System.out.println("네이버:" + naverAuthUrl);
        HttpSession session1 = request.getSession();
        session1.setAttribute("naverData", naverAuthUrl);

        /*model.addAttribute("naverData", naverAuthUrl);*/
    //네이버
        //model.addAttribute("url", naverAuthUrl);
        PrintWriter out = response.getWriter();
        out.println(naverAuthUrl);

    }

    private String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    //네이버 로그인 성공시 callback호출 메소드
    @RequestMapping(value = "/login/naver", method = {RequestMethod.GET, RequestMethod.POST})
    public String callback(@RequestParam String code, @RequestParam String state, HttpSession session, HttpServletResponse response) throws IOException, ParseException {
        System.out.println("여기는 callback");
        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginBO.getAccessToken(session, code, state);
//1. 로그인 사용자 정보를 읽어온다.
        apiResult = naverLoginBO.getUserProfile(oauthToken); //String형식의 json데이터
/** apiResult json 구조
 {"resultcode":"00",
 "message":"success",
 "response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}}
 **/
//2. String형식인 apiResult를 json형태로 바꿈
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(apiResult);
        JSONObject jsonObj = (JSONObject) obj;
//3. 데이터 파싱
//Top레벨 단계 _response 파싱
        JSONObject response_obj = (JSONObject) jsonObj.get("response");
//response의 nickname값 파싱
        String id = (String) response_obj.get("email");
        String name = (String) response_obj.get("name");
        String birthday = (String) response_obj.get("birthday");

        System.out.println(id);
        System.out.println(name);
        System.out.println(birthday);

        Member member = new Member();
        if(memberService.check(id)==0){
            member.setId(id);
            member.setName(name);
            member.setBirthday(birthday);
            member.setSort(Sort.NAVER);
            String password = generateRandomString();
            member.setPassword(password);

            memberService.join(member);
        }else if(memberService.check(id)>0){
            member = memberService.findMember(id);
        }

        if(response_obj.get("email")!=null){
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
}

