package com.cognizant.customer.app.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.customer.app.entity.Customer;
import com.cognizant.customer.app.exceptions.ResourceNotFoundException;
import com.cognizant.customer.app.payload.CustomerDto;
import com.cognizant.customer.app.repository.CustomerRepo;
import com.cognizant.customer.app.service.CustomerService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CustomerServiceImpl implements CustomerService, UserDetailsService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Autowired
	private ModelMapper modelMapper;

	// CREATE
	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
		// CustomerDto to Customer conversion
		Customer customer = this.dtoToCustomer(customerDto);

		// set customerId
		String id = customer.getName().replace(" ", "").toLowerCase().substring(0, 3)
				.concat(UUID.randomUUID().toString().replace("-", "").substring(0, 7));
		customer.setCustomerId(id);

		// Encode Password
		customer.setPassword(pwdEncoder.encode(customer.getPassword()));
		
		log.debug("Creating a Customer with id: {}", id);

		// save customer in database
		Customer savedCustomer = this.customerRepo.save(customer);
		return this.customerToDto(savedCustomer);
	}

	// READ
	@Override
	public CustomerDto getCustomerByCustomerId(String customerId) {
		log.debug("Finding a Customer with id: {}", customerId);
		
		Customer customer = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", customerId));

		return this.customerToDto(customer);
	}

	// UPDATE
	@Override
	public CustomerDto updateCustomer(CustomerDto customerDto, String customerId) {
		log.debug("Updating a Customer with id: {}", customerId);
		
		Customer customer = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", customerId));

		// update data to customer
		customer.setAddress(customerDto.getAddress());
		customer.setAge(customerDto.getAge());
		customer.setName(customerDto.getName());
		customer.setPhone(customerDto.getPhone());
		customer.setPassword(customerDto.getPassword());
		customer.setEmail(customerDto.getEmail());

		// update customer
		Customer updatedCustomer = this.customerRepo.save(customer);
		return this.customerToDto(updatedCustomer);
	}

	@Override
	public UserDetails loadUserByUsername(String customerId) throws UsernameNotFoundException {
		Optional<Customer> opt = this.customerRepo.findById(customerId);

		if (!opt.isPresent()) {
			log.error("Customer with id: {} not found", customerId);
			throw new UsernameNotFoundException("Customer not exist");
		}

		// Read customer
		Customer customer = opt.get();

		// authorities
		Set<String> authorities = new HashSet<>();
		authorities.add("NORMAL");
		log.debug("Creating customer with id: {}", customerId);
		
		return new User(customerId, customer.getPassword(),
				authorities.stream().map((role) -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
	}

	// Utility methods
	private CustomerDto customerToDto(Customer customer) {
		return this.modelMapper.map(customer, CustomerDto.class);
	}

	private Customer dtoToCustomer(CustomerDto customerDto) {
		return this.modelMapper.map(customerDto, Customer.class);
	}

}
