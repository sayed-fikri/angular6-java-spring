import {Injectable} from "@angular/core";
import {environment} from "@env/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Therapist} from "@app/shared/models/identity/therapist.model";
import {TherapistProfile} from "@app/therapist/profile/therapist-profile/component/therapist-profile.model";
import {User} from "@app/shared/models/identity/user.model";

@Injectable()
export class TherapistIdentityService{

    private IDENTITY_API: string = environment.endpoint+ '/api/therapist/identity';

    constructor (private http: HttpClient,){

    }

//    ============================================================================
//    THERAPIST
//    ============================================================================

    // Get Current User info
    findTherapist(): Observable<Therapist> {
        return this.http.get<Therapist>(this.IDENTITY_API +'/therapists' );
    }

    findTherapistProfile(): Observable<TherapistProfile> {
        return this.http.get<TherapistProfile>(this.IDENTITY_API +'/therapists-profile' );
    }

    therapistUpdate(code: Therapist){
        return this.http.put (this.IDENTITY_API+'/therapists/'+code.code,
            JSON.stringify(code));
    }

    therapistUserUpdate(code: User){
        return this.http.put (this.IDENTITY_API+'/therapists-user',
            JSON.stringify(code));
    }








}
