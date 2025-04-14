package proj.Kape.Kapehan.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import proj.Kape.Kapehan.models.InvoiceItemModel;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Receipt {

    public static void generateReceipt(List<InvoiceItemModel> items, double total, double cash, double change, String filename) {
         // Create a PDF document
         Rectangle receiptSize = new Rectangle(150, 900);
         Document document = new Document(receiptSize, 0, 0, 0, 0);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Pull logo
            InputStream imageStream = Receipt.class.getResourceAsStream("/scenes/images/logo.png");
            if (imageStream != null) {
                Image logo = Image.getInstance(imageStream.readAllBytes());
                logo.scaleAbsolute(100, 100); // Resize the image
                logo.setAlignment(Image.ALIGN_CENTER);
                document.add(logo);
            } else {
                System.out.println("Image not found!");
            }

            // Css Shits
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Paragraph title = new Paragraph("Audacitea Cafe", boldFont);
            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);
            Font dets = new Font(Font.FontFamily.TIMES_ROMAN, 8);
            Font font = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
            Paragraph AdDits = new Paragraph("Phase 1, 119 Armstrong Ave, Village, Para\u00f1aque, 1709 Metro Manila", dets);
            AdDits.setAlignment(Element.ALIGN_CENTER);
            document.add(AdDits);
            Paragraph PonDits = new Paragraph("Phone: 0916 572 0001", dets);
            PonDits.setAlignment(Element.ALIGN_CENTER);
            Paragraph UserDets = new Paragraph("Cashier: Admin", dets);
            Paragraph separator = new Paragraph("=============================", font);
            separator.setAlignment(Element.ALIGN_CENTER);
            UserDets.setAlignment(Element.ALIGN_CENTER);
            document.add(PonDits);
            document.add(UserDets);
            document.add(separator);
            

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 1, 2, 2});

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

            PdfPCell itemHeader = new PdfPCell(new Phrase("Item", headerFont));
            PdfPCell qtyHeader = new PdfPCell(new Phrase("Qty", headerFont));
            PdfPCell priceHeader = new PdfPCell(new Phrase("Price", headerFont));
            PdfPCell subtotalHeader = new PdfPCell(new Phrase("Subtotal", headerFont));

            itemHeader.setBorder(Rectangle.BOTTOM);
            qtyHeader.setBorder(Rectangle.BOTTOM);
            priceHeader.setBorder(Rectangle.BOTTOM);
            subtotalHeader.setBorder(Rectangle.BOTTOM);

            table.addCell(itemHeader);
            table.addCell(qtyHeader);
            table.addCell(priceHeader);
            table.addCell(subtotalHeader);

            Font cellFont = new Font(Font.FontFamily.HELVETICA, 8);

            for (InvoiceItemModel item : items) {
                PdfPCell itemCell = new PdfPCell(new Phrase(item.getItemName(), cellFont));
                PdfPCell qtyCell = new PdfPCell(new Phrase(String.valueOf(item.getQuantity()), cellFont));
                PdfPCell priceCell = new PdfPCell(new Phrase("\u20b1" + item.getPrice(), cellFont));
                PdfPCell subtotalCell = new PdfPCell(new Phrase("\u20b1" + item.getSubtotal(), cellFont));

                itemCell.setBorder(Rectangle.NO_BORDER);
                qtyCell.setBorder(Rectangle.NO_BORDER);
                priceCell.setBorder(Rectangle.NO_BORDER);
                subtotalCell.setBorder(Rectangle.NO_BORDER);

                table.addCell(itemCell);
                table.addCell(qtyCell);
                table.addCell(priceCell);
                table.addCell(subtotalCell);
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total: \u20b1" + total,font));
            document.add(new Paragraph("Cash: \u20b1" + cash,font));
            document.add(new Paragraph("Change: \u20b1" + change,font));
            document.add(separator);
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            Paragraph time = new Paragraph("Date: " + timestamp,font);
            time.setAlignment(Element.ALIGN_CENTER);
            document.add(time);
            document.add(new Paragraph(" "));

            Paragraph footer = new Paragraph("\nThank you for your purchase!\n\"Come again soon \u2615", font);
            document.add(footer);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
