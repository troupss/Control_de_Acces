package com.crni99.qrcodegenerator.controller;

import com.crni99.qrcodegenerator.models.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {
    @Value("${stripe.apipublic}")
    private String publicKey;

    @GetMapping("/card-payment")
    public String home(Model model) {
        model.addAttribute("request", new Request());
        return "pago";
    }

    @PostMapping("/card-payment")
    public String showCard(@ModelAttribute Request request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "pago";
        }
        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", request.getAmount());
        model.addAttribute("email", request.getEmail());
        model.addAttribute("productName", request.getProductName());

        return "checkout";
    }


}
