package mainProject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*** DECISION ***
 * La clase Medicamento implementa Comparable para poder ordenar
 * listas dinamicas de medicamentos por nombre
 */

public class Medicamento implements Comparable<Medicamento> {

	public String nombre;
	public String empresa;
	public String principioActivo;
	public Date fechaSolicitud;
	public Date fechaAprobacion;
	public Date fechaRetirada;
	public ArrayList<String> indicaciones;
	public ArrayList<String> efectosAdversos;
	public String estadoSolicitud;
	public static final String APROBADO = "aprobado";
	public static final String RETIRADO = "retirado";
	public static final String PENDIENTE = "pendiente de aprobacion";
	
	// Constructor de medicamento con todos los atributos
	public Medicamento(String n, String e, String pa, Calendar fs, ArrayList<String> i, ArrayList<String> ea, String es) {
		nombre = n;
		empresa = e;
		principioActivo = pa;
		fechaSolicitud = fs.getTime();
		fechaAprobacion = null;
		fechaRetirada = null;
		indicaciones = i;
		efectosAdversos = ea;
		estadoSolicitud = es;
	}
	
	// Setters y Getters necesarios
	public String getNombre() {
		return nombre;
	}
	
	public String getPActivo() {
		return principioActivo;
	}
	
	public void setFechaAprobacion(Calendar fa) {
		if (fa == null) {
			fechaAprobacion = null;
		} else {
			fechaAprobacion = fa.getTime();
		}
	}
	
	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}
	
	public void setFechaRetirada(Calendar fr) {
		if (fr == null) {
			fechaRetirada = null;
		} else {
			fechaRetirada = fr.getTime();
		}
	}
	
	public Date getFechaRetirada() {
		return fechaRetirada;
	}
	
	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String es) {
		estadoSolicitud = es;
	}

	public String toString() {
		return "Medicamento: " + nombre + "\nDesarrollado por: " + empresa + "\nPrincipio activo: " + principioActivo + "\nFecha de solicitud: " + fechaSolicitud;
	}

	@Override
	public int compareTo(Medicamento m) {
		return nombre.compareTo(m.getNombre());
	}
}
