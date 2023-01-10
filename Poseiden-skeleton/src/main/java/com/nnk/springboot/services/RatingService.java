package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    public List<Rating> getRatingList() {
        return ratingRepository.findAll();
    }

    public void add(Rating rating) { ratingRepository.save(rating); }
    public void deleteRating(Integer id) {
        ratingRepository.deleteById(id);
    }

    public Rating getRating(Integer id) {
        return  ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Rating Id:" + id));
    }
}
