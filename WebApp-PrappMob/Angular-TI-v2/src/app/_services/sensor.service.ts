import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { WebsocketService } from './websocket.service';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { ISensor } from '../_models/sensor';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SensorService {

  serverData: JSON;

  public _url: string = environment.ws_url+"sensors";

  constructor(private http: HttpClient, 
    private websocketService: WebsocketService) {}

  getSensors(): Observable<ISensor[]>{
      return this.http.get<ISensor[]>(this._url).pipe(catchError(this.errorHandler));
  }

  getSensorsByStation(station_id): Observable<ISensor[]>{
    return this.http.get<ISensor[]>(this._url+"/"+station_id).pipe(catchError(this.errorHandler));
  }



  errorHandler(error: HttpErrorResponse){
    return throwError(error.message || "Server error"); 
  }
}
