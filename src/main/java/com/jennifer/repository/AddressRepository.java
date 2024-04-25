package com.jennifer.repository;

import com.jennifer.entity.Address;
import com.jennifer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long>{




    Optional<Address> findById(Long addressId);
}
