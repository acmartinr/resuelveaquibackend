package com.ecommerce.ecommerce.Utils;

import com.ecommerce.ecommerce.Models.ProductSold;
import com.ecommerce.ecommerce.Models.Sale;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PDFGenerator {


    public static void generatePDF(Sale sale, ProductSold[] productos, double amount)throws Exception {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A6);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Text
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
            contentStream.newLineAtOffset( 20, page.getMediaBox().getHeight() - 52);
            contentStream.showText("Name of the user: "+sale.getUserShopping().getFirstname()+" "+sale.getUserShopping().getLastname());
            for (ProductSold productSold:productos){
                int i=2;
                contentStream.newLineAtOffset( 20, page.getMediaBox().getHeight() - (52*i));
            contentStream.showText("name of the product: "+productSold.getProduct().getName()+" "+"price: "+productSold.getProduct().getPrice());
            contentStream.showText("quantity of product: "+productSold.getQuantity()+" "+"amount of this product: "+productSold.getQuantity()*productSold.getProduct().getPrice());
            i++;
            }
            contentStream.showText("Total amount: "+amount);
            contentStream.endText();
            contentStream.close();

            document.save("bill_payment.pdf");

        }
    }
}
