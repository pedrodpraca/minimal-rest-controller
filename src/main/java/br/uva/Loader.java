/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva;

import br.uva.model.clinica.ClinicaMedicaDLO;
import br.uva.model.clinica.especialidades.EspecialidadeDLO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author csiqueira
 */
@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	private ClinicaMedicaDLO dlo;
	
	@Autowired
	private EspecialidadeDLO especialidadesDLO;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		especialidadesDLO.criarEspecialidades();
		dlo.criarDadosTeste();
	}
}
