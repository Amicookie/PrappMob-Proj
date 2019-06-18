import { Component, OnInit } from '@angular/core';
import { SensorService } from 'src/app/_services/sensor.service';
import { WebsocketService } from 'src/app/_services/websocket.service';
import { DataSharingService } from 'src/app/_services/data-sharing.service';
import { ISensor } from 'src/app/_models/sensor';

@Component({
  selector: 'app-sensor',
  templateUrl: './sensor.component.html',
  styleUrls: ['./sensor.component.css', '../workstation/workstation.component.css', '../../app.component.css']
})
export class SensorComponent implements OnInit {

  public sensors: any;
  public errorMsg;

  workstation_name = "";
  workstation_id = -1;


  clicked_sensor_name = "";
  clicked_sensor_id = -1;

  _navigateToSamples = false;


  constructor(private _sensorService: SensorService,
    private webSocketService: WebsocketService,
    private dataSharingService: DataSharingService) {

      this.dataSharingService.isSensorSelected.subscribe(
        value=>{
          this._navigateToSamples = value;
        }
      )


     }

  ngOnInit() {

    this.workstation_name = localStorage.getItem('station_name');
    this.workstation_id = Number(localStorage.getItem('station_id'));

    this._sensorService.getSensorsByStation(this.workstation_id)
    .subscribe(data => this.sensors = data,
               error => this.errorMsg = error);

  }


  navigateToSamples(sensor_id, sensor_name){
    this.clicked_sensor_id = sensor_id;
    this.clicked_sensor_name = sensor_name;
    this._navigateToSamples = true;
    localStorage.setItem('sensor_id', this.clicked_sensor_id.toString());
    localStorage.setItem('sensor_name', this.clicked_sensor_name);
    // this.dataSharingService.isWorkstationSelected.next(true);
    // localStorage.removeItem('station_name');
    // localStorage.removeItem('station_id');
    this.dataSharingService.isSensorSelected.next(true);
  }

  backToWorkstations(){
    localStorage.removeItem('station_name');
    localStorage.removeItem('station_id');
    this.dataSharingService.isWorkstationSelected.next(false);
  }

}
