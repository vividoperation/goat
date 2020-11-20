package com.usc.csci401.goatservice;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.usc.csci401.goatcommon.dto.UserProfileDTO;
import com.usc.csci401.goatdao.UserRepository;
import com.usc.csci401.goatdao.model.User;
import com.usc.csci401.goatservice.param.UserCreateParam;
import com.usc.csci401.goatservice.param.UserUpdateParam;
import com.usc.csci401.goatservice.service.UserService;
import com.usc.csci401.goatservice.service.impl.UserServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoatServiceApplicationTests {

	private final UserRepository userRepository = mock(UserRepository.class);

	private UserService userService;

	private User user;

	@BeforeEach
	void initUseCase() {
		user = new User("minghao", "mzhu@gmail.com", "1234", 1234);
		userService = new UserServiceImpl(userRepository);
	}

	@Test
	void createUserTest(){
		UserCreateParam param = new UserCreateParam("minghao", "1234", 1234, "mzhu@gmail.com");
		when(userRepository.save(user)).thenReturn(user);
		String username = userService.createUser(param);
		assertEquals(username, param.getUsername());
	}

	@Test
	void updateUserTest(){
		String username = "minghao";
		UserUpdateParam param = new UserUpdateParam("mzhu@gmail.com", 1234);
		userService.updateUser(param, username);
		verify(userRepository).updateById(username, param.getEmail(), param.getPhone());
	}

	@Test
	void getUserProfileTest(){
		when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
		UserProfileDTO expected = userService.selectUser(user.getUsername());
		assertEquals(expected.getUsername(), user.getUsername());
		verify(userRepository).findByUsername(user.getUsername());
	}

	@Test
	void getPasswordTest(){
		when(userRepository.findByUsername("minghao")).thenReturn(Optional.of(user));
		String expected = userService.getPassword(user.getUsername());
		assertEquals(expected, user.getPassword());
		verify(userRepository).findByUsername(user.getUsername());
	}


}
