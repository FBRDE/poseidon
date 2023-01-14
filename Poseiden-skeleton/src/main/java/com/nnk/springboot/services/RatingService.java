package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    private static final Logger logger = LogManager.getLogger("RatingService");
    public List<Rating> getRatingList() {
        return ratingRepository.findAll();
    }

    public Rating add(Rating rating) {
        if (ratingRepository.existsByMoodysRating(rating.getMoodysRating()))
        {
            logger.error("ERROR: this moodyRating is already used.");
            return null;
        }
        Rating rating1=ratingRepository.save(rating);
        if (rating1!=null)
            logger.info("Rating added successfully!");
        return rating1;
    }
    public boolean deleteRating(Integer id) {
        boolean isDeleted = false;

        if (!(ratingRepository.existsById(id))) {
            logger.error("Unknown rating with id : {}", id);
            return isDeleted;
        }
        else
        {
            ratingRepository.deleteById(id);
            logger.info("Rating deleted successfully!");
            isDeleted = true;
        }
        return isDeleted;
    }

    public Rating getRating(Integer id) {
        if (ratingRepository.existsById(id))
        {
            logger.info("Rating found successfully!");
            return ratingRepository.findById(id).get();
        }
        else
        {
            logger.info("rating not found!");
            return null;
        }

    }

    public Rating update(Rating rating,Integer id) {
        if (ratingRepository.existsById(id))
        {
            rating.setId(id);
            Rating rating1=ratingRepository.save(rating);
            if (rating1!=null)
                logger.info("Rating updated successfully!");
            else
                logger.info("Rating Not updated!");
            return rating1;
        }
        else
            logger.info("Rating Not found!");
        return null;
    }

}
