package com.genea.dto.request;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter


public class InitializePaymentRequest {
    private BigDecimal amount;
    private String email;
    private String reference;

}
