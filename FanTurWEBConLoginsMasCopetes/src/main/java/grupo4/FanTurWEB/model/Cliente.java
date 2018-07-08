package grupo4.FanTurWEB.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Past;

@Entity
public class Cliente extends User {
	
	//@Past
	@Temporal(TemporalType.DATE)
	private Date nacimiento;

	@OneToMany(mappedBy="cliente", fetch=FetchType.EAGER)
	private Set<Reserva> reservas;
	
	@Transient
	private Reserva reserva;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "idContacto")
	private Contacto contacto;
	
	public Cliente() {
		super();
	}
	
	public Cliente(String nombre, String apellido, String user,String password, Date nacimiento, Contacto contacto, String rol) {
		super(nombre, apellido, user, password, rol);
		this.nacimiento = nacimiento;
		this.contacto = contacto;
	}

	public Date getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	public Set<Reserva> getReservas() {
		return reservas;
	}
	
	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public void reservar(Paquete paquete) {
		reserva = Reserva.createReserva(paquete, this);
		if (reserva != null) {
			if (this.reservas == null) {
				this.reservas = new HashSet<Reserva>();
			}
			this.reservas.add(reserva);
		}
	}

	@Override
	public String toString() {
		return "Cliente [getNacimiento()=" + getNacimiento() + ", getContacto()=" + getContacto() + ", toString()="
				+ super.toString() + "]";
	}
	
}
