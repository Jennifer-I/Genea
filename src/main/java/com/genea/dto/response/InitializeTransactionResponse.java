package com.genea.dto.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitializeTransactionResponse {

    private String status;
    private String message;
    private Data data;


    @Getter
    @Setter
    public static class Data {

        @SerializedName("authorization_url")
        private String authorizationUrl;
        @SerializedName("access_code")
        private String accessCode;
        @SerializedName("reference")
        private String reference;

    }

}