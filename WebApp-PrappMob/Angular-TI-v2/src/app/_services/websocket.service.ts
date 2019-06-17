import { Injectable } from '@angular/core';
import * as io from 'socket.io-client';
import { Observable, Subject, BehaviorSubject } from 'rxjs';
import { environment } from '../../environments/environment';
import { showToast } from '../toaster-helper';
import { Socket } from 'ng6-socket-io';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private socket;

  public _sample_added: BehaviorSubject<any> = new BehaviorSubject({sensor_id: -1});


  constructor() { }

  close() {
    this.socket.disconnect();
  }

  connect(): Subject<MessageEvent> {
    this.socket = io.connect(environment.ws_url);

    // We define our observable which will observe any incoming messages
    // from our socket.io server.

    let observable = new Observable(observer => {
      this.socket.on('message', (data) => {
        console.log("Received message from Websocket Server")
        observer.next(data);
      })

      // Zapisanie sample
      this.socket.on('sampleSaved', (data) => {
        showToast('New Sample added on sensor ' + data.sensor_id + '!');
        this._sample_added.next({sensor_id: data.sensor_id});

      })

      return () => {
        this.socket.disconnect();
      }
    });

    // We define our Observer which will listen to messages
    // from our other components and send messages back to our
    // socket server whenever the `next()` method is called.

    let observer = {
      next: (data: Object) => {
        this.socket.emit('chat', JSON.stringify(data));
      },
    }

    return Subject.create(observer, observable);
  }

  // Sockets -----
  emitEventOnSampleSaved(value, timestamp, sensor_id) {
    this.socket.emit('sampleSaved', JSON.stringify({value, timestamp, sensor_id}));
  }

}