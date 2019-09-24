package com.fast.track.program.api;

import com.fast.track.program.api.vo.Course;
import com.fast.track.program.api.vo.CourseType;
import com.fast.track.program.domain.model.TrgCourse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ProgramTransformer {

    public Course toVo(TrgCourse course){
        Course vo = new Course();
        vo.setCourseCode(course.getCourseCode());
        vo.setCourseId(course.getCourseId());
        vo.setCourseName(course.getCourseName());
        vo.setCourseType(CourseType.get(course.getCourseType().ordinal()));
        return vo;
    }

    public List<Course> toVos(List<TrgCourse> courses){
        List<Course> vos = new ArrayList<>();
        for(TrgCourse course : courses){
            vos.add(toVo(course));
        }

        return vos;
    }
}
