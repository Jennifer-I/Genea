package com.jennifer.service;

import com.jennifer.dto.request.AddressRequest;
import com.jennifer.dto.response.AddressResponse;
import com.jennifer.entity.ShippingAddress;

import java.util.List;

public interface ShippingAddressService {

        void addShippingAddress(AddressRequest addressRequest);
        String updateShippingAddress(AddressRequest addressRequest);
        String deleteShippingAddress(Long id);
        AddressResponse  getShippingAddress(Long id);


        void setDefaultShippingAddress(Long addressId) throws Exception;

        ShippingAddress getDefaultShippingAddress();

    List<AddressResponse> getAllShippingAddresses();
}
