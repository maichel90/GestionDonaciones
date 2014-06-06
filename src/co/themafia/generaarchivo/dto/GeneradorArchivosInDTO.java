package co.themafia.generaarchivo.dto;

import java.util.List;

public class GeneradorArchivosInDTO {
	
	private String nombreDonante;
	
	private String numeroIdentificacion;
	
	private String tipoDocumento;
	
	private String correo;
	
	private List<GeneradorArchivosDetalleInDTO> datosDonacion;

	public String getNombreDonante() {
		return nombreDonante;
	}

	public void setNombreDonante(String nombreDonante) {
		this.nombreDonante = nombreDonante;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public List<GeneradorArchivosDetalleInDTO> getDatosDonacion() {
		return datosDonacion;
	}

	public void setDatosDonacion(List<GeneradorArchivosDetalleInDTO> datosDonacion) {
		this.datosDonacion = datosDonacion;
	}
}
