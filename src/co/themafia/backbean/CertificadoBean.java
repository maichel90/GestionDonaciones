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
	
	private Integer cedula;
	@PersistenceContext EntityManager em;
	@Resource UserTransaction ut;
	
	public Integer getCedula() {
		return cedula;
	}

	public void setCedula(Integer cedula) {
		this.cedula = cedula;
	}
	
	public void GenerarCertificadoPDF() {
		Query q = em.createNamedQuery("Donante.findByCedula");
		q.setParameter("identificacion", cedula);
		Donante d = (Donante) q.getSingleResult();
		d.setDonacions( em.createQuery("Select d from Donacion d where d.donante = :donante").setParameter("donante",d).getResultList());
		GenerarArchivoBean g = new GenerarArchivoBean();
		try {
			g.generarArchivo(d);
			g.enviarCorreo(d.getNombre());
		} catch (IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
