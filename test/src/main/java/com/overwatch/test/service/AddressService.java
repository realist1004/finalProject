package com.overwatch.test.service;

import com.overwatch.test.domain.Address;
import com.overwatch.test.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;


    @Transactional
    public Long join(Address address){
        addressRepository.save(address);
        return address.getNum();
    }

    @Transactional
    public List<Address> getAllAddr(Long memberId) {
        return addressRepository.findAddr(memberId);
    }

    @Transactional
    public List<Address> findAddrs(Long num){
        return addressRepository.findAddr(num);
    }


    @Transactional
    public int delete(Long addr_num){
        addressRepository.delete(addr_num);
        return 1;
    }
}
