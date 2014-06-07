package co.themafia.backbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PostLoad;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import org.primefaces.model.DualListModel;

import co.themafia.entities.Campanha;
import co.themafia.entities.Persona;
import co.themafia.entities.Tipopersona;

public class CampanaBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombreCamapana;
	private String razonSocial;
	private String contacto;
	private String telefonoContacto;
	private Integer idAdministrador;
	private List<Persona> administradores;
	private List<Campanha> campanas;
	
	private DualListModel<String> losAgentes;
	
	private List<Persona> agentes;
	
	public List<String> getAgentesIni() {
		return agentesIni;
	}


	public void setAgentesIni(List<String> agentesIni) {
		this.agentesIni = agentesIni;
	}

	private List<String> agentesIni;
	private List<String> agentesSel;
	
	@PersistenceContext EntityManager em;
	@Resource UserTransaction ut;
	
	private Integer idCamapanaSel;
	
	@PostConstruct
	private void CargarInformacion() {
		agentesIni = new ArrayList<String>();
		Tipopersona t = new Tipopersona();
		t.setIdTipoPersona(1);
		administradores = em.createNamedQuery("Persona.findByTipoPersona").setParameter("idTipoPersona",t).getResultList();
		campanas = em.createNamedQuery("Campanha.findAll").getResultList();
		//System.out.println(tip.getRol()+"        Rol");
	}
	
	
	public DualListModel<String> getLosAgentes() {
		return losAgentes;
	}


	public void setLosAgentes(DualListModel<String> losAgentes) {
		this.losAgentes = losAgentes;
	}


	public List<String> getAgentesSel() {
		return agentesSel;
	}


	public void setAgentesSel(List<String> agentesSel) {
		this.agentesSel = agentesSel;
	}


	public Integer getIdCamapanaSel() {
		return idCamapanaSel;
	}

	public void setIdCamapanaSel(Integer idCamapanaSel) {
		this.idCamapanaSel = idCamapanaSel;
	}

	public String getNombreCamapana() {
		return nombreCamapana;
	}
	public void setNombreCamapana(String nombreCamapana) {
		this.nombreCamapana = nombreCamapana;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public String getTelefonoContacto() {
		return telefonoContacto;
	}
	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}
	public List<Persona> getAdministradores() {
		return administradores;
	}
	public void setAdministradores(List<Persona> administradores) {
		this.administradores = administradores;
	}
	public Integer getIdAdministrador() {
		return idAdministrador;
	}
	public void setIdAdministrador(Integer idAdministrador) {
		this.idAdministrador = idAdministrador;
	}
	public List<Campanha> getCampanas() {
		return campanas;
	}
    public void setCampanas(List<Campanha> campanas) {
		this.campanas = campanas;
	}
	
	public List<Persona> getAgentes() {
		return agentes;
	}

	public void setAgentes(List<Persona> agentes) {
		this.agentes = agentes;
	}

	public void GuardarCamapana(Persona creador) {
		Campanha c = new Campanha();
		c.setNombre(nombreCamapana);
		c.setRazonSocial(razonSocial);
		c.setContacto(contacto);
		c.setTelefonoContacto(telefonoContacto);
		c.setPersona1(creador);
		c.setPersona2(ConsultarPersona());
		c.setEstado((byte) 1);
		try {
			ut.begin();
			em.persist(c);
			ut.commit();
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_INFO,"Sistema informa", "Se realizo la creacion de la campana"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_WARN,"Sistema informa", "Revise los datos para la creacion"));
		}
	}
	
	private Persona ConsultarPersona() {
		Query q = em.createNamedQuery("Persona.findByIdPersona");
		q.setParameter("id", this.idAdministrador);
		Persona t = (Persona) q.getSingleResult();
		return t;
	}
		
	public void mostrarAgentes(){
		Query q = em.createNamedQuery("Tipopersona.findTipoAgent");
		Tipopersona tip = (Tipopersona) q.getSingleResult();
		Query q2 = em.createNamedQuery("Persona.findAgents");
		q2.setParameter("agente", tip);
		agentes = q2.getResultList();
	}

}
