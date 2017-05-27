/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfupdate;

/**
 *
 * @author Dass
 */
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.XfaForm;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
 
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 
public class PdfUpdate {
 
    public static final String SRC = "D:/RBS/PDFupdate/xfasampleEn.pdf";
    //public static final String DEST = "D:/RBS/PDFupdate/data.xml";
    public static final String XML = "D:/RBS/PDFupdate/data.xml";
    public static final String DEST = "D:/RBS/PDFupdate/xfasampleEn2.pdf";
    
 
    public static void main(String[] args) throws IOException, DocumentException, TransformerException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        //new PdfUpdate().readXml(SRC, DEST);
        //new PdfUpdate().manipulatePdf(SRC, DEST);
        new PdfUpdate().manipulatePdf(SRC, DEST);
    }
 
    public void manipulatePdf(String src, String dest)
        throws IOException, DocumentException {
        PdfReader.unethicalreading = false;
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader,
                new FileOutputStream(dest));
        AcroFields form = stamper.getAcroFields();
        XfaForm xfa = form.getXfa();
        xfa.fillXfaForm(new FileInputStream(XML));
        stamper.close();
        reader.close();
    }
    
    public void manipulatePdf2(String src, String dest)
        throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader,
                new FileOutputStream(dest));
        AcroFields form = stamper.getAcroFields();
        XfaForm xfa = form.getXfa();
        xfa.fillXfaForm(new FileInputStream(XML));
        stamper.close();
        reader.close();
    }
    
    public void readXml(String src, String dest)
        throws IOException, DocumentException, TransformerException {
        PdfReader reader = new PdfReader(src);
        AcroFields form = reader.getAcroFields();
        XfaForm xfa = form.getXfa();
        Node node = xfa.getDatasetsNode();
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if("data".equals(list.item(i).getLocalName())) {
                node = list.item(i);
                break;
            }
        }
        list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if("movies".equals(list.item(i).getLocalName())) {
                node = list.item(i);
                break;
            }
        }
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        FileOutputStream os = new FileOutputStream(dest);
        tf.transform(new DOMSource(node), new StreamResult(os));
        reader.close();
    }
}