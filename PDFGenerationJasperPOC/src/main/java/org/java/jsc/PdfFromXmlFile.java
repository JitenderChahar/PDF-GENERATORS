package org.java.jsc;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;

public class PdfFromXmlFile {

	public static void main(String[] args) throws JRException, IOException {

		// Compile jrxml file.
		JasperReport jasperReport = JasperCompileManager
				.compileReport("Blank_A4.jrxml");

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();

		// DataSource
		// This is simple example, no database.
		// then using empty datasource.
		// JRDataSource dataSource = new JREmptyDataSource();
		File file = new File("demo.json");
		JRDataSource dataSource = new JsonDataSource(file);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				parameters, dataSource);

		// Make sure the output directory exists.
		File outDir = new File("jasperoutput");
		outDir.mkdirs();

		// Export to PDF.
		/*JasperExportManager.exportReportToPdfFile(jasperPrint,
				"jasperoutput/StyledTextReport.pdf");*/
		JasperExportManager.exportReportToPdfFile(jasperPrint,
				"jasperoutput/demo.pdf");

		System.out.println("Done!");
	}
}