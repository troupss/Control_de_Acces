package com.crni99.qrcodegenerator.controller;

import com.crni99.qrcodegenerator.repository.PartitsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class partitsController {
    private PartitsRepository repository;

    public partitsController(PartitsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/partits")
    public String getPartits(Model model, @RequestParam(name = "error", required = false) String error) {
        List partits = repository.findAll();
        model.addAttribute("partits", partits);
        model.addAttribute("error", error);
        return "mostrarPartits";
    }

}
