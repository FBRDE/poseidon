package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RatingController {
    // Inject Rating service
    @Autowired
    private RatingService ratingService;
    private static final Logger logger = LogManager.getLogger("RatingController");

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        // Find all Rating, add to model
        List<Rating> ratingList= ratingService.getRatingList();
        model.addAttribute("ratingList", ratingList);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // Check data valid and save to db, after saving return Rating list
        if (!result.hasErrors()) {
            ratingService.add(rating);
            model.addAttribute("ratingList", ratingService.getRatingList());
            return "redirect:/rating/list";
        }
        else
        {
            for (Object object : result.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    logger.error("Error @PostMapping('/rating/validate') "+fieldError.getCode());
                }

                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    logger.error("Error @PostMapping('/rating/validate') "+objectError.getCode());
                }
            }
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // Get Rating by Id and to model then show to the form
        Rating rating = ratingService.getRating(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // Check required fields, if valid call service to update Rating and return Rating list
        if (result.hasErrors()) {
            return "rating/update";
        }
        else
        {
            for (Object object : result.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    logger.error("Error @PostMapping('/rating/update/{id}') "+fieldError.getCode());
                }

                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    logger.error("Error @PostMapping('/rating/update/{id}') "+objectError.getCode());
                }
            }
        }
        rating.setId(id);
        ratingService.add(rating);
        model.addAttribute("ratingList", ratingService.getRatingList());
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // Find Rating by Id and delete the Rating, return to Rating list
        ratingService.deleteRating(id);
        List<Rating> ratingList= ratingService.getRatingList();
        model.addAttribute("ratingList", ratingList);
        return "redirect:/rating/list";
    }
}
