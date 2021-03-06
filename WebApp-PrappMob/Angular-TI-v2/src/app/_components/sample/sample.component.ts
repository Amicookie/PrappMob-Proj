import { Component, OnInit } from '@angular/core';
import { SampleService } from 'src/app/_services/sample.service';
import { WebsocketService } from 'src/app/_services/websocket.service';
import { DataSharingService } from 'src/app/_services/data-sharing.service';

@Component({
  selector: 'app-sample',
  templateUrl: './sample.component.html',
  styleUrls: ['./sample.component.css', '../workstation/workstation.component.css', '../../app.component.css']
})
export class SampleComponent implements OnInit {

  public samples: any;
  public errorMsg;

  workstation_name = "";
  workstation_id = -1;
  sensor_name = "";
  sensor_id = -1;



  _navigateToSamples = false;
  value_entered = false;

  constructor(private _sampleService: SampleService,
    private webSocketService: WebsocketService,
    private dataSharingService: DataSharingService) {

      this.dataSharingService.isSensorSelected.subscribe(
        value=>{
          this._navigateToSamples = value;
        }
      )

      this.webSocketService._sample_added.subscribe(
        value => {
          if (value.sensor_id !== -1) {
  
            console.log('weszlo do ifa');
            console.log('value.sample_id' + value.sensor_id);

            this._sampleService.getSamplesBySensors(value.sensor_id)
            .subscribe(data => this.samples = data);
            
            // 
            // if(this.samples[this.samples.length-1].file_last_editor_id == localStorage.getItem('user_id')){
            //    this.file_new_name_input = "";
            //     this.file_new_content_input = "";
            // }

            this.webSocketService._sample_added.next({sensor_id: -1});
            console.log('wartosc sample_added po .next(-1)'+value);
  
          }
        }
      )


     }

  ngOnInit() {

    this.workstation_name = localStorage.getItem('station_name');
    this.workstation_id = Number(localStorage.getItem('station_id'));
    this.sensor_name = localStorage.getItem('sensor_name');
    this.sensor_id = Number(localStorage.getItem('sensor_id'));


    this._sampleService.getSamplesBySensors(this.sensor_id)
    .subscribe(data => this.samples = data,
               error => this.errorMsg = error);

  }


  backToSensors(){
    localStorage.removeItem('sensor_id');
    localStorage.removeItem('sensor_name');
    this.dataSharingService.isSensorSelected.next(false);
  }


  addSample(sample_value_input, sample_timestamp_input = null){
    if(sample_value_input == null) {
      this.value_entered = true;
    } else {
      this._sampleService.createSample(sample_value_input, sample_timestamp_input, localStorage.getItem('sensor_id'));
      this.value_entered = false;
    }//wyczyść zawartość textarea jeśli utworzono xd

  }

}
