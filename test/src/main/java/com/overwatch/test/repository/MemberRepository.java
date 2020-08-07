package com.overwatch.test.repository;

import com.overwatch.test.domain.Address;
import com.overwatch.test.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    @PersistenceContext //  entityManager를 자동으로 주입해주는 애노테이션
    private EntityManager em;

    public int save(Member member) {    //  저장하는 메서드
        em.persist(member);
        return 1;
    }

    public Member findOne(Long num) {
        return em.find(Member.class, num);
    }


    /*public Member findOneTemp(String id){
        return em.find(Member.class, id);
    }*/


    public Member findOneTemps(String id) {
        Member member = new Member();
        String jpql = "select m from Member m where m.id = :id";

        TypedQuery<Member> query = em.createQuery(jpql, Member.class);
        query.setParameter("id", id);


        try {
            member = query.getSingleResult();
           /* Long result = member.getNum();

            System.out.println("id랑 password로 찾아낸 pk" + result);*/

        } catch (Exception e) {
            System.out.println("여기는 들어오니??");
            member.setNum(-1L);
            member.setId("-1");
            member.setPassword("-1");
        }

        return member;
    }

    public Member findOneTemps(Long num) {
        Member member = new Member();
        String jpql = "select m from Member m where m.num = :num";

        TypedQuery<Member> query = em.createQuery(jpql, Member.class);
        query.setParameter("num", num);


        try {
            member = query.getSingleResult();
           /* Long result = member.getNum();

            System.out.println("id랑 password로 찾아낸 pk" + result);*/

        } catch (Exception e) {
            System.out.println("여기는 들어오니??");
            member.setNum(-1L);
            member.setId("-1");
            member.setPassword("-1");
        }

        return member;
    }


    public List<Member> findAll() {  //  멤버 전체를 찾는 메서드
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name =: name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public int checkMemberId(String id) {    //  회원 가입시 중복아이디 체크 처리 관련 메서드
        int result = em.createQuery("select m from Member m where m.id =: id", Member.class)
                .setParameter("id", id).getResultList().size();

        return result;
    }

    public int update(String id, String name, String password, String phone, String birthday) {
        Optional<Member> member = Optional.ofNullable(findOneTemps(id));
        final int[] result = {0};
        member.ifPresent(selectMember -> {
            selectMember.setName(name);
            selectMember.setPassword(password);
            selectMember.setPhone(phone);
            selectMember.setBirthday(birthday);
            result[0] = save(selectMember);

        });

        return result[0];

    }

    public int updateKN(String id, String name, String phone, String birthday) {
        Optional<Member> member = Optional.ofNullable(findOneTemps(id));
        int[] result = {0};
        member.ifPresent(selectMember -> {
            selectMember.setName(name);
            selectMember.setPhone(phone);
            selectMember.setBirthday(birthday);
            result[0] = save(selectMember);

        });
        return result[0];
    }


    public int checkMemberPhone(String phone) {    //  회원 가입시 중복아이디 체크 처리 관련 메서드
        int result = em.createQuery("select m from Member m where m.phone =: phone", Member.class)
                .setParameter("phone", phone).getResultList().size();

        return result;
    }

    public Member findId(String id, String password) {
        Member member = new Member();
        String jpql = "select m from Member m where m.id = :id and m.password = :password";

        TypedQuery<Member> query = em.createQuery(jpql, Member.class);
        query.setParameter("id", id);
        query.setParameter("password", password);

        try {
            member = query.getSingleResult();
            Long result = member.getNum();

            System.out.println("id랑 password로 찾아낸 pk" + result);

        } catch (Exception e) {
            System.out.println("여기는 들어오니??");
            member.setNum(-1L);
            member.setId("-1");
            member.setPassword("-1");
        }

        return member;
    }

    /*public void saveAddr(Address address) {
        em.persist(address);
    }*/
}
