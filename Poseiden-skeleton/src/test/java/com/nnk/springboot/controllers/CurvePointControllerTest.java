package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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

public class CurvePointControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

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
    public void getAllCurvePointCurvePointControllerTest() throws Exception {

        CurvePoint curvePoint1 = new CurvePoint(10, 10d, 30d);
        CurvePoint curvePoint2 = new CurvePoint(20, 25d, 35d);
        List<CurvePoint> list=new ArrayList<>();
        list.add(curvePoint1);
        list.add(curvePoint2);

        when(curvePointService.getCurvePointList()).thenReturn(list);

        mockMvc
                .perform(get("/curvePoint/list"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attribute("curvePointList", list))
                .andExpect(model().hasNoErrors());

    }

    @WithMockUser
    @Test
    public void showFormAddCurvePointControllerTest() throws Exception {

        mockMvc
                .perform(get("/curvePoint/add"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));

    }

    @WithMockUser
    @Test
    public void validateAddCurvePointControllerTest() throws Exception {

        CurvePoint curvePoint1 = new CurvePoint(10, 10d, 30d);
        CurvePoint curvePoint2 = new CurvePoint(20, 25d, 35d);
        List<CurvePoint> list=new ArrayList<>();
        list.add(curvePoint1);
        list.add(curvePoint2);

        when(curvePointService.getCurvePointList()).thenReturn(list);
        when(curvePointService.add(any(CurvePoint.class))).thenReturn(curvePoint1);

        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "1")
                        .param("term", "2")
                        .param("value", "3"))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is3xxRedirection())
                        .andExpect(view().name("redirect:/curvePoint/list"))
                        .andReturn();

    }

    @Test
    @WithMockUser
    public void getRequestCurvePointUpdateIdShouldReturnSuccess() throws Exception {

        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        curvePoint.setId(1);

        when(curvePointService.getCurvePointById(anyInt())).thenReturn(curvePoint);

        mockMvc.perform(get("/curvePoint/update/{id}", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"))
                .andReturn();

    }

    @Test
    @WithMockUser
    public void postRequestCurvePointUpdateIdShouldReturnSuccess() throws Exception {

        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        curvePoint.setId(1);

        List<CurvePoint> list = new ArrayList<>();
        list.add(curvePoint);

        when(curvePointService.getCurvePointList()).thenReturn(list);
        when(curvePointService.update(curvePoint,1)).thenReturn(curvePoint);
        mockMvc.perform(post("/curvePoint/update/{id}", "1")
                        .param("id", "1")
                        .param("curveId", "2")
                        .param("term", "3")
                        .param("value", "4"))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andReturn();

    }

    @Test
    @WithMockUser
    public void getRequestCurvePointDeleteIdShouldReturnSuccess() throws Exception {

        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        curvePoint.setId(1);

        List<CurvePoint> list = new ArrayList<>();
        list.add(curvePoint);

        when(curvePointService.getCurvePointList()).thenReturn(list);
        when(curvePointService.deleteCurvePoint(1)).thenReturn(true);

        mockMvc.perform(get("/curvePoint/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andReturn();

    }
}
