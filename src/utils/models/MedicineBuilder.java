package utils.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

import utils.documents.FITextParser;

public class MedicineBuilder {

	public Medicine getFromLocal(String barcode){
		
		File file = new File(FITextParser.TXT_FI_PARSED_PATH + barcode + ".txt");
		if(!file.exists())
			return null;
		
		StringBuilder builder = new StringBuilder();
		FileInputStream in = null;
		try {
			int i;
			in = new FileInputStream(file);
			while((i = in.read()) >= 0){
				builder.append((char)i);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] titles = getTitles(builder);
		String[] sections = getSections(builder);
		
		if(titles != null && sections != null){
			Medicine medicine = new Medicine(barcode, new FolhetoInformativo(titles,sections));
			
			return medicine;
		}
		return null;
	}
	
	private String[] getTitles(StringBuilder document){
		
		Vector<String> aux = new Vector<String>();
		int start = 0, end = 0, next = 0;
		start = document.indexOf("<menu>");
		end = document.lastIndexOf("</menu>");
		while((next = document.indexOf("</menu>", start)) <= end && (next > 0) && (start >= 0)){
			aux.add(document.substring(start + "<menu>".length(),next));
			start = document.indexOf("<menu>",next);
		}
		
		String[] menu = new String[aux.size()];
		for(int i = 0; i < aux.size(); i++)
			menu[i] = aux.get(i);
		
		return menu;
	}
	
	private String[] getSections(StringBuilder document){
		
		Vector<String> aux = new Vector<String>();
		int start = 0, end = 0, next = 0;
		start = document.indexOf("<text>");
		end = document.lastIndexOf("</text>");
		while((next = document.indexOf("</text>", start)) <= end && (next > 0) && (start >= 0)){
			aux.add(document.substring(start + "<text>".length(),next));
			start = document.indexOf("<text>",next);
		}
		
		String[] sections = new String[aux.size()];
		for(int i = 0; i < aux.size(); i++)
			sections[i] = aux.get(i);
		
		return sections;
	}
}
