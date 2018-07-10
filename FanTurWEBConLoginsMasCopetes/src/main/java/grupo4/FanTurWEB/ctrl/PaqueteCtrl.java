package grupo4.FanTurWEB.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;

import grupo4.FanTurWEB.controladores.ClienteCont;
import grupo4.FanTurWEB.model.Alojamiento;
import grupo4.FanTurWEB.model.Evento;
import grupo4.FanTurWEB.model.Hotel;
import grupo4.FanTurWEB.model.Paquete;
import grupo4.FanTurWEB.model.dao.interfaces.EventoDao;

@Named
@ViewScoped
public class PaqueteCtrl extends Ctrl<Paquete> implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(PaqueteCtrl.class.getName());
	
	
	private Alojamiento alojamiento;
	
	private String nombreHotel;
	
	private int evento_id;
	
	private List<Evento> eventos;
	
	private List<Evento> eventosAsignados = new ArrayList<Evento>();
	
	private EventoCtrl eventoCtrl;
	
	@EJB
	private EventoDao eventoEJB;
	
	
	public List<Evento> getEventosAsignados() {
		return eventosAsignados;
	}

	public void setEventosAsignados(List<Evento> eventosAsignados) {
		this.eventosAsignados = eventosAsignados;
	}

	public EventoCtrl getEventoCtrl() {
		return eventoCtrl;
	}

	public void setEventoCtrl(EventoCtrl eventoCtrl) {
		this.eventoCtrl = eventoCtrl;
	}

	public List<Evento> getEventos() {
		return eventos;
	}
	
	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
	
	
	
	public int getEvento_id() {
		return evento_id;
	}
	
	public void setEvento_id(int evento_id) {
		this.evento_id = evento_id;
	}
	
	
	
	public String getNombreHotel() {
		return nombreHotel;
	}
	
	public void setNombreHotel(String nombreHotel) {
		this.nombreHotel = nombreHotel;
	}
	
	
	
	public Alojamiento getAlojamiento() {
		return alojamiento;
	}
	
	public void setAlojamiento(Alojamiento alojamiento) {
		this.alojamiento = alojamiento;
	}
	
	
	
	
	@PostConstruct
	private void init() {
		//client = ClientBuilder.newClient();
		//webTarget = client.target("http://localhost:8080/FanTurWEB/rest/paquetes");
		webTarget = webTarget.path("paquetes");
		modelObj =  new Paquete();
		alojamiento = new Alojamiento();
		modelObj.setAlojamiento(alojamiento);
		modelList = new ArrayList<Paquete> (this.getAll());
		eventos = new ArrayList<Evento> (eventoEJB.findAll());
	}

	@Override
	public String getId(Paquete paquete) {
		return String.valueOf(paquete.getId());
	}

	@Override
	protected Class<Paquete> getClase() {
		return Paquete.class;
	}
	
	
	
	public String crear() {
		this.create();
		return "gestionarPaquetes.xhtml?faces-redirect=true";
	}
	
	
	
	
	public Hotel getHotelByNombre(String nombre) {
		logger.info("Entró al método getHotelByNombre");
		client = ClientBuilder.newClient();
		webTarget = client.target("http://localhost:8080/FanTurWEB/rest/hoteles");
		invocation = webTarget.path(nombre).request().buildGet();
		logger.info("Seteó el invocation");
		response = invocation.invoke();
		logger.info("Seteó response");
		Hotel telo = response.readEntity(Hotel.class);
		logger.info("Seteó telo");
		webTarget = client.target("http://localhost:8080/FanTurWEB/rest/paquetes");
		return telo;
	}
	
	
	
	public Evento getEventoById(int id) {
		client = ClientBuilder.newClient();
		webTarget = client.target("http://localhost:8080/FanTurWEB/rest/eventos");
		invocation = webTarget.path(String.valueOf(id)).request().buildGet();
		response = invocation.invoke();
		Evento event = response.readEntity(Evento.class);
		webTarget = client.target("http://localhost:8080/FanTurWEB/rest/paquetes");
		return event;
			
	}
	
	

	
	
	public void agregarHotel() {
		//logger.info("lo que tiene nombreHotel:" + nombreHotel);
		//Hotel telo = hotelCtrl.getByNombre(nombreHotel);
		logger.info("El nombre del telo seleccionado: " + nombreHotel );
		
		Hotel hotel = this.getHotelByNombre(nombreHotel);
		
		logger.info("El hotel que trajo el metodo getHotelByNombre tiene el nombre: " + hotel.getNombre());
		
		modelObj.getAlojamiento().addHotel(hotel);
		
		logger.info("Se ha añadido el hotel");
	}
	
	
	public void agregarEvento() {
		logger.info("El id del evento seleccionado: " + String.valueOf(evento_id) );
		Evento evento = this.getEventoById(evento_id);
		logger.info("El evento que trajo el metodo getEventoById tiene la descripcion: " + evento.getDescripcion());
		modelObj.addEvento(evento);
		eventosAsignados.add(evento);
		logger.info("Se ha añadido el evento");
		
		int i = 0;
		for (Evento e : eventos) {
			logger.info("Entra en el for");
			
			if(e.getId() == evento_id) {
				
				logger.info("Entra en el if()");
				
				eventos.remove(i);
				
				logger.info("Removió el evento");
			}
			
			i++;
			logger.info("Incrementa el índice");
		}
	}
	
	
	public String borrar(int id) {
		this.delete(id);
		return "gestionarPaquetes.xhtml?faces-redirect=true";
	}
	
	
	
}
