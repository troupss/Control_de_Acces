package com.crni99.qrcodegenerator.controller;

import java.io.IOException;

import com.crni99.qrcodegenerator.config.PasswordGenerator;
import com.crni99.qrcodegenerator.models.Tickets;
import com.crni99.qrcodegenerator.repository.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crni99.qrcodegenerator.service.QRCodeService;

@Controller
public class MainController {

	private QRCodeService qrCodeService;
	@Autowired
	private PasswordGenerator passwordGenerator;

	public MainController(QRCodeService qrCodeService) {
		this.qrCodeService = qrCodeService;
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@PostMapping("/generate")
	public String generateQRCode(Model model, RedirectAttributes redirectAttributes) {

		String complicatedString = passwordGenerator.generatePassword(20); // Cambia 20 por la longitud deseada


		String qrCode = qrCodeService.getQRCode(complicatedString);

		model.addAttribute("text", complicatedString);
		model.addAttribute("qrcode", qrCode);

		return "index";
	}


	@GetMapping("/decode")
	public String decodeQRCode() {
		return "decode";
	}

	@PostMapping("/uploadQrCode")
	public String uploadQrCode(@RequestParam("qrCodeFile") MultipartFile qrCodeFile,
			RedirectAttributes redirectAttributes) {
		if (qrCodeFile.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Please choose file to upload.");
			return "redirect:/decode";
		}
		try {
			String qrContent = qrCodeService.decodeQR(qrCodeFile.getBytes());
			redirectAttributes.addFlashAttribute("qrContent", qrContent);
			return "redirect:/decode";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/decode";
	}

}
