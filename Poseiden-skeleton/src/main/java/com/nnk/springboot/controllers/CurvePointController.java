package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurvePointController {
    // Inject Curve Point service
    @Autowired
    private CurvePointService curveService;
    private static final Logger logger = LogManager.getLogger("CurveController");
    @GetMapping("/curvePoint/list")
    public String home(Model model)
    {
        // find all Curve Point, add to model
        List<CurvePoint> curvePointList= curveService.getCurvePointList();
        model.addAttribute("curvePointList", curvePointList);

        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm( CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // Check data valid and save to db, after saving return Curve list
        if (!result.hasErrors()) {
            curveService.add(curvePoint);
            model.addAttribute("curvePointList", curveService.getCurvePointList());
            return "redirect:/curvePoint/list";
        }
        else
        {
            for (Object object : result.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    logger.error("Error @PostMapping('/curvePoint/validate') "+fieldError.getCode());
                }

                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    logger.error("Error @PostMapping('/curvePoint/validate') "+objectError.getCode());
                }
            }
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // Get CurvePoint by Id and to model then show to the form
        CurvePoint curvePoint = curveService.getCurvePointById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // Check required fields, if valid call service to update Curve and return Curve list
        if (result.hasErrors()) {
            for (Object object : result.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    logger.error("Error @PostMapping('/curvePoint/update/{id}') "+fieldError.getCode());
                }

                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    logger.error("Error @PostMapping('/curvePoint/update/{id}') "+objectError.getCode());
                }
            }
            return "curvePoint/update";
        }
        curveService.update(curvePoint,id);
        model.addAttribute("curvePointList", curveService.getCurvePointList());
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // Find Curve by Id and delete the Curve, return to Curve list
        curveService.deleteCurvePoint(id);
        List<CurvePoint> curvePointList= curveService.getCurvePointList();
        model.addAttribute("curvePointList", curvePointList);
        return "redirect:/curvePoint/list";
    }
}
