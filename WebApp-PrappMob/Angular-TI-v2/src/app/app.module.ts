import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { UiModule } from './_components/ui/ui.module';

import { HttpClientModule, HttpClientJsonpModule } from '@angular/common/http';

import { WebsocketService } from './_services/websocket.service';

import { FormsModule } from '@angular/forms';
import { DataSharingService } from './_services/data-sharing.service';
import { HeaderComponent } from './_components/ui/header/header.component';
import { LayoutComponent } from './_components/ui/layout/layout.component';
import { WorkstationComponent } from './_components/workstation/workstation.component';
import { SampleComponent } from './_components/sample/sample.component';
import { SensorComponent } from './_components/sensor/sensor.component';
import { FilterComponent } from './_components/filter/filter.component';

@NgModule({
  declarations: [
    AppComponent,
    WorkstationComponent,
    SampleComponent,
    SensorComponent,
    FilterComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpClientJsonpModule,
    AppRoutingModule,
    UiModule,
    FormsModule
  ],
  providers: [
    WebsocketService,
    DataSharingService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
