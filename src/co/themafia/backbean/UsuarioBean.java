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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.primefaces.event.RowEditEvent;

import co.themafia.entities.Persona;
import co.themafia.entities.Tipopersona;

public class UsuarioBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String edad;
	private String email;
	private String telefono;
	private String celular;
	private String direccion;
	private String contrasena;
	private Integer idPerfil;
	private List<Tipopersona> tipoPersonas;
	private List<Persona> usuarios;
	@PersistenceContext EntityManager em;
	@Resource UserTransaction ut;
	
	@PostConstruct
	private void CargarDatos(){
		tipoPersonas = em.createNamedQuery("Tipopersona.findAll").getResultList();
		usuarios = em.createNamedQuery("Persona.findAll").getResultList();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEdad() {
		return edad;
	}
	public void setEdad(String edad) {
		this.edad = edad;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public List<Tipopersona> getTipoPersonas() {
		return tipoPersonas;
	}
	public void setTipoPersonas(List<Tipopersona> tipoPersonas) {
		this.tipoPersonas = tipoPersonas;
	}	
	public Integer getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}
	public List<Persona> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Persona> usuarios) {
		this.usuarios = usuarios;
	}

	public void GuardarNuevoUsuario() {
		
		System.out.println("ENTRO");
		Persona p = new Persona();
		p.setNombre(nombre);
		p.setEdad(edad);
		p.setEmail(email);
		p.setTelefono(telefono);
		p.setCelular(celular);
		p.setTipopersona(ConsultarTipoPersona());
		p.setPassword(contrasena);
		p.setDireccion(direccion);
		
		try {
			ut.begin();
			em.persist(p);
			ut.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
		if(p.getIdPersona()!=0){
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_INFO,"Sistema informa","Se ha agregado el usuario con exito"));
		}else {
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_WARN,"Sistema informa", "Se ha presentado un error por favor verifique los datos"));
		}
	}
	
	private Tipopersona ConsultarTipoPersona() {
		Query q = em.createNamedQuery("Tipopersona.byIdTipo");
		q.setParameter("id", this.idPerfil);
		Tipopersona t = (Tipopersona) q.getSingleResult();
		return t;
	}
	
	public void EditarUsuario(RowEditEvent event) {
		Persona p = (Persona) event.getObject();
		p.setTipopersona(ConsultarTipoPersona());
		try {
			ut.begin();
			em.merge(p);
			ut.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_WARN,"Sistema informa", "Se realizo la modificacion"));
	}


}
