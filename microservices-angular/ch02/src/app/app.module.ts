import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes, PreloadAllModules } from '@angular/router';

import { AppComponent } from './app.component';
import { CustomerService } from './customer-service';
import { AppCustomerComponent } from './app-customer.component';
import { AppCustomerListComponent } from './app-customer-list.component';
import { AppNewCustomerComponent } from './app-new-customer.component';

@NgModule({
  declarations: [
    AppComponent,
    AppCustomerListComponent,
    AppCustomerComponent,
    AppNewCustomerComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    RouterModule.forRoot([
      {
         path: 'customers',
         component: AppCustomerListComponent
      },
      {
        path: 'customer/:id',
        component: AppCustomerComponent
      },
      {
        path: 'newCustomer',
        component: AppNewCustomerComponent
      }

   ])

  ],
  providers: [CustomerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
