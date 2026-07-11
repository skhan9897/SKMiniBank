package com.bank.model;

import java.io.OutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ReceiptPDF {

    public static void generate(OutputStream out,
                                Customer customer) throws Exception {

        Document document = new Document(PageSize.A4,40,40,40,40);

        PdfWriter.getInstance(document, out);

        document.open();

        Font titleFont = new Font(Font.HELVETICA,22,Font.BOLD);

        Font headingFont = new Font(Font.HELVETICA,15,Font.BOLD);

        Font normalFont = new Font(Font.HELVETICA,12,Font.NORMAL);

        Paragraph title =
                new Paragraph("SK MINI BANK",titleFont);

        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);

        Paragraph subTitle =
                new Paragraph("ACCOUNT OPENING RECEIPT",
                        headingFont);

        subTitle.setAlignment(Element.ALIGN_CENTER);

        document.add(subTitle);

        document.add(new Paragraph(" "));
        
        
        PdfPTable table = new PdfPTable(2);

table.setWidthPercentage(100);

table.setSpacingBefore(15);

table.setWidths(new float[]{35,65});

// Customer Name
table.addCell(new PdfPCell(new Phrase("Customer Name", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getFullName(), normalFont)));

// Customer ID
table.addCell(new PdfPCell(new Phrase("Customer ID", headingFont)));
table.addCell(new PdfPCell(new Phrase(String.valueOf(customer.getCustomerId()), normalFont)));

// Customer Code
table.addCell(new PdfPCell(new Phrase("Customer Code", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getCustomerCode(), normalFont)));

// CIF Number
table.addCell(new PdfPCell(new Phrase("CIF Number", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getCifNumber(), normalFont)));

// Account Number
table.addCell(new PdfPCell(new Phrase("Account Number", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getAccountNumber(), normalFont)));

// Account Type
table.addCell(new PdfPCell(new Phrase("Account Type", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getAccountType(), normalFont)));

// Branch
table.addCell(new PdfPCell(new Phrase("Branch", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getBranch(), normalFont)));

// IFSC
table.addCell(new PdfPCell(new Phrase("IFSC Code", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getIfscCode(), normalFont)));

// Mobile
table.addCell(new PdfPCell(new Phrase("Mobile Number", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getMobile(), normalFont)));

// Email
table.addCell(new PdfPCell(new Phrase("Email", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getEmail(), normalFont)));

// Opening Balance
table.addCell(new PdfPCell(new Phrase("Opening Balance", headingFont)));
table.addCell(new PdfPCell(new Phrase("₹ " + customer.getBalance(), normalFont)));

// Login ID
table.addCell(new PdfPCell(new Phrase("Login ID", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getAccountNumber(), normalFont)));

// Login Password
table.addCell(new PdfPCell(new Phrase("Login Password", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getPassword(), normalFont)));

// Transaction PIN
table.addCell(new PdfPCell(new Phrase("Transaction PIN", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getTransactionPin(), normalFont)));

// UPI ID
table.addCell(new PdfPCell(new Phrase("UPI ID", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getUpiId(), normalFont)));

document.add(table);

document.add(new Paragraph(" "));

// Account Status
table.addCell(new PdfPCell(new Phrase("Account Status", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getStatus(), normalFont)));

// KYC Status
table.addCell(new PdfPCell(new Phrase("KYC Status", headingFont)));
table.addCell(new PdfPCell(new Phrase(customer.getKycStatus(), normalFont)));

document.add(new Paragraph(" "));

// Receipt Date
Paragraph date = new Paragraph(
        "Receipt Generated : " + new java.util.Date(),
        normalFont);

date.setAlignment(Element.ALIGN_RIGHT);

document.add(date);

document.add(new Paragraph(" "));

// Important Note
Paragraph noteTitle = new Paragraph(
        "IMPORTANT",
        headingFont);

document.add(noteTitle);

document.add(new Paragraph(
        "• Keep this receipt safe.",
        normalFont));

document.add(new Paragraph(
        "• Login using Account Number and Password.",
        normalFont));

document.add(new Paragraph(
        "• Never share your Password or Transaction PIN.",
        normalFont));

document.add(new Paragraph(" "));

// Signature Table
PdfPTable signTable = new PdfPTable(2);

signTable.setWidthPercentage(100);

signTable.setSpacingBefore(30);

signTable.setWidths(new float[]{50,50});

PdfPCell customerSign =
        new PdfPCell(new Phrase(
        "\n\n____________________\nCustomer Signature",
        normalFont));

customerSign.setBorder(0);

PdfPCell officerSign =
        new PdfPCell(new Phrase(
        "\n\n____________________\nAuthorized Bank Officer",
        normalFont));

officerSign.setHorizontalAlignment(Element.ALIGN_RIGHT);
officerSign.setBorder(0);

signTable.addCell(customerSign);
signTable.addCell(officerSign);

document.add(signTable);

document.add(new Paragraph(" "));

// Footer
Paragraph footer = new Paragraph(
        "SK MINI BANK\n"
      + "This is a Computer Generated Account Opening Receipt.\n"
      + "© 2026 SK Mini Bank. All Rights Reserved.",
        normalFont);

footer.setAlignment(Element.ALIGN_CENTER);

document.add(footer);

// Close PDF
document.close();
    }
    
    
}
        