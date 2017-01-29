package utils.response;

import utils.models.Medicine;

public class InfarmedResponseParser implements ResponseParser{

	private Medicine medicine = null;
	
	public InfarmedResponseParser(Medicine medicine){
		this.medicine = medicine;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	@Override
	public String parse() {
		String response = "<html><head><meta charset=\"UTF-8\"></head><body><fi>";
		
		for(String title : medicine.getFi().getTitles()){
			response += "<menu>";
			response += title;
			response += "</menu>";
		}
		
		for(String text : medicine.getFi().getSections()){
			response += "<text>";
			response += text;
			response += "</text>";
		}
		
		response += "</fi><rcm>";
		
		/*
		 * 
		 * Por o código para o rcm e demais ficheiros
		 * 
		 *
		 */
		
		response += "</rcm></body></html>";
		
		return response;
	}
	
}
