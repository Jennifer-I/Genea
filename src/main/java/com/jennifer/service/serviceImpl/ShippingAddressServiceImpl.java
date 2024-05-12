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

    @Override
    public String deleteShippingAddress(Long id) {
        try {
            shippingAddressRepository.deleteById(id);
            return "Shipping address deleted successfully";
        } catch (Exception e) {
            log.error("error occurred while deleting shipping address", e);
            return "Failed to delete shipping address";
        }
    }


    @Override
    public AddressResponse getShippingAddress(Long id) {
        ShippingAddress shippingAddress = shippingAddressRepository.findById(id)
                .orElseThrow(() -> new CustomException("Shipping address not found"));
        return new AddressResponse(shippingAddress);
    }


    @Override
    public void setDefaultShippingAddress(Long addressId) throws Exception {
        Optional<ShippingAddress> optionalAddress = shippingAddressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            ShippingAddress newDefaultAddress = optionalAddress.get();
            boolean isAlreadyDefault = newDefaultAddress.isDefaultShippingAddress();

            if (isAlreadyDefault) {
                return;
            }
            long totalAddressCount = shippingAddressRepository.count();
            if (totalAddressCount == 1) {
                throw new Exception("There is only one address in total, it must remain as default.");
            }

            log.info("Unsetting default status for current default address");
            Optional<ShippingAddress> optionalCurrentDefault = shippingAddressRepository.findByIsDefaultShippingAddress(true);
            if (optionalCurrentDefault.isPresent()) {
                ShippingAddress currentDefaultAddress = optionalCurrentDefault.get();
                currentDefaultAddress.setDefaultShippingAddress(false);
                shippingAddressRepository.save(currentDefaultAddress);
            }

            log.info("Setting default shipping address");
            newDefaultAddress.setDefaultShippingAddress(true);
            shippingAddressRepository.save(newDefaultAddress);
        } else {
            throw new Exception("Shipping address not found with ID: " + addressId);
        }
    }

    @Override
    public ShippingAddress getDefaultShippingAddress() {
        Optional<ShippingAddress> optionalShippingAddress = shippingAddressRepository.findByIsDefaultShippingAddress(true);
        if (optionalShippingAddress.isPresent()) {
            return optionalShippingAddress.get();
        } else {
            throw new CustomException("Default shipping address not found");
        }
    }

    @Override
    public List<AddressResponse> getAllShippingAddresses() {
        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAll();
        return shippingAddresses.stream()
                .map(AddressResponse::new)
                .collect(Collectors.toList());
    }

}








