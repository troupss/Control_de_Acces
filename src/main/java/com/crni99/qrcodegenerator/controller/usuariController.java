package com.crni99.qrcodegenerator.controller;

import com.crni99.qrcodegenerator.models.Usuaris;
import com.crni99.qrcodegenerator.repository.UsuarisRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class usuariController {
    private final UsuarisRepository repository;

    public usuariController(UsuarisRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/usuaris")
    public String getUsuaris(Model model, @RequestParam(name = "error", required = false) String error) {
        List<Usuaris> usuaris = repository.findAll();
        model.addAttribute("usuaris", usuaris);
        model.addAttribute("error", error);
        return "mostrarUsuaris";
    }

    @GetMapping("/registrar")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("usuari", new Usuaris());
        return "registrarUsuaris";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(Usuaris usuario) {
        repository.save(usuario);
        return "redirect:/usuaris";
    }

    @GetMapping("/loguejar")
    public String mostrarFormularioLogin() {
        return "loguejarUsuaris";
    }

    @PostMapping("/iniciarSesion")
    public String iniciarSesion(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        Usuaris usuario = repository.findByEmailAndPassword(email, password);
        if (usuario != null) {
            // Si las credenciales son correctas, puedes redirigir a una página de inicio
            return "redirect:/inicio";
        } else {
            // Si las credenciales son incorrectas, mostrar un mensaje de error
            model.addAttribute("error", "Credenciales incorrectas, por favor intente nuevamente.");
            return "redirect:/loguejar?error";
        }
    }
}



