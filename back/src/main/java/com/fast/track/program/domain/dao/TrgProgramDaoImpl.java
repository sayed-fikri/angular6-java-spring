package com.fast.track.program.domain.dao;


import com.fast.track.program.domain.model.TrgCourse;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public class TrgProgramDaoImpl implements TrgProgramDao {

    @PersistenceContext //injectable
    private EntityManager em;

    //Create
    @Override
    public void create(TrgCourse course){
        em.persist(course); //insert into course...same as database mysql
        em.flush();
    }


    public List<TrgCourse> findCourses(){
        Query query = em.createQuery("select kambing from Course kambing");
        return query.getResultList();
    }
    //Read
    //Update
    //Delete


}
