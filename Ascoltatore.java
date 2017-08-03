package Client;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.io.font.FontConstants;
import java.io.File;



import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class Ascoltatore implements WindowListener {
    public void windowClosing(WindowEvent e) {
        scrittura();

    }

    public void windowClosed(WindowEvent e) {
        System.exit(0);
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
    public void scrittura()
    {
        String nomeChatFirst=Username.returnUsernameStringa();
        String nomeChatSecond="";
        String testochat;
       for(int i=0;i<Utenti.getTestoUser().size();i++) {
           nomeChatSecond=Utenti.returnUser().get(i).getText();
           testochat=Utenti.returnTestoUser().get(i).getText();
           try {
               scrivi(testochat,nomeChatFirst+"$"+nomeChatSecond);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }
    public void scrivi(String messaggio,String nomeChat) throws IOException {
        String temp;
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(nomeChat + ".pdf"));
        Document doc = new Document(pdfDoc).setFontColor(Color.RED);
        Text testo = new Text(messaggio);
        Paragraph p = new Paragraph().setMargin(0);
        p.add(testo);
        doc.add(p);
        doc.close();
    }


}