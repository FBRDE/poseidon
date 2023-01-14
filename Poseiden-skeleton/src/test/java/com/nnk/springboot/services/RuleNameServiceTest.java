package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleNameServiceTest {




	@InjectMocks
	private RuleNameService ruleNameService;
	@Mock
	private RuleNameRepository ruleNameRepository;

	@Test
	public void getRuleNameListTest() {

		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		RuleName rule2 = new RuleName("Rule Name2", "Description2", "Json2", "Template2", "SQL2", "SQL Part2");
		List<RuleName> list=new ArrayList<>();
		list.add(rule);
		list.add(rule2);
		when(ruleNameRepository.findAll()).thenReturn(list);
		List<RuleName> listResult=ruleNameService.getRuleNameList();
		assertEquals(list.size(),listResult.size());
		assertTrue(listResult.equals(list));
	}
	@Test
	public void deleteRuleWhenElementExistTest(){

		when( ruleNameRepository.existsById(1)).thenReturn(true);
		doNothing().when(ruleNameRepository).deleteById(1);
		assertEquals(true,ruleNameService.deleteRuleName(1));
	}
	@Test
	public void deleteRuleWhenElementNotExistTest(){

		when( ruleNameRepository.existsById(1)).thenReturn(false);
		doNothing().when(ruleNameRepository).deleteById(1);
		assertEquals(false,ruleNameService.deleteRuleName(1));
	}

	@Test
	public void addWhenRuleNameExistTest(){
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		rule.setId(1);
		when( ruleNameRepository.existsByName("Rule Name")).thenReturn(true);

		assertEquals(null,ruleNameService.add(rule));
	}

	@Test
	public void addWhenRuleNameNotExistTest(){
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		rule.setId(1);
		when( ruleNameRepository.existsByName("Rule Name")).thenReturn(false);
		when( ruleNameRepository.save(rule)).thenReturn(rule);
		assertEquals(rule,ruleNameService.add(rule));
	}
	@Test
	public void getRuleNameByIdWhenElementExistTest(){
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		rule.setId(1);
		when( ruleNameRepository.existsById(1)).thenReturn(true);
		when( ruleNameRepository.findById(1)).thenReturn(Optional.of(rule));
		assertEquals(rule,ruleNameService.getRuleName(1));
	}

	@Test
	public void getRuleNameByIdWhenElementNotExistTest(){

		when( ruleNameRepository.existsById(1)).thenReturn(false);

		assertEquals(null,ruleNameService.getRuleName(1));
	}

	@Test
	public void updateWhenIdExistTest(){
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		when( ruleNameRepository.existsById(1)).thenReturn(true);
		when( ruleNameRepository.save(rule)).thenReturn(rule);

		assertEquals(rule,ruleNameService.update(rule,1));
		assertEquals(1,rule.getId());
	}
	@Test
	public void updateWhenIdNotExistTest(){
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		when( ruleNameRepository.existsById(1)).thenReturn(false);
		assertEquals(null,ruleNameService.update(rule,1));

	}

}
