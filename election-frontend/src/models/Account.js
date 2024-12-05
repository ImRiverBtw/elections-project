export class Account {
    id;
    username;
    email;
    role;

    constructor(id, username = null) {
        this.id = id;
        this.username = username;
    }

    equalsById(other){
        return other != null && (other.id === this.id);
    }
    equals(other){
        return other != null &&
            (other.id === this.id) &&
            (other.username === this.username) &&
            (other.email === this.email) &&
            (other.role === this.role);
    }

    static copyConstructor(account){
        if (account === null || account === undefined) return null;
        return Object.assign(new Account(0), account);
    }

    static createSample(id) {
        let newAccount = new Account(id, `account${id}`);
        newAccount.email = `account${id}@hva.nl`;
        newAccount.role = "USER";
        return newAccount;
    }
}