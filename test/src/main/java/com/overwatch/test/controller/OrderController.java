package com.overwatch.test.controller;

import com.overwatch.test.domain.Address;
import com.overwatch.test.domain.Member;
import com.overwatch.test.domain.watch.Watch;
import com.overwatch.test.service.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Log
@Controller
@Transactional
public class OrderController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private KakaoPay kakaoPay;


    @PostMapping("/cart")
    public String order(OrderRealFormTest orderRealFormTest, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception  {
        response.setCharacterEncoding("UTF-8");
        String goTo = "single";
        Member member = checkLogin(request, response);

        if(member != null) {

            System.out.println("Hello 주문");

            String item_image = orderRealFormTest.getItem_image1();
            String item_name = orderRealFormTest.getItem_name();
            String item_price = orderRealFormTest.getItem_price();
            String add = orderRealFormTest.getAdd();
            String amount = orderRealFormTest.getAmount();

            System.out.println("====================");
            System.out.println("주문테스트 " + item_image + " " + item_name + " " + item_price + " " + add + " " + amount + " ");
            System.out.println("====================");

            Long memberId = member.getNum();
            List<Address> lists = addressService.getAllAddr(memberId);

            List<String> addrList = new ArrayList<String>();

            orderRealFormTest.setMember_id(String.valueOf(member.getNum()));


            if(lists.size() != 0) {
                orderRealFormTest.setZipcode(lists.get(0).getAddCode());
                orderRealFormTest.setAddress1(lists.get(0).getBasicAdd());
                orderRealFormTest.setAddress2(lists.get(0).getDetailAdd());
                orderRealFormTest.setAddress3(lists.get(0).getExtraAdd());
            }

            String zipcode = "";
            String address01 = "";
            String address02 = "";
            String address03 = "";
            String address = null;

            for (Address addr : lists) {
                zipcode = addr.getAddCode();
                address01 = addr.getBasicAdd();
                address02 = addr.getDetailAdd();
                address03 = addr.getExtraAdd();
                address = zipcode + ":" + address01 + ":" + address02 + ":" + address03;
                addrList.add(address);
            }

            model.addAttribute("addrList", addrList);
            model.addAttribute("order_item", orderRealFormTest);

            goTo = "cart";

        } else {
            model.addAttribute("item_detail", orderRealFormTest);
        }
        return goTo;

    }

    private Member checkLogin(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("loginUser");

        return member;
    }

    @PostMapping("/orderReal")
    public String orderReal(OrderRealFormTest orderRealFormTest, Model model) throws Exception{

        System.out.println(orderRealFormTest.getItem_image1());
        System.out.println();
        System.out.println("주문 상세 정보~~~!!!!");
        System.out.println("상품 이름 : "+orderRealFormTest.getItem_name());
        System.out.println("상품 가격 : "+orderRealFormTest.getItem_price());
        System.out.println("상품 수량 : "+orderRealFormTest.getAdd());
        System.out.println("상품 총액 : "+orderRealFormTest.getAmount());

        System.out.println("주문자 이름: "+orderRealFormTest.getOrder_name());
        System.out.println("수취인 이름: "+orderRealFormTest.getRecipient());
        System.out.println("전화 번호 : "+orderRealFormTest.getTel());
        System.out.println("우편번호: "+orderRealFormTest.getZipcode());
        System.out.println("주소1: "+orderRealFormTest.getAddress1());
        System.out.println("주소2: "+orderRealFormTest.getAddress2());
        System.out.println("주소3: "+orderRealFormTest.getAddress3());

        String zipcode = orderRealFormTest.getZipcode();
        String address1 = orderRealFormTest.getAddress1();
        String address2 = orderRealFormTest.getAddress2();
        String address3 = orderRealFormTest.getAddress3();
        String totalAddr = zipcode+" "+address1+" "+address2+" "+address3;


        // 주문 테스트,,
        Long memberId = Long.parseLong(orderRealFormTest.getMember_id());
        Member member = memberService.findOne(memberId);

        Address address = new Address();
        address.setNum(member.getNum());
        address.setAddCode(zipcode);
        address.setBasicAdd(address1);
        address.setDetailAdd(address2);
        address.setExtraAdd(address3);
        address.setMember(member);

        /*addressService.join(address);*/

        Long itemId = Long.parseLong(orderRealFormTest.getItem_id());
        Watch watch = itemService.findOne(itemId);

        int count = 1;

        // 주문,,, 주문 테이블에 들어가나요?
        Long order_id = orderService.order(member.getNum(), watch.getNum(), count, address);

        // 이 주문번호를 orderRealFormTest에 넣어줘야, 결제 요청을 보낼 떄, 그 주문을 특정할 수 있다.

        orderRealFormTest.setOrder_id(order_id);
        System.out.println(order_id+"  제어가 여기까지 온다");

        model.addAttribute("order_payment", orderRealFormTest);


        return "payment";
    }

    @PostMapping("/kakaoPay")
    public void KakaoPay(Model model, OrderRealFormTest orderRealFormTest, HttpServletResponse response) throws IOException {

        log.info("kakaoPay post..............");
        System.out.println("주문번호 : "+orderRealFormTest.getOrder_id());
        System.out.println(orderRealFormTest.getItem_image1());
        System.out.println(orderRealFormTest.getItem_name());
        System.out.println(orderRealFormTest.getItem_price());

        // kakaoPay에 이름이랑 가격을 전달한다.
        String toPay = kakaoPay.kakaoPayReady(orderRealFormTest);
        System.out.println(toPay);
        response.sendRedirect(toPay);

        /*return "redirect:"+kakaoPay.kakaoPayReady();*/
    }

    @GetMapping("/kakaoPaySuccess")
    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) {

        log.info("kakaoPaySuccess get.........");
        log.info("kakaoPaySuccess pg_token : "+pg_token);
    }


    @PostMapping("/payments/complete")
    public void afterPayment(HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        out.println("success");

    }

    @GetMapping("/paySuccessPage")
    public String afterSuccess() {
        return "kakaoPaySuccess";
    }

    @PostMapping("/single")
    public String itemDetail(OrderRealFormTest orderRealFormTest, Model model) throws Exception {


        String item_name = orderRealFormTest.getItem_name();
        String item_price = orderRealFormTest.getItem_price();
        String item_image1 = orderRealFormTest.getItem_image1();
        String item_image1_1 = orderRealFormTest.getItem_image1_1();
        String item_image1_2 = orderRealFormTest.getItem_image1_2();

        System.out.println("====================");
        System.out.println("상세정보 "+item_name+" "+item_price+" "+item_image1+" "+item_image1_1+" "+item_image1_2);
        System.out.println("====================");

        model.addAttribute("item_detail", orderRealFormTest);

        return "single";
    }


}


