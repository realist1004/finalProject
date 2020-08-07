package com.overwatch.test.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.overwatch.test.domain.Address;
import com.overwatch.test.domain.Member;
import com.overwatch.test.domain.Sort;
import com.overwatch.test.service.MemberService;
import com.overwatch.test.util.Config;
import com.overwatch.test.util.MakeCertifiNum;
import com.overwatch.test.util.SendSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.security.util.Password;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import static com.overwatch.test.domain.Sort.BASIC;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/test")
    public void testWrite(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int res = memberService.check(request.getParameter("userId"));

        PrintWriter out = response.getWriter();
        out.println(res);

    }

    @RequestMapping(value="", method=RequestMethod.GET)
    public String loginGET() {

        return "";
    }


    @PostMapping("/sessionOut")
    public void sessionOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");
        session.removeAttribute("loginName");
        session.removeAttribute("loginSort");
        session.removeAttribute("loginNum");
        // session.invalidate();
        int res = 1;
        PrintWriter out = response.getWriter();
        out.println(res);
    }


    @PostMapping("/login")
    public void loginCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*request.setCharacterEncoding("UTF-8");*/
        response.setContentType("text/html; charset=utf-8");
        System.out.println("로그인 체크");

        String checkid = (String)request.getParameter("checkid");
        String checkpwd = (String)request.getParameter("checkpwd");

        System.out.println(checkid + " " + checkpwd);

        Member checkMem = memberService.findMember(checkid);

        String id = memberService.findMember(checkid).getId();
        String pwd = memberService.findMember(checkid).getPassword();

//        System.out.println("pwd => " +pwd);
        PrintWriter out = response.getWriter();

        int res = 0;
        System.out.println("================================");
        System.out.println(bCryptPasswordEncoder.matches(checkpwd, pwd));
        System.out.println("================================");


        if (id.equals(checkid) && bCryptPasswordEncoder.matches(checkpwd, pwd) && checkMem.getNum() != -1L) {
            HttpSession session = request.getSession();
            Member member = memberService.findMember(id);
            session.setAttribute("loginUser", member);
            session.setAttribute("loginName", member.getName());
            session.setAttribute("loginSort", member.getSort());
            session.setAttribute("loginNum", member.getNum());


            res = 1;
            String name = member.getName();
            System.out.println("깨지나요??" + name+" "+res);
            /*String message = res+" "+name;
            String endMessage = URLDecoder.decode(message, "UTF-8");*/

            out.println(res + " " + name);

        } else {
            out.println(res);
        }
    }

    @PostMapping("/identify")
    public void identify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("전화번호 보내기 들어오나요?");

        String certifiNum = "";

        String phone = request.getParameter("phone");
        int res = memberService.checkPhone(request.getParameter("phone"));

        MakeCertifiNum mc = new MakeCertifiNum();
        certifiNum = mc.makeNum();

        System.out.println(certifiNum + "디버깅 중입니다.");

        Config co = new Config();
        Config.content = "고객님의 인증번호는 [" + certifiNum + "] 입니다.";
        Config.receiver = phone;

        request.setAttribute("certifiNum", certifiNum);

        System.out.println(certifiNum);
        System.out.println(res);
        // 여기서 실제로 sms를 보낸다.
        SendSMS sms = new SendSMS();
        SendSMS.sendingSMS();

        /*System.out.println("몇 번이나 도나요?????");*/
        PrintWriter out = response.getWriter();
        out.println(certifiNum + " " + res);
    }

    @PostMapping("/member/new")
    public void newMember(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int res = memberService.check(request.getParameter("id"));
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String pwd1 = request.getParameter("pwd1");
        PrintWriter out = response.getWriter();

        if (res == 1) {
            out.println(res);
        } else {
            Member member = new Member();
            member.setId(id);
            member.setName(name);
            member.setPhone(phone);
            member.setPassword(pwd1);
            member.setSort(BASIC);

            memberService.join(member);
            out.println(res);
        }
    }

    @PostMapping("/profile/update")
    public String profileUpdate(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object loginSort = session.getAttribute("loginSort");
        System.out.println("~~~~~~~~~~~~~~~~~~~~"+loginSort);
        if(loginSort.equals(BASIC)){
            return "myPage/profileBasic";
        }else{
            return "myPage/profileKaNaver";
        }

    }

    @PostMapping("/profile/updateKaNaver")
    public String profileUpdateKN(HttpServletRequest request, @RequestParam String name, @RequestParam String email, @RequestParam String phone, @RequestParam String phone2, @RequestParam String phone3, @RequestParam String year, @RequestParam String month, @RequestParam String day){

        String phoneTotal =  phone+phone2+phone3;
        String birthday = year+"."+month+"."+day;
        int result = memberService.updateKN(email, name, phoneTotal, birthday);

        if(result > 0) {
            Member member = memberService.findMember(email);
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", member);
            System.out.println("업데이트가 되었습니다.");
        } else {
            System.out.println("업데이트가 실패했어요.");
        }
        return "myPage/profile";
    }


    @PostMapping("/profile/updateBasic")
    public String profileUpdateB(HttpServletRequest request, @RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String phone, @RequestParam String phone2, @RequestParam String phone3, @RequestParam String year, @RequestParam String month, @RequestParam String day) {
        System.out.println("들어옴1");

        String phoneTotal =  phone+phone2+phone3;
        String birthday = year+"."+month+"."+day;
        int result = memberService.update(email, name, password, phoneTotal, birthday);


        System.out.println("들어옴2");

        if(result > 0) {
            System.out.println("업데이트가 되었습니다.");
            Member member = memberService.findMember(email);
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", member);
        } else {
            System.out.println("업데이트가 실패했어요.");
        }

        return "redirect:/";
    }


}
