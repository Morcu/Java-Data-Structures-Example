package mainProject;

import java.util.Calendar;

import edalib.list.doublelink.DList;


public interface IAgencia {

	boolean recibir(Medicamento medicamento);

	boolean revisar();

	boolean consultarMedicamento(String nombreMedicamento);

	boolean aprobar(String nombreMedicamento);

	boolean retirar(String nombreMedicamento);

	DList<Medicamento> consultarAprobados(Calendar fecha);

	DList<Medicamento> consultarAprobados();

	DList<Medicamento> consultarRetirados(Calendar fecha);

	DList<Medicamento> consultarRetirados();

	DList<Medicamento> consultaMedicamentos(String principioActivo);

}