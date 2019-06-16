import { Injectable } from '@angular/core';
import * as io from 'socket.io-client';
import { Observable, Subject, BehaviorSubject } from 'rxjs';
import { environment } from '../../environments/environment';
import { showToast } from '../toaster-helper';
import { Socket } from 'ng6-socket-io';

@Injectable({
  providedIn: 'root'
})


@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private socket;

  constructor() { }

  close() {
    this.socket.disconnect();
  }

  connect(): Subject<MessageEvent>{
    this.socket = io.connect(environment.ws_url);
    
    let observable = new Observable(observer => {

      // Zapisanie sample
      this.socket.on('sampleSaved', (data)=>{
        showToast('New Sample added on sensor ' + data.sensor_id + '!');
      })

      return() => {
        this.socket.disconnect();
      }
    })

    let observer = {
      next: (data: Object) => {
        this.socket.emit('chat', JSON.stringify(data));
      },
    }
    return Subject.create(observer,observable);
  }


  //obsługa eventów

  // Sockets -----
  emitEventOnSampleSaved(value, timestamp, sensor_id){
    this.socket.emit('sampleSaved', JSON.stringify({value, timestamp, sensor_id}));
  }

}