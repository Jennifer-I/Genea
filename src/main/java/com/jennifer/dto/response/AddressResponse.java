package com.jennifer.dto.response;

import com.jennifer.entity.ShippingAddress;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {
        private String addressLine;
        private String city;
        private String state;
        private String email;
        private String phoneNumber;



    public AddressResponse(ShippingAddress shippingAddress) {
        this.addressLine = shippingAddress.getAddressLine();
        this.city = shippingAddress.getCity();
        this.state = shippingAddress.getState();
        this.email = shippingAddress.getUser().getEmail();
        this.phoneNumber = shippingAddress.getUser().getPhoneNumber();
    }




}


