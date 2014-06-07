package co.themafia.rest;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import co.themafia.entities.Campanha;
import co.themafia.entities.Donacion;
import co.themafia.entities.Donante;

public class RestCliente {
	
	@PersistenceContext EntityManager em;
	@Resource UserTransaction ut;

	public void ConsumirServicioCCA() {		
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = fact.newDocumentBuilder();
			Document doc = db.parse("http://localhost/CCA/CALL/result/xml");
			doc.getDocumentElement().normalize();
			
			NodeList nodos = doc.getElementsByTagName("item");
			for(int a=0;a<nodos.getLength();a++){
				Node n = nodos.item(a);
				if(n.getNodeType() == Node.ELEMENT_NODE){
					Element e = (Element) n;
					
					Donacion d = new Donacion();
					d.setCampanha(ConsultarCampana(e.getElementsByTagName("id_campania").item(0).getTextContent()));
					d.setDonante(ConsultarDonante(e.getElementsByTagName("num_documento").item(0).getTextContent()));
					d.setFormaPago(e.getElementsByTagName("tipo_donacion").item(0).getTextContent());
					d.setFechaDonacion(convertirFecha(e.getElementsByTagName("fecha_contacto").item(0).getTextContent()));
					d.setNumeroTarjeta(Integer.parseInt(e.getElementsByTagName("numeroTarjeta").item(0).getTextContent()));
					d.setFranquiciaTarjeta(e.getElementsByTagName("franquicia").item(0).getTextContent());
					d.setCodSecTarjeta(new Random().nextInt(899)+100);
					String valor =e.getElementsByTagName("valor_donacion").item(0).getTextContent();
					if(valor.length()==0){
						d.setValor(0);
					}else {
						d.setValor(Integer.parseInt(valor));
					}
					ut.begin();
					em.persist(d);
					ut.commit();					
				}
			}
		} catch (SAXException |IOException |ParserConfigurationException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		}finally{
			FacesContext.getCurrentInstance().addMessage("Notificacion", new FacesMessage(FacesMessage.SEVERITY_INFO,"Sistema informa", "Se realizo el carge de donates con exito"));
		}
	}
	
	public void GenerarCobroTarjetas() {
		Query q = em.createNamedQuery("Donacion.findByFormaPago");
		q.setParameter("forma", "1");
		List<Donacion> donaciones = q.getResultList();
		
		for(Donacion d: donaciones){
			try {
				DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = fact.newDocumentBuilder();
				Document doc = db.parse("http://localhost/CCA/CALL/creditcard/approve/"+d.getFranquiciaTarjeta()+"/"+d.getNumeroTarjeta()+"/xml");
				doc.getDocumentElement().normalize();
				d.setNumeroAprobacion(Integer.parseInt(doc.getElementsByTagName("numeroAprobacion").item(0).getTextContent()));
				d.setNumeroRecibo(Integer.parseInt(doc.getElementsByTagName("numeroRecibo").item(0).getTextContent()));
				d.setEstado((byte) 1);	
				ut.begin();
				em.merge(d);
				ut.commit();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public Donante ConsultarDonante(String num_documento) {
		Query q = em.createNamedQuery("Donante.findByCedula");
		q.setParameter("identificacion", Integer.parseInt(num_documento));
		return (Donante) q.getSingleResult();
	}
	
	public Campanha ConsultarCampana(String idcampana) {
		Campanha c = em.find(Campanha.class, Integer.parseInt(idcampana));
		return c;
	}
	
	private Date convertirFecha(String fecha) {
		String[] datos = fecha.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(datos[0]), Integer.parseInt(datos[1])-1, Integer.parseInt(datos[2]));
		return cal.getTime();
	}
}
