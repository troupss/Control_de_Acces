package com.crni99.qrcodegenerator.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QRCodeService {

	private static int backgroundColor = 0xFF000002;
	private static int paintColor = 0xFFF8F9FA;

	public String getQRCode(String text) {
		byte[] image = new byte[0];
		try {
			image = QRCodeService.getQRCodeImage(text, 250, 250);
		} catch (WriterException | IOException e) {
			e.printStackTrace();
		}
		String qrcode = Base64.getEncoder().encodeToString(image);
		return qrcode;
	}

	private static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(backgroundColor, paintColor);

		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, matrixToImageConfig);
		byte[] pngData = pngOutputStream.toByteArray();
		return pngData;
	}

	public String decodeQR(byte[] qrCodeBytes) {
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(qrCodeBytes);
			BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
			BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
			HybridBinarizer hybridBinarizer = new HybridBinarizer(bufferedImageLuminanceSource);
			BinaryBitmap binaryBitmap = new BinaryBitmap(hybridBinarizer);
			MultiFormatReader multiFormatReader = new MultiFormatReader();
			Result result = multiFormatReader.decode(binaryBitmap);
			return result.getText();
		} catch (NotFoundException e) {
			return "QR Code Not Found In This Image!";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
