import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { WebsocketService } from './websocket.service';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { IWorkstation } from '../_models/workstation';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class WorkstationService {

  serverData: JSON;

  public _url: string = environment.ws_url+"workstations";

  constructor(private http: HttpClient, 
    private websocketService: WebsocketService) {}

  getWorkstations(): Observable<IWorkstation[]>{
      return this.http.get<IWorkstation[]>(this._url).pipe(catchError(this.errorHandler));
  }

  getWorkstation(station_id): Observable<IWorkstation[]>{
    return this.http.get<IWorkstation[]>(this._url+"/"+station_id).pipe(catchError(this.errorHandler));
  }


  errorHandler(error: HttpErrorResponse){
    return throwError(error.message || "Server error"); 
  }
  
}
