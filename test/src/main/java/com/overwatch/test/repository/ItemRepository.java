package com.overwatch.test.repository;

import com.overwatch.test.domain.watch.Watch;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Watch watch) {

        if(watch.getNum() == null) {  // 저장가능.
            em.persist(watch);
        } else {
            em.merge(watch);
        }

    }

    public Watch findOne(Long id) {
        return em.find(Watch.class, id);
    }

    public List<Watch> findAll() {
        return em.createQuery("select i from Watch i order by i.num desc", Watch.class).getResultList();
    }


}
