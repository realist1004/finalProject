package com.overwatch.test.repository;

import com.overwatch.test.domain.Address;
import com.overwatch.test.domain.Delivery;
import com.overwatch.test.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class AddressRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Address address){
        System.out.println("*********************");
        System.out.println(address.getNum());
        System.out.println("*********************");
        em.merge(address);
        em.detach(address);
        /*em.persist(address);*/

        System.out.println("111*********************");
        System.out.println(address.getNum());
        System.out.println("*********************");
    }

    public Address findOne(Long num){
        return em.find(Address.class, num);
    }

    public List<Address> findAll(Long memberId){
        return em.createQuery("select a from Address a, Member m where m.num =: num",Address.class)
                .setParameter("num", memberId)
                .getResultList();
    }

    public List<Address> findAddr(Long num){
        return em.createQuery("select a from Address a join a.member m where m.num =: num",Address.class)
                .setParameter("num", num)
                .getResultList();
    }

    public void delete(Long num){

        Address address = em.find(Address.class, num);
        Member member = em.find(Member.class, address.getMember().getNum());
        Delivery delivery = em.find(Delivery.class, address.getDelivery().getNum());

        member.setAddrs(null);
        delivery.setAddress(null);

        em.remove(address);

    }



}
