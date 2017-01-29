package utils.models;

public class FolhetoInformativo {
	
	private String[] titles = null;
	private String[] sections = null;

	public FolhetoInformativo(String[] titles, String[] sections){
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
