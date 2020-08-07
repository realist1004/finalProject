package com.overwatch.test.controller;

import com.google.gson.JsonObject;
import com.overwatch.test.domain.watch.Rolex;
import com.overwatch.test.domain.watch.Watch;
import com.overwatch.test.repository.ItemRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

@Controller
public class ProductController {

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping("order/care")
    public String care(){return "admin/care";}

    @RequestMapping("/product/register")
    public String register(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "admin/register";
    }

    @RequestMapping("/product/list")
    public String list() {
        return "admin/list";
    }


    @RequestMapping("/product/category")
    public String category() {
        return "admin/category";
    }

    @RequestMapping("/product/showcase")
    public String showcase() {
        return "admin/showcase";
    }

    @RequestMapping("/order/order_list")
    public String orderList() {
        return "admin/ordermanage/order_list";
    }

    @RequestMapping("/order/search_order")
    public String searchOrder(){return  "admin/ordermanage/search_order";}

    @RequestMapping("/order/payment_list")
    public String paymentList() {
        return "admin/ordermanage/payment_list";
    }

    @RequestMapping("/order/test2")
    public String test2(){return "admin/ordermanage/test/test2";}

    @RequestMapping("/order/test3")
    public String test3(){return "admin/ordermanage/test/test3";}

    @RequestMapping("/order/test4")
    public String test4(){return "admin/ordermanage/test/test4";}

    @RequestMapping("/order/test5")
    public String test5(){return "admin/ordermanage/test/test5";}

    @RequestMapping("/order/order_cancel")
    public String orderCancel() {
        return "admin/ordermanage/order_cancel";
    }

    @RequestMapping("/order/cancle2")
    public String cancle2(){return "admin/ordermanage/test/cancle2";}

    @RequestMapping("/order/cancle3")
    public String cancle3(){return "admin/ordermanage/test/cancle3";}

    @RequestMapping("/order/cancle4")
    public String cancle4(){return "admin/ordermanage/test/cancle4";}

    @PostMapping(value="/uploadSummernoteImageFile", produces = "application/json")
    @ResponseBody
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        JsonObject jsonObject = new JsonObject();

        String fileRoot = "C:\\summernote\\";	//저장될 외부 파일 경로
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
            jsonObject.addProperty("url", "/img/"+savedFileName);
            jsonObject.addProperty("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }

        return jsonObject;
    }

    @PostMapping("/product/create")
    public String create(@Valid ItemForm form, BindingResult result) throws UnsupportedEncodingException {
        System.out.println("itemForm 검사");
        System.out.println(form.getThumb());


        System.out.println(form.getName());
        System.out.println(form.getPrice());
        System.out.println(form);

        Watch watch = new Rolex();

        watch.setName(form.getName());
        watch.setPrice(form.getPrice());
        watch.setStockQuantity(10000);
        watch.setItem_images01(form.getThumb());
        /*watch.setTest_images(form.getThumb());*/

        itemRepository.save(watch);

        return "admin/main";
    }



}
