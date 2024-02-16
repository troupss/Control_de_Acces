package com.crni99.qrcodegenerator.controller;

import java.util.Date;
import java.time.LocalDate;

import com.crni99.qrcodegenerator.models.Partits;
import com.crni99.qrcodegenerator.models.Tickets;
import com.crni99.qrcodegenerator.repository.PartitsRepository;
import com.crni99.qrcodegenerator.repository.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crni99.qrcodegenerator.service.QRCodeService;


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
			Partits partits = partitsRepository.findById(ticket.getPartitId()).orElse(null);
			System.out.println(partits.getData());
			System.out.println(currentDate);

			if (partits != null) {
				if (partits.getData().equals(date)) {
					System.out.println("El Partit és avui");
					return true;
				} else {
					System.out.println("El Partit no és avui");
					System.out.println("Data del partit: " + partits.getData());
					return false;
				}
			} else {
				return false;
			}
		} else {
			System.out.println("Error: Ticket no encontrado");
		}
		return false;
	}
}
