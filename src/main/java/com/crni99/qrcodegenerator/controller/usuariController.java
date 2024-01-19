package com.crni99.qrcodegenerator.controller;

import com.crni99.qrcodegenerator.repository.UsuarisRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class usuariController {
    private UsuarisRepository repository;

    public usuariController(UsuarisRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/usuaris")
    public String getUsuaris(Model model, @RequestParam(name = "error", required = false) String error) {
        List usuaris = repository.findAll();
        model.addAttribute("usuaris", usuaris);
        model.addAttribute("error", error);
        return "mostrarUsuaris";
    }
}
