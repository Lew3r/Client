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
               scrivicolorato(testochat,nomeChatFirst+"$"+nomeChatSecond);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }
    public void scrivicolorato(String messaggio,String nomeChat) throws IOException {
        String temp;
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(nomeChat+".pdf"));
        Document doc = new Document(pdfDoc);
        if(!(messaggio.equals(""))) {
            temp = messaggio.substring(1);
            while (temp.indexOf("£") != -1 || temp.indexOf("$") != -1) {
                temp = messaggio.substring(1);
                String testo;
                int indicedoll = messaggio.indexOf("$");
                int indicester = messaggio.indexOf("£");
                int indicedoll2 = temp.indexOf("$");
                int indicester2 = temp.indexOf("£");
                if (indicedoll == -1)
                    indicedoll = 100000000;
                if (indicester == -1)
                    indicester = 100000000;
                if (indicedoll2 == -1)
                    indicedoll2 = 100000000;
                if (indicester2 == -1)
                    indicester2 = 100000000;

                if (indicedoll < indicester) {
                    if (indicedoll2 < indicester2) {
                        if (indicedoll2 != indicester2) {
                            testo = temp.substring(0, indicedoll2);
                            scrivired(testo,nomeChat,pdfDoc,doc);
                            messaggio = temp.substring(indicedoll2);
                        }

                    } else {
                        if (indicedoll2 != indicester2) {
                            testo = temp.substring(0, indicester2);
                            scrivired(testo,nomeChat,pdfDoc,doc);
                            messaggio = temp.substring(indicester2);
                        }

                    }
                } else {
                    if (indicedoll2 < indicester2) {
                        if (indicedoll2 != indicester2) {
                            testo = temp.substring(0, indicedoll2);
                            scriviblue(testo,nomeChat,pdfDoc,doc);
                            messaggio = temp.substring(indicedoll2);
                        }
                    } else {
                        if (indicedoll2 != indicester2) {
                            testo = temp.substring(0, indicester2);
                            scriviblue(testo,nomeChat,pdfDoc,doc);
                            messaggio = temp.substring(indicester2);
                        }
                    }
                }

            }
            if(messaggio.charAt(0)=='$') {
                scrivired(messaggio.substring(1), nomeChat, pdfDoc, doc);
                doc.close();
            }
            else {
                scriviblue(messaggio.substring(1), nomeChat, pdfDoc, doc);
                doc.close();
            }
        }
    }
    public void scrivired(String testorosso,String nomeChat,PdfDocument pdfDoc,Document doc) throws IOException {

        Text testo=new Text(testorosso).setFontColor(Color.RED);
        Paragraph p = new Paragraph().setMargin(0);
             p.add(testo);
             doc.add(p);
       // doc.close();
    }
    public void scriviblue(String testoblue, String nomeChat, PdfDocument pdfDoc, Document doc) throws IOException {
        Text testo=new Text(testoblue).setFontColor(Color.BLUE) .setFont(PdfFontFactory.createFont(FontConstants.HELVETICA));
        Paragraph p = new Paragraph().setMargin(0);
        p.add(testo);
        doc.add(p);
    }
}