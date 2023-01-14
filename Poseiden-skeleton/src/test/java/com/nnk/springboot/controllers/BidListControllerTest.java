package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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

public class BidListControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

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
    public void getAllBidBidListControllerTest() throws Exception {

        BidList bid1 = new BidList("Account Test1", "Type Test1", 10d);
        BidList bid2 = new BidList("Account Test2", "Type Test2", 20d);
        List<BidList> list=new ArrayList<>();
        list.add(bid1);
        list.add(bid2);

        when(bidListService.getBidList()).thenReturn(list);

        mockMvc
                .perform(get("/bidList/list"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("bidList", list))
                .andExpect(model().hasNoErrors());

    }

    @WithMockUser
    @Test
    public void showFormAddBidListControllerTest() throws Exception {

        mockMvc
                .perform(get("/bidList/add"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

    }

    @WithMockUser
    @Test
    public void validateAddBidListControllerTest() throws Exception {

        BidList bid1 = new BidList("Account Test1", "Type Test1", 10d);
        BidList bid2 = new BidList("Account Test2", "Type Test2", 20d);
        List<BidList> list=new ArrayList<>();
        list.add(bid1);
        list.add(bid2);

        when(bidListService.getBidList()).thenReturn(list);
        when(bidListService.add(any(BidList.class))).thenReturn(bid1);

        mockMvc.perform(post("/bidList/validate")
                        .param("bidListId", "1")
                        .param("account", "Account Test")
                        .param("type", "Type Test")
                        .param("bidQuantity", "10"))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is3xxRedirection())
                        .andExpect(view().name("redirect:/bidList/list"))
                        .andReturn();

    }

    @Test
    @WithMockUser
    public void getRequestBidListUpdateIdShouldReturnSuccess() throws Exception {


        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        when(bidListService.getBidById(anyInt())).thenReturn(bid);

        mockMvc.perform(get("/bidList/update/{id}", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"))
                .andReturn();

    }

    @Test
    @WithMockUser
    public void postRequestBidListUpdateIdShouldReturnSuccess() throws Exception {

        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        List<BidList> list = new ArrayList<>();
        list.add(bid);

        when(bidListService.getBidList()).thenReturn(list);
        when(bidListService.update(bid,1)).thenReturn(bid);
        mockMvc.perform(post("/bidList/update/{id}", "1")

                        .param("bidListId", "1")
                        .param("account", "Account Test")
                        .param("type", "Type Test")
                        .param("bidQuantity", "10"))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andReturn();

    }

    @Test
    @WithMockUser
    public void getRequestBidListDeleteIdShouldReturnSuccess() throws Exception {

        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        List<BidList> bidList = new ArrayList<>();
        bidList.add(bid);

        when(bidListService.getBidList()).thenReturn(bidList);
        when(bidListService.deleteBid(1)).thenReturn(true);

        mockMvc.perform(get("/bidList/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andReturn();

    }
}
