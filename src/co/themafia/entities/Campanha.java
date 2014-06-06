package co.themafia.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the campanha database table.
 * 
 */
@Entity
@NamedQuery(name="Campanha.findAll", query="SELECT c FROM Campanha c")
public class Campanha implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCampanha;

	private String contacto;

	private byte estado;

	@Temporal(TemporalType.DATE)
	private Date fechaFinal;

	@Temporal(TemporalType.DATE)
	private Date fechaInicio;

	private int meta;

	private String nombre;

	private String razonSocial;

	private String telefonoContacto;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="PersonaCreacion")
	private Persona persona1;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="PersonaAdmin")
	private Persona persona2;

	//bi-directional many-to-many association to Donante
	@ManyToMany(mappedBy="campanhas")
	private List<Donante> donantes;

	//bi-directional many-to-one association to Donacion
	@OneToMany(mappedBy="campanha")
	private List<Donacion> donacions;

	//bi-directional many-to-many association to Persona
	@ManyToMany
	@JoinTable(
		name="personacampanha"
		, joinColumns={
			@JoinColumn(name="Campanha_idCampanha")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Persona_idPersona")
			}
		)
	private List<Persona> personas;

	public Campanha() {
	}

	public int getIdCampanha() {
		return this.idCampanha;
	}

	public void setIdCampanha(int idCampanha) {
		this.idCampanha = idCampanha;
	}

	public String getContacto() {
		return this.contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public byte getEstado() {
		return this.estado;
	}

	public void setEstado(byte estado) {
		this.estado = estado;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public int getMeta() {
		return this.meta;
	}

	public void setMeta(int meta) {
		this.meta = meta;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getTelefonoContacto() {
		return this.telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public Persona getPersona1() {
		return this.persona1;
	}

	public void setPersona1(Persona persona1) {
		this.persona1 = persona1;
	}

	public Persona getPersona2() {
		return this.persona2;
	}

	public void setPersona2(Persona persona2) {
		this.persona2 = persona2;
	}

	public List<Donante> getDonantes() {
		return this.donantes;
	}

	public void setDonantes(List<Donante> donantes) {
		this.donantes = donantes;
	}

	public List<Donacion> getDonacions() {
		return this.donacions;
	}

	public void setDonacions(List<Donacion> donacions) {
		this.donacions = donacions;
	}

	public Donacion addDonacion(Donacion donacion) {
		getDonacions().add(donacion);
		donacion.setCampanha(this);

		return donacion;
	}

	public Donacion removeDonacion(Donacion donacion) {
		getDonacions().remove(donacion);
		donacion.setCampanha(null);

		return donacion;
	}

	public List<Persona> getPersonas() {
		return this.personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

}