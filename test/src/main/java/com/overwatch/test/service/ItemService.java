package com.overwatch.test.service;

import com.overwatch.test.domain.watch.Watch;
import com.overwatch.test.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // updateItem은 나중에 구현~~!


    // findOne과 findAll, save 등을 함.
    public void save(Watch watch) {
        itemRepository.save(watch);
    }

    public Watch findOne(Long watchId) {
        return itemRepository.findOne(watchId);
    }

    public List<Watch> findItems() {
        return itemRepository.findAll();
    }

}
