package com.overwatch.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_num")
    private Long num;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_num")
    private Member member;  //  주문 회원 객체

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderWatch> orderItems = new ArrayList<OrderWatch>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_num")
    private Delivery delivery;

    private LocalDateTime orderDate;    //  주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //  주문 상태   [ORDER, CANCEL]



    // 주문 생성 메서드
    // OrderItem... orderItems 이 구문의 의미가
    // List<OrderItem> orderItems와 같나?
    // 주문 과정에 필요한 것들.. member, delivery, orderItem
    // 누가 무엇을 주문했고, 배송 상태는 어떻고, 그 사람 주소는 어떤지..
    public static Order createOrder(Member member, Delivery delivery, OrderWatch... orderWatchs) {

        Order order = new Order();
        order.setMember(member);    // 연관관계 편의 메서드
        order.setDelivery(delivery);

        // orderItems는 여러 개가 있을 수 있기 때문에
        // 단축 for문을 쓴다.
        // orderItems에는
        for(OrderWatch orderWatch: orderWatchs) {
            order.addOrderItem(orderWatch);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        // 이렇게 이것저것 넣어준 다음에
        // order를 반환해준다.

        return order;
    }

    // 여기서
    private void addOrderItem(OrderWatch orderWatch) {
        orderItems.add(orderWatch);
        orderWatch.setOrder(this);
    }

    public void cancel() {
        // 배송 상태가 어떤지 확인해야 한다.
        // delivery가 주문 과정에서 필요한 이유
        // 만약, deliveryStatus가 comp라면 주문 취소가 되면 안 된다.
        if(delivery.getStatus().equals(DeliveryStatus.COMP)) {
            throw new IllegalStateException("이미 배송이 완료되었습니다.");
        }

        // 주문 취소가 가능한 상태라면
        this.setStatus(OrderStatus.CANCEL);

        // 여러 개를 주문했을 수가 있다.
        // for문을 돌면서

        for(OrderWatch orderItem: orderItems) {
            orderItem.cancel();
        }

        delivery.setStatus(DeliveryStatus.CANCEL);

    }
}
