package com.genea.dto.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionResponse {

    private boolean status;
    private String message;

    @SerializedName("data")
    private List<Data> transactions;


    @Getter
    @Setter
    public static class Data {
        private Long id;

        private String domain;

        private String status;

        private String reference;


        private Integer amount;

        private String message;

        private String channel;

        private String currency;

        @SerializedName("gateway_response")
        private String gatewayResponse;

        @SerializedName("paid_at")
        private String paid_at;

        @SerializedName("created_at")
        private String created_at;

        private customer customer;
    }

    @Getter
    @Setter
    public static class customer {

        @SerializedName("first_name")
        private String firstName;
        @SerializedName("last_name")
        private String lastName;

        private String email;
        private String phone;
        @SerializedName("metadata")
        private String metadata;
        @SerializedName("customer_code")
        private String customerCode;
    }
}
