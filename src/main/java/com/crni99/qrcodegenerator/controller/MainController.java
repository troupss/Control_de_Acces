package com.crni99.qrcodegenerator.controller;

import java.io.IOException;
import java.util.Date;
import java.time.LocalDate;
import java.util.Optional;

import com.crni99.qrcodegenerator.config.PasswordGenerator;
import com.crni99.qrcodegenerator.models.Partits;
import com.crni99.qrcodegenerator.models.Tickets;
import com.crni99.qrcodegenerator.repository.PartitsRepository;
import com.crni99.qrcodegenerator.repository.TicketsRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crni99.qrcodegenerator.service.QRCodeService;

import javax.servlet.http.Part;

@Controller
public class MainController {

	private QRCodeService qrCodeService;
	@Autowired
	private TicketsRepository ticketsRepository;
	@Autowired
	private PartitsRepository partitsRepository;

	public MainController(QRCodeService qrCodeService) {
		this.qrCodeService = qrCodeService;
		this.ticketsRepository = ticketsRepository;
		this.partitsRepository = partitsRepository;
	}
	@GetMapping("/")
	public String index() {
		return "redirect:/usuaris";
	}
	@GetMapping("/decode")
	public String decodeQRCode(Model model) {
		model.addAttribute("qrValido", false);
		return "decode";
	}

	@PostMapping("/uploadQrCode")
	public String uploadQrCode(@RequestParam("qrContent") String qrContent, Model model, RedirectAttributes redirectAttributes) {
		System.out.println(qrContent);

		boolean qrValido = validarQrCode(qrContent);

		if (qrValido) {
			model.addAttribute("qrValido", true);
		} else {
			model.addAttribute("qrValido", false);
		}

		return "decode";
	}
	private boolean validarQrCode(String qrContent) {
		LocalDate currentDate = LocalDate.now();
		Date date = java.sql.Date.valueOf(currentDate);

		String ticketId = qrContent;

		Tickets ticket = ticketsRepository.findByTicketId(ticketId);

		if (ticket != null) {
			if (ticket != null) {
				Partits partits = partitsRepository.findById(ticket.getPartitId()).orElse(null);
				System.out.println(partits.getData());
				System.out.println(currentDate);

				if (partits != null) {
					if (partits.getData().compareTo(date) > 0) {
						System.out.println("El Partit no és avui");
						return false;
					} else {
						System.out.println("El Partit és avui");
						return true;
					}
				} else {
					return false;
				}
			} else {
				System.out.println("Error: TicketId no válido");
			}
		} else {
			System.out.println("Error: Ticket no encontrado");
		}
		return false;
	}
}
