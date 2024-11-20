export class Affiliation {
    constructor(id, name, votes){
        this.id = id;
        this.name = name;
        this.votes = votes;
    }

    //create a party with dummy data
    static createParty(id){

        return new Affiliation(
            shortName,
            longName,
            voteCount,
            seatCount
        )
    }


}
