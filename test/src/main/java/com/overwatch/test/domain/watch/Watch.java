package com.overwatch.test.domain.watch;

import com.overwatch.test.domain.Category;
import com.overwatch.test.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Watch {

    @Id
    @GeneratedValue
    private Long num;

    private String brand;   //  상품 브랜드

    private String name;    //  상품명

    private int price;  //  상품가격

    private int stockQuantity;  //  재고수량

    @ManyToMany(mappedBy = "watches")
    private List<Category> categories = new ArrayList<Category>();

    @Lob
    private String item_images01;

    private String item_images02;
    private String item_images03;



    // 주문수량만큼 재고수량에서 빼야 함
    // 적정재고는 고려하지 않음.
    public void removeStock(int count) {
        int resStock = this.stockQuantity -= count;
        if(resStock < 0) {
            throw new NotEnoughStockException("재고 수량 필요");
        }

        this.stockQuantity = resStock;
    }

    // 재고수량을 증가시키는 로직, 만약, 주문이 취소되었다면, 또는 관리자 페이지에서
    // 재고수량을 증가시켜주었다면, 이 로직을 쓰면 된다.

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
}
