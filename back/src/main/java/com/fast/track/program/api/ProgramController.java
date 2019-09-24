package com.fast.track.program.api;


import com.fast.track.program.api.vo.Course;
import com.fast.track.program.business.service.TrgProgramService;
import com.fast.track.program.domain.model.CourseType;
import com.fast.track.program.domain.model.TrgCourse;
import com.fast.track.program.domain.model.TrgCourseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@RequestMapping("api/program/")
public class ProgramController {


    private TrgProgramService programService;
    private ProgramTransformer transformer;

    @Autowired
    public ProgramController(TrgProgramService programService, ProgramTransformer transformer){
        this.programService = programService;
        this.transformer = transformer;
    }

    @PostMapping(value = "register-academic-course")
    public ResponseEntity<String> registerAcademicCourse(@RequestBody Course course){ //VO parameter front,course is a object
        //value from front become vo,tranform model get dat from vo,transform tanform to model,

        TrgCourse trgCourse = new TrgCourseImpl();//MODEL goal

        trgCourse.setCourseName(course.getCourseName());
        trgCourse.setCourseType(CourseType.get(course.getCourseType().ordinal()));
        trgCourse.setCourseCode(course.getCourseCode());


        programService.registerCourse(trgCourse);
        return ResponseEntity.ok("Course Created");
    }

    @GetMapping(value="courses")
    public ResponseEntity<List<Course>> findCourses(){

        List<TrgCourse> courses = programService.findCourses();
        return ResponseEntity.ok(transformer.toVos(courses));

    }



}
