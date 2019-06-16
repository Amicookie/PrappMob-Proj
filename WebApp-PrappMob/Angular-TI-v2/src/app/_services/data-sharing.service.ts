import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { catchError } from 'rxjs/operators';
import { WorkstationService } from './workstation.service';

@Injectable({
  providedIn: 'root'
})
export class DataSharingService {
  public isWorkstationSelected: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public isSensorSelected: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public isDatabaseConnectionEstablished: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  //private _dataGained: String = "";

  //  private _StationComponent: WorkstationComponent;

  constructor(private http: HttpClient,
    private _WSService: WorkstationService) {
    if (localStorage.getItem('workstation_id') === null) {
      this.isWorkstationSelected.next(false);
    } else {
      this.isWorkstationSelected.next(true);
    }
    if (localStorage.getItem('sensor_id') === null) {
      this.isSensorSelected.next(false);
    } else {
      this.isSensorSelected.next(true);
    }// }

    this._WSService.getWorkstation(1).subscribe(data => {
      //this._dataGained = data;
      this.isDatabaseConnectionEstablished.next(true);
      console.log("CheckConnection() ---");
    },
      error => {
        console.log(error);
        console.log(this.errorHandler(error));
      //  this._dataGained = null;
        this.isDatabaseConnectionEstablished.next(false);
      });
  }


  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || "Server error");
  }
}
