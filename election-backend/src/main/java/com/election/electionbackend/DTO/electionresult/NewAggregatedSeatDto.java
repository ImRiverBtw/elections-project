package com.election.electionbackend.DTO.electionresult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewAggregatedSeatDto {
    private long id; // ID of the party
    private String name; // Name of the party
    private int seatCount;

}
