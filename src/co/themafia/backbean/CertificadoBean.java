package co.themafia.backbean;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import co.themafia.entities.Donante;

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
		Query q = em.createNativeQuery("Donante.findByCedula");
		q.setParameter("cedula", cedula);
		Donante d = (Donante) q.getSingleResult();
		System.out.println(d.getNombre());
	}
}
