package com.billcom.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {

    @GetMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward://";
    }

}
