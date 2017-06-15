package mainProject;

import java.util.Calendar;
import java.util.Date;

import edalib.list.doublelink.DList;
import edalib.list.singlelink.SList;
import edalib.tree.bstree.BSTree;

public class AgenciaF2 extends AgenciaF1 implements IAgencia {

	private BSTree<String, Medicamento> medicamentosComercializados = new BSTree<String, Medicamento>();
	
	
	public AgenciaF2(AgenciaF1 agenciaF1) {
		medicamentosRecibidos = agenciaF1.medicamentosRecibidos;
		
		Medicamento medicamentoActual;
		for (int i = 0; i < agenciaF1.medicamentosComercializados.getSize(); i++) {
			medicamentoActual = agenciaF1.medicamentosComercializados.getAt(i);
			medicamentosComercializados.insert(medicamentoActual.getNombre(), medicamentoActual);
	
		}
		
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
						medicamentosComercializados.insert(medicamentoActual.getNombre(), medicamentoActual);
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
	public DList<Medicamento> consultaMedicamentos(String principioActivo) {
		DList<Medicamento> listaM = new DList<Medicamento>();
		Medicamento medicamentoActual;
		
		SList<Medicamento> MComercializadosS = (SList<Medicamento>) medicamentosComercializados.getPreorder();
		
		
		System.out.println();
		System.out.println("Consulta de todos los medicamentos con el principio activo: " + principioActivo);
		
		if (ordenAsc()) {
			// Ascendente
			for (int i = 0; i < MComercializadosS.getSize(); i++) {
				medicamentoActual = MComercializadosS.getAt(i);
				
				if (medicamentoActual.getPActivo().equalsIgnoreCase(principioActivo)) {
					System.out.println();
					System.out.println(medicamentoActual.toString());
					listaM.addLast(medicamentoActual);
				}
			}
		} else {
			// Descendente
			for (int i = (MComercializadosS.getSize() - 1); i >= 0; i--) {
				medicamentoActual = MComercializadosS.getAt(i);
				
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
	
	protected Medicamento buscaMedicamento(String nombreMedicamento) {
		if (medicamentosComercializados != null && medicamentosComercializados.containsKey(nombreMedicamento)) {
			Medicamento medicamentoActual = medicamentosComercializados.getElementByKey(nombreMedicamento);
			return medicamentoActual;
		}
		
		return null;
	}
	
	
	
	protected DList<Medicamento> consultaGenerica(Date fechaConsulta, String estado) {
		DList<Medicamento> listaM = new DList<Medicamento>();
		Medicamento medicamentoActual;
		
		SList<Medicamento> MComercializadosS = (SList<Medicamento>) medicamentosComercializados.getPreorder();
		
		
		System.out.println();
		if (fechaConsulta == null) {
			System.out.println("Consulta de todos los medicamentos " + estado + "s");
		} else {
			System.out.println("Consulta de medicamentos " + estado + "s a partir de: " + fechaConsulta);
		}
		
		if (ordenAsc()) {
			// Ascendente
			for (int i = 0; i < MComercializadosS.getSize(); i++) {
				medicamentoActual = MComercializadosS.getAt(i);
				
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
			for (int i = (MComercializadosS.getSize() - 1); i >= 0; i--) {
				medicamentoActual = MComercializadosS.getAt(i);
				
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
	

}
