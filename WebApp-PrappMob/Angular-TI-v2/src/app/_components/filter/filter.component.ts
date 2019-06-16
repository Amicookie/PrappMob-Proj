import { Component, OnInit } from '@angular/core';
import { WorkstationService } from 'src/app/_services/workstation.service';
import { SensorService } from 'src/app/_services/sensor.service';
import { SampleService } from 'src/app/_services/sample.service';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css', '../workstation/workstation.component.css', '../../app.component.css']
})
export class FilterComponent implements OnInit {

  private sensors: any;
  private workstations: any;
  private filteredSamples: any;

  private _filtered = false;
  private _connectionToDB = false;

  public errorMsg;

  constructor(private _workstationService: WorkstationService,
    private _sensorService: SensorService,
    private _sampleService: SampleService) { }

  ngOnInit() {

    this._sensorService.getSensors()
      .subscribe(data => {
        this.sensors = data;
        this._connectionToDB = true;
      },
        error => {
          this.errorMsg = error;
          this._connectionToDB = false;
        });


    this._workstationService.getWorkstations()
      .subscribe(data => this.workstations = data,
        error => this.errorMsg = error);

  }


  filterSamples(dateFrom = null, dateTo = null, workstationId = -1, sensorId = -1) {

    this._sampleService.filterSamples(dateFrom, dateTo, workstationId, sensorId)
      .subscribe(data => {
        this.filteredSamples = data;
        this._filtered = true;
      },
        error => this.errorMsg = error);
  }


  backToFilterView() {
    this._filtered = false;
    this.filteredSamples = null;
  }


}
