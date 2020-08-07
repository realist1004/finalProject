package com.overwatch.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_num")
    private Long num;

    private String id;

    private String name;

    private String password;

    private String phone;

    private String birthday;

    @Enumerated(EnumType.STRING)
    private Sort sort;

    /*@Embedded
    private Address address;*/

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<Order>();

    @OneToMany(mappedBy = "member")
    private List<Address> addrs = new ArrayList<Address>();





}
