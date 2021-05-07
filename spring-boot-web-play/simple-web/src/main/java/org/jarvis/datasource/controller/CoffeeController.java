package org.jarvis.consumer.controller;

import org.jarvis.consumer.controller.request.NewCoffeeRequest;
import org.jarvis.consumer.model.Coffee;
import org.jarvis.web.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author marcus
 * @date 2020/8/24-9:38
 */
@Controller
@RequestMapping(value = "/coffee")
public class CoffeeController {
    @Autowired
    private CoffeeService coffeeService;

    @PostMapping(value = "increased")
    public Coffee addCoffeeWithoutBindResult(NewCoffeeRequest coffeeRequest) {
        return coffeeService.save(coffeeRequest.getName(), coffeeRequest.getPrice());
    }
}
