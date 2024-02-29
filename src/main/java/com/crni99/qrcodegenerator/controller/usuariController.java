package com.crni99.qrcodegenerator.controller;

import com.crni99.qrcodegenerator.models.Usuaris;
import com.crni99.qrcodegenerator.repository.UsuarisRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
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
    public String guardarUsuario(Usuaris usuario, HttpSession session) {
        repository.save(usuario);

        session.setAttribute("usuarioLogueado", usuario);

        return "redirect:/usuaris";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "loguejarUsuaris";
    }

    @PostMapping("/iniciarSesion")
    public String iniciarSesion(@RequestParam("dni") String dni, @RequestParam("password") String password, Model model, HttpSession session) {

        Usuaris usuario = repository.findByDni(dni);
        if (usuario != null) {
            if (usuario.getPassword().equals(password)) {
                session.setAttribute("usuarioLogueado", usuario);
                return "redirect:/usuaris";
            }else{
                model.addAttribute("error", "Els credencials introduits no son correctes. Sisplau, torna-ho a provar");
                return "loguejarUsuaris";
            }
        }model.addAttribute("error", "Els credencials introduits no son correctes. Sisplau, torna-ho a provar");
        return "loguejarUsuaris";
    }

    @GetMapping("/login/guest")
    public String loginAsGuest(HttpSession session) {
        // Lógica para iniciar sesión como invitado (podrías generar un usuario temporal)
        // Aquí un ejemplo básico de cómo podrías hacerlo
        Usuaris usuarioInvitado = new Usuaris();
        usuarioInvitado.setDni("invitat");
        usuarioInvitado.setPassword("invitat123"); // Contraseña temporal
        usuarioInvitado.setNom("Invitat");
        // Guardar el usuario invitado en la sesión
        session.setAttribute("usuariLoguejat", usuarioInvitado);
        // Redirigir al usuario a la página de tickets
        return "redirect:/partits";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidar la sesión para cerrar la sesión del usuario
        session.invalidate();
        // Redirigir a la página de inicio o a donde desees después de cerrar sesión
        return "redirect:/login";
    }
}



