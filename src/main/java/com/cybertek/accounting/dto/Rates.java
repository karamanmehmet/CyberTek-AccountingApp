package com.cybertek.accounting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Rates {

    @JsonProperty("CAD")
    private double CAD;

    @JsonProperty("INR")
    private double INR;

    @JsonProperty("EUR")
    private double EUR;

    @JsonProperty("AUD")
    private double AUD;

    @JsonProperty("GBP")
    private double GBP;
}
