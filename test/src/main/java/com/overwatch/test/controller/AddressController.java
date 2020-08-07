package com.overwatch.test.controller;

import com.overwatch.test.domain.Address;
import com.overwatch.test.domain.Member;
import com.overwatch.test.service.AddressService;
import com.overwatch.test.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/myPage/register")
    public String newAdd(HttpServletRequest request,
                         @RequestParam("ma_rcv_title") String addName,
                         @RequestParam("address_zip1") String addZip1,
                         @RequestParam("address_addr1") String addZip2,
                         @RequestParam("address_addr2") String addZip3,
                         @RequestParam("address_addr3") String addZip4,
                         @RequestParam("num") Long num, Model model) {

        Address address = new Address();
        address.setAddName(addName);
        address.setAddCode(addZip1);
        address.setBasicAdd(addZip2);
        address.setDetailAdd(addZip3);
        address.setExtraAdd(addZip4);

        Member member = memberService.findMember(num);
        address.setMember(member);

        addressService.join(address);

        List<Address> addresses = addressService.findAddrs(num);
        model.addAttribute("list", addresses);
        /*model.addAttribute("address", address);*/
        return "/myPage/address";
    }

    @PostMapping("/address/delete")
    public void delete(@RequestParam Long addr_num, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        int res = addressService.delete(addr_num);

        out.println(res);

    }

    @PostMapping("/getDefaultAddr")
    public void getDefaultAddr(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("loginUser");
        List<Address> lists = addressService.getAllAddr(member.getNum());
        String address = "";


        if(lists.size() != 0) {
            String zipcode = lists.get(0).getAddCode().trim();
            String address01 = lists.get(0).getBasicAdd().trim();
            String address02 = lists.get(0).getDetailAdd().trim();
            String address03 = lists.get(0).getExtraAdd().trim();
            address = zipcode + ":" + address01 + ":" + address02 + ":" + address03;
        }
        PrintWriter out = response.getWriter();
        out.println(address);

    }

    @PostMapping("/getLatestAddr")
    public void getLatestAddr(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("loginUser");
        List<Address> lists = addressService.getAllAddr(member.getNum());

        String address = "";

        int lastestIndex = lists.size()-1;
        if(lists.size() != -1) {
            String zipcode = lists.get(lastestIndex).getAddCode().trim();
            String address01 = lists.get(lastestIndex).getBasicAdd().trim();
            String address02 = lists.get(lastestIndex).getDetailAdd().trim();
            String address03 = lists.get(lastestIndex).getExtraAdd().trim();
            address = zipcode + ":" + address01 + ":" + address02 + ":" + address03;
        }

        PrintWriter out = response.getWriter();
        out.println(address);
    }
}
