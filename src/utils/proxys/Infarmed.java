package utils.proxys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import utils.agents.WebAgent;
import utils.documents.FITextParser;
import utils.documents.FilesHandler;
import utils.models.Medicine;
import utils.models.MedicineBuilder;
import utils.net.CertificatesFactory;
import utils.net.HTTPConnection;
import utils.net.SessionHandler;

public class Infarmed extends WebAgent{
	
	//Urls para intera��o com o site da infarmed
    private final static String INFAMED_BASE_URL = "http://app10.infarmed.pt";
    private final static String INFARMED_URL_LOGIN = "http://app7.infarmed.pt/infomed/login.php"; //Login
    private final static String INFAMED_URL_LISTA = "http://app7.infarmed.pt/infomed/lista.php"; //Pagina para submiss�o
    
    private final String USERNAME = "u_nome=infomed&"; //Username em 14/11/2016
    private final String PASSWORD = "u_pass=infomed"; //Password em 14/11/2016
    private final String INFAMED_PARAM_LISTA_1 = "dci=&estado_aim=&nome_comer=&disp=&data_fim=&data_inicio=&dosagem=&cnpem=&chnm=&forma_farmac=&atc=&disp=&pesquisa_titular=&numero_reg=";
    private final String INFAMED_RARAM_LISTA_2 = "&grupo_produto=&pesquisa_cft=&lim_sup=15&Submit_.x=99&Submit_.y=23&Submit_=Pesquisar!"; //Estas duas strings contem os parametros a submeter no formulario final
    private final String INFAMED_MED_ID_PARAM = "med_id=";
    private final String INFAMED_DCI_PARAM = "&dci";
    private final String INFAMED_RCM_LINK_PART_1 = "/infomed/download_ficheiro.php?med_id=";
    private final String INFAMED_RCM_LINK_PART_2 = "&tipo_doc=rcm";
    private final String INFAMED_FI_LINK_PART_1 = "/infomed/download_ficheiro.php?med_id=";
    private final String INFAMED_FI_LINK_PART_2 = "&tipo_doc=fi";
    
    private final static String CERTIFICATAE_NAME = "infarmed.pt.crt";
    
    private SessionHandler session = null;
    private CertificatesFactory cf = null;
    private String barCode = null;
    private String id = null;
    
    public String getVarId(){
    	if(id == null) return "";
    	return id;
    }
    
    public Infarmed(String barCode){
        this.barCode = barCode;
        
        try {
            cf = new CertificatesFactory(CERTIFICATAE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public boolean exists() {
		
		if(existsLocal())
			return true;
		else if(getFromWeb())
			return true;
		
		return false;
        
	}
	
	public boolean existsLocal(){
		if(!FITextParser.existsParsed(barCode)){
			if(FilesHandler.existsTxtFI(barCode)){
				FITextParser parser = new FITextParser(barCode);
				parser.parseAndSave();
				return true;
			}
		}else
			return true;
		
		return false;
	}
	
	private Medicine getFromLocal(){
		if(!existsLocal())
			return null;
		
		File file = new File(FITextParser.TXT_FI_PARSED_PATH + barCode + ".txt");
		Medicine medicine = null;
		medicine = new MedicineBuilder().getFromLocal(barCode);
		
		return medicine;
	}
	
	private boolean getFromWeb(){
		if(!this.isLogged())
			login();
		if(!getId())
			return false;
		byte[] fi = getFI();
		
		utils.documents.PDFtoTextParser parser = new utils.documents.PDFtoTextParser();
		if(fi != null && !FilesHandler.existsPdfFI(barCode))
		{
			try {
				parser.parseFIFromPDF( fi, barCode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		FITextParser par = new FITextParser(barCode);
		par.parseAndSave();
		return true;
	}
	
	private boolean getId(){
		
		try {
            connection = new HTTPConnection(INFAMED_URL_LISTA);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
		
		StringBuilder buffer = new StringBuilder();
		//Submeter codigo de barras
        connection.connect();
        connection.sendRequest("POST",INFAMED_PARAM_LISTA_1 + barCode + INFAMED_RARAM_LISTA_2, session);
        buffer = connection.getHTML();
        session.save(connection.getHeader());
        connection.disconnect();
      //Obter o id do medicamento
        /*int start_index = buffer.indexOf("infarmed.pt/infomed/detalhes.php");
        start_index = buffer.indexOf(INFAMED_MED_ID_PARAM,start_index);*/
        int start_index = buffer.indexOf(INFAMED_MED_ID_PARAM);
        int end_index = buffer.indexOf(INFAMED_DCI_PARAM,start_index);

        id = buffer.substring(start_index + INFAMED_MED_ID_PARAM.length(), end_index);
       
        return (id != null && isValidId());
	}
	
	//Fazer login
	public boolean login() {
		try {
            connection = new HTTPConnection(INFARMED_URL_LOGIN);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        connection.connect();
        connection.sendRequest("POST",USERNAME + PASSWORD, session);
        session = new SessionHandler();
        session.save(connection.getHeader());
        connection.disconnect();
        
        if(session.build() == null || session.build().equalsIgnoreCase(""))
            return false;
        
        session.setIsLogged(true);
        
        return true;
	}
	
	public boolean isLogged(){
		return (this.session != null && this.session.isLogged());
	}

	private boolean isValidId() {
		return id.matches("^-?\\d+$");
	}
	
	private byte[] getFI(){
		try {
            connection = new HTTPConnection(INFAMED_BASE_URL + INFAMED_FI_LINK_PART_1 + id + INFAMED_FI_LINK_PART_2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
		
		connection.connect();
        byte[] document = null;
        document = connection.getDocument();
        connection.disconnect();
		
		return document != null ? document : null;
	}
	
	private byte[] getRCM(){
		try {
            connection = new HTTPConnection(INFAMED_BASE_URL + INFAMED_RCM_LINK_PART_1 + id + INFAMED_RCM_LINK_PART_2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
		
		connection.connect();
        byte[] document = null;
        document = connection.getDocument();
        connection.disconnect();
		
		return document != null ? document : null;
	}

	@Override
	public Medicine getMedicine() {
		/*if((id == null || !isValidId()))
            return null;*/
		
		Medicine medicine = null;
		if(existsLocal())
			medicine = getFromLocal();
		else if(getFromWeb())
			medicine = getFromLocal();
		return medicine;
	}

}
