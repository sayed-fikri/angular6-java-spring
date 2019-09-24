package com.fast.track.program.business.service;

import com.fast.track.TrackApplicationTests;
import com.fast.track.program.domain.model.CourseType;
import com.fast.track.program.domain.model.TrgCourse;
import com.fast.track.program.domain.model.TrgCourseImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TrgProgramServiceImplTest extends TrackApplicationTests {


    @Autowired
    TrgProgramService programService;

    @Test
    public void registerCourse() {
        TrgCourse matCourse = new TrgCourseImpl();

        matCourse.setCourseName("Irichment");
        matCourse.setCourseCode("ID_1283");
        matCourse.setCourseType(CourseType.ACADEMIC);


        programService.registerCourse(matCourse);

    }

}