package org.example.tickettoride.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketOperationResponse {
    private String result;
    private Integer change;
    private Integer lackOf;
    private String currency;
}
