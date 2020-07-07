package it.tredi.dw4.docway.beans;

import it.tredi.dw4.adapters.AdaptersConfigurationLocator;
import it.tredi.dw4.adapters.DocumentFormsAdapter;
import it.tredi.dw4.beans.Showdoc;
import it.tredi.dw4.docway.model.XwFile;
import it.tredi.dw4.utils.XMLUtil;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;

public class DocWayFirmaRemotaAruba extends ShowdocDoc{

	private Showdoc showdoc;
	private DocumentFormsAdapter formsAdapter;
	private boolean visible = false;

	private String nRecord;
	private String outputFileType = "";
	private String credenzialeFirmaRemota = "";

	private String userPassword;
	private String userOtp;

	private List<XwFile> files = new ArrayList<XwFile>();
	private List<XwFile> filesDaFirmare = new ArrayList<XwFile>();

	private String message = "";
	
	public DocWayFirmaRemotaAruba() throws Exception {
		this.formsAdapter 	= new DocumentFormsAdapter(AdaptersConfigurationLocator.getInstance().getAdapterConfiguration("docwayService"));
	}

	@Override
	public void init(Document dom) {
		this.credenzialeFirmaRemota = XMLUtil.parseStrictAttribute(dom, "//configFirmaDigitale/@credenzialeFirmaRemota");
		this.visible = true;
	}
	
	@Override
	public void reload() throws Exception {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Chiusura del popup dialog
	 * @return
	 * @throws Exception
	 */
	public String close() throws Exception {
		visible = false;

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("showdocFirmaDigitaleAruba", null);
		return null;
	}

	public void setFormsAdapter(DocumentFormsAdapter formsAdapter) {
		this.formsAdapter = formsAdapter;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserOtp() {
		return userOtp;
	}

	public void setUserOtp(String userOtp) {
		this.userOtp = userOtp;
	}

	public String getnRecord() {
		return nRecord;
	}

	public void setnRecord(String nRecord) {
		this.nRecord = nRecord;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getOutputFileType() {
		return outputFileType;
	}

	public void setOutputFileType(String outputFileType) {
		this.outputFileType = outputFileType;
	}

	public String getCredenzialeFirmaRemota() {
		return credenzialeFirmaRemota;
	}

	public void setCredenzialeFirmaRemota(String credenzialeFirmaRemota) {
		this.credenzialeFirmaRemota = credenzialeFirmaRemota;
	}

	public List<XwFile> getFilesDaFirmare() {
		return filesDaFirmare;
	}

	public void setFilesDaFirmare(List<XwFile> filesDaFirmare) {
		this.filesDaFirmare = filesDaFirmare;
	}

	public List<XwFile> getFiles() {
		return files;
	}

	public void setFiles(List<XwFile> files) {
		this.files = files;
	}

	public Showdoc getShowdoc() {
		return showdoc;
	}

	public void setShowdoc(Showdoc showdoc) {
		this.showdoc = showdoc;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
