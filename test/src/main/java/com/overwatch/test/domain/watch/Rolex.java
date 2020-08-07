package com.overwatch.test.domain.watch;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("B")
public class Rolex extends Watch {

    private String model;   //  ex) 서브마리너? 해당 브랜드의 모델

}
