package grupo4.FanTurWEB.ctrl;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;

import grupo4.FanTurWEB.model.Paquete;

@Named
@ViewScoped
public class PaqueteCtrl extends Ctrl<Paquete> implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@PostConstruct
	private void init() {
		modelObj =  new Paquete();
		client = ClientBuilder.newClient();
		webTarget = client.target("http://localhost:8080/FanTurWEB/rest/paquetes");
		afterCreate = "pagina adonde ir despues de crear";
		afterUpdate = "pagina adonde ir despues de actualizar";
		afterDelete = "pagina adonde ir despues de borrar";
	}
	
	
	
	
}
