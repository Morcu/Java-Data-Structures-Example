package mainProject;

import java.util.ArrayList;
import java.util.Calendar;

public class Test {

	public static void main(String[] args) {
		
		Calendar fecha = Calendar.getInstance();
		
		// Creamos la agencia AEMPS
		AgenciaF1 AEMPS = new AgenciaF1();
		AgenciaF2 AEMPSArbol;
		
		// Creamos el medicamento efferalgan
		ArrayList<String> indicaciones = new ArrayList<String>();
		indicaciones.add("Dolor");
		indicaciones.add("Fiebre");
		
		ArrayList<String> efectosAdversos = new ArrayList<String>();
		efectosAdversos.add("Erupciones cutáneas");
		efectosAdversos.add("Alteraciones sanguíneas");
		efectosAdversos.add("Hipoglucemia");
		efectosAdversos.add("Hipotensión");
		
		Medicamento efferalgan = new Medicamento(
				"B",
				"Upsa Laboratories",
				"Paracetamol",
				Calendar.getInstance(), // Calendario con la fecha actual
				indicaciones,
				efectosAdversos,
				Medicamento.PENDIENTE);
		
		Medicamento paracetamolGenerico = new Medicamento(
				"C",
				"Generic Laboratories",
				"Paracetamol",
				Calendar.getInstance(), // Calendario con la fecha actual
				indicaciones,
				efectosAdversos,
				Medicamento.PENDIENTE);
		
		Medicamento ibuprofenoGenerico = new Medicamento(
				"A",
				"Generic Laboratories",
				"Ibuprofeno",
				Calendar.getInstance(), // Calendario con la fecha actual
				indicaciones,
				efectosAdversos,
				Medicamento.PENDIENTE);
		
		// Se lo pasamos a la AEMPS para que lo revise en un futuro
		AEMPS.recibir(efferalgan);
		AEMPS.recibir(paracetamolGenerico);
		AEMPS.recibir(ibuprofenoGenerico);
		
		// AEMPS revisa los medicamentos recibidos
		AEMPS.revisar();
		
		AEMPSArbol = AEMPS.convert();
		AEMPSArbol.consultarAprobados();
		
		
		AEMPSArbol.consultarMedicamento("A");
		AEMPSArbol.consultarMedicamento("Z");
		AEMPSArbol.consultarMedicamento("A");
		
		AEMPSArbol.consultarAprobados(fecha);
		
		AEMPSArbol.consultarAprobados();
		
		AEMPSArbol.consultarRetirados(fecha);
		
		AEMPSArbol.consultarRetirados();
		
		AEMPSArbol.consultaMedicamentos("Paracetamol");
		
		/*
		AEMPS.consultarMedicamento("A");
		AEMPS.consultarMedicamento("Z");
		AEMPS.consultarMedicamento("A");
		
		AEMPS.consultarAprobados(fecha);
		
		AEMPS.consultarAprobados();
		
		AEMPS.consultarRetirados(fecha);
		
		AEMPS.consultarRetirados();
		
		AEMPS.consultaMedicamentos("Paracetamol");
		*/
		
	}

}
