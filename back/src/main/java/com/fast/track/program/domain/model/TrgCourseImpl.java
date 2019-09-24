package com.fast.track.program.domain.model;


import javax.persistence.*;

@Table (name = "TRG_COURSE")
@Entity(name = "Course")
public class TrgCourseImpl implements TrgCourse{

    //Pull up memebers @Overide  (alt+r+l)


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID") //ctrl+shift+f6 refactor
    private Long courseId;
    @Column(name = "COURSE_NAME")
    private String courseName;
    @Column(name = "COURSE_CODE")
    private String courseCode;
    @Column(name = "COURSE_TYPE")
    private CourseType courseType;

    public TrgCourseImpl() {
    }

    public TrgCourseImpl(Long courseId, String courseName, String courseCode, CourseType courseType) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseType = courseType;
    }

    @Override
    public Long getCourseId() {
        return courseId;
    }

    @Override
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String getCourseName() {
        return courseName;
    }

    @Override
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String getCourseCode() {
        return courseCode;
    }

    @Override
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public CourseType getCourseType() {
        return courseType;
    }

    @Override
    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
}
