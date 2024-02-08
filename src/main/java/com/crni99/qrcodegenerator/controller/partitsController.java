package com.crni99.qrcodegenerator.controller;

import com.crni99.qrcodegenerator.config.PasswordGenerator;
import com.crni99.qrcodegenerator.models.Partits;
import com.crni99.qrcodegenerator.models.Tickets;
import com.crni99.qrcodegenerator.repository.PartitsRepository;
import com.crni99.qrcodegenerator.repository.TicketsRepository;
import com.crni99.qrcodegenerator.service.QRCodeService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class partitsController {

    private QRCodeService qrCodeService;

    @Autowired
    private PartitsRepository repository;

    @Autowired
    private PasswordGenerator passwordGenerator;
    private final TicketsRepository ticketsRepository;


    public partitsController(QRCodeService qrCodeService,
                             TicketsRepository ticketsRepository) {
        this.qrCodeService = qrCodeService;
        this.ticketsRepository = ticketsRepository;
    }




    @GetMapping("/partits")
    public String getPartits(Model model, @RequestParam(name = "error", required = false) String error) {
        List partits = repository.findAll();
        model.addAttribute("partits", partits);
        model.addAttribute("error", error);
        return "mostrarPartits";
    }

    @GetMapping("/Comprartickets/{id}")
    public String comprarTickets(@PathVariable("id") int id, Model model){
        try{
            Partits partits = repository.findById(id).get();
            model.addAttribute("partits", partits);
            model.addAttribute("tickets", new Tickets());
        } catch (Exception e) {
            String error = "No s'ha pogut comprar el ticket amb id: " + id;
        }
        return "compraTickets";
    }

    @PostMapping("/generarTickets")
    public String generarTicket(@ModelAttribute Tickets tickets, @RequestParam String correo, @RequestParam String DNI, @RequestParam("id") int id, RedirectAttributes redirectAttributes, Model model) {
        try {
            String complicatedString = passwordGenerator.generatePassword(20); // Cambia 20 por la longitud deseada
            //String encryptedTicketId = BCrypt.hashpw(complicatedString, BCrypt.gensalt()); //ENCRIPTAR

            Tickets newTicket = new Tickets();
            newTicket.setTicketId(complicatedString);
            newTicket.setUserDni(DNI);
            newTicket.setPartitId(id);


            ticketsRepository.save(newTicket);

            String qrCode = qrCodeService.getQRCode(complicatedString);
            model.addAttribute("qrcode", qrCode);

        } catch (Exception e) {
            String error = "No s'ha pogut generar el ticket";
            redirectAttributes.addFlashAttribute("error", error);
        }
        return "mostrarQR";
    }
}
