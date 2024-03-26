package com.Day5.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Day5.model.CustomerUserDetail;
import com.Day5.model.User;
import com.Day5.repository.UserRepository;

@Service
public class CostomUserDetailService implements UserDetailsService {

	
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 Optional<User> user= userRepository.findUserByEmail(email);
		 user.orElseThrow(()->new UsernameNotFoundException("user is not found!!"));
		return user.map(CustomerUserDetail::new).get();
	}

	
	
}
