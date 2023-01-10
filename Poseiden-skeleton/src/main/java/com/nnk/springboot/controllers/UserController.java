package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
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

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LogManager.getLogger("UserController");

    /**
     * Get HTML page used to display all users list.
     *
     * @param model
     * @return /user/list.html page
     */
    @GetMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userService.findAllUsers());
        logger.info("GET request SUCCESS for: /user/list");
        return "user/list";
    }

    /**
     * Get HTML page used to add a new user.
     *
     * @param model
     * @return /user/add.html page
     */
    @GetMapping("user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        logger.info("GET request SUCCESS for: /user/add");
        return "user/add";
    }
    /**
     * Post HTML page used to validate a new user information.
     *
     * @param user
     * @param result
     * @param model
     * @return /user/list.html page if good request, or /user/add
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            userService.saveUser(user);
            model.addAttribute("users", userService.findAllUsers());
            logger.info("@PostMapping('/user/validate succeeded");
            return "redirect:/user/list";
        }
        else
        {
            for (Object object : result.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    logger.error("Error @PostMapping('/user/validate') "+fieldError.getCode());
                }

                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    logger.error("Error @PostMapping('/user/validate') "+objectError.getCode());
                }
            }
        }
        return "user/add";
    }
    /**
     * Get HTML page used to update an existing user.
     *
     * @param id
     * @param model
     * @return /user/update.html page
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        User user = userService.findById(id);
        if(user==null)
        {
            logger.error("Invalid user Id:" + id);
            throw new IllegalArgumentException("Invalid user Id:" + id);
        }
        else
        {
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update"; }
    }

    /**
     * Post HTML page used to update a user.
     *
     * @param id
     * @param user
     * @param result
     * @param model
     * @return /user/list.html page if good request, or /user/update
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
        else
        {
            for (Object object : result.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    logger.error("Error @PostMapping('/user/update/{id}') "+fieldError.getCode());
                }

                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    logger.error("Error @PostMapping('/user/update/{id}') "+objectError.getCode());
                }
            }
        }

        userService.updateUser(id,user);
        model.addAttribute("users", userService.findAllUsers());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id);
        if(user==null)
        {
            logger.error("Invalid user Id:" + id);
            throw new IllegalArgumentException("Invalid user Id:" + id);
        }
        else
        {
       if(userService.deleteUser(id))
           logger.info("User deleted successfully");
       else
           logger.error("Error while deleting user:"+ id);
        model.addAttribute("users", userService.findAllUsers());
        return "redirect:/user/list";
        }
    }
}
