package com.fast.track.program.domain.dao;

import com.fast.track.program.domain.model.TrgCourse;

import java.util.List;

public interface TrgProgramDao {
    //Create
    void create (TrgCourse course);

    List<TrgCourse> findCourses();
}
