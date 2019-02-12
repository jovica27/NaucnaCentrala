package root.demo.lucene.indexing.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.poi.POIXMLProperties;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import root.demo.lucene.model.IndexUnit;

public class Word2007Handler extends DocumentHandler {

	public IndexUnit getIndexUnit(File file) {
		IndexUnit retVal = new IndexUnit();

		try {
			XWPFDocument wordDoc = new XWPFDocument(new FileInputStream(file));
			XWPFWordExtractor we = new XWPFWordExtractor(wordDoc);

			String text = we.getText();
			retVal.setText(text);

			POIXMLProperties props = wordDoc.getProperties();

			String title = props.getCoreProperties().getTitle();
			retVal.setTitle(title);

			String keywords = props.getCoreProperties()
					.getUnderlyingProperties().getKeywordsProperty().getValue();
			retVal.setKeywords(keywords);

			retVal.setFilename(file.getCanonicalPath());
			
			String modificationDate=DateTools.dateToString(new Date(file.lastModified()),DateTools.Resolution.DAY);
			retVal.setFiledate(modificationDate);
			
			we.close();

		} catch (Exception e) {
			System.out.println("Problem pri parsiranju docx fajla");
		}

		return retVal;
	}

	@Override
	public String getText(File file) {
		String text = null;
		try {
			XWPFDocument wordDoc = new XWPFDocument(new FileInputStream(file));
			XWPFWordExtractor we = new XWPFWordExtractor(wordDoc);
			text = we.getText();
			we.close();
		}catch (Exception e) {
			System.out.println("Problem pri parsiranju docx fajla");
		}
		return text;
	}

}
