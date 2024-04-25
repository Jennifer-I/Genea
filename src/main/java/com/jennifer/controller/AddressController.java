package com.jennifer.controller;

import com.jennifer.dto.request.AddressRequest;
import com.jennifer.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
    private final AddressService addressService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/addAddress")
    public ResponseEntity<String> addAddress(@RequestBody AddressRequest addressRequest) {
        return ResponseEntity.ok(addressService.saveAddress(addressRequest));
    }



}
