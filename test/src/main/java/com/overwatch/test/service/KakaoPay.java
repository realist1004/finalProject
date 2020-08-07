package com.overwatch.test.service;

import com.overwatch.test.controller.OrderRealFormTest;
import com.overwatch.test.dto.KakaoPayApprovalVO;
import com.overwatch.test.dto.KakaoPayReadyVO;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Log
public class KakaoPay {

    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;

    String toPay = "";


    public String kakaoPayReady(OrderRealFormTest orderRealFormTest) {
        System.out.println("kakaoPayReday 메서드에 들어왔어요");
        RestTemplate restTemplate = new RestTemplate();
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK "+"c3dc12136a01287185a9868bd938a5aa");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE+";charset=UTF-8");

        System.out.println("*****************");
        System.out.println("orderId check"+orderRealFormTest.getOrder_id());
        System.out.println("*****************");
        // 서버로 요청할 body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        // cid는 그냥 테스트 용도로 카카오측에서 제공해주는 것.
        // 실제 결제가 되길 원한다면, 가맹점 신청을 해야함.
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", String.valueOf(orderRealFormTest.getOrder_id()));
        params.add("partner_user_id", "hoan");
        params.add("item_name", orderRealFormTest.getItem_name());
        params.add("quantity", orderRealFormTest.getAdd());
        params.add("total_amount", orderRealFormTest.getItem_price());
        params.add("tax_free_amount", "100");
        params.add("approval_url", "http://localhost:8585/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:8585/kakaoPayCancel");
        params.add("fail_url", "http://localhost:8585/kakaoPaySuccessFail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST+"/v1/payment/ready"), body, KakaoPayReadyVO.class);
            toPay = kakaoPayReadyVO.getNext_redirect_pc_url();
            System.out.println(kakaoPayReadyVO.toString());
            System.out.println(toPay);

        } catch (RestClientException e) {
            e.printStackTrace();
            System.out.println("체크 포인트9");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println("체크 포인트10");


        return toPay;
   }

   public KakaoPayApprovalVO kakaoPayInfo(String pg_token) {

       log.info("KakaoPayInfoVO............................................");
       log.info("-----------------------------");

       RestTemplate restTemplate = new RestTemplate();

       // 서버로 요청할 Header
       HttpHeaders headers = new HttpHeaders();
       headers.add("Authorization", "KakaoAK " + "admin key를 넣어주세요~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!");
       headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
       headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

       // 서버로 요청할 Body
       MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
       params.add("cid", "TC0ONETIME");
       params.add("tid", kakaoPayReadyVO.getTid());
       params.add("partner_order_id", "1001");
       params.add("partner_user_id", "gorany");
       params.add("pg_token", pg_token);
       params.add("total_amount", "2100");

       HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

       try {
           kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
           log.info("" + kakaoPayApprovalVO);

           return kakaoPayApprovalVO;

       } catch (RestClientException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (URISyntaxException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }

       return null;
   }

}
