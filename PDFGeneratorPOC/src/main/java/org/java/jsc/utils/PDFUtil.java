package org.java.jsc.utils;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PDFUtil {

	private static final Logger logger = Logger.getLogger(PDFUtil.class);

	public static void createEmptyPDF(String pdfName) {
		logger.info("Inside createEmptyPDF() of PDFUtil");

		try {
			PDDocument doc = new PDDocument();
			doc.addPage(new PDPage());
			doc.save(pdfName);
			doc.close();
			System.out.println();
		} catch (COSVisitorException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		logger.info("PDF path : " + System.getProperty("user.dir"));
		logger.info("Exiting createEmptyPDF() of PDFUtil");
	}

	public static void createTextPDF(String pdfName) {
		logger.info("Inside createTextPDF() of PDFUtil");

		try {
			PDDocument doc = new PDDocument();
			PDPage page = new PDPage();

			doc.addPage(page);

			PDPageContentStream content = new PDPageContentStream(doc, page);

			content.beginText();
			content.setFont(PDType1Font.HELVETICA, 26);
			content.moveTextPositionByAmount(220, 750);
			content.drawString("Registration Form");
			content.endText();

			content.beginText();
			content.setFont(PDType1Font.HELVETICA, 16);
			content.moveTextPositionByAmount(80, 700);
			content.drawString("Name : ");
			content.endText();

			content.beginText();
			content.setFont(PDType1Font.HELVETICA, 16);
			content.moveTextPositionByAmount(80, 650);
			content.drawString("Father Name : ");
			content.endText();

			content.beginText();
			content.moveTextPositionByAmount(80, 600);
			content.drawString("DOB : ");
			content.endText();

			content.close();
			doc.save(pdfName);
			doc.close();
		} catch (COSVisitorException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		logger.info("PDF path : " + System.getProperty("user.dir"));
		logger.info("Exiting createTextPDF() of PDFUtil");
	}
}
