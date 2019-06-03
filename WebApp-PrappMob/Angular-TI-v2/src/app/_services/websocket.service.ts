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

  emitEventOnChatMessageSent(username, message){

    //this.socket.emit('chat', {username, message});
  }


  // Sockets -----
  emitEventOnSampleSaved(value, timestamp, sensor_id){
    this.socket.emit('sampleSaved', JSON.stringify({value, timestamp, sensor_id}));
  }



  // OLD 
  emitEventOnFileSaved(username, file_name) {
    this.socket.emit('fileSaved', JSON.stringify({username, file_name}));
  }

  emitEventOnFileLocked(user_id, username, file_name, file_id){
    this.socket.emit('fileLocked', JSON.stringify({user_id, username, file_name, file_id}));
  }

  
  emitEventOnFileUnlocked(username, file_name, file_id, user_id){
    this.socket.emit('fileUnlocked', JSON.stringify({username, file_name, file_id, user_id}));
  }

  emitEventOnFileUpdated(file_name, username){
    this.socket.emit('fileUpdated', JSON.stringify({file_name, username}));
  }

  emitEventFileDeletion(file_id, file_name, username){
    this.socket.emit('fileDeletion', JSON.stringify({file_id, file_name, username}));
  }

  emitSocketClientTypeConnected(user_id) {
    this.socket.emit('socketConnectionTypeHelper', JSON.stringify({user_id}));
  }

}