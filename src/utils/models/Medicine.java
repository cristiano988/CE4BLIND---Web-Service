package utils.models;

public class Medicine {

	private String barcode = null;
	private String id = null;
	private FolhetoInformativo fi = null;
	private RCM rcm = null;
	
	public Medicine(String barcode){
		this.barcode = barcode;
	}
	
	public Medicine(String barcode, FolhetoInformativo fi){
		this(barcode, fi, null);
	}
	
	public Medicine(String barcode, FolhetoInformativo fi, RCM rcm){
		this.barcode = barcode;
		this.fi = fi;
		this.rcm = rcm;
	}

	public FolhetoInformativo getFi() {
		return fi;
	}

	public void setFi(FolhetoInformativo fi) {
		this.fi = fi;
	}

	public RCM getRcm() {
		return rcm;
	}

	public void setRcm(RCM rcm) {
		this.rcm = rcm;
	}
	
	
}
