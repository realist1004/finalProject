package com.overwatch.test.domain.watch;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("F")
public class Ferragamo extends Watch {

    private String model;
}
