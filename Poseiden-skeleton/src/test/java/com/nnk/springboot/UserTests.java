package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void UserTest() {
		User user = new User("user_test","role_test","123@@Abcdef");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));

		// Save
		user = userRepository.save(user);
		Assert.assertNotNull(user.getId());
		Assert.assertEquals(user.getUserName(), "user_test");
		Assert.assertEquals(user.getRole(), "role_test");

//		// Update
		user.setUserName("user2");
		user = userRepository.save(user);
		Assert.assertEquals(user.getUserName(), "user2");

		// Find
		List<User> listResult = userRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

//		// Delete
		Integer id = user.getId();
		userRepository.delete(user);
		Optional<User> user1 = userRepository.findById(id);
		Assert.assertFalse(user1.isPresent());
	}
}
