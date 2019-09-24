package com.fast.track.program.business.service;

import com.fast.track.program.domain.model.TrgCourse;

import java.util.List;

public interface TrgProgramService {

    public void registerCourse(TrgCourse course);

    List<TrgCourse> findCourses();

}
