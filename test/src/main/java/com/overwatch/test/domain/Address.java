package com.overwatch.test.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
/*@Embeddable*/
@Setter
/*@NoArgsConstructor  //  기본생성자
@AllArgsConstructor*/ //  멤버를 인자로 생성함*/
@Entity
/*@Table*/
@Table(name="address")
public class Address {

    //배송지 번호
    /*@Id
    @GeneratedValue
    private Long addNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_num")
    private Member member;*/
    //  primary Key
    @Id
    @GeneratedValue
    @Column(name = "addr_num")
    private Long num;

    //배송지명
    private String addName;

    //우편번호
    private String addCode;

    //기본주소
    private String basicAdd;

    //상세주소
    private String detailAdd;

    //추가주소
    private String extraAdd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_num")
    private Member member;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_num")
    private Delivery delivery;

}
