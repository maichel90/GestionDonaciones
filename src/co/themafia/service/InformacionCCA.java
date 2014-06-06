package co.themafia.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.xml.bind.annotation.XmlSeeAlso;

import co.themafia.entities.Campanha;
import co.themafia.entities.Donante;

@WebService(serviceName="InformacionCCA")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso(HashMap.class)

public class InformacionCCA implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext EntityManager em;
	@Resource UserTransaction ut;
	
	@WebMethod
	public List<CampanaWS> ConsultarCampanasActivas() {
		List<CampanaWS> resp = new ArrayList<>();
		List<Campanha> campanas = em.createNamedQuery("Campanha.findAll").getResultList();
		for(Campanha c: campanas){
			CampanaWS r = new CampanaWS(c.getIdCampanha(), c.getNombre());
			resp.add(r);
		}
		return resp;
	}
	
	@WebMethod
	public List<DonanteWS> ConsultarDonantesCampana(Integer idCampana) {
		List<DonanteWS> resp = new ArrayList<>();
		try {
			ut.begin();
			Campanha c = em.find(Campanha.class, idCampana);
			System.out.println("=========================================="+c.getIdCampanha());
			List<Donante> donantes = c.getDonantes();
			System.out.println("=========================================="+donantes.size());
			for(Donante d: donantes){
				DonanteWS don = new DonanteWS();
				don.setIdDonante(d.getIdDonante());
				don.setCedula(d.getCedula());
				don.setNombre(d.getNombre());
				don.setCelular(d.getCelular());
				don.setCelularOficina(d.getCelularOficina());
				don.setTelefono(d.getTelefono());
				don.setTelefonoOficina(d.getTelefonoOficina());
				don.setDireccion(d.getDireccion());
				resp.add(don);
			}
		} catch (NotSupportedException |SystemException e) {
			e.printStackTrace();
		}
		return resp;
	}
}