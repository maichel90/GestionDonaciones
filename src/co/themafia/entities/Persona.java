package co.themafia.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the persona database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Persona.findAll", query="SELECT p FROM Persona p"),
	@NamedQuery(name="Persona.findByIdPersona", query="SELECT p FROM Persona p where p.idPersona=:id"),
	@NamedQuery(name="Persona.findByTipoPersona", query="SELECT p FROM Persona p where p.tipopersona=:idTipoPersona"),
	@NamedQuery(name="Persona.onlyPerson", query="SELECT p FROM Persona p where p.email=:email and p.password=:pass"),
	@NamedQuery(name="Persona.findAgents", query="SELECT p FROM Persona p where p.tipopersona=:agente")
})
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idPersona;

	private String celular;

	private String direccion;

	private String edad;

	private String email;

	private String nombre;

	private String password;

	private String telefono;

	//bi-directional many-to-one association to Campanha
	@OneToMany(mappedBy="persona1")
	private List<Campanha> campanhas1;

	//bi-directional many-to-one association to Campanha
	@OneToMany(mappedBy="persona2")
	private List<Campanha> campanhas2;

	//bi-directional many-to-one association to Tipopersona
	@ManyToOne
	private Tipopersona tipopersona;

	//bi-directional many-to-many association to Campanha
	@ManyToMany
	@JoinTable(
		name="personacampanha"
		, joinColumns={
			@JoinColumn(name="Persona_idPersona")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Campanha_idCampanha")
			}
		)
	private List<Campanha> campanhas3;

	public Persona() {
	}

	public int getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEdad() {
		return this.edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Campanha> getCampanhas1() {
		return this.campanhas1;
	}

	public void setCampanhas1(List<Campanha> campanhas1) {
		this.campanhas1 = campanhas1;
	}

	public Campanha addCampanhas1(Campanha campanhas1) {
		getCampanhas1().add(campanhas1);
		campanhas1.setPersona1(this);

		return campanhas1;
	}

	public Campanha removeCampanhas1(Campanha campanhas1) {
		getCampanhas1().remove(campanhas1);
		campanhas1.setPersona1(null);

		return campanhas1;
	}

	public List<Campanha> getCampanhas2() {
		return this.campanhas2;
	}

	public void setCampanhas2(List<Campanha> campanhas2) {
		this.campanhas2 = campanhas2;
	}

	public Campanha addCampanhas2(Campanha campanhas2) {
		getCampanhas2().add(campanhas2);
		campanhas2.setPersona2(this);

		return campanhas2;
	}

	public Campanha removeCampanhas2(Campanha campanhas2) {
		getCampanhas2().remove(campanhas2);
		campanhas2.setPersona2(null);

		return campanhas2;
	}

	public Tipopersona getTipopersona() {
		return this.tipopersona;
	}

	public void setTipopersona(Tipopersona tipopersona) {
		this.tipopersona = tipopersona;
	}

	public List<Campanha> getCampanhas3() {
		return this.campanhas3;
	}

	public void setCampanhas3(List<Campanha> campanhas3) {
		this.campanhas3 = campanhas3;
	}

}