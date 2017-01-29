package utils.documents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import main.MyRESTServices;

public class FITextParser {
	
	public final static String TXT_FI_PARSED_PATH = FilesHandler.TXT_FI_PATH + "parsed" + MyRESTServices.separator;
	
	private String barcode = null;
	private String document = null;
	private FileInputStream in = null;
	private FileOutputStream out= null;
	
	
	public FITextParser(String barcode){
		this.barcode = barcode;
	}
	
	public void parseAndSave(){
		if(readDocument())
			document = trimDocAndRemoveChars(document);
		String[] titles = getFITitles(document);
		String[] fi = parse(titles, document);
		saveDocumentSections(titles,fi);
	}
	
	public static boolean existsParsed(String barcode){
		return (new File(TXT_FI_PARSED_PATH + barcode + ".txt").exists());
	}
	
	private void parse(){
		
	}
	
	private String[] parse(String[] titles, String document){
		
		String _document = document.toLowerCase();
		String[] _titles = new String[titles.length];
		for(int i = 0; i < _titles.length ; i++){
			_titles[i] = titles[i].toLowerCase();
		}
		
		String[] parsed = new String[_titles.length];
		int index = 0, next = 0;
		for(int i = 0; i < _titles.length -1; i++){
			index = _document.lastIndexOf(_titles[i]);
			next = _document.lastIndexOf(_titles[i+1]);
			parsed[i] = document.substring(index,next);
		}
		parsed[_titles.length -1] = document.substring(next);
		
		return parsed;
	}
	
	private boolean readDocument(){
		
		File file = new File(FilesHandler.TXT_FI_PATH + barcode + ".txt");
		document = "";
		
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		int aux;
		try {
			while((aux = in.read()) >= 0)
			{
				document += String.valueOf((char)aux);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private void saveDocumentSections(String[] titles, String[] sections){
		File file = new File(TXT_FI_PARSED_PATH + barcode + ".txt");
		
		try {
			if(!file.exists())
				file.createNewFile();
			else return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(titles.length != sections.length){
			try {
				for(String section : sections){
					out.write("<section>".getBytes());
					out.write(section.getBytes());
					out.write("<section>".getBytes());
				}
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			
			try {
				for(int i = 0; i < titles.length; i++){
					out.write("<menu>".getBytes());
					out.write(titles[i].getBytes());
					out.write("</menu>".getBytes());
					out.write("<text>".getBytes());
					out.write(sections[i].getBytes());
					out.write("</text>".getBytes());
				}
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void saveDocument(){
		File file = new File(TXT_FI_PARSED_PATH + barcode + ".txt");
		
		try {
			if(!file.exists())
				file.createNewFile();
			else return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			out.write(document.getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String trimDocAndRemoveChars(String document){
		
		String template = "";
		int aux, contador = 0, doc_size = document.length();
		String aux_line = "";
		
		for(aux = 0; aux < doc_size; aux++)
		{
			char tmpp = document.charAt(aux);
			if(tmpp == '\n')
			{
				if(!isUpperCase(aux_line) && !isDate(aux_line)){
					template += aux_line;
				}
				contador = 0;
				aux_line = "";
			}else if(aux == 32)contador++;
			if(aux != 12)aux_line += tmpp;
		}
		
		return template;
	}
	
	private boolean isUpperCase(String line){
		for(int i = 0; i < line.length() -1 ; i++){
			if((int)line.charAt(i) < 65 || (int)line.charAt(i) > 90)
				if((int)line.charAt(i) != 32 && line.charAt(i) != '\n')
					return false;
		}
		return true;
	}
	
	private boolean isDate(String line){
		boolean hasNumber = false;
		boolean hasHifem = false;
		for(int i = 0; i < line.length() - 1; i++){
			if(line.charAt(i) > 47 && line.charAt(i) < 58)
				hasNumber = true;
			if(line.charAt(i) == 45)
				hasHifem = true;
		}
		return hasNumber && hasHifem;
	}
	
	private boolean isPossibleTitle(String[] document, String line, int start){
		return startsWithNumber(line) && secondIsPoint(line) && existsAfter(line, document, start); 
	}
	
	private boolean existsAfter(String line, String[] document, int start){
		
		for(int i = start; i < document.length - 1; i++)
			if(line.equalsIgnoreCase(line))
				return true;
		return false;
	}
	
	private String[] getLines(String document, int nlinhas){
		String[] lines = new String[nlinhas];
		lines = document.split(System.getProperty("line.separator"));
		
		return lines;
	}
	
	private int countLines(String document){
		String lines[] = document.split("\r\n|\r|\n");
		return lines.length;
	}
	
	private boolean startsWithNumber(String line){
		return ((int)line.charAt(0) > 47) && ((int)line.charAt(0) < 58);
	}
	
	private boolean secondIsPoint(String line){
		return line.charAt(1) == 46;
	}
	
	private String[] getFITitles(String  document){
		int n_lines = countLines(document);
		String[] doc_lines = getLines(document,n_lines);
		Hashtable<String,String> titles = new Hashtable<String,String>();
		String aux = "";
		
		for(int i = 0; i < n_lines - 3; i++){
			
			if(titles.isEmpty()){ //ainda não foi encontrado nenhum candidato
				
				/*
				 * Temos que preencher a primeira linha
				 * 
				 * */
				
				while(!startsWithNumber(doc_lines[i]) || !secondIsPoint(doc_lines[i]))
					i++;
				if((!startsWithNumber(doc_lines[i - 1]) || !secondIsPoint(doc_lines[i - 1])) 
						&& (startsWithNumber(doc_lines[i + 1]) || secondIsPoint(doc_lines[i + 1]))){
					// Foi encontrado um candidato para primeira linha do cabeçalho
					// Falta verificar que não existe repetido no indice
					if(existsAfter(doc_lines[i], doc_lines,i)){
						titles.put("" + doc_lines[i].charAt(0), doc_lines[i]);
						i++;
					}
				}
			}
			
		/*
		 * Agora falta obter os candidatos.
		 * Verificar se a linha anterior e a seguinte começam por numero e ponto.
		 * 
		 * */
			
			else{
				if(isPossibleTitle(doc_lines,doc_lines[i],i + 1))
						titles.put("" + doc_lines[i].charAt(0), doc_lines[i]);
			}
		}
		
		String[] final_lines;
		if(titles.size() > 0){
			final_lines = new String[titles.size()];
			int i = titles.size() -1;
			for(String _line : titles.keySet()){
				final_lines[i] = titles.get(_line);
				i--;
			}
		}else
			return null;
		return final_lines;
	}
}
