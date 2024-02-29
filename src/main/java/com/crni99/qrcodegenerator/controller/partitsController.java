package com.crni99.qrcodegenerator.controller;

import com.crni99.qrcodegenerator.config.PasswordGenerator;
import com.crni99.qrcodegenerator.models.Partits;
import com.crni99.qrcodegenerator.models.Request;
import com.crni99.qrcodegenerator.models.Tickets;
import com.crni99.qrcodegenerator.models.Usuaris;
import com.crni99.qrcodegenerator.repository.PartitsRepository;
import com.crni99.qrcodegenerator.repository.TicketsRepository;
import com.crni99.qrcodegenerator.service.QRCodeService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class partitsController {

    private QRCodeService qrCodeService;

    @Autowired
    private PartitsRepository repository;

    @Autowired
    private PasswordGenerator passwordGenerator;
    private final TicketsRepository ticketsRepository;

    @Value("${stripe.apipublic}")
    private String publicKey;

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
    public String comprarTickets(@PathVariable("id") int id, Model model, HttpSession session){
        try{
            // Verificar si el usuario está autenticado
            Usuaris usuarioLogueado = (Usuaris) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado == null) {
                // Si no está autenticado, redirigir a la página de inicio de sesión
                return "redirect:/login";
            }
            Partits partits = repository.findById(id).get();
            //PAGO VERIFICAR
            model.addAttribute("request", new Request());
            //
            model.addAttribute("partits", partits);
            model.addAttribute("tickets", new Tickets());

            // Obtener el usuario logueado de la sesión
            Usuaris usuarioLogueado = (Usuaris) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado != null) {
                System.out.println("El usuario logueado es: " + usuarioLogueado);
                //Agregar la variable id del partido a una variable a la session
                session.setAttribute("idPartit", id);

                model.addAttribute("usuarioLogueado", usuarioLogueado);
                model.addAttribute("emailUsuarioLogueado", usuarioLogueado.getEmail());
            }

        } catch (Exception e) {
            String error = "No s'ha pogut comprar el ticket amb id: " + id;
        }
        return "compraTickets";
    }

    @GetMapping("/generarTickets")
    public String generarTicket(
            @RequestParam(name = "payment_intent", required = true) String paymentIntent,
            @RequestParam(name = "payment_intent_client_secret", required = true) String paymentIntentClientSecret,
            @RequestParam(name = "redirect_status", required = true) String redirectStatus,
            Model model, HttpSession session) {
        try {
            String complicatedString = passwordGenerator.generatePassword(20); // Cambia 20 por la longitud deseada

            System.out.println("Payment Intent: " + paymentIntent);
            System.out.println("Payment Intent Client Secret: " + paymentIntentClientSecret);
            System.out.println("Redirect Status: " + redirectStatus);

            //Obtener el dni y el partit id de la session
            String dni = (String) session.getAttribute("dniUsuari");
            int partitId = (int) session.getAttribute("idPartit");

            Tickets newTicket = new Tickets();
            newTicket.setTicketId(complicatedString);
            newTicket.setPartitId(partitId);
            newTicket.setUserDni(dni);

            ticketsRepository.save(newTicket);

            String qrCode = qrCodeService.getQRCode(complicatedString);
            model.addAttribute("qrcode", qrCode);

        } catch (Exception e) {
            String error = "No s'ha pogut generar el ticket";
        }
        return "mostrarQR";
    }
    @PostMapping("/validar-pago/{amount}")
    public String validarPago(@PathVariable("amount") BigDecimal amount, @ModelAttribute Partits partits, Model model, @ModelAttribute Request request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error: " + bindingResult.getAllErrors());

            return "mostrarPartits";
        }

        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", request.getAmount());
        model.addAttribute("email", request.getEmail());
        model.addAttribute("productName", request.getProductName());


        return "checkout";
    }
}
