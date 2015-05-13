package com.aspose.liferay.portlets.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import aspose.pdf.BorderInfo;
import aspose.pdf.MarginInfo;
import aspose.pdf.Pdf;
import aspose.pdf.Row;
import aspose.pdf.Section;
import aspose.pdf.Table;

import com.aspose.cells.HTMLLoadOptions;
import com.aspose.cells.StyleFlag;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.words.CellVerticalAlignment;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.Font;
import com.aspose.words.HeightRule;
import com.aspose.words.LoadFormat;
import com.aspose.words.LoadOptions;
import com.aspose.words.ParagraphAlignment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

/**
 * Aspose Export Helper.
 * 
 * This helper provides functions to export to doc, xls and pdf formats.
 * 
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 * 
 */
public class AsposExportHelper {

	private static final Log s_log = LogFactoryUtil
			.getLog(AsposExportHelper.class);

	public static void exportToDoc(File tempDocFile, byte[] bytes)
			throws PortalException, SystemException {
		try {

			InputStream is = new ByteArrayInputStream(bytes);
			LoadOptions loadOptions = new LoadOptions();
			loadOptions.setLoadFormat(LoadFormat.HTML);

			Document doc = new Document(is, loadOptions);

			doc.save(new FileOutputStream(tempDocFile),
					com.aspose.words.SaveFormat.DOC);

		} catch (Exception e) {
			String msg = "Aspose: Unable to export to ms word format.. some error occured: "
					+ e.getMessage();

			s_log.error(msg, e);

			throw new PortalException(
					"Aspose: Unable to export to ms word format.. some error occured",
					e);
		}
	}

	public static void exportToPdf(File tempPdfFile, byte[] bytes)
			throws PortalException, SystemException {
		try {

			InputStream is = new ByteArrayInputStream(bytes);
			LoadOptions loadOptions = new LoadOptions();
			loadOptions.setLoadFormat(LoadFormat.HTML);

			Document doc = new Document(is, loadOptions);

			doc.save(new FileOutputStream(tempPdfFile),
					com.aspose.words.SaveFormat.PDF);

		} catch (Exception e) {
			String msg = "Aspose: Unable to export to PDF format.. some error occured: "
					+ e.getMessage();

			s_log.error(msg, e);

			throw new PortalException(
					"Aspose: Unable to export PDF format.. some error occured",
					e);
		}
	}

	public static void exportToXls(File tempXlsFile, byte[] bytes)
			throws PortalException, SystemException {
		try {

			InputStream is = new ByteArrayInputStream(bytes);

			HTMLLoadOptions loadOptions = new HTMLLoadOptions();

			Workbook sheet = new Workbook(is, loadOptions);

			sheet.save(new FileOutputStream(tempXlsFile),
					com.aspose.cells.SaveFormat.XLSX);

		} catch (Exception e) {
			String msg = "Aspose: Unable to export to ms excel format.. some error occured: "
					+ e.getMessage();

			s_log.error(msg, e);

			throw new PortalException(
					"Aspose: Unable to export to ms excel format.. some error occured",
					e);
		}
	}

	public static void exportDynamicListToDoc(File tempDocFile,
			DDLRecordSet recordSet) throws Exception {
		List<DDLRecord> ddlRecords = recordSet.getRecords();
		Fields fields = ddlRecords.get(0).getFields();
		Set<String> fieldNames = fields.getNames();
		List<String> fieldList = new ArrayList<String>();
		fieldList.addAll(fieldNames);
		Collections.reverse(fieldList);

		com.aspose.words.Document doc = new com.aspose.words.Document();

		// DocumentBuilder provides members to easily add content to a
		// document.
		DocumentBuilder builder = new DocumentBuilder(doc);
		Font font = builder.getFont();

		font.setSize(16);

		font.setColor(java.awt.Color.BLUE);

		font.setName("Arial");

		builder.insertParagraph();
		// Write a new paragraph in the document with the text

		builder.insertParagraph();
		builder.startTable();

		// Set height and define the height rule for the header row.
		builder.getRowFormat().setHeight(40.0);
		builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
		builder.getPageSetup().setPageWidth(1000);
		// Some special features for the header row.
		builder.getCellFormat().getShading()
				.setBackgroundPatternColor(new java.awt.Color(198, 217, 241));
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
		builder.getFont().setSize(14);
		builder.getFont().setName("Arial");
		builder.getFont().setBold(true);

		// builder.getCellFormat().setWidth(100.0);

		for (String fieldName : fieldList) {
			if (!fieldName.startsWith("_")) {
				// Setting the values to the table heading
				builder.insertCell();
				builder.write(fieldName.toUpperCase());

			}
		}
		builder.endRow();
		// Set features for the other rows and cells.
		builder.getCellFormat().getShading()
				.setBackgroundPatternColor(java.awt.Color.WHITE);
		// builder.getCellFormat().setWidth(100.0);

		builder.getCellFormat().setVerticalAlignment(
				CellVerticalAlignment.CENTER);

		// Reset height and define a different height rule for table body
		builder.getRowFormat().setHeight(30.0);
		builder.getRowFormat().setHeightRule(HeightRule.AUTO);

		for (DDLRecord record : ddlRecords) {

			builder.getFont().setSize(12);
			builder.getFont().setBold(false);
			for (String fieldName : fieldList) {
				if (!fieldName.startsWith("_")) {
					builder.insertCell();

					if (record.getFieldDataType(fieldName).equals("date")) {
						SimpleDateFormat timeStampFormat = new SimpleDateFormat(
								"yyyy-MM-dd");

						try {
							String dateValue = timeStampFormat.format(record
									.getFieldValue(fieldName));
							builder.write(dateValue);
						} catch (Exception e) {
							builder.write("---");
						}

					} else
						builder.write(record.getFieldValue(fieldName)
								.toString());

				}
			}

			builder.endRow();
		}
		builder.endTable();
		builder.insertParagraph();
		builder.insertParagraph();

		// Save the document

		doc.save(new FileOutputStream(tempDocFile),
				com.aspose.words.SaveFormat.DOC);

	}

	public static void exportDynamicListToPdf(File tempPdfFile,
			DDLRecordSet recordSet) throws Exception {
		List<DDLRecord> ddlRecords = recordSet.getRecords();
		Fields fields = ddlRecords.get(0).getFields();
		Set<String> fieldNames = fields.getNames();
		List<String> fieldList = new ArrayList<String>();
		fieldList.addAll(fieldNames);
		Collections.reverse(fieldList);

		// Create PDF document
		Pdf pdf1 = new Pdf();
		// Add a section into the PDF document
		Section sec1 = pdf1.getSections().add();
		sec1.getPageInfo().setPageWidth(1000);
		// Add a text paragraph into the section
		Table table = new Table(sec1);

		sec1.getParagraphs().add(table);
		MarginInfo margin = new MarginInfo();
		margin.setLeft(5f);
		margin.setRight(5f);
		margin.setTop(5f);
		margin.setBottom(5f);
		// Write a new paragraph in the document with the text
		// Set the default cell padding to the MarginInfo object
		table.setDefaultCellPadding(margin);
		table.setDefaultCellBorder(new BorderInfo(
				com.aspose.pdf.BorderSide.All, 0.1F));
		table.setBorder(new BorderInfo(com.aspose.pdf.BorderSide.All, 1F));

		Row row1 = table.getRows().add();

		for (String fieldName : fieldList) {
			if (!fieldName.startsWith("_")) {

				// Setting the values to the table heading
				row1.getCells().add(fieldName.toUpperCase());

			}
		}

		for (DDLRecord record : ddlRecords) {

			Row rows = table.getRows().add();
			for (String fieldName : fieldList) {
				if (!fieldName.startsWith("_")) {

					if (record.getFieldDataType(fieldName).equals("date")) {
						SimpleDateFormat timeStampFormat = new SimpleDateFormat(
								"yyyy-MM-dd");

						try {
							String dateValue = timeStampFormat.format(record
									.getFieldValue(fieldName));

							rows.getCells().add(dateValue);
						} catch (Exception e) {

							rows.getCells().add("---");
						}

					} else

						rows.getCells().add(
								record.getFieldValue(fieldName).toString());

				}
			}

		}

		// Create shape
		pdf1.save(new FileOutputStream(tempPdfFile));
	}

	public static void exportDynamicListToXls(File tempXlsFile,
			DDLRecordSet recordSet) throws Exception {
		List<DDLRecord> ddlRecords = recordSet.getRecords();
		Fields fields = ddlRecords.get(0).getFields();
		Set<String> fieldNames = fields.getNames();
		List<String> fieldList = new ArrayList<String>();
		fieldList.addAll(fieldNames);
		Collections.reverse(fieldList);
		Workbook workbook = new Workbook();
		// Obtaining the reference of the first worksheet
		Worksheet sheet = workbook.getWorksheets().get(0);

		com.aspose.cells.Cells cells = sheet.getCells();

		char ch = 'A';

		for (String fieldName : fieldList) {
			if (!fieldName.startsWith("_")) {
				// Setting the values to the cells
				com.aspose.cells.Cell cell = cells.get(ch + "1");
				cell.setValue(fieldName.toUpperCase());
				ch++;
			}
		}

		// Create a new style object.
		int styleIdx = workbook.getStyles().add();
		com.aspose.cells.Style style = workbook.getStyles().get(styleIdx);

		// Set the number format.
		style.setNumber(14);

		// Set the font color to red color.
		style.getFont().setColor(com.aspose.cells.Color.getBlue());
		style.getFont().setBold(true);
		// Name the style.
		style.setName("Heading");
		com.aspose.cells.Range range = cells.createRange("A1", ch + "1");
		// Initialize styleflag object.
		StyleFlag flag = new StyleFlag();

		// Set all formatting attributes on.
		flag.setAll(true);

		// Apply the style (described above)to the range.
		range.applyStyle(style, flag);
		style.update();

		ch = 'A';
		int i = 2;
		for (DDLRecord record : ddlRecords) {
			for (String fieldName : fieldList) {
				if (!fieldName.startsWith("_")) {

					com.aspose.cells.Cell cell = cells.get(ch + "" + i);

					if (record.getFieldDataType(fieldName).equals("date")) {
						SimpleDateFormat timeStampFormat = new SimpleDateFormat(
								"yyyy-MM-dd");

						try {
							String dateValue = timeStampFormat.format(record
									.getFieldValue(fieldName));
							cell.setValue(dateValue);
						} catch (Exception e) {
							cell.setValue("---");
						}

					} else
						cell.setValue(record.getFieldValue(fieldName));

					ch++;
				}
			}

			i++;
			ch = 'A';
		}

		// Save the document

		workbook.save(new FileOutputStream(tempXlsFile),
				com.aspose.cells.SaveFormat.XLSX);

	}
}