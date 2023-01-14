package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingServiceTest {



	@InjectMocks
	private RatingService ratingService;
	@Mock
	private RatingRepository ratingRepository;

	@Test
	public void getRatingListTest() {

		Rating rating1 = new Rating("Moody Rating1", "Sand PRating1", "Fitch Rating1", 10);
		Rating rating2 = new Rating("Moody Rating2", "Sand PRating2", "Fitch Rating2", 20);

		List<Rating> list=new ArrayList<>();
		list.add(rating1);
		list.add(rating2);
		when(ratingRepository.findAll()).thenReturn(list);
		List<Rating> listResult=ratingService.getRatingList();
		assertEquals(list.size(),listResult.size());
		assertTrue(listResult.equals(list));
	}
	@Test
	public void deleteRatingWhenElementExistTest(){

		when( ratingRepository.existsById(1)).thenReturn(true);
		doNothing().when(ratingRepository).deleteById(1);
		assertEquals(true,ratingService.deleteRating(1));
	}
	@Test
	public void deleteRatingWhenElementNotExistTest(){

		when( ratingRepository.existsById(1)).thenReturn(false);
		doNothing().when(ratingRepository).deleteById(1);
		assertEquals(false,ratingService.deleteRating(1));
	}

	@Test
	public void addWhenMoodyRatingExistTest(){
		Rating rating1 = new Rating("Moody Rating1", "Sand PRating1", "Fitch Rating1", 10);

		when( ratingRepository.existsByMoodysRating("Moody Rating1")).thenReturn(true);

		assertEquals(null,ratingService.add(rating1));
	}

	@Test
	public void addWhenMoodyRatingNotExistTest(){
		Rating rating = new Rating("Moody Rating1", "Sand PRating1", "Fitch Rating1", 10);
		when( ratingRepository.existsByMoodysRating("Moody Rating1")).thenReturn(false);
		when( ratingRepository.save(rating)).thenReturn(rating);
		assertEquals(rating,ratingService.add(rating));
	}
	@Test
	public void getRatingByIdWhenElementExistTest(){
		Rating rating = new Rating("Moody Rating1", "Sand PRating1", "Fitch Rating1", 10);
		rating.setId(1);

		when( ratingRepository.existsById(1)).thenReturn(true);
		when( ratingRepository.findById(1)).thenReturn(Optional.of(rating));
		assertEquals(rating,ratingService.getRating(1));
	}

	@Test
	public void getRatingByIdWhenElementNotExistTest(){

		when( ratingRepository.existsById(1)).thenReturn(false);

		assertEquals(null,ratingService.getRating(1));
	}

	@Test
	public void updateWhenIdExistTest(){
		Rating rating = new Rating("Moody Rating1", "Sand PRating1", "Fitch Rating1", 10);
		rating.setId(1);
		when( ratingRepository.existsById(1)).thenReturn(true);
		when( ratingRepository.save(rating)).thenReturn(rating);

		assertEquals(rating,ratingService.update(rating,1));
		assertEquals(1,rating.getId());
	}
	@Test
	public void updateWhenIdNotExistTest(){
		Rating rating = new Rating("Moody Rating1", "Sand PRating1", "Fitch Rating1", 10);

		when( ratingRepository.existsById(1)).thenReturn(false);

		assertEquals(null,ratingService.update(rating,1));

	}

}
