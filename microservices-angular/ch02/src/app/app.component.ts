import { Component, OnInit } from '@angular/core';
import { Customer } from './customer';
import { CustomerService } from './customer-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  title: string;

  constructor ( private _customerService : CustomerService ) { 
  }

  ngOnInit(): void {
    this._customerService.getAppname().then ( (appInfo: any) => this.title = appInfo.appName );
  }

}
