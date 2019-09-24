import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Course} from '../model/course.model';
import {environment} from '../../environments/environment';


export const programBaseUrl ='api/program/';

@Injectable()
export class ProgramService {

  constructor (private  http : HttpClient){


  }

  //create
  findAllCourse(): Observable<Course[]>{
    return this.http.get<Course[]>(environment.baseUrl.concat(programBaseUrl).concat('courses'));
  }


  registerNewCourse(course : Course){
    return  this.http.post(environment.baseUrl.concat(programBaseUrl).concat('register-academic-course'), course);
  }

}
