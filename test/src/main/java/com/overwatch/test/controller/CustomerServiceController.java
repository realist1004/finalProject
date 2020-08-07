package com.overwatch.test.controller;

import com.overwatch.test.util.SendEmail;
import com.overwatch.test.util.SendMegazine;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerServiceController {

    @PostMapping("/customer/complain")
    public String complain(CustomerServiceForm form, BindingResult result) {


        System.out.println("고객불만센터에 들어 왔어요~!!!");

        SendEmail sendEmail = new SendEmail();
        sendEmail.sendEmail(form.getSubject(), form.getMessage(), form.getName(), form.getEmail());

        System.out.println(form.getSubject()+" "+form.getMessage()+" "+form.getName()+" "+form.getEmail());

        return "redirect:/contact";
    }


    @PostMapping("/customer/subscribe")
    public String subscribe(@RequestParam("email") String email) {


        System.out.println(email+"님이 저희 페이지를 구독해주셨어요.");
        SendMegazine sendMegazine = new SendMegazine();
        sendMegazine.sendMegazine(email);

        return "redirect:/";
    }



}
