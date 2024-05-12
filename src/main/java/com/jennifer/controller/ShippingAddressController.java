package com.jennifer.controller;

import com.jennifer.dto.request.AddressRequest;
import com.jennifer.dto.response.AddressResponse;
import com.jennifer.entity.ShippingAddress;
import com.jennifer.exception.CustomException;
import com.jennifer.service.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/shippingAddress")
public class ShippingAddressController {
    private final ShippingAddressService shippingAddressService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/addShippingAddress")
    public ResponseEntity<Void> addShippingAddress(@RequestBody AddressRequest addressRequest) {
        try {
            shippingAddressService.addShippingAddress(addressRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error occurred while adding shipping address", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/deleteShippingAddress/{id}")
    public ResponseEntity<String> deleteShippingAddress(@PathVariable Long id) {
        try {
            shippingAddressService.deleteShippingAddress(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error occurred while adding shipping address", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/setDefaultShippingAddress/{id}")
    public ResponseEntity<String> setDefaultShippingAddress(@PathVariable Long id) {
        try {
            shippingAddressService.setDefaultShippingAddress(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error occurred while adding shipping address", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/getShippingAddress/{id}")
    public ResponseEntity<AddressResponse> getShippingAddress(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(shippingAddressService.getShippingAddress(id));
        } catch (Exception e) {
            log.error("Error occurred while adding shipping address", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/getsAllShippingAddresses")
    public ResponseEntity<List<AddressResponse>> getAllShippingAddresses() {
        List<AddressResponse> shippingAddresses = shippingAddressService.getAllShippingAddresses();
        return ResponseEntity.ok(shippingAddresses);
    }





}