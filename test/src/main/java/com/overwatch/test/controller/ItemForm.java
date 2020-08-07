package com.overwatch.test.controller;

import lombok.Data;

@Data
public class ItemForm {

    private String thumb;   // 대표 이미지
    private long id;        // 상품번호
    private String name;    // 상품명/admin/main
    private String bdesc;   // 요약설명
    private int price;      // 판매가격
    private int buyout;     // 매입가격
    private int stock;      // 초기재고
    private String mdate;   // 제조년월
    private String cat1;    // 제조사 분류
    private String cat2;    // 제품 분류
    private String fdesc;   // 전체설명
    private String psale;   // 판매 상태
    private String pshow;   // 게시 상태
}
