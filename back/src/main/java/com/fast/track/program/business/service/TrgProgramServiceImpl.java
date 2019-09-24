package com.fast.track.program.business.service;


import com.fast.track.program.domain.dao.TrgProgramDao;
import com.fast.track.program.domain.model.TrgCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TrgProgramServiceImpl implements TrgProgramService{

    private TrgProgramDao programDao;

    @Autowired
    public TrgProgramServiceImpl(TrgProgramDao programDao) {
        this.programDao = programDao;
    }


    @Override
    public void registerCourse(TrgCourse course) {
        programDao.create(course);
    }

    @Override
    public List<TrgCourse> findCourses() { return programDao.findCourses();
    }
}
