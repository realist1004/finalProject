package com.overwatch.test;

import com.overwatch.test.domain.Address;
import com.overwatch.test.domain.watch.Watch;
import com.overwatch.test.service.AddressService;
import com.overwatch.test.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HelloController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemService itemService;


    @GetMapping("/popup")
    public String popup() { return "popup"; }

    @GetMapping("/admin/main")
    public String m_main() {
        return "admin/main";
    }


    /* myPage 부분*/
    @GetMapping("/myPage")
    public String myPage() {

        return "myPage";
    }

    @RequestMapping("/myPage/address")
    public String address(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long mnum = (Long) session.getAttribute("loginNum");
        System.out.println("들어옴!~~~~~~~~~~~~~~~~" + mnum);
        List<Address> addresses = addressService.findAddrs(mnum);
        model.addAttribute("list", addresses);

        return "/myPage/address";
    }

    @GetMapping("/myPage/register")
    public String register() {
        return "myPage/register";
    }

    @GetMapping("/myPage/myBasket")
    public String myBasket() {
        return "myPage/myBasket";
    }

    @GetMapping("/myPage/myBasket2")
    public String myBasket2() { return "myPage/myBasket2"; }

    @GetMapping("/myPage/myBasket3")
    public String myBasket3() { return "myPage/myBasket3"; }

    /*@GetMapping("/myPage/board")
    public String board() {
        return "myPage/board";
    }

    @GetMapping("/myPage/coupon")
    public String coupon() {
        return "myPage/coupon";
    }*/

    @GetMapping("/myPage/mileage")
    public String mileage() {
        return "myPage/mileage";
    }

    @GetMapping("/myPage/orderList")
    public String orderList() {
        return "myPage/orderList";
    }

    @GetMapping("/myPage/profile")
    public String profile() {

        return "myPage/profile";
    }



    /*@GetMapping("/myPage/wishList")
    public String wishList() {
        return "myPage/wishList";
    }*/

    @GetMapping("/")
    public String hello(Model model) {
        System.out.println("여기서 item 정보들을 다 받아올 것입니다.");

        List<Watch> watches = itemService.findItems();
        System.out.println("*********************");
        for(Watch wat: watches) {
            System.out.println(wat.getItem_images01());
            System.out.println(wat.getItem_images02());
            System.out.println(wat.getItem_images03());
        }
        System.out.println("*********************");

        System.out.println(watches.get(0).getBrand());

        model.addAttribute("itemList", watches);

        return "index";
    }

    @GetMapping("/hi")
    public String hi() {
        return "hello";
    }


    @GetMapping("/about")
    public String about() {
        return "about";
    }


    @GetMapping("/mens")
    public String mens() {
        return "mens";
    }

    @GetMapping("/womens")
    public String womens() {
        return "womens";
    }


    @GetMapping("/icons")
    public String icons() {
        return "icons";
    }

    @GetMapping("/typography")
    public String typography() {
        return "typography";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/single")
    public String single() {
        return "single";
    }

    @PostMapping("/search")
    public String search(@RequestParam("search") String search) {
        System.out.println(search + "를 검색하셨습니다. 이와 관련한 결과를 내 놓아라!");

        return "redirect:/";
    }

}