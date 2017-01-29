package utils.documents;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PDFtoTextParser {
	
	public PDFtoTextParser(){
		
	}
	
	private boolean pdfToTxt(String pdfPath){
		try {
			Runtime.getRuntime().exec(FilesHandler.PDF_TO_TEXT_TOOL_PATH + " -layout " + pdfPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void parseFIFromPDF(byte[] document, String barcode) throws Exception{
		/*if(!FilesHandler.existsPdfFI(barcode))
			FilesHandler.fiFromBytes(document, barcode);*/
		if(!FilesHandler.existsPdfIntxt(barcode))
			FilesHandler.fiPDFinTXTPath(document, barcode);
		if(!pdfToTxt(FilesHandler.TXT_FI_PATH + barcode + ".pdf"))
			throw new Exception("Error on Parsing: error parsing pdf");
		while(!(new File(FilesHandler.TXT_FI_PATH + barcode + ".txt").exists())){}
	}
	
	public void parseRCMFromPDF(byte[] document, String barcode) throws Exception{
		if(!FilesHandler.existsPdfRCM(barcode))
			FilesHandler.rcmFromBytes(document, barcode);
		if(!pdfToTxt(FilesHandler.PDF_RCM_PATH + barcode + ".pdf"))
			throw new Exception("Error on Parsing: error parsing pdf");
		while(!(new File(FilesHandler.PDF_FI_PATH + barcode + ".txt").exists())){}
		FilesHandler.mooveFile(FilesHandler.PDF_RCM_PATH + barcode + ".txt", 
				FilesHandler.TXT_RCM_PATH + barcode + ".txt");
	}
	
}
