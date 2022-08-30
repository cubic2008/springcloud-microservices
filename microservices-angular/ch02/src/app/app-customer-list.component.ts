import { Component, OnInit } from '@angular/core';
import { Customer } from './customer';
import { CustomerService } from './customer-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customer-list',
  templateUrl: './app-customer-list.component.html',
  styleUrls: ['./app-customer-list.component.css']
})
export class AppCustomerListComponent implements OnInit {
  title = 'Cubic Bank Application';
  customerList: Customer[];

  constructor ( private router: Router, private customerService : CustomerService ) {
  }

  ngOnInit(): void {
    this.customerService.getAllCustomers().then ( customerList => this.customerList = customerList );
  }

  gotoDetail(customer: Customer): void {
    this.router.navigate(['/customer', customer.id]);
  }

  createCustomer(): void {
    this.router.navigate(['/newCustomer']);
  }

}
