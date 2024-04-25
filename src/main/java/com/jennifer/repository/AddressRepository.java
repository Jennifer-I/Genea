package com.jennifer.repository;

import com.jennifer.entity.Address;
import com.jennifer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long>{


    Address findByUser(User loggedInUser);
}
