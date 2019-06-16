import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery';
import * as bootstrap from 'bootstrap';
import { WebsocketService } from './_services/websocket.service';
import { Subject } from 'rxjs';
import { ChatService } from './_services/chat.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'PrappMob-Proj';

  constructor(private chat: ChatService) {}

  ngOnInit(){
    this.chat.messages.subscribe(message => {
      console.log(message);
    }); 
  }

  sendMessage() {
    this.chat.sendMessage("username","Test Message");
  }




}
