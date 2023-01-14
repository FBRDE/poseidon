package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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

public class TradeControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

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
    public void getAllTradeControllerTest() throws Exception {

        Trade trade = new Trade("Trade Account", "Type",10d);
        Trade trade2 = new Trade("Trade Account", "Type",20d);
        List<Trade> list=new ArrayList<>();
        list.add(trade);
        list.add(trade2);

        when(tradeService.getTradeList()).thenReturn(list);

        mockMvc
                .perform(get("/trade/list"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attribute("tradeList", list))
                .andExpect(model().hasNoErrors());

    }

    @WithMockUser
    @Test
    public void showFormAddTradeControllerTest() throws Exception {

        mockMvc
                .perform(get("/trade/add"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));

    }

    @WithMockUser
    @Test
    public void validateAddTradeControllerTest() throws Exception {

        Trade trade = new Trade("Trade Account", "Type",10d);
        Trade trade2 = new Trade("Trade Account", "Type",20d);
        List<Trade> list=new ArrayList<>();
        list.add(trade);
        list.add(trade2);

        when(tradeService.getTradeList()).thenReturn(list);
        when(tradeService.add(any(Trade.class))).thenReturn(trade);

        mockMvc.perform(post("/trade/validate")
                        .param("id", "1")
                        .param("account", "Account Test")
                        .param("type", "Type Test")
                        .param("buyQuantity", "10"))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is3xxRedirection())
                        .andExpect(view().name("redirect:/trade/list"))
                        .andReturn();

    }

    @Test
    @WithMockUser
    public void getRequestTradeUpdateIdShouldReturnSuccess() throws Exception {


        Trade trade = new Trade("Trade Account", "Type",10d);
        trade.setTradeId(1);

        when(tradeService.getTrade(anyInt())).thenReturn(trade);

        mockMvc.perform(get("/trade/update/{id}", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"))
                .andReturn();

    }

    @Test
    @WithMockUser
    public void postRequestTradeUpdateIdTest() throws Exception {

        Trade trade = new Trade("Trade Account", "Type",10d);
        trade.setTradeId(1);

        List<Trade> list = new ArrayList<>();
        list.add(trade);

        when(tradeService.getTradeList()).thenReturn(list);
        when(tradeService.update(trade,1)).thenReturn(trade);
        mockMvc.perform(post("/trade/update/{id}", "1")

                        .param("id", "1")
                        .param("account", "Account Test")
                        .param("type", "Type Test")
                        .param("bidQuantity", "10"))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andReturn();

    }

    @Test
    @WithMockUser
    public void getRequestTradeDeleteIdTest() throws Exception {

        Trade trade = new Trade("Trade Account", "Type",10d);
        trade.setTradeId(1);

        List<Trade> list = new ArrayList<>();
        list.add(trade);

        when(tradeService.getTradeList()).thenReturn(list);
        when(tradeService.deleteTrade(1)).thenReturn(true);

        mockMvc.perform(get("/trade/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andReturn();

    }
}
