package com.overwatch.test.domain;

import com.overwatch.test.domain.watch.Watch;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "order_watch")
public class OrderWatch {

    @Id
    @GeneratedValue
    @Column(name = "order_watch_num")
    private Long num;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "watch_num")
    private Watch watch;    //  주문 상품 객체

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_num")
    private Order order;    //  주문 객체
    
    private int orderPrice; //  주문 가격
    
    private int count;  //  주문 수량

    public static OrderWatch createOrderWatch(Watch watch, int price, int count) {
        OrderWatch orderWatch = new OrderWatch();
        orderWatch.setWatch(watch);
        orderWatch.setOrderPrice(price);
        orderWatch.setCount(count);


        // 저 수량만큼 주문을 했으니, 그 아이템의 수량만큼 빼야 함.
        watch.removeStock(count);

        return orderWatch;
    }

    // 주문이 취소되면, 빼 주었던 수량을 다시 더해주어야 함.
    public void cancel() {
        getWatch().addStock(count);
    }

    public int getTotalPrice() {
        return getOrderPrice()*getCount();
    }


}









