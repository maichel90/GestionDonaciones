package co.themafia.backbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.primefaces.model.DualListModel;

import co.themafia.entities.Campanha;
import co.themafia.entities.Persona;
import co.themafia.entities.Tipopersona;

public class AgenteBean {
	
	@PersistenceContext EntityManager em;
	@Resource UserTransaction ut;
	
	private static final long serialVersionUID = 1L;
	
	private DualListModel<String> losAgentes;
	
	private List<Persona> agentes;
	
	private Integer idCamapanaSel;
	
	public Integer getIdCamapanaSel() {
		return idCamapanaSel;
	}

	public void setIdCamapanaSel(Integer idCamapanaSel) {
		this.idCamapanaSel = idCamapanaSel;
	}

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

	private List<String> agentesIni = new ArrayList<String>();
	private List<String> agentesSel = new ArrayList<String>();
	
	public void iniciarAgentes(){
		//idCamapanaSel = campana;
		//agentesSel = new ArrayList<String>();
		//agentesIni = new ArrayList<String>();
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
	
	public void asignarAgentesSeleccionados(List<String> agentes){
		Query q2 = em.createNamedQuery("Campanha.findbyId");
		q2.setParameter("id", idCamapanaSel);
		Campanha campahana = (Campanha) q2.getSingleResult();
		List<Persona> agentesObjeto = new ArrayList<Persona>();
		for (String nombre : agentes) {
			Query q = em.createNamedQuery("Persona.findbyName");
			q.setParameter("nomAgente", nombre);
			Persona agenteSeleccionado = (Persona) q.getSingleResult();
			agentesObjeto.add(agenteSeleccionado);
		}
		campahana.setIdCampanha(idCamapanaSel);
		campahana.setPersonas(agentesObjeto);
		try {
			ut.begin();
			em.merge(campahana);
			ut.commit();
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_INFO,"Sistema informa", "Se han agregado los agentes a la campaña"));
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_WARN,"Sistema informa", "No se han podido agregado los agentes a la campaña"));
			e.printStackTrace();
		}
	}

}
