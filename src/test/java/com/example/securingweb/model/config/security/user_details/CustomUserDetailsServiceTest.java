package com.example.securingweb.model.config.security.user_details;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class CustomUserDetailsServiceTest {

	CustomUserDetailsService userDetails = new CustomUserDetailsService();

	@Test
	void testLoadUserByUsername() {
		UserDetails user = userDetails.loadUserByUsername("AndreVanilla");
		Assertions.assertEquals("AndreVanilla", user.getUsername());
	}

	@Test
	void testLoadUserByUsernameNotFound() {
		Assertions.assertThrows(UsernameNotFoundException.class, () -> userDetails.loadUserByUsername(""));
	}

}
