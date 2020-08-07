package com.overwatch.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="delivery")
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_num")
    private Long num;

    // @Embedded
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "addr_num")
    private Address address;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_num")
    private Order order;
    
    private int price;  //  배송비

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
