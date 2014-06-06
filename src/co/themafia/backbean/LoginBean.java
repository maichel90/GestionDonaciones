package co.themafia.backbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import co.themafia.entities.Persona;
import co.themafia.entities.Tipopersona;

public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String usuario;
	private String password;
	private String perfil;
	private Persona persona;
	private String login;
	@PersistenceContext EntityManager em;

	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
		String datos[] = login.split("_");
		usuario = datos[0];
		
	}
	
	public String ConsultarUsuario() {
		String datos[] = login.split("_");
		password = datos[1];
		String resp = "ninguno";
		try {
			Query q = em.createNamedQuery("Persona.onlyPerson");
			q.setParameter("email", this.usuario);
			q.setParameter("pass", password);
			persona = (Persona) q.getSingleResult();
			if(persona!=null){
				resp="autenticado";
				setPerfil(persona.getTipopersona().getRol());
			}else{
				FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_WARN,"Sistema informa", "No se ha podido autenticar"));
			}
		} catch (NoResultException e) {
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_WARN,"Sistema informa", "No se ha podido autenticar"));
			System.err.println("No se encontro ningun usuario con las credenciales");
		}
		System.out.println(resp);
		return resp;
	}
}
