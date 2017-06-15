package mainProject;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import edalib.list.doublelink.DList;
import edalib.list.doublelink.DSortedList;
import edalib.list.singlelink.SQueue;

public class AgenciaF1 implements IAgencia {
	
	protected Scanner teclado = new Scanner(System.in);
	
	/*** DECISION ***
	 * Hemos decidido usar la estructura de cola de la libreria edalib para los medicamentos recibidos
	 * De esta forma son revisados en estricto orden de llegada tal y como se pide
	 */
	protected SQueue<Medicamento> medicamentosRecibidos = new SQueue<Medicamento>();
	
	/*** DECISION ***
	 * Para los medicamentos comercializados hemos decidido utilizar una lista doblemente enlazada ordenada
	 * Esta lista es parte de la libreria edalib
	 */
	protected DSortedList<Medicamento> medicamentosComercializados = new DSortedList<Medicamento>();
	
	@Override
	public boolean recibir(Medicamento medicamento) {
		if (medicamentosRecibidos != null) {
			medicamentosRecibidos.enqueue(medicamento); // Añadimos el medicamento a la cola
			return true;
		}
		
		return false;
	}

	@Override
	public boolean revisar() {
		if (medicamentosRecibidos != null) {
			// Revisamos los medicamentos que están pendientes de revisión
			while (!medicamentosRecibidos.isEmpty()) {
				// Sacamos el primer medicamento de la cola
				Medicamento medicamentoActual = medicamentosRecibidos.dequeue();
				System.out.println();
				System.out.println(medicamentoActual.toString() + "\n\n¿Debería ser aprobado? (s/n) ");
				String respuesta = teclado.nextLine();
				
				/*** DECISION ***
				  La complejidad de aprobar un medicamento es T(n) = c(3n+7)
				 */
				if (respuesta.equalsIgnoreCase("s")) { // TODO: Se deberian añadir mas casos validos
					/*** DECISION ***
					 * No tiene sentido llamar al metodo aprobar proporcionado por la interfaz ya
					 * que el medicamento ha salido de la cola
					 */
					
					/*** DECISION ***
					 * Suponemos que la clave unica identificativa de un medicamento es su nombre
					 * No podremos tener dos medicamentos con el mismo nombre
					 */
					
					// Comprobamos que no este ya comercializado
					if (buscaMedicamento(medicamentoActual.getNombre()) != null) {												
						System.out.println("El medicamento " + medicamentoActual.getNombre() + " ya está comercializado.");		
					} else {																								
						// Aprobamos el medicamento																			
						medicamentoActual.setEstadoSolicitud(Medicamento.APROBADO);											
						medicamentoActual.setFechaAprobacion(Calendar.getInstance());										
						medicamentosComercializados.add(medicamentoActual);													
					}
				}
			}
			System.out.println();
			System.out.println("Ya no quedan medicamentos por revisar");
			return true;
		}
		
		return false;
	}

	@Override
	public boolean consultarMedicamento(String nombreMedicamento) {
		Medicamento medicamentoActual = buscaMedicamento(nombreMedicamento);
		
		System.out.println();
		System.out.println("Consulta del medicamento: " + nombreMedicamento);
		System.out.println();
		if (medicamentoActual != null) {
			if (medicamentoActual.getEstadoSolicitud().equalsIgnoreCase(Medicamento.APROBADO)) {
				System.out.println(medicamentoActual.toString() + "\n\n¿Debería ser retirado? (s/n) ");
				
				String respuesta = teclado.nextLine();
				
				if (respuesta.equalsIgnoreCase("s")) { // TODO: Se deberian añadir mas casos validos
					retirar(nombreMedicamento);
				}
			} else if (medicamentoActual.getEstadoSolicitud().equalsIgnoreCase(Medicamento.RETIRADO)) {
				System.out.println(medicamentoActual.toString() + "\n\n¿Debería ser aprobado otra vez? (s/n) ");
				
				String respuesta = teclado.nextLine();
				
				if (respuesta.equalsIgnoreCase("s")) { // TODO: Se deberian añadir mas casos validos
					aprobar(nombreMedicamento);
				}
			}
			return true;
		} else {
			System.out.println("El medicamento " + nombreMedicamento + " no ha sido encontrado.");
			return false;
		}
	}

	/*** DECISION ***
	 * 1.5 - 
	 */
	@Override
	public boolean aprobar(String nombreMedicamento) {
		Medicamento medicamentoActual = buscaMedicamento(nombreMedicamento);
		if (medicamentoActual != null) {
			medicamentoActual.setEstadoSolicitud(Medicamento.APROBADO);
			medicamentoActual.setFechaAprobacion(Calendar.getInstance());
			medicamentoActual.setFechaRetirada(null);
			System.out.println();
			System.out.println("Medicamento " + nombreMedicamento + " aprobado" );
			return true;
		}
		
		return false;
	}

	@Override
	public boolean retirar(String nombreMedicamento) {
		Medicamento medicamentoActual = buscaMedicamento(nombreMedicamento);
		if (medicamentoActual != null) {
			medicamentoActual.setEstadoSolicitud(Medicamento.RETIRADO);
			medicamentoActual.setFechaAprobacion(null);
			medicamentoActual.setFechaRetirada(Calendar.getInstance());
			System.out.println();
			System.out.println("Medicamento " + nombreMedicamento + " retirado" );
			return true;
		}
		
		return false;
	}

	@Override
	public DList<Medicamento> consultarAprobados(Calendar fecha) {
		Date fechaConsulta = fecha.getTime();
		return consultaGenerica(fechaConsulta, Medicamento.APROBADO);
	}

	@Override
	public DList<Medicamento> consultarAprobados() {
		return consultaGenerica(null, Medicamento.APROBADO);
	}

	@Override
	public DList<Medicamento> consultarRetirados(Calendar fecha) {
		Date fechaConsulta = fecha.getTime();
		return consultaGenerica(fechaConsulta, Medicamento.RETIRADO);
	}

	@Override
	public DList<Medicamento> consultarRetirados() {
		return consultaGenerica(null, Medicamento.RETIRADO);
	}

	/*** DECISION ***
	  La complejidad de buscar un medicamento es T(n) = c(6n)
	 */
	@Override
	public DList<Medicamento> consultaMedicamentos(String principioActivo) {
		DList<Medicamento> listaM = new DList<Medicamento>();
		Medicamento medicamentoActual;
		
		System.out.println();
		System.out.println("Consulta de todos los medicamentos con el principio activo: " + principioActivo);
		
		if (ordenAsc()) {
			// Ascendente
			for (int i = 0; i < medicamentosComercializados.getSize(); i++) {   
				medicamentoActual = medicamentosComercializados.getAt(i);		
				
				if (medicamentoActual.getPActivo().equalsIgnoreCase(principioActivo)) { 
					System.out.println();												
					System.out.println(medicamentoActual.toString());
					listaM.addLast(medicamentoActual);
				}
			}
		} else {
			// Descendente
			for (int i = (medicamentosComercializados.getSize() - 1); i >= 0; i--) {
				medicamentoActual = medicamentosComercializados.getAt(i);
				
				if (medicamentoActual.getPActivo().equalsIgnoreCase(principioActivo)) {
					System.out.println();
					System.out.println(medicamentoActual.toString());
					listaM.addLast(medicamentoActual);
				}
			}
		}
		
		return listaM;
	}
	
	// Metodos utiles
	
	/*** DECISION ***
	  La complejidad de buscar un medicamento es T(n) = c(3n+3)
	 */
	protected Medicamento buscaMedicamento(String nombreMedicamento) {
		if (medicamentosComercializados != null && !medicamentosComercializados.isEmpty()) {	 
			Medicamento medicamentoActual; 														
			for (int i = 0; i < medicamentosComercializados.getSize(); i++) {					
				medicamentoActual = medicamentosComercializados.getAt(i);						
				if (medicamentoActual.getNombre().equalsIgnoreCase(nombreMedicamento)) {		
					return medicamentoActual;													
				}
			}
		}
		
		return null;
	}
	
	protected DList<Medicamento> consultaGenerica(Date fechaConsulta, String estado) {
		DList<Medicamento> listaM = new DList<Medicamento>();
		Medicamento medicamentoActual;
		
		System.out.println();
		if (fechaConsulta == null) {
			System.out.println("Consulta de todos los medicamentos " + estado + "s");
		} else {
			System.out.println("Consulta de medicamentos " + estado + "s a partir de: " + fechaConsulta);
		}
		
		if (ordenAsc()) {
			// Ascendente
			for (int i = 0; i < medicamentosComercializados.getSize(); i++) {
				medicamentoActual = medicamentosComercializados.getAt(i);
				
				if (medicamentoActual.getEstadoSolicitud().equalsIgnoreCase(estado) 
						&& (
							fechaConsulta == null 
							|| (medicamentoActual.getFechaAprobacion() != null && medicamentoActual.getFechaAprobacion().after(fechaConsulta))
							|| (medicamentoActual.getFechaRetirada() != null && medicamentoActual.getFechaRetirada().after(fechaConsulta))
						)
					) {
					System.out.println();
					System.out.println(medicamentoActual.toString());
					listaM.addLast(medicamentoActual);
				}
			}
		} else {
			// Descendente
			for (int i = (medicamentosComercializados.getSize() - 1); i >= 0; i--) {
				medicamentoActual = medicamentosComercializados.getAt(i);
				
				if (medicamentoActual.getEstadoSolicitud().equalsIgnoreCase(estado) 
						&& (
							fechaConsulta == null 
							|| (medicamentoActual.getFechaAprobacion() != null && medicamentoActual.getFechaAprobacion().after(fechaConsulta))
							|| (medicamentoActual.getFechaRetirada() != null && medicamentoActual.getFechaRetirada().after(fechaConsulta))
						)
					) {
					System.out.println();
					System.out.println(medicamentoActual.toString());
					listaM.addLast(medicamentoActual);
				}
			}
		}
		
		return listaM;
	}
	
	protected boolean ordenAsc() {
		String respuesta;
		
		while (true) {
			System.out.println();
			System.out.println("¿Ordenar de forma alfabetica ascendente o descendente? (asc/des) ");
			respuesta = teclado.nextLine();
			if (respuesta.equalsIgnoreCase("asc")) {
				return true;
			} else if (respuesta.equalsIgnoreCase("des")) {
				return false;
			} else {
				System.out.println();
				System.out.println("Respuesta no válida, vuelva a seleccionar 'asc' o 'des'.");
			}
		}
	}
	
	// Conversion a AgenciaF2
	public AgenciaF2 convert() {
		return (new AgenciaF2(this));
	}
}
