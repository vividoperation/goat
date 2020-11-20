package com.usc.csci401.goatweb;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.usc.csci401.goatcommon.dto.UserProfileDTO;
import com.usc.csci401.goatcommon.util.JsonUtils;
import com.usc.csci401.goatservice.param.UserCreateParam;
import com.usc.csci401.goatservice.param.UserLoginParam;
import com.usc.csci401.goatservice.param.UserUpdateParam;
import com.usc.csci401.goatservice.service.impl.UserServiceImpl;
import com.usc.csci401.goatweb.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@AutoConfigureMockMvc
class GoatWebApplicationTests {

  private MockMvc mockMvc;

  private final UserServiceImpl userService = mock(UserServiceImpl.class);

  @BeforeEach
  void initUseCase() {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    UserController userController = new UserController(userService, bCryptPasswordEncoder);
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  public void getUserProfileTest() throws Exception {
    UserProfileDTO profile = new UserProfileDTO("tommy", "t@gmail.com", 1234);
    when(userService.selectUser("tommy")).thenReturn(profile);
    this.mockMvc.perform(
        get("/user/profile/get").param("username", "tommy").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().json(
        "{\"result\":{\"username\":\"tommy\",\"email\":\"t@gmail.com\",\"phone\":1234},\"msg\":null}"));
  }


  @Test
  public void updateUserProfileTest() throws Exception {
    UserUpdateParam param = new UserUpdateParam("t@gmail.com", 1234);
    this.mockMvc.perform(post("/user/profile/update?username=minghao").content(JsonUtils.toJson(param)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json("{\"result\":true,\"msg\":null}"));
  }

  @Test
  public void registerUserTest() throws Exception {
    UserCreateParam param = new UserCreateParam("tommy", "1234", 1234, "t@gmail.com");
    this.mockMvc.perform(post("/user/register").content(JsonUtils.toJson(param)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
  }

  @Test
  public void loginTest() throws Exception {
    UserLoginParam param = new UserLoginParam("spike", "1234");
    when(userService.getPassword(param.getUsername())).thenReturn("$2a$10$MWqTk.vQ/HiShxXk9Zfeb.dNyBaqKhe.mrAXwyIOgiz8WBQ8uWscy");
    this.mockMvc.perform(post("/user/login").content(JsonUtils.toJson(param)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    verify(userService).getPassword(param.getUsername());
  }


}