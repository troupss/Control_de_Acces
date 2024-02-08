package com.crni99.qrcodegenerator.controller;

import com.crni99.qrcodegenerator.models.Tickets;
import com.crni99.qrcodegenerator.repository.TicketsRepository;
import com.crni99.qrcodegenerator.service.QRCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ticketsController {
    private QRCodeService qrCodeService;
    private TicketsRepository repository;

    public ticketsController(QRCodeService qrCodeService, TicketsRepository repository) {
        this.repository = repository;
        this.qrCodeService = qrCodeService;
    }
    @GetMapping("/tickets")
    public String getTickets(Model model, @RequestParam(name = "error", required = false) String error) {
        List tickets = repository.findAll();
        model.addAttribute("tickets", tickets);
        model.addAttribute("error", error);
        return "mostrarTickets";
    }

    @GetMapping("/mostrarQR/{id}")
    public String comprarTickets(@PathVariable("id") String id, Model model){
        try{
            String qrCode = qrCodeService.getQRCode(id);
            model.addAttribute("qrcode", qrCode);
        } catch (Exception e) {
            String error = "No s'ha pogut comprar el ticket amb id: " + id;
        }
        return "mostrarQR";
    }
}