package com.cherry.stunner.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    @RequestMapping("/admin")
    public ModelAndView admin() {

        return new ModelAndView("admin");

    }

}
