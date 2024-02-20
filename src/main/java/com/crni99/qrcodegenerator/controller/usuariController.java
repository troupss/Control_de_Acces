package com.crni99.qrcodegenerator.controller;

import com.crni99.qrcodegenerator.models.Usuaris;
import com.crni99.qrcodegenerator.repository.UsuarisRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String iniciarSesion(@RequestParam("email") String email, @RequestParam("password") String password) {
        // Aquí deberías implementar la lógica para verificar el inicio de sesión
        // Por ejemplo, buscar el usuario en la base de datos por el correo electrónico y verificar la contraseña

        // Suponiendo que la verificación de inicio de sesión es exitosa, puedes redirigir a una página de inicio
        return "redirect:/inicio";
    }

}


