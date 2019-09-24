import { Component } from '@angular/core';

import {Observable} from 'rxjs';
import {Course, CourseType} from './model/course.model';
import {ProgramService} from './service/program.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'app';

  $courses: Observable<Course[]>;

  courseName : string='';
  courseType : CourseType;
  courseCode : string='';



  constructor(private programService: ProgramService) {
    this.initData();

  }

  initData() {
    this.$courses = this.programService.findAllCourse();

    this.$courses.subscribe(data => {
      console.log(data);
    }, error => {
      console.log(error);
    });
  }

  registerNewCourse():void{

    this.programService.registerNewCourse({
      courseCode :"codeasd asd",
      courseId : 0,
      courseName : "new course",
      courseType : CourseType.ACADEMIC
      // courseCode :this.courseCode,
      // courseId : 0,
      // courseName :this.courseName,
      // courseType : this.courseType

    }).subscribe(data=>{

      this.initData();

    }, error1 => {
      this.initData();
    });

  }



}
