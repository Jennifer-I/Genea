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
import org.springframework.dao.DataIntegrityViolationException;
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
        if (loggedInUser.getAddressId() != null) {
            throw new CustomException("Address already exists");
        }

        Address address = new Address();
        address.setAddressLine(addressRequest.getAddressLine());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setPhoneNumber(addressRequest.getPhoneNumber());
        address.setUser(loggedInUser);

        try {
            log.info("Saving address and linking it to the user");
            addressRepository.save(address);

            loggedInUser.setAddressId(address.getId());
            userRepository.save(loggedInUser);
            return "Address saved successfully";

        } catch (DataIntegrityViolationException e) {
            throw new CustomException("Error saving address: User already has an address");
        }
    }





}


