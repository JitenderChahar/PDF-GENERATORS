package org.java.jsc.client;

import org.java.jsc.utils.PDFUtil;

public class Main {
	public static void main(String[] args) {
		String emptyPDF = "EmptyPDF.pdf";
		PDFUtil.createEmptyPDF(emptyPDF);

		String textPDF = "PdfWithtext.pdf";
		PDFUtil.createTextPDF(textPDF);
	}
}
