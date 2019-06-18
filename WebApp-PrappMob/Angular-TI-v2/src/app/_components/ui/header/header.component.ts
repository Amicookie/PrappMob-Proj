import { Component, OnInit } from '@angular/core';
import { DataSharingService } from 'src/app/_services/data-sharing.service';
import { showToast } from 'src/app/toaster-helper';
import { WorkstationComponent } from '../../workstation/workstation.component';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css',
              '../../../app.component.css']
})
export class HeaderComponent implements OnInit {

  isUserLoggedIn: boolean;
  user_name: string;

  _sampleBrowsing: boolean = false;
  _connectionToDB: boolean = false;

  constructor(private dataSharingService: DataSharingService) { 

    this.dataSharingService.isSensorSelected.subscribe(
      value=>{
        this._sampleBrowsing = value;
      }
    )

    this.dataSharingService.isDatabaseConnectionEstablished.subscribe(
      value=> {
        console.log("isDatabaseConnEstablished:"+value);
        this._connectionToDB = value;
        
      }
    )
    
  }

  ngOnInit() {
  }




}
