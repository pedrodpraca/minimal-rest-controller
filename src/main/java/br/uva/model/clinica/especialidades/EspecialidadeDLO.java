package br.uva.model.clinica.especialidades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author csiqueira
 */
@Service
@Transactional
public class EspecialidadeDLO {
	
	private final static String[] LISTA_NOMES = {"Cardiologia", "Maternidade", "Odontologia", "Oncologia", "Otorrinolaringologia", "Pediatria", "Psicologia", "Psiquiatria", "Endocrinologia", "Urologia", "Obstreta", "Pneumologia", "Alergista", "Ginecologia", "Clínico Geral", "Vacinação Infantil", "Vacinação Adulta", "Mudança de sexo", "Redesignação Sexual"};
	
	@Autowired
	private EspecialidadeDAO dao;	
	
	public Iterable<Especialidade> findAll() {
		return dao.findAll();
	}
	
	public void criarEspecialidades() {
		for (String nomeEspecialidade : LISTA_NOMES) {
			Especialidade e = new Especialidade();
			e.setNome(nomeEspecialidade);
			dao.save(e);
		}
	}
	
}