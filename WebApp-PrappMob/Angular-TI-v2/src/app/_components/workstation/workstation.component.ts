import { Component, OnInit } from '@angular/core';
import { WorkstationService } from 'src/app/_services/workstation.service';
import { WebsocketService } from 'src/app/_services/websocket.service';
import { DataSharingService } from 'src/app/_services/data-sharing.service';

@Component({
  selector: 'app-workstation',
  templateUrl: './workstation.component.html',
  styleUrls: ['./workstation.component.css']
})
export class WorkstationComponent implements OnInit {

  public workstations: any;
  public errorMsg;


  // If clicked on one of the workstations ---
  clicked_station_name = "";
  clicked_station_id = -1;

  _navigateToSensors = false;


  constructor(private _workstationService: WorkstationService,
    private webSocketService: WebsocketService,
    private dataSharingService: DataSharingService) {

      this.dataSharingService.isWorkstationSelected.subscribe(
        value=>{
          this._navigateToSensors = value;
        }
      )


     }

  ngOnInit() {
    this._workstationService.getWorkstations()
    .subscribe(data => this.workstations = data,
                error => this.errorMsg = error);
  }


  navigateToSensors(station_id, station_name){
    this.clicked_station_id = station_id;
    this.clicked_station_name = station_name;
    this._navigateToSensors = true;
    localStorage.setItem('station_id', this.clicked_station_id.toString());
    localStorage.setItem('station_name', this.clicked_station_name);
    this.dataSharingService.isWorkstationSelected.next(true);
  }

}
