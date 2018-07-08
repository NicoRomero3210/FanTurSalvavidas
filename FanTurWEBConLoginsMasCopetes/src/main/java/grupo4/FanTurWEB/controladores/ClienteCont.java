package grupo4.FanTurWEB.controladores;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import grupo4.FanTurWEB.model.Cliente;
import grupo4.FanTurWEB.model.Contacto;
//import grupo4.FanTurWEB.model.dao.ContactoDao;
import grupo4.FanTurWEB.model.dao.interfaces.ClienteDao;

@Named("clienteBean")
@ViewScoped
public class ClienteCont implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(ClienteCont.class.getName());

	
	
	@EJB
	private ClienteDao clienteEJB;

	private Cliente cliente;
	
	private Contacto contacto;
	
	private int id_cli;
	
	
	
	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
	
	
	
	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
		
	
	public int getId_cli() {
		return id_cli;
	}
	
	public void setId_cli(int id_cli) {
		this.id_cli = id_cli;
	}
	
	
	
	@PostConstruct
	public void onInit() {
		contacto = new Contacto();
		cliente = new Cliente();
	}
	
	
	
	public void registrar() {
		try {
			
			logger.info("Se va a crear un cliente: " + cliente);
			
			logger.info("Se va a setear el siguiente contacto al cliente: " + contacto);
			
			logger.info("Se va a setear el siguiente contacto al cliente: " + contacto);

			
			this.cliente.setContacto(this.contacto);
			
			logger.info("Se seteó el siguiente contacto al cliente: " + cliente.getContacto());
			
			//AGREGADO
			this.cliente.setRol("USER");
			
			clienteEJB.create(cliente);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Exitoso!"));
			
		}catch(Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Registro Fallido"));
		}
	}
	
	
	public void eliminar() {
		try {
			cliente = clienteEJB.findById(id_cli);
			clienteEJB.delete(cliente);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Borrado Exitoso"));
		}catch(Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Borrado Fallido"));
		}
	}

}
