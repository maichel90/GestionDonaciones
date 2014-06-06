package co.themafia.backbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

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
	@PersistenceContext EntityManager em;
	@Resource UserTransaction ut;
	
	@PostConstruct
	private void CargarInformacion() {
		Tipopersona t = new Tipopersona();
		t.setIdTipoPersona(1);
		administradores = em.createNamedQuery("Persona.findByTipoPersona").setParameter("idTipoPersona",t).getResultList();
		campanas = em.createNamedQuery("Campanha.findAll").getResultList();
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

}
