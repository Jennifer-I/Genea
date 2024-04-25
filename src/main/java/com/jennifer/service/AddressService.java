package com.jennifer.service;

import com.jennifer.dto.request.AddressRequest;
import com.jennifer.dto.response.ApiResponse;

public interface AddressService {
    String saveAddress(AddressRequest addressRequest);

     ApiResponse<String> deleteAddress(Long addressId);
}
