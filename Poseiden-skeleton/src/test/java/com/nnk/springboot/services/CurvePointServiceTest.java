package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurvePointServiceTest {

	@InjectMocks
	private CurvePointService curvePointService;
	@Mock
	private CurvePointRepository curvePointRepository;

	@Test
	public void getCurvePointTest() {

		CurvePoint curvePoint1 = new CurvePoint(10, 10d, 30d);
		CurvePoint curvePoint2 = new CurvePoint(15, 14d, 25d);
		List<CurvePoint> list=new ArrayList<>();
		list.add(curvePoint1);
		list.add(curvePoint2);
		when(curvePointRepository.findAll()).thenReturn(list);
		List<CurvePoint> listResult=curvePointService.getCurvePointList();
		assertEquals(list.size(),listResult.size());
		assertTrue(listResult.equals(list));
	}
	@Test
	public void deleteCurvePointWhenElementExistTest(){

		when( curvePointRepository.existsById(1)).thenReturn(true);
		doNothing().when(curvePointRepository).deleteById(1);
		assertEquals(true,curvePointService.deleteCurvePoint(1));
	}
	@Test
	public void deleteCurvePointWhenElementNotExistTest(){

		when( curvePointRepository.existsById(1)).thenReturn(false);
		doNothing().when(curvePointRepository).deleteById(1);
		assertEquals(false,curvePointService.deleteCurvePoint(1));
	}

	@Test
	public void addWhenCurveIdNotExistTest(){
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

		when( curvePointRepository.existsByCurveId(10)).thenReturn(false);
		when( curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
		assertEquals(curvePoint,curvePointService.add(curvePoint));
	}

	@Test
	public void addWhenCurveIdExistTest(){
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

		when( curvePointRepository.existsByCurveId(10)).thenReturn(true);
		assertEquals(null,curvePointService.add(curvePoint));
	}

	@Test
	public void getCurvePointByIdWhenElementExistTest(){
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		curvePoint.setId(1);
		when( curvePointRepository.existsById(1)).thenReturn(true);
		when( curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
		assertEquals(curvePoint,curvePointService.getCurvePointById(1));
	}

	@Test
	public void getCurvePointByIdWhenElementNotExistTest(){

		when( curvePointRepository.existsById(1)).thenReturn(false);

		assertEquals(null,curvePointService.getCurvePointById(1));
	}

	@Test
	public void updateWhenIdExistTest(){
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

		when( curvePointRepository.existsById(1)).thenReturn(true);
		when( curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

		assertEquals(curvePoint,curvePointService.update(curvePoint,1));
		assertEquals(1,curvePoint.getId());
	}
	@Test
	public void updateWhenIdNotExistTest(){

		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		when( curvePointRepository.existsById(1)).thenReturn(false);
		assertEquals(null,curvePointService.update(curvePoint,1));

	}

}
