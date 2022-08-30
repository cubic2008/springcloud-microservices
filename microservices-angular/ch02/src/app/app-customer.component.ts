import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router }   from '@angular/router';
import { Location }                 from '@angular/common';
import 'rxjs/add/operator/switchMap';

import { Customer } from './customer';
import { CustomerService } from './customer-service';

@Component({
  moduleId: module.id,  
  selector: 'app-customer',
  templateUrl: './app-customer.component.html',
  styleUrls: ['./app-customer.component.css']
})
export class AppCustomerComponent implements OnInit {
  private customer: Customer;
  private editMode: boolean = false;
  private modifiedCustomer: Customer;

  constructor (
        private customerService : CustomerService,
        private route: ActivatedRoute,
        private location: Location,
        private router: Router ) {
  }

  ngOnInit(): void {
    console.log ( "In AppCustomerComponent.ngOnInit()." );
    this.route.params.subscribe ( params => {
        console.log ( params['id'] );
        this.customerService.getCustomer(+params['id']).then ( customer => this.customer = customer );
     } );
  }

  goBack(): void {
    this.location.back();
  }

  edit(): void {
        // this.modifiedCustomer = this.customer.cloneAAA();
    //   console.log ( "test output", this.customer.test() );
    this.modifiedCustomer = Object.assign({}, this.customer);
    this.editMode = true;
  }

  cancel(): void {
      this.editMode = false;
  }

  save(): void {
    this.customerService.updateCustomer ( this.modifiedCustomer ).then ( customer => this.router.navigate(['/customers']) );
  }

  displayableAccountType ( type: string ) : string {
    console.log ( "get accountType() is called." );
    if ( type == 'C' ) {
        return "Chequing";
    } else if ( type == 'S' ) {
        return "Saving";
    } else if ( type == 'G' ) {
        return "GIC";
    } else {
        return "Unknown account type"
    }
}


}
