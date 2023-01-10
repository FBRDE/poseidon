package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {
    // Inject Trade service
    @Autowired
    private TradeService tradeService;
    private static final Logger logger = LogManager.getLogger("TradeController");
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        // Find all Trade, add to model
        List<Trade> tradeList= tradeService.getTradeList();
        model.addAttribute("tradeList", tradeList);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // Check data valid and save to db, after saving return Trade list
        if (!result.hasErrors()) {
            tradeService.add(trade);
            model.addAttribute("tradeList", tradeService.getTradeList());
            return "redirect:/trade/list";
        }
        else
        {
            for (Object object : result.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    logger.error("Error @PostMapping('/trade/validate') "+fieldError.getCode());
                }

                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    logger.error("Error @PostMapping('/trade/validate') "+objectError.getCode());
                }
            }
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form => DONE
        Trade trade = tradeService.getTrade(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list => DONE

        if (result.hasErrors()) {
            return "trade/update";
        }
        else
        {
            for (Object object : result.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    logger.error("Error @PostMapping('/trade/update/{id}') "+fieldError.getCode());
                }

                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    logger.error("Error @PostMapping('/trade/update/{id}') "+objectError.getCode());
                }
            }
        }
        trade.setTradeId(id);
        tradeService.add(trade);
        model.addAttribute("tradeList", tradeService.getTradeList());
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // Find Trade by Id and delete the Trade, return to Trade list
        tradeService.deleteTrade(id);
        List<Trade> tradeList= tradeService.getTradeList();
        model.addAttribute("tradeList", tradeList);
        return "redirect:/trade/list";
    }
}
