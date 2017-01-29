package utils.models;

public class RCM {
	
	private String[] titles = null;
	private String[] sections = null;

	public RCM(String[] titles, String[] sections){
		this.titles = titles;
		this.sections = sections;
	}
	
	public void setTitles(String[] titles){
		this.titles = titles;
	}
	
	public void setSections(String[] sections){
		this.sections = sections;
	}
	
	public String[] getTitles(){
		return this.titles;
	}
	
	public String[] getSections(){
		return this.sections;
	}

}
