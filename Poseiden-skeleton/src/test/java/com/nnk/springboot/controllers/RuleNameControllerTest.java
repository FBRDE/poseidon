package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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

public class RuleNameControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

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
    public void getAllRuleNameControllerTest() throws Exception {

        RuleName rule1 = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        RuleName rule2 = new RuleName("Rule Name1", "Description1", "Json1", "Template1", "SQL1", "SQL Part1");
        List<RuleName> list=new ArrayList<>();
        list.add(rule1);
        list.add(rule2);

        when(ruleNameService.getRuleNameList()).thenReturn(list);

        mockMvc
                .perform(get("/ruleName/list"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attribute("ruleNameList", list))
                .andExpect(model().hasNoErrors());

    }

    @WithMockUser
    @Test
    public void showFormAddRuleNameControllerTest() throws Exception {

        mockMvc
                .perform(get("/ruleName/add"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));

    }

    @WithMockUser
    @Test
    public void validateAddRuleNameControllerTest() throws Exception {

        RuleName rule1 = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        RuleName rule2 = new RuleName("Rule Name1", "Description1", "Json1", "Template1", "SQL1", "SQL Part1");
        List<RuleName> list=new ArrayList<>();
        list.add(rule1);
        list.add(rule2);

        when(ruleNameService.getRuleNameList()).thenReturn(list);
        when(ruleNameService.add(any(RuleName.class))).thenReturn(rule1);

        mockMvc.perform(post("/ruleName/validate")
                        .param("id", "1")
                        .param("name", "Rule Name Test")
                        .param("description", "Description Test")
                        .param("json", "Json")
                        .param("template", "Template")
                        .param("sqlStr", "SQL")
                        .param("sqlPart", "SQL Part"))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is3xxRedirection())
                        .andExpect(view().name("redirect:/ruleName/list"))
                        .andReturn();

    }

    @Test
    @WithMockUser
    public void getRequestRuleNameUpdateIdShouldReturnSuccess() throws Exception {

        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        rule.setId(1);

        when(ruleNameService.getRuleName(anyInt())).thenReturn(rule);

        mockMvc.perform(get("/ruleName/update/{id}", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"))
                .andReturn();

    }

    @Test
    @WithMockUser
    public void postRequestRuleNameUpdateTest() throws Exception {

        RuleName rule1 = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        RuleName rule2 = new RuleName("Rule Name1", "Description1", "Json1", "Template1", "SQL1", "SQL Part1");
        List<RuleName> list=new ArrayList<>();
        list.add(rule1);
        list.add(rule2);

        when(ruleNameService.getRuleNameList()).thenReturn(list);

        when(ruleNameService.getRuleNameList()).thenReturn(list);
        when(ruleNameService.update(rule1,1)).thenReturn(rule1);
        mockMvc.perform(post("/ruleName/update/{id}", "1")

                        .param("id", "1")
                        .param("name", "Rule Name Test")
                        .param("description", "Description Test")
                        .param("json", "Json")
                        .param("template", "Template")
                        .param("sqlStr", "SQL")
                        .param("sqlPart", "SQL Part"))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andReturn();

    }

    @Test
    @WithMockUser
    public void getRequestRuleNameDeleteIdTest() throws Exception {

        RuleName rule1 = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        RuleName rule2 = new RuleName("Rule Name1", "Description1", "Json1", "Template1", "SQL1", "SQL Part1");
        List<RuleName> list=new ArrayList<>();
        list.add(rule1);
        list.add(rule2);

        when(ruleNameService.getRuleNameList()).thenReturn(list);

        when(ruleNameService.getRuleNameList()).thenReturn(list);
        when(ruleNameService.deleteRuleName(1)).thenReturn(true);

        mockMvc.perform(get("/ruleName/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andReturn();

    }
}
