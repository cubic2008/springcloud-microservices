import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router }   from '@angular/router';
import { Location }                 from '@angular/common';
import 'rxjs/add/operator/switchMap';

import { Customer } from './customer';
import { CustomerService } from './customer-service';

@Component({
  moduleId: module.id,  
  selector: 'app-new-customer',
  templateUrl: './app-new-customer.component.html',
  styleUrls: ['./app-new-customer.component.css']
})
export class AppNewCustomerComponent implements OnInit {
  customer: Customer;

  constructor (
        private customerService : CustomerService,
        private router: Router ) {
  }

  ngOnInit(): void {
    console.log ( "In AppNewCustomerComponent.ngOnInit()." );
    this.customer = new Customer ( );
  }

  create(): void {
    this.customerService.createCustomer ( this.customer ).then ( customer => this.router.navigate(['/customers']) );
    // this.router.navigate(['/customers']);
  }

}
