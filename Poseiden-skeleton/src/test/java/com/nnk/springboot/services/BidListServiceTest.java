package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidListServiceTest {

	@InjectMocks
	private BidListService bidListService;
	@Mock
	private BidListRepository bidListRepository;

	@Test
	public void getBidListTest() {

		BidList bid1 = new BidList("Account Test1", "Type Test1", 10d);
		BidList bid2 = new BidList("Account Test2", "Type Test2", 20d);
		List<BidList> list=new ArrayList<>();
		list.add(bid1);
		list.add(bid2);
		when(bidListRepository.findAll()).thenReturn(list);
		List<BidList> listResult=bidListService.getBidList();
		assertEquals(list.size(),listResult.size());
		assertTrue(listResult.equals(list));
	}
	@Test
	public void deleteBidWhenElementExistTest(){

		when( bidListRepository.existsById(1)).thenReturn(true);
		doNothing().when(bidListRepository).deleteById(1);
		assertEquals(true,bidListService.deleteBid(1));
	}
	@Test
	public void deleteBidWhenElementNotExistTest(){

		when( bidListRepository.existsById(1)).thenReturn(false);
		doNothing().when(bidListRepository).deleteById(1);
		assertEquals(false,bidListService.deleteBid(1));
	}

	@Test
	public void addWhenAccountExistTest(){
		BidList bid1 = new BidList("Account_Test", "Type_Test", 10d);
		bid1.setBidListId(1);
		when( bidListRepository.existsByAccount("Account_Test")).thenReturn(true);

		assertEquals(null,bidListService.add(bid1));
	}

	@Test
	public void addWhenAccountNotExistTest(){
		BidList bid1 = new BidList("Account_Test", "Type_Test", 10d);
		bid1.setBidListId(1);
		when( bidListRepository.existsByAccount("Account_Test")).thenReturn(false);
		when( bidListRepository.save(bid1)).thenReturn(bid1);
		assertEquals(bid1,bidListService.add(bid1));
	}
	@Test
	public void getBidByIdWhenElementExistTest(){
		BidList bid1 = new BidList("Account_Test", "Type_Test", 10d);
		bid1.setBidListId(1);
		when( bidListRepository.existsById(1)).thenReturn(true);
		when( bidListRepository.findById(1)).thenReturn(Optional.of(bid1));
		assertEquals(bid1,bidListService.getBidById(1));
	}

	@Test
	public void getBidByIdWhenElementNotExistTest(){

		when( bidListRepository.existsById(1)).thenReturn(false);

		assertEquals(null,bidListService.getBidById(1));
	}

	@Test
	public void updateWhenIdExistTest(){
		BidList bid1 = new BidList("Account_Test", "Type_Test", 10d);

		when( bidListRepository.existsById(1)).thenReturn(true);
		when( bidListRepository.save(bid1)).thenReturn(bid1);

		assertEquals(bid1,bidListService.update(bid1,1));
		assertEquals(1,bid1.getBidListId());
	}
	@Test
	public void updateWhenIdNotExistTest(){
		BidList bid1 = new BidList("Account_Test", "Type_Test", 10d);

		when( bidListRepository.existsById(1)).thenReturn(false);

		assertEquals(null,bidListService.update(bid1,1));

	}

}
