package com.fast.track.program.domain.dao;


import com.fast.track.TrackApplicationTests;
import com.fast.track.program.domain.model.CourseType;
import com.fast.track.program.domain.model.TrgCourse;
import com.fast.track.program.domain.model.TrgCourseImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TrgProgramDaoImplTest extends TrackApplicationTests {

    @Autowired
    private TrgProgramDao programDao;

    @Test
    public void testCreateCourse(){


        TrgCourse matCourse = new TrgCourseImpl();

        matCourse.setCourseName("Earistic");
        matCourse.setCourseCode("ID_1233");
        matCourse.setCourseType(CourseType.ACADEMIC);


        programDao.create(matCourse);


    }

}