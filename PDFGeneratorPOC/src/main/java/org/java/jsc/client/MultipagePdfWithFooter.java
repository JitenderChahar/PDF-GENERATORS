package org.java.jsc.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class MultipagePdfWithFooter {

	// Hold the report font size.
	private final static float FONT_SIZE = 10f;

	// Hold the initial x position.
	private final static float X0 = 5f;

	// Hold the padding bottom of the document.
	private final static float PADDING_BOTTOM_OF_DOCUMENT = 30f;

	// header image
	private final static String HEADER_IMAGE = "second_page_header.jpg";

	/**
	 * This method for include the footer to the each page in pdf document.
	 * 
	 * @param doc
	 *            Set the pdf document.
	 * @param reportName
	 *            Set the report name.
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private void addReportFooter(final PDDocument doc, final String reportName)
			throws IOException {

		PDPageContentStream footercontentStream = null;
		try {

			List pages = doc.getDocumentCatalog().getAllPages();

			for (int i = 0; i < pages.size(); i++) {

				PDPage page = ((PDPage) pages.get(i));
				footercontentStream = new PDPageContentStream(doc, page, true,
						true);
				footercontentStream.drawLine(X0, 50,
						PDPage.PAGE_SIZE_A4.getWidth() - X0, 50);
				footercontentStream.beginText();
				footercontentStream.setFont(PDType1Font.HELVETICA_BOLD,
						FONT_SIZE);
				footercontentStream
						.moveTextPositionByAmount(
								X0,
								(PDPage.PAGE_SIZE_A4.getLowerLeftY() + PADDING_BOTTOM_OF_DOCUMENT));
				footercontentStream.drawString(reportName);
				footercontentStream.moveTextPositionByAmount(
						(PDPage.PAGE_SIZE_A4.getUpperRightX() / 2),
						(PDPage.PAGE_SIZE_A4.getLowerLeftY()));
				footercontentStream.drawString((i + 1) + " / " + pages.size());
				footercontentStream.endText();
				footercontentStream.close();

			}
		} catch (final IOException exception) {
			throw new RuntimeException(exception);
		} finally {

			if (footercontentStream != null) {
				try {
					footercontentStream.close();
				} catch (final IOException exception) {
					throw new RuntimeException(exception);
				}

			}
		}

	}

	/**
	 * This method created a document with 2 pages.
	 * 
	 * @return Document with consist of two pages.
	 * @throws IOException
	 */
	private PDDocument createDocumentWithMultiplePage() throws IOException {

		PDPageContentStream contentStream = null;
		PDPageContentStream contentStream1 = null;
		PDDocument doc = null;

		try {
			// create empty document.
			doc = new PDDocument();
			// create fist page.
			PDPage page = new PDPage();
			page.setMediaBox(PDPage.PAGE_SIZE_A4);
			doc.addPage(page);

			contentStream = new PDPageContentStream(doc, page);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE);
			contentStream.moveTextPositionByAmount(5, 800);

			contentStream.drawString("Test page one");
			contentStream.endText();
			contentStream.close();

			// Create next page.
			PDPage page1 = new PDPage();
			page1.setMediaBox(PDPage.PAGE_SIZE_A4);
			doc.addPage(page1);

			contentStream1 = new PDPageContentStream(doc, page1);
			contentStream1.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE);
			contentStream1.beginText();
			contentStream1.moveTextPositionByAmount(5, 800);

			contentStream1.drawString("Test page Two");
			contentStream1.endText();
			contentStream1.close();
		} catch (final IOException exception) {
			throw new RuntimeException(exception);
		} finally {

			if (contentStream != null) {
				try {
					contentStream.close();
				} catch (final IOException exception) {
					throw new RuntimeException(exception);
				}

			}
			if (contentStream1 != null) {
				try {
					contentStream1.close();
				} catch (final IOException exception) {
					throw new RuntimeException(exception);
				}

			}
		}

		return doc;
	}

	/**
	 * This method for include the header to the each page in pdf document.
	 * 
	 * @param doc
	 *            Set the pdf document.
	 * @param reportName
	 *            Set the report name.
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private void addReportHeader(final PDDocument doc) throws IOException {

		PDPageContentStream headercontentStream = null;
		try {

			List pages = doc.getDocumentCatalog().getAllPages();

			for (int i = 1; i < pages.size(); i++) {

				PDPage page = ((PDPage) pages.get(i));
				headercontentStream = new PDPageContentStream(doc, page, true,
						true);

				PDXObjectImage image = new PDJpeg(doc, new FileInputStream(
						HEADER_IMAGE));

				headercontentStream.drawImage(image, 180, 700);

				headercontentStream.close();

			}
		} catch (final IOException exception) {
			throw new RuntimeException(exception);
		} finally {

			if (headercontentStream != null) {
				try {
					headercontentStream.close();
				} catch (final IOException exception) {
					throw new RuntimeException(exception);
				}

			}
		}

	}

	/**
	 * Main method
	 * 
	 * @param arg
	 * @throws IOException
	 * @throws COSVisitorException
	 */
	public static void main(String arg[]) throws IOException,
			COSVisitorException {

		MultipagePdfWithFooter obj = new MultipagePdfWithFooter();
		PDDocument doc = obj.createDocumentWithMultiplePage();

		obj.addReportHeader(doc);
		obj.addReportFooter(doc, "PDF Generator POC");
		doc.save(new FileOutputStream(new File("TestPDfWithMultiPage.pdf")));
	}
}
