export interface Course {

  courseCode : string;
  courseName : string;
  courseId : number;
  courseType :CourseType

}


export enum CourseType {
  ACADEMIC,
  CO_CURRICULUM
}
