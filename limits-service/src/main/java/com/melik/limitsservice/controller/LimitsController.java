package com.melik.limitsservice.controller;

import com.melik.limitsservice.bean.Limits;
import com.melik.limitsservice.configuration.LimitConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mselvi
 * @Created 17.09.2023
 */

@RestController
@RequestMapping("/limits")
public class LimitsController {

    private final LimitConfiguration configuration;

    public LimitsController(LimitConfiguration configuration) {
        this.configuration = configuration;
    }

    @GetMapping
    public Limits retrieveLimits() {
        return new Limits(configuration.getMinimum(), configuration.getMaximum());
    }
}
