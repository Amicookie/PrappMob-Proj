import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddSampleComponent } from './_components/add/add-sample/add-sample.component';
import { ReadDataComponent } from './_components/read/read-data/read-data.component';
import { FooterComponent } from './_components/ui/footer/footer.component';
import { HeaderComponent } from './_components/ui/header/header.component';
import { LayoutComponent } from './_components/ui/layout/layout.component';

@NgModule({
  declarations: [
    AppComponent,
    AddSampleComponent,
    ReadDataComponent,
    FooterComponent,
    HeaderComponent,
    LayoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
