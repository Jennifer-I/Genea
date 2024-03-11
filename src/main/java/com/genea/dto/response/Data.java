package com.genea.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Data {

    private Long id;
    private String domain;
    private String status;
    private String reference;

    @JsonProperty("receipt_number")
    private String receiptNumber;
    private Integer amount;
    private String message;

    @JsonProperty("gateway_response")
    private String gatewayResponse;

    @JsonProperty("paid_at")
    private String paid_at;

    @JsonProperty("created_at")
    private String created_at;
    private String channel;
    private String currency;

}
