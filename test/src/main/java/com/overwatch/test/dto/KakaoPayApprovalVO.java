package com.overwatch.test.dto;

import lombok.Data;

import java.util.Date;

@Data
public class KakaoPayApprovalVO {

    private String aid, tid, cid, sid;
    private String partner_order_id, partner_user_id, payment_method_type;
    private String item_name, item_code, payload;
    private Integer quantity;
    private Date created_at, approved_at;


}
