package co.themafia.backbean;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;

import au.com.bytecode.opencsv.CSVReader;
import co.themafia.entities.Campanha;
import co.themafia.entities.Donacion;
import co.themafia.entities.Donante;
import co.themafia.entities.Persona;

public class DonanteBean {

	@PersistenceContext EntityManager em;
	@Resource UserTransaction ut;
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private Integer cedula;
	private String direccion;
	private String telefono;
	private String celular;
	private String teleOfi;
	private String celuOfi;
	private Integer id;
	private Integer seleccion;
	private Integer decision;
	private Integer idCamapanaSel;
	
	private UploadedFile file;
	
	private List<Donante> donantes;
	
	private List<Donante> donantesConDonaciones;
	
	public Integer getIdCamapanaSel() {
		return idCamapanaSel;
	}
	public void setIdCamapanaSel(Integer idCamapanaSel) {
		this.idCamapanaSel = idCamapanaSel;
	}
	public DualListModel<String> getLosDonantes() {
		return losDonantes;
	}
	public void setLosDonantes(DualListModel<String> losDonantes) {
		this.losDonantes = losDonantes;
	}
	public List<String> getDonantesIni() {
		return DonantesIni;
	}
	public void setDonantesIni(List<String> donantesIni) {
		DonantesIni = donantesIni;
	}
	public List<String> getDonantesSel() {
		return DonantesSel;
	}
	public void setDonantesSel(List<String> donantesSel) {
		DonantesSel = donantesSel;
	}

	private List<Donante> donantesSinDonaciones;
	
	private DualListModel<String> losDonantes;
	private List<String> DonantesIni = new ArrayList<String>();
	private List<String> DonantesSel = new ArrayList<String>();
	
	
	public Integer getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(Integer seleccion) {
		this.seleccion = seleccion;
	}
	
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Donante> getDonantes() {
		return donantes;
	}
	public void setDonantes(List<Donante> donantes) {
		this.donantes = donantes;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getCedula() {
		return cedula;
	}
	public void setCedula(Integer cedula) {
		this.cedula = cedula;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getTeleOfi() {
		return teleOfi;
	}
	public void setTeleOfi(String teleOfi) {
		this.teleOfi = teleOfi;
	}
	public String getCeluOfi() {
		return celuOfi;
	}
	public void setCeluOfi(String celuOfi) {
		this.celuOfi = celuOfi;
	}
	
	public List<Donante> getDonantesConDonaciones() {
		return donantesConDonaciones;
	}
	public void setDonantesConDonaciones(List<Donante> donantesLista) {
		this.donantesConDonaciones = donantesLista;
	}
	
	public List<Donante> getDonantesSinDonaciones() {
		return donantesSinDonaciones;
	}
	public void setDonantesSinDonaciones(List<Donante> donantesSinDonaciones) {
		this.donantesSinDonaciones = donantesSinDonaciones;
	}
	public Integer getDecision() {
		return decision;
	}
	public void setDecision(Integer decision) {
		this.decision = decision;
	}
	public void GuardarDonante() {
		Donante d = new Donante();
		d.setNombre(nombre);
		d.setCedula(cedula);
		d.setDireccion(direccion);
		d.setTelefono(telefono);
		d.setCelular(celular);
		if(getTeleOfi()==null){
			this.teleOfi = "0";
		}
		if(getCeluOfi()==null){
			this.celuOfi="0";
		}
		d.setTelefonoOficina(teleOfi);
		d.setCelularOficina(celuOfi);
		try {
			ut.begin();
			em.persist(d);
			ut.commit();
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_INFO,"Sistema informa", "Se ha Grabado el Donante"));
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_WARN,"Sistema informa", "No se ha podido Grabar Donante"));
			e.printStackTrace();
		}
	}
	
	@PostConstruct
	public void CargarDonantes(){
		donantes = em.createNamedQuery("Donante.findAll").getResultList();
	}
	
	public void CargarInfoDonante(){
		System.out.println(seleccion);
		try{
			Query q = em.createNamedQuery("Donante.findByCedula");
			q.setParameter("identificacion", seleccion);
			Donante donante = (Donante) q.getSingleResult();
			this.setId(donante.getIdDonante());
			this.setNombre(donante.getNombre());
			this.setCedula(donante.getCedula());
			this.setDireccion(donante.getDireccion());
			this.setTelefono(donante.getTelefono());
			this.setCelular(donante.getCelular());
			this.setTeleOfi(donante.getTelefonoOficina());
			this.setCeluOfi(donante.getCelularOficina());
		}catch(NoResultException nre){
			nre.printStackTrace();
			
		}
		
	}
	
	public void ModificarDonante() {
		Donante d = new Donante();
		d.setIdDonante(id);
		d.setNombre(nombre);
		d.setCedula(cedula);
		d.setDireccion(direccion);
		d.setTelefono(telefono);
		d.setCelular(celular);
		if(getTeleOfi()==null){
			this.teleOfi = "0";
		}
		if(getCeluOfi()==null){
			this.celuOfi="0";
		}
		d.setTelefonoOficina(teleOfi);
		d.setCelularOficina(celuOfi);
		try {
			ut.begin();
			em.merge(d);
			ut.commit();
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_INFO,"Sistema informa", "Se ha Modificado el Donante"));
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_WARN,"Sistema informa", "No se ha podido Modificar Donante"));
			e.printStackTrace();
		}
	}
	
	public void CargarArchivoDonantes(){
		try {
			//InputStream input = file.getInputstream();
			InputStream in = new BufferedInputStream(file.getInputstream());
			File archivo = new File("D:\\datos\\" + file.getFileName());
			FileOutputStream fout = new FileOutputStream(archivo);
			while (in.available() != 0) {
                fout.write(in.read());
            }
            fout.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AgregarDonantesArchivo();		
	}
	
	public void AgregarDonantesArchivo(){
		String [] nextLine;
	      try {
	    	  ut.begin();
	          File archivo = new File ("D:\\datos\\"+file.getFileName());
	    	  CSVReader reader = new CSVReader(new FileReader(archivo),';');
	            while ((nextLine = reader.readNext()) != null) {
	            	Donante d = new Donante();
	            	//System.out.println(nextLine[0]);
	        		d.setNombre(nextLine[0]);
	        		d.setCedula(Integer.parseInt(nextLine[1]));
	        		d.setDireccion(nextLine[2]);
	        		d.setTelefono(nextLine[3]);
	        		d.setCelular(nextLine[4]);
	        		d.setTelefonoOficina(nextLine[5]);
	        		d.setCelularOficina(nextLine[6]);
	        		em.persist(d);
	          }
	          ut.commit();
	          FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_INFO,"Sistema informa", "Se han agregado los Donantes"));
	      }
	      catch(Exception e){
	    	  FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_WARN,"Sistema informa", "Ocurrio un problema, Hable con el Administrador de la aplicacion"));
			  e.printStackTrace();
	      }    
	}
	
	
	public void elegirConsulta(){
		
	}
	
	public void inicializarDonantesConDonaciones(){
		
		Query q = em.createNamedQuery("Donante.findByDonadores");
		//donantesConDonaciones = new ArrayList<Donante>();
		donantesConDonaciones = q.getResultList();
		for (Donante don : donantesConDonaciones) {
			DonantesIni.add(don.getNombre());
		}
		DonantesSel = new ArrayList<String>();
		losDonantes = new DualListModel<>(DonantesIni, DonantesSel);
	}
	
	public void asignarDonantesSeleccionados(List<String> donantes){
		Query q2 = em.createNamedQuery("Campanha.findbyId");
		q2.setParameter("id", idCamapanaSel);
		Campanha campahana = (Campanha) q2.getSingleResult();
		List<Donante> donantesObjeto = new ArrayList<Donante>();
		for (String nombre : donantes) {
			Query q = em.createNamedQuery("Donante.findByNombre");
			q.setParameter("nombreDon", nombre);
			Donante donanteSeleccionado = (Donante) q.getSingleResult();
			donantesObjeto.add(donanteSeleccionado);
			System.out.println("asdasdasdas"+donanteSeleccionado.getCedula());
		}
//		campahana.setIdCampanha(idCamapanaSel);
		campahana.setDonantes(donantesObjeto);
		for (Donante donante : campahana.getDonantes()) {
			System.out.println(donante.getCedula()+"ghjghjghjhgjggj");
		}
		try {
			ut.begin();
			for (Donante donante : donantesObjeto) {
				System.out.println("insert into campanhadonante values ("+idCamapanaSel+","+donante.getIdDonante()+");");
				em.createNativeQuery("insert into gestioncampanas.campanhadonante values ("+idCamapanaSel+","+donante.getIdDonante()+");");
			}
			ut.commit();
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_INFO,"Sistema informa", "Se han agregado los donantes a la campaña"));
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_WARN,"Sistema informa", "No se han podido agregado los agentes a la campaña"));
			e.printStackTrace();
		}
	}

	
}
