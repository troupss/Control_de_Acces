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

    @GetMapping("/register")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("usuari", new Usuaris());
        return "registrarUsuaris";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(Usuaris usuario) {
        repository.save(usuario);
        return "redirect:/usuaris";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "loguejarUsuaris";
    }

    @PostMapping("/iniciarSesion")
    public String iniciarSesion(@RequestParam("dni") String dni, @RequestParam("password") String password, Model model) {
        System.out.println(dni);
        System.out.println(password);

        Usuaris usuario = repository.findByDni(dni);
        if (usuario != null) {
            if (usuario.getPassword().equals(password)) {
                return "redirect:/usuaris";
            }
        }
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "loguejarUsuaris";
    }
}



