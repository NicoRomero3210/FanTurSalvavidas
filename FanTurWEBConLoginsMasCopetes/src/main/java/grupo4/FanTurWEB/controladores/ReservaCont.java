package grupo4.FanTurWEB.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import grupo4.FanTurWEB.model.Cliente;
import grupo4.FanTurWEB.model.Evento;
import grupo4.FanTurWEB.model.Paquete;
import grupo4.FanTurWEB.model.Reserva;
import grupo4.FanTurWEB.model.dao.interfaces.ClienteDao;
import grupo4.FanTurWEB.model.dao.interfaces.PaqueteDao;
import grupo4.FanTurWEB.model.dao.interfaces.ReservaDao;

@Named("reservaBean")
@ViewScoped
public class ReservaCont implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(ReservaCont.class.getName());
	
	@EJB
	private ClienteDao clienteEJB;
	
	private Cliente cliente;
	
	@EJB
	private ReservaDao reservaEJB;
	
	@EJB
	private PaqueteDao paqueteEJB;
	
	private List<Paquete> paquetes;
	
	private Reserva reserva;
	
	
	
	public Reserva getReserva() {
		return reserva;
	}
	
	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	
	
	
	public List<Paquete> getPaquetes() {
		return paquetes;
	}
	
	public void setPaquetes(List<Paquete> paquetes) {
		this.paquetes = paquetes;
	}
	
	
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}



	@PostConstruct
	public void init() {
		String nombre = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
		cliente = clienteEJB.findByUser(nombre);
		paquetes = this.mostrarPaquetesDisponibles();
		reserva = new Reserva();
		
	}
	
	
	
	
	public List<Paquete> mostrarPaquetesDisponibles() {
		
		List<Paquete> paquetesDisponibles = new ArrayList<Paquete> (paqueteEJB.findAll());
		
	    for (ListIterator<Paquete> iterator = paquetesDisponibles.listIterator(); iterator
	            .hasNext();) {
	        Paquete paquete = iterator.next();
	        
	        if (paquete.getCantidad() == 0) {
	        	iterator.remove();
	        }
	    }
	    return paquetesDisponibles;
		
	}
	
	
	public String reservar(int paqId) {
		Paquete paq = paqueteEJB.findById(paqId);
		reserva.setPaquete(paq);
		reserva.setCliente(cliente);
		reservaEJB.create(reserva);
		cliente.getReservas().add(reserva);
		clienteEJB.update(cliente.getId(), cliente);
		logger.info("el cliente " + cliente.getNombre() + "tiene las siguientes reservas: " + cliente.getReservas());
		
		
		paq.setCantidad(paq.getCantidad() - 1);
		paqueteEJB.update(paq.getId(), paq);
		
		
		return "reservarPaquetes.xhtml?faces-redirect=true";
		
	}
	
	
}
