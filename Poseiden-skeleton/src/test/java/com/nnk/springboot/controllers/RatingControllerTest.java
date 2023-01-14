package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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

public class RatingControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

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


    @WithMockUser
    @Test
    public void getAllRatingControllerTest() throws Exception {

        Rating rating1 = new Rating("Moody Rating1", "Sand PRating1", "Fitch Rating1", 10);
        Rating rating2 = new Rating("Moody Rating2", "Sand PRating2", "Fitch Rating2", 20);
        List<Rating> list=new ArrayList<>();
        list.add(rating1);
        list.add(rating2);

        when(ratingService.getRatingList()).thenReturn(list);

        mockMvc
                .perform(get("/rating/list"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attribute("ratingList", list))
                .andExpect(model().hasNoErrors());

    }

    @WithMockUser
    @Test
    public void showFormAddRatingControllerTest() throws Exception {

        mockMvc
                .perform(get("/rating/add"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

    }

    @WithMockUser
    @Test
    public void validateAddRatingControllerTest() throws Exception {

        Rating rating1 = new Rating("Moody Rating1", "Sand PRating1", "Fitch Rating1", 10);
        Rating rating2 = new Rating("Moody Rating2", "Sand PRating2", "Fitch Rating2", 20);
        List<Rating> list=new ArrayList<>();
        list.add(rating1);
        list.add(rating2);

        when(ratingService.getRatingList()).thenReturn(list);
        when(ratingService.add(any(Rating.class))).thenReturn(rating1);

        mockMvc.perform(post("/rating/validate")
                        .param("id", "1")
                        .param("moodysRating", "Moody Rating1")
                        .param("sandPRating", "Sand PRating1")
                        .param("fitchRating", "Fitch Rating1")
                        .param("orderNumber", "10"))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is3xxRedirection())
                        .andExpect(view().name("redirect:/rating/list"))
                        .andReturn();

    }

    @Test
    @WithMockUser
    public void getRequestRatingUpdateIdShouldReturnSuccess() throws Exception {

        Rating rating = new Rating("Moody Rating1", "Sand PRating1", "Fitch Rating1", 10);
        rating.setId(1);

        when(ratingService.getRating(anyInt())).thenReturn(rating);

        mockMvc.perform(get("/rating/update/{id}", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"))
                .andReturn();

    }

    @Test
    @WithMockUser
    public void postRequestRatingUpdateIdShouldReturnSuccess() throws Exception {

        Rating rating = new Rating("Moody Rating1", "Sand PRating1", "Fitch Rating1", 10);
        rating.setId(1);

        List<Rating> list = new ArrayList<>();
        list.add(rating);

        when(ratingService.getRatingList()).thenReturn(list);
        when(ratingService.update(rating,1)).thenReturn(rating);
        mockMvc.perform(post("/rating/update/{id}", "1")

                        .param("id", "1")
                        .param("moodysRating", "Moody Rating1")
                        .param("sandPRating", "Sand PRating1")
                        .param("fitchRating", "Fitch Rating1")
                        .param("orderNumber", "10"))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andReturn();

    }

    @Test
    @WithMockUser
    public void getRequestRatingDeleteIdShouldReturnSuccess() throws Exception {

        Rating rating = new Rating("Moody Rating1", "Sand PRating1", "Fitch Rating1", 10);
        rating.setId(1);

        List<Rating> list = new ArrayList<>();
        list.add(rating);

        when(ratingService.getRatingList()).thenReturn(list);
        when(ratingService.deleteRating(1)).thenReturn(true);

        mockMvc.perform(get("/rating/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andReturn();

    }
}
