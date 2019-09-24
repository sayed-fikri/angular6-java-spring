package com.fast.track.program.domain.model;

public interface TrgCourse {
    Long getCourseId();

    void setCourseId(Long courseId);

    String getCourseName();

    void setCourseName(String CourseName);

    String getCourseCode();

    void setCourseCode(String courseCode);

    CourseType getCourseType();

    void setCourseType(CourseType courseType);
}
