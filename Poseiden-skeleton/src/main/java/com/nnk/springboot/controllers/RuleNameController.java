package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {
    // Inject RuleName service
    @Autowired
    private RuleNameService ruleNameService;
    private static final Logger logger = LogManager.getLogger("RuleNameController");
    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        // Find all RuleName, add to model
        List<RuleName> ruleNameList= ruleNameService.getRuleNameList();
        model.addAttribute("ruleNameList", ruleNameList);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // Check data valid and save to db, after saving return RuleName list

        if (!result.hasErrors()) {
            ruleNameService.add(ruleName);
            model.addAttribute("ratingList", ruleNameService.getRuleNameList());
            return "redirect:/ruleName/list";
        }
        else
        {
            for (Object object : result.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    logger.error("Error @PostMapping('/ruleName/validate') "+fieldError.getCode());
                }

                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    logger.error("Error @PostMapping('/ruleName/validate') "+objectError.getCode());
                }
            }
        }
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // Get RuleName by Id and to model then show to the form
        RuleName ruleName = ruleNameService.getRuleName(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // Check required fields, if valid call service to update RuleName and return RuleName list
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        else
        {
            for (Object object : result.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    logger.error("Error @PostMapping('/ruleName/update/{id}') "+fieldError.getCode());
                }

                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    logger.error("Error @PostMapping('/ruleName/update/{id}') "+objectError.getCode());
                }
            }
        }

        ruleNameService.update(ruleName,id);
        model.addAttribute("ratingList", ruleNameService.getRuleNameList());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // Find RuleName by Id and delete the RuleName, return to Rule list
        ruleNameService.deleteRuleName(id);
        List<RuleName> ruleNameList= ruleNameService.getRuleNameList();
        model.addAttribute("ruleNameList", ruleNameList);
        return "redirect:/ruleName/list";
    }
}
