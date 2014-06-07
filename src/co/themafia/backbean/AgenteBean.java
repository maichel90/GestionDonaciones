package co.themafia.backbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import org.primefaces.model.DualListModel;

import co.themafia.entities.Persona;
import co.themafia.entities.Tipopersona;

public class AgenteBean {
	
	@PersistenceContext EntityManager em;
	@Resource UserTransaction ut;
	
	private static final long serialVersionUID = 1L;
	
	private DualListModel<String> losAgentes;
	
	private List<Persona> agentes;
	
	public List<String> getAgentesIni() {
		return agentesIni;
	}

	public DualListModel<String> getLosAgentes() {
		return losAgentes;
	}

	public void setLosAgentes(DualListModel<String> losAgentes) {
		this.losAgentes = losAgentes;
	}

	public List<Persona> getAgentes() {
		return agentes;
	}

	public void setAgentes(List<Persona> agentes) {
		this.agentes = agentes;
	}

	public List<String> getAgentesSel() {
		return agentesSel;
	}

	public void setAgentesSel(List<String> agentesSel) {
		this.agentesSel = agentesSel;
	}

	public void setAgentesIni(List<String> agentesIni) {
		this.agentesIni = agentesIni;
	}

	private List<String> agentesIni;
	private List<String> agentesSel;
	
	public void iniciarEleccionCampana(){
		//idCamapanaSel = campana;
		agentesSel = new ArrayList<String>();
		agentesIni = new ArrayList<String>();
		Query q = em.createNamedQuery("Tipopersona.findTipoAgent");
		Tipopersona tip = (Tipopersona) q.getSingleResult();
		Query q2 = em.createNamedQuery("Persona.findAgents");
		q2.setParameter("agente", tip);
		agentes = q2.getResultList();
		for (Persona agent : agentes) {
			agentesIni.add(agent.getNombre());
			System.out.println(agent.getNombre()+"   asdasdsadasdsadas");
		}
		losAgentes = new DualListModel<>(agentesIni, agentesSel);
	}

}
