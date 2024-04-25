package com.jennifer.service.serviceImpl;

import com.jennifer.dto.request.AddressRequest;
import com.jennifer.entity.Address;
import com.jennifer.entity.User;
import com.jennifer.exception.CustomException;
import com.jennifer.exception.UserNotFoundException;
import com.jennifer.repository.AddressRepository;
import com.jennifer.repository.UserRepository;
import com.jennifer.service.AddressService;
import com.jennifer.utils.LoggedInUserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;

    private final LoggedInUserUtils utils;
    private final AddressRepository addressRepository;

    private User getUser() {
        String loggedInUsername = utils.getLoggedInUser();
        if (loggedInUsername == null) {
            throw new CustomException("User not logged in");
        }
        return userRepository.findByEmail(loggedInUsername)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    @Override
    public String saveAddress(AddressRequest addressRequest) {
        User loggedInUser = getUser();
        Address existingAddress = loggedInUser.getAddress();

        if (existingAddress != null) {
            existingAddress.setAddressLine(addressRequest.getAddressLine());
            existingAddress.setCity(addressRequest.getCity());
            existingAddress.setState(addressRequest.getState());
            existingAddress.setPhoneNumber(addressRequest.getPhoneNumber());
            addressRepository.save(existingAddress);
            return "Address updated successfully";
        } else {
            Address newAddress = new Address();
            newAddress.setAddressLine(addressRequest.getAddressLine());
            newAddress.setCity(addressRequest.getCity());
            newAddress.setState(addressRequest.getState());
            newAddress.setPhoneNumber(addressRequest.getPhoneNumber());
            newAddress.setUser(loggedInUser);
            loggedInUser.setAddress(newAddress);
            addressRepository.save(newAddress);
            return "Address saved successfully";
        }
    }






}


