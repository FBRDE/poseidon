package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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

import javax.validation.Valid;
import java.util.List;

@Controller
public class BidListController {
    private static final Logger logger = LogManager.getLogger("BidListController");
    // Inject Bid service
    @Autowired
    BidListService bidListService;
    @GetMapping("/bidList/list")
    public String home(Model model)
    {
        // Call service find all bids to show to the view
        List<BidList> bidList= bidListService.getBidList();
        model.addAttribute("bidList", bidList);

        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // Check data valid and save to db, after saving return bid list
        if (!result.hasErrors()) {
            bidListService.add(bid);
            logger.info("@PostMapping(/bidList/validate): Bid added successfully");
            model.addAttribute("bidList", bidListService.getBidList());

            return "redirect:/bidList/list";
        }
        else
        {
            for (Object object : result.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    logger.error("Error @GetMapping('/bidList/list') "+fieldError.getCode());
                }
                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    logger.error("Error @PostMapping(/bidList/validate) "+objectError.getCode());
                }
            }
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // Get Bid by Id and to model then show to the form
        BidList bidList = bidListService.getBidById(id);
        model.addAttribute("bidList", bidList);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        // Check required fields, if valid call service to update Bid and return list Bid

        if (result.hasErrors()) {
            for (Object object : result.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    logger.error(" Error @PostMapping('/bidList/update/{id}') "+fieldError.getCode());
                }
                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    logger.error(" Error @PostMapping('/bidList/update/{id}') "+objectError.getCode());
                }
            }
            return "bidList/update";
        }
        bidListService.update(bidList,id);
        model.addAttribute("bidList", bidListService.getBidList());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteBid(id);
        List<BidList> bidList= bidListService.getBidList();
        model.addAttribute("bidList", bidList);
        return "redirect:/bidList/list";
    }
}
