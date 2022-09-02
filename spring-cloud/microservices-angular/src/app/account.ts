export class Account {
    id: number;
    accountNo: string;
    type: string;
    balance: number;
    customerId: number;
    
    public get accountType ( ) : string {
        if ( this.type == 'C' ) {
            return "Chequing";
        } else if ( this.type == 'S' ) {
            return "Saving";
        } else if ( this.type == 'G' ) {
            return "GIC";
        } else {
            return "Unknown account type"
        }
    }

}