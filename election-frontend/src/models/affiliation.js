export class Affiliation {
    constructor(shortName, longName, votes, seats){
        this.shortName = shortName;
        this.longName = longName;
        this.votes = votes;
        this.seats = seats
    }

    //create a party with dummy data
    static createParty(shortName){
        let longName = shortName;
        const minVotes = 1;
        const maxVotes = 2000;
        let voteCount = Math.floor(Math.random() * (maxVotes - minVotes + 1)) + minVotes;
        const votesPerSeat = 69.485
        let seatCount = Math.floor(voteCount / votesPerSeat)
        return new Affiliation(
            shortName,
            longName,
            voteCount,
            seatCount
        )
    }

}
