import { Account } from './account';

export class Customer {
    id: number;
    customerNo: string;
    name: string;
    email: string;
    address: string;
    accountList: Account[];

    // cloneAAA(): Customer {
    //     let customer = new Customer ( );
    //     customer.id = this.id;
    //     customer.customerNo = this.customerNo;
    //     customer.name = this.name;
    //     customer.email = this.email;
    //     customer.address = this.address;
    //     return customer;
    // }

    // test() : string {
    //     return "abc";
    // }
}
