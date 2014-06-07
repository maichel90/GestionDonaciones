package co.themafia.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the donante database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Donante.findAll", query="SELECT d FROM Donante d"),
	@NamedQuery(name="Donante.findByCedula", query="SELECT d FROM Donante d where d.cedula = :identificacion"),
	@NamedQuery(name="Donante.findByDonadores", query="SELECT distinct(d) FROM Donante d where d.idDonante not in (select don.donante.idDonante FROM Donacion don where don.donante.idDonante=d.idDonante)"),
	@NamedQuery(name="Donante.findByNoDonadores", query="SELECT distinct(d) FROM Donante d where d.idDonante not in (select don.donante.idDonante FROM Donacion don where don.donante.idDonante=d.idDonante)"),
	@NamedQuery(name="Donante.findByNombre", query="SELECT d FROM Donante d where d.nombre = :nombreDon")
})
public class Donante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idDonante;

	private int cedula;

	private String celular;

	private String celularOficina;

	private String direccion;

	private String nombre;

	private String telefono;

	private String telefonoOficina;

	//bi-directional many-to-many association to Campanha
	@ManyToMany
	@JoinTable(
		name="campanhadonante"
		, joinColumns={
			@JoinColumn(name="Donante_idDonante")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Campanha_idCampanha")
			}
		)
	private List<Campanha> campanhas;

	//bi-directional many-to-one association to Donacion
	@OneToMany(mappedBy="donante")
	private List<Donacion> donacions;

	public Donante() {
	}

	public int getIdDonante() {
		return this.idDonante;
	}

	public void setIdDonante(int idDonante) {
		this.idDonante = idDonante;
	}

	public int getCedula() {
		return this.cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCelularOficina() {
		return this.celularOficina;
	}

	public void setCelularOficina(String celularOficina) {
		this.celularOficina = celularOficina;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTelefonoOficina() {
		return this.telefonoOficina;
	}

	public void setTelefonoOficina(String telefonoOficina) {
		this.telefonoOficina = telefonoOficina;
	}

	public List<Campanha> getCampanhas() {
		return this.campanhas;
	}

	public void setCampanhas(List<Campanha> campanhas) {
		this.campanhas = campanhas;
	}

	public List<Donacion> getDonacions() {
		return this.donacions;
	}

	public void setDonacions(List<Donacion> donacions) {
		this.donacions = donacions;
	}

	public Donacion addDonacion(Donacion donacion) {
		getDonacions().add(donacion);
		donacion.setDonante(this);

		return donacion;
	}

	public Donacion removeDonacion(Donacion donacion) {
		getDonacions().remove(donacion);
		donacion.setDonante(null);

		return donacion;
	}

}