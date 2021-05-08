package org.jarvis.consumer.controller;

import org.jarvis.consumer.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Date;

/**
 * author:tennyson date:2020/6/23
 **/
@Controller
@RequestMapping(value = "/ftl")
public class FreeMarkerController {

    @Autowired
    private Resource resource;

    @RequestMapping(value = "books")
    public String index(ModelMap model){
        model.addAttribute("list", Arrays.asList("string1", "string2", "string3", "string4", "string5", "string6"));
        model.addAttribute("name", "   htTps://wWw.zHyD.mE   ");
        model.addAttribute("htmlText", "<span style=\"color: red;font-size: 16px;\">html内容</span>");
        model.addAttribute("num", 123.012);
        model.addAttribute("null", null);
        model.addAttribute("dateObj", new Date());
        model.addAttribute("bol", true);
        model.addAttribute("resource",resource);
        return "books";
    }

}
