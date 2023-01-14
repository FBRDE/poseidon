package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

public class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    private final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()) // Integrate SpringSecurity to SpringMVC
                .build();
    }


    @WithMockUser(username = "admin", authorities = { "ADMIN"})
    @Test
    public void getAllUserControllerTest() throws Exception {

        User user = new User("admin","admin","password");
        List<User> list=new ArrayList<>();
        list.add(user);

        when(userService.findAllUsers()).thenReturn(list);

        mockMvc
                .perform(get("/user/list"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attribute("users", list))
                .andExpect(model().hasNoErrors());

    }

    @WithMockUser(username = "admin", authorities = { "ADMIN"})
    @Test
    public void showFormAddUserControllerTest() throws Exception {

        mockMvc
                .perform(get("/user/add"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));

    }

    @WithMockUser(username = "admin", authorities = { "ADMIN"})
    @Test
    public void validateAddUserControllerTest() throws Exception {

        User user = new User("admin","admin","Akjh546**");
        List<User> list=new ArrayList<>();
        list.add(user);

        when(userService.findAllUsers()).thenReturn(list);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user/validate")
                        .param("id", "1")
                        .param("fullName", "Name")
                        .param("UserName", "User")
                        .param("role", "USER")
                        .param("password","Akjh546**"))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is3xxRedirection())
                        .andExpect(view().name("redirect:/user/list"))
                        .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN"})
    public void getRequestUserUpdateIdShouldReturnSuccess() throws Exception {

        User user = new User("admin","admin","password");
        user.setId(1);

        when(userService.findById(anyInt())).thenReturn(user);

        mockMvc.perform(get("/user/update/{id}", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"))
                .andReturn();

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN"})
    public void postRequestUserUpdateIdTest() throws Exception {

        User user = new User("admin","admin","password");
        user.setId(1);

        List<User> list = new ArrayList<>();
        list.add(user);

        when(userService.findAllUsers()).thenReturn(list);
        when(userService.updateUser(1,user)).thenReturn(user);
        mockMvc.perform(post("/user/update/{id}", "1")

                        .param("id", "1")
                        .param("fullName", "Name")
                        .param("UserName", "User")
                        .param("role", "USER")
                        .param("password","Akjh546**"))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"))
                .andReturn();

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN"})
    public void getRequestUserDeleteIdTest() throws Exception {

        User user = new User("admin","admin","password");
        user.setId(1);

        List<User> list = new ArrayList<>();
        list.add(user);

        when(userService.findAllUsers()).thenReturn(list);
        when(userService.findById(1)).thenReturn(user);

        when(userService.deleteUser(1)).thenReturn(true);

        mockMvc.perform(get("/user/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"))
                .andReturn();

    }
}
