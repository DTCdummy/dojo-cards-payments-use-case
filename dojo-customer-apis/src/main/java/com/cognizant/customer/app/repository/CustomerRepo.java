package com.cognizant.customer.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.customer.app.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, String> {

	Optional<Customer> findByEmail(String email);

	Boolean existsByEmail(String email);

}
