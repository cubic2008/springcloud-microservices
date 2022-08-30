import { Injectable } from "@angular/core";
import { Http, Headers } from "@angular/http";

import 'rxjs/add/operator/toPromise';

import { Customer } from "./customer";
import { Account } from "./account";

@Injectable()
export class CustomerService {
    private customerUrl = 'http://localhost:2001/v1/customers';
    private appUrl = 'http://localhost:2001/v1/app';
    
    constructor(private http: Http) { }

    getAllCustomers(): Promise<Customer[]> {
      return this.http.get(this.customerUrl)
                 .toPromise()
                 .then(response => response.json() as Customer[])
                 .catch(this.handleError);
    }
    private handleError(error: any): Promise<any> {
      console.error('An error occurred', error); // for demo purposes only
      return Promise.reject(error.message || error);
    }

    getCustomer(id : number): Promise<Customer> {
        console.log ( "id", id );
        return this.http.get(this.customerUrl + "/" + id)
                   .toPromise()
                   .then ( response => response.json() as Customer )
                   .catch ( this.handleError );
      }

    getAppname ( ) : Promise<string> {
        return this.http.get(`${this.appUrl}/name`)
                .toPromise()
                .then ( response => response.json() as string )
                .catch(this.handleError);
    }

    private headers = new Headers({'Content-Type': 'application/json'});

    createCustomer ( customer : Customer ) : Promise<Customer> {
        return this.http.post(this.customerUrl, JSON.stringify(customer), {headers: this.headers})
                .toPromise()
                .then(res => res.json() as Customer)
                .catch(this.handleError);
    }

    updateCustomer ( customer : Customer ) : Promise<Customer> {
        return this.http.put(this.customerUrl + "/" + customer.id, JSON.stringify(customer), {headers: this.headers})
                .toPromise()
                .then(res => res.json() as Customer)
                .catch(this.handleError);
    }
  }
