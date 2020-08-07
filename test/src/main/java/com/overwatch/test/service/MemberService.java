package com.overwatch.test.service;

import com.overwatch.test.domain.Address;
import com.overwatch.test.domain.Member;
import com.overwatch.test.exception.PasswordWrongException;
import com.overwatch.test.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long join(Member member) {

        //valdateDuplicateMember(member); //  중복 회운 검사 메서드

        String encodePassword = bCryptPasswordEncoder.encode(member.getPassword());
//        System.out.println(member.getPassword()+"================================================");
//        System.out.println(encodePassword+"===============================================");
        member.setPassword(encodePassword);
        repository.save(member);
        return member.getNum();
    }

    //  전체 회원 조회 메서드
    public List<Member> findMembers() {
        return repository.findAll();
    }

    //  특정 ID번호에 해당하는 객체를 조회하는 메서드
    public Member findOne(Long memberNum) {
        return repository.findOne(memberNum);
    }

    /*//  특정 ID번호에 해당하는 객체를 조회하는 메서드
    public Member findOneId(String id){
        return repository.findOneTemp(id);
    }*/

    public Member findMember(String id) {
        return repository.findOneTemps(id);
    }

    public Member findMember(Long num) {
        return repository.findOneTemps(num);
    }

    public Member findOneTemp(String id, String pwd) {

        return repository.findId(id, pwd);
    }


    @Transactional
    public int check(String id) {

        return repository.checkMemberId(id);
    }

    @Transactional
    public int checkPhone(String phone) {

        return repository.checkMemberPhone(phone);
    }

    @Transactional
    public int update(String id, String name, String password, String phone, String birthday) {

        String encodePassword = bCryptPasswordEncoder.encode(password);

        return repository.update(id, name, encodePassword, phone, birthday);
    }

    @Transactional
    public int updateKN(String id, String name, String phone, String birthday) {
        return repository.updateKN(id, name, phone, birthday);
    }



    /*public void saveAddr(Address address) {
        repository.saveAddr(address);
    }*/
}

