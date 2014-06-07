package co.themafia.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the donacion database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Donacion.findAll", query="SELECT d FROM Donacion d"),
	@NamedQuery(name="Donacion.findByFormaPago", query="SELECT d FROM Donacion d where d.formaPago = :forma")
})
public class Donacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idDonacion;

	private int codSecTarjeta;

	private byte estado;

	@Temporal(TemporalType.DATE)
	private Date fechaDonacion;

	private String formaPago;

	private String franquiciaTarjeta;

	private int numeroAprobacion;

	private int numeroRecibo;

	private int numeroTarjeta;

	private int valor;

	//bi-directional many-to-one association to Campanha
	@ManyToOne
	private Campanha campanha;

	//bi-directional many-to-one association to Donante
	@ManyToOne
	private Donante donante;

	public Donacion() {
	}

	public int getIdDonacion() {
		return this.idDonacion;
	}

	public void setIdDonacion(int idDonacion) {
		this.idDonacion = idDonacion;
	}

	public int getCodSecTarjeta() {
		return this.codSecTarjeta;
	}

	public void setCodSecTarjeta(int codSecTarjeta) {
		this.codSecTarjeta = codSecTarjeta;
	}

	public byte getEstado() {
		return this.estado;
	}

	public void setEstado(byte estado) {
		this.estado = estado;
	}

	public Date getFechaDonacion() {
		return this.fechaDonacion;
	}

	public void setFechaDonacion(Date fechaDonacion) {
		this.fechaDonacion = fechaDonacion;
	}

	public String getFormaPago() {
		return this.formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getFranquiciaTarjeta() {
		return this.franquiciaTarjeta;
	}

	public void setFranquiciaTarjeta(String franquiciaTarjeta) {
		this.franquiciaTarjeta = franquiciaTarjeta;
	}

	public int getNumeroAprobacion() {
		return this.numeroAprobacion;
	}

	public void setNumeroAprobacion(int numeroAprobacion) {
		this.numeroAprobacion = numeroAprobacion;
	}

	public int getNumeroRecibo() {
		return this.numeroRecibo;
	}

	public void setNumeroRecibo(int numeroRecibo) {
		this.numeroRecibo = numeroRecibo;
	}

	public int getNumeroTarjeta() {
		return this.numeroTarjeta;
	}

	public void setNumeroTarjeta(int numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public int getValor() {
		return this.valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Campanha getCampanha() {
		return this.campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public Donante getDonante() {
		return this.donante;
	}

	public void setDonante(Donante donante) {
		this.donante = donante;
	}

}