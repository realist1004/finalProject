package com.overwatch.test.service;


import com.overwatch.test.domain.*;
import com.overwatch.test.domain.watch.Watch;
import com.overwatch.test.repository.AddressRepository;
import com.overwatch.test.repository.ItemRepository;
import com.overwatch.test.repository.MemberRepository;
import com.overwatch.test.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AddressRepository addressRepository;

    // orderRepository의 findOne과 findAll을 써서
    // orderService를 쓴다.

    // 아마도 가장 복잡한 Service층
    // 다른 레포지토리도 활용하여 주문이라는 하나의 기능을 구현
    // 주문은 member, item, order를 함께 활용해 작업해야 함.

    // 테스트를 위해, member와 item 테이블에 하나씩 넣어둠.
    public Long order(Long memberId, Long watchId, int count, Address address) {
        // pk로, pk에 해당하는 member 객체를 얻어옴.
        System.out.println("*********************");
        System.out.println(address.getNum());
        System.out.println("*********************");


        Member member = memberRepository.findOne(memberId);

        // pk로, pk에 해당하는 item을 얻어옴
        Watch watch = itemRepository.findOne(watchId);


        // 배송정보 조회
        Delivery delivery = new Delivery();

        /*delivery.setAddress(member.getAddress());*/

        delivery.setAddress(address);
        delivery.setStatus(DeliveryStatus.READY);
        address.setDelivery(delivery);


        // 주문상품을 생성,, item 객체와 이 item의 가격, 수량을 매게변수로 주어서, orderItem에 넣어줌.
        OrderWatch orderWatch = OrderWatch.createOrderWatch(watch, watch.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderWatch);

        orderRepository.save(order);

        return order.getNum();
    }

    // 주문 취소
    public void cancelOrder(Long orderId) {

        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 취소
        order.cancel();
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }





}
