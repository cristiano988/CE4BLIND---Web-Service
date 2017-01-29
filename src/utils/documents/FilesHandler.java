package utils.documents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import main.MyRESTServices;
import static java.nio.file.StandardCopyOption.*;

public class FilesHandler {
	
	public final static String PDF_TO_TEXT_TOOL_FOLDER_PATH = MyRESTServices.ASSETS_PATH + MyRESTServices.separator + "tools" + MyRESTServices.separator + "pdftotext" + MyRESTServices.separator;
	public final static String PDF_TO_TEXT_TOOL_PATH = PDF_TO_TEXT_TOOL_FOLDER_PATH + "pdftotext";
	public final static String PDF_PATH = MyRESTServices.ASSETS_PATH + MyRESTServices.separator + "documents" + MyRESTServices.separator + "pdf" + MyRESTServices.separator;
	public final static String TXT_PATH = MyRESTServices.ASSETS_PATH + MyRESTServices.separator + "documents" + MyRESTServices.separator + "txt" + MyRESTServices.separator;
	public final static String PDF_FI_PATH = PDF_PATH + "fi"  + MyRESTServices.separator;
	public final static String PDF_RCM_PATH = PDF_PATH + "rcm"  + MyRESTServices.separator;
	public final static String TXT_FI_PATH = TXT_PATH + "fi" + MyRESTServices.separator;
	public final static String TXT_RCM_PATH = TXT_PATH + "rcm"  + MyRESTServices.separator;
	
	public FilesHandler(){
		
	}
	
	public static boolean existsTxtFI(String barcode){
		return (new File(TXT_FI_PATH + barcode + ".txt").exists());
	}
	
	public static boolean existsTxtRCM(String barcode){
		return (new File(TXT_RCM_PATH + barcode + ".txt").exists());
	}
	
	public static boolean existsPdfFI(String barcode){
		return (new File(PDF_FI_PATH + barcode + ".pdf").exists());
	}
	
	public static boolean existsPdfRCM(String barcode){
		return (new File(PDF_RCM_PATH + barcode + ".pdf").exists());
	}
	
	public static boolean existsPdfIntxt(String barcode){
		return (new File(TXT_FI_PATH + barcode + ".pdf").exists());
	}
	
	public static boolean fiFromBytes(byte[] data, String name){
		File file = new File(PDF_FI_PATH + name + ".pdf");
		try{
			if(!file.exists())
				file.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
		fileFromBytes(data, file);
		return true;
	}
	
	public static boolean rcmFromBytes(byte[] data, String name){
		File file = new File(PDF_RCM_PATH + name + ".pdf");
		try{
			if(!file.exists())
				file.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
		fileFromBytes(data, file);
		return true;
	}
	
	private static void fileFromBytes(byte[] data, File file){
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.write(data);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean fiPDFinTXTPath(byte[] data, String name){
		File file = new File(TXT_FI_PATH + name + ".pdf");
		try{
			if(!file.exists())
				file.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
		fileFromBytes(data, file);
		return true;
	}
	
	public static void mooveFile(String source, String destination) throws IOException{
		
		File sourceFile = new File(source);
		File destinationFile = new File(destination);
		
		if(destinationFile.exists())
			Files.delete(destinationFile.toPath());
		Files.move(sourceFile.toPath(), destinationFile.toPath());
	}
}
