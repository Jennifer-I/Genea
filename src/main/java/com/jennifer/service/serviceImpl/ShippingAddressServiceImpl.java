package com.jennifer.service.serviceImpl;

import com.jennifer.dto.request.AddressRequest;
import com.jennifer.dto.response.AddressResponse;
import com.jennifer.entity.ShippingAddress;
import com.jennifer.entity.User;
import com.jennifer.exception.CustomException;
import com.jennifer.exception.UserNotFoundException;
import com.jennifer.repository.ShippingAddressRepository;
import com.jennifer.repository.UserRepository;
import com.jennifer.service.ShippingAddressService;
import com.jennifer.utils.LoggedInUserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {
    private final UserRepository userRepository;
    private final LoggedInUserUtils utils;
    private final ShippingAddressRepository shippingAddressRepository;


    private User getUser() {
        String loggedInUsername = utils.getLoggedInUser();
        if (loggedInUsername == null) {
            throw new CustomException("User not logged in");
        }
        return userRepository.findByEmail(loggedInUsername)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void addShippingAddress(AddressRequest addressRequest) {
        User loggedInUser = getUser();
        List<ShippingAddress> shippingAddresses = new ArrayList<>(loggedInUser.getShippingAddress());

        if (shippingAddresses.size() >= 5) {
            throw new IllegalStateException("User already has the maximum number of shipping addresses.");
        }

        boolean addressExists = shippingAddresses.stream()
                .anyMatch(existingAddress -> isDuplicateAddress(existingAddress, addressRequest));

        if (addressExists) {
            throw new IllegalStateException("Shipping address already exists.");
        }

        log.info("Shipping address size: {}", shippingAddresses.size());
        ShippingAddress newShippingAddress = createShippingAddress(addressRequest);

        if (shippingAddresses.stream().noneMatch(ShippingAddress::isDefaultShippingAddress)) {
            newShippingAddress.setDefaultShippingAddress(true);
            log.info("Setting default shipping address");
        }

        shippingAddresses.add(newShippingAddress);
        loggedInUser.setShippingAddress(shippingAddresses);
        shippingAddressRepository.save(newShippingAddress);
    }

    @Override
    public String updateShippingAddress(AddressRequest addressRequest) {
        return null;
    }


    private boolean isDuplicateAddress(ShippingAddress existingAddress, AddressRequest newAddress) {
        boolean sameStreet = existingAddress.getAddressLine().equalsIgnoreCase(newAddress.getAddressLine());
        boolean sameCity = existingAddress.getCity().equalsIgnoreCase(newAddress.getCity());
        boolean samePostalCode = existingAddress.getState().equalsIgnoreCase(newAddress.getState());

        return sameStreet && sameCity && samePostalCode;
    }


    private ShippingAddress createShippingAddress(AddressRequest addressRequest) {
        ShippingAddress newShippingAddress = new ShippingAddress();
        newShippingAddress.setAddressLine(addressRequest.getAddressLine());
        newShippingAddress.setCity(addressRequest.getCity());
        newShippingAddress.setState(addressRequest.getState());
        newShippingAddress.setPhoneNumber(getUser().getPhoneNumber());
        newShippingAddress.setEmail(getUser().getEmail());
        newShippingAddress.setCreatedAt(LocalDateTime.now());
        newShippingAddress.setUser(getUser());
        return newShippingAddress;
    }








