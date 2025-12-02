package alpha.planner;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileOutputStream;
import java.io.FileInputStream;

public class HtmlToPdfIText {
    public static void main(String[] args) throws Exception {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("user.home")+"\\Desktop\\output.pdf"));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(System.getProperty("user.home")+"\\Desktop\\KAREN_MID.html"));
        document.close();
        System.out.println("PDF Created!");
    }
}
