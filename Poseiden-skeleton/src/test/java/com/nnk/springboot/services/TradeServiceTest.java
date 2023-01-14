package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeServiceTest {

	@InjectMocks
	private TradeService tradeService;
	@Mock
	private TradeRepository tradeRepository;

	@Test
	public void getTradeListTest() {

		Trade trade = new Trade("Trade Account", "Type",10d);
		Trade trade2 = new Trade("Trade Account", "Type",20d);
		List<Trade> list=new ArrayList<>();
		list.add(trade);
		list.add(trade2);
		when(tradeRepository.findAll()).thenReturn(list);
		List<Trade> listResult=tradeService.getTradeList();
		assertEquals(list.size(),listResult.size());
		assertTrue(listResult.equals(list));
	}
	@Test
	public void deleteTradeWhenElementExistTest(){

		when( tradeRepository.existsById(1)).thenReturn(true);
		doNothing().when(tradeRepository).deleteById(1);
		assertEquals(true,tradeService.deleteTrade(1));
	}
	@Test
	public void deleteTradeWhenElementNotExistTest(){

		when( tradeRepository.existsById(1)).thenReturn(false);
		doNothing().when(tradeRepository).deleteById(1);
		assertEquals(false,tradeService.deleteTrade(1));
	}

	@Test
	public void addWhenAccountExistTest(){
		Trade trade = new Trade("Trade Account", "Type",10d);
		trade.setTradeId(1);
		when( tradeRepository.existsByAccount("Trade Account")).thenReturn(true);

		assertEquals(null,tradeService.add(trade));
	}

	@Test
	public void addWhenAccountNotExistTest(){
		Trade trade = new Trade("Trade Account", "Type",10d);
		trade.setTradeId(1);
		when( tradeRepository.existsByAccount("Trade Account")).thenReturn(false);
		when( tradeRepository.save(trade)).thenReturn(trade);
		assertEquals(trade,tradeService.add(trade));
	}
	@Test
	public void getTradeByIdWhenElementExistTest(){
		Trade trade = new Trade("Trade Account", "Type",10d);
		trade.setTradeId(1);
		when( tradeRepository.existsById(1)).thenReturn(true);
		when( tradeRepository.findById(1)).thenReturn(Optional.of(trade));
		assertEquals(trade,tradeService.getTrade(1));
	}

	@Test
	public void getTraByIdWhenElementNotExistTest(){

		when( tradeRepository.existsById(1)).thenReturn(false);

		assertEquals(null,tradeService.getTrade(1));
	}

	@Test
	public void updateWhenIdExistTest(){
		Trade trade = new Trade("Trade Account", "Type",10d);

		when( tradeRepository.existsById(1)).thenReturn(true);
		when( tradeRepository.save(trade)).thenReturn(trade);

		assertEquals(trade,tradeService.update(trade,1));
		assertEquals(1,trade.getTradeId());
	}
	@Test
	public void updateWhenIdNotExistTest(){
		Trade trade = new Trade("Trade Account", "Type",10d);

		when( tradeRepository.existsById(1)).thenReturn(false);

		assertEquals(null,tradeService.update(trade,1));

	}

}
