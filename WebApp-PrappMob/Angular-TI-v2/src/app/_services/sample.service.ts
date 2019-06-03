import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ISample } from '../_models/sample';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { WebsocketService } from '../_services/websocket.service';
import { showToast } from '../toaster-helper';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SampleService {

  serverData: JSON;

  public _url: string = environment.ws_url + "samples";

  constructor(private http: HttpClient,
    private websocketservice: WebsocketService) { }

  getSamples(): Observable<ISample[]> {
    return this.http.get<ISample[]>(this._url).pipe(catchError(this.errorHandler));
  }

  getSamplesBySensors(sensor_id): Observable<ISample[]> {
    return this.http.get<ISample[]>(this._url + "/" + sensor_id).pipe(catchError(this.errorHandler));
  }

  createSample(value, timestamp, sensor_id){
    const self = this;
    let encoded_data = JSON.stringify({value, timestamp, sensor_id});
    console.log(encoded_data)
    let headers = new HttpHeaders().set("Content-Type", "application/json;charset=utf-8");
    
    return this.http.post(this._url, encoded_data, {headers})
                    .subscribe(
                      data => {
                      console.log("POST request is successful! ", encoded_data);
                    },
                      error => {
                      console.log("Error", error);
                      showToast("An error: "+ error.name +" occurred while adding your file, please try again!");
                    },

                    // COMPLETED
                      () => {
                        self.websocketservice.emitEventOnSampleSaved(value, timestamp, sensor_id);

                        //showToast("File: "+file_name+" saved!");
                        // add new file to file component and refresh
                    }
                    );
  }



  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || "Server error");
  }

}
