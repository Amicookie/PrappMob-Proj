import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataSharingService {
  public isUserLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public isWorkstationSelected: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public isSensorSelected: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  
  constructor() { 
    if(localStorage.getItem('user_id')===null){
      this.isUserLoggedIn.next(false);
    } else {
      this.isUserLoggedIn.next(true);
    }
    if(localStorage.getItem('workstation_id')===null){
      this.isWorkstationSelected.next(false);
    } else {
      this.isWorkstationSelected.next(true);
    }
    if(localStorage.getItem('sensor_id')===null){
      this.isSensorSelected.next(false);
    } else {
      this.isSensorSelected.next(true);
    }
    
   }
  

  
}
