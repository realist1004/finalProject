package com.overwatch.test.domain;

import com.overwatch.test.domain.watch.Watch;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_num")
    private Long num;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_watch",
            joinColumns = @JoinColumn(name = "watch_num"))
    private List<Watch> watches = new ArrayList<Watch>();


}
