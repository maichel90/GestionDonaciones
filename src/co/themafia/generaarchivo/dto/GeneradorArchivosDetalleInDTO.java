package co.themafia.generaarchivo.dto;

public class GeneradorArchivosDetalleInDTO {

	private String fechaDonacion;
	
	private String nombreCampana;
	
	private String valorCampana;
	
	private String formaPago;

	public String getFechaDonacion() {
		return fechaDonacion;
	}

	public void setFechaDonacion(String fechaDonacion) {
		this.fechaDonacion = fechaDonacion;
	}

	public String getNombreCampana() {
		return nombreCampana;
	}

	public void setNombreCampana(String nombreCampana) {
		this.nombreCampana = nombreCampana;
	}

	public String getValorCampana() {
		return valorCampana;
	}

	public void setValorCampana(String valorCampana) {
		this.valorCampana = valorCampana;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
}
