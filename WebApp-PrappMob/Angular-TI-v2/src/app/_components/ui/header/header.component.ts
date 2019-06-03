import { Component, OnInit } from '@angular/core';
import { DataSharingService } from 'src/app/_services/data-sharing.service';
import { showToast } from 'src/app/toaster-helper';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css',
              '../../../app.component.css']
})
export class HeaderComponent implements OnInit {

  isUserLoggedIn: boolean;
  user_name: string;

  constructor(private dataSharingService: DataSharingService) { 
    
  }

  ngOnInit() {
  }


}
