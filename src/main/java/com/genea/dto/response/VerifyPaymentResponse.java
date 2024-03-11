package com.genea.dto.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyPaymentResponse {

    private boolean status;
    private String message;

    private verificationData data;



    @Getter
    @Setter
    public static class verificationData {

        private Long id;
        @SerializedName("domain")
        private String domain;
        @SerializedName("status")
        private String status;
        @SerializedName("reference")
        private String reference;

        @SerializedName("amount")
        private Integer amount;
        @SerializedName("message")
        private String message;
        @SerializedName("channel")
        private String channel;
        @SerializedName("currency")
        private String currency;

        @SerializedName("gateway_response")
        private String gatewayResponse;

        @SerializedName("paid_at")
        private String paid_at;

        @SerializedName("created_at")
        private String created_at;



    }

}
