package co.themafia.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipopersona database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Tipopersona.findAll", query="SELECT t FROM Tipopersona t"),
	@NamedQuery(name="Tipopersona.byIdTipo", query="SELECT t FROM Tipopersona t where t.idTipoPersona=:id"),
	@NamedQuery(name="Tipopersona.findTipoAgent", query="SELECT t FROM Tipopersona t where t.idTipoPersona=3")
})public class Tipopersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idTipoPersona;

	private String rol;

	//bi-directional many-to-one association to Persona
	@OneToMany(mappedBy="tipopersona")
	private List<Persona> personas;

	public Tipopersona() {
	}

	public int getIdTipoPersona() {
		return this.idTipoPersona;
	}

	public void setIdTipoPersona(int idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}

	public String getRol() {
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public List<Persona> getPersonas() {
		return this.personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	public Persona addPersona(Persona persona) {
		getPersonas().add(persona);
		persona.setTipopersona(this);

		return persona;
	}

	public Persona removePersona(Persona persona) {
		getPersonas().remove(persona);
		persona.setTipopersona(null);

		return persona;
	}
}