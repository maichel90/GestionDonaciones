package co.themafia.backbean;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import co.themafia.entities.Donacion;
import co.themafia.entities.Donante;
import co.themafia.generaarchivo.bean.GenerarArchivoBean;

public class CertificadoBean implements Serializable{
	
	
	@PersistenceContext EntityManager em;
	@Resource UserTransaction ut;
	
	private String correo;
	private Integer cedula;
	private Boolean error;
	public Integer getCedula() {
		return cedula;
	}

	public void setCedula(Integer cedula) {
		this.cedula = cedula;
	}
	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public void GenerarCertificadoPDF() {
		error = Boolean.FALSE;
		Query q = em.createNamedQuery("Donante.findByCedula");
		q.setParameter("identificacion", cedula);
		Donante d = (Donante) q.getSingleResult();
		List<Donacion> donaciones = em.createQuery("Select d from Donacion d where d.donante = :donante").setParameter("donante",d).getResultList();
		if(donaciones.isEmpty()){
			error = Boolean.TRUE;
			System.out.println("error reporte");
			return;
		}
		d.setDonacions( donaciones);
		GenerarArchivoBean g = new GenerarArchivoBean();
		try {
			
			g.generarArchivo(d);
			g.enviarCorreo(correo);
		} catch (IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
