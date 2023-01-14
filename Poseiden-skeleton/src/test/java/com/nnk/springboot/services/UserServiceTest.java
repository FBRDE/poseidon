package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

	@InjectMocks
	private UserService userService;
	@Mock
	private UserRepository userRepository;

	@Test
	public void getUserListTest() {

		User user = new User("user_test","role_test","123@@Abcdef");
		User user2 = new User("user_test2","role_test2","123@@Abcdef2");
		List<User> list=new ArrayList<>();
		list.add(user);
		list.add(user2);
		when(userRepository.findAll()).thenReturn(list);
		List<User> listResult=userService.findAllUsers();
		assertEquals(list.size(),listResult.size());
		assertTrue(listResult.equals(list));
	}
	@Test
	public void deleteUserWhenElementExistTest(){

		when( userRepository.existsById(1)).thenReturn(true);
		doNothing().when(userRepository).deleteById(1);
		assertEquals(true,userService.deleteUser(1));
	}
	@Test
	public void deleteUserWhenElementNotExistTest(){

		when( userRepository.existsById(1)).thenReturn(false);
		doNothing().when(userRepository).deleteById(1);
		assertEquals(false,userService.deleteUser(1));
	}

	@Test
	public void addWhenUserNameExistTest(){
		User user = new User("user_test","role_test","123@@Abcdef");
		user.setId(1);
		when( userRepository.existsByUserName("user_Test")).thenReturn(true);

		assertEquals(null,userService.saveUser(user));
	}

	@Test
	public void addWhenUserNameNotExistTest(){
		User user = new User("user_test","role_test","123@@Abcdef");
		user.setId(1);
		when( userRepository.existsByUserName("user_Test")).thenReturn(false);
		when( userRepository.save(user)).thenReturn(user);
		assertEquals(user,userService.saveUser(user));
	}
	@Test
	public void getUserByIdWhenElementExistTest(){
		User user = new User("user_test","role_test","123@@Abcdef");
		user.setId(1);
		when( userRepository.existsById(1)).thenReturn(true);
		when( userRepository.findById(1)).thenReturn(Optional.of(user));
		assertEquals(user,userService.findById(1));
	}

	@Test
	public void getUserByIdWhenElementNotExistTest(){

		when( userRepository.existsById(1)).thenReturn(false);

		assertEquals(null,userService.findById(1));
	}

	@Test
	public void updateWhenIdExistTest(){
		User user = new User("user_test","role_test","123@@Abcdef");

		when( userRepository.findUserById(1)).thenReturn(user);
		when( userRepository.save(user)).thenReturn(user);

		assertEquals(user,userService.updateUser(1,user));

	}
	@Test
	public void updateWhenIdNotExistTest(){
		User user = new User("user_test","role_test","123@@Abcdef");

		when( userRepository.findUserById(1)).thenReturn(null);

		assertEquals(null,userService.updateUser(1,user));

	}
}
