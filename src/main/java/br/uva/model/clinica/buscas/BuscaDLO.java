package br.uva.model.clinica.buscas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author csiqueira
 */
@Service
@Transactional
public class BuscaDLO {
	
	@Autowired
	private BuscaDAO dao;	
	
	public String criar(String query) {
		String[] ref = query.split("[ ]+");
		List<String> input;

		if(ref.length == 0) {
			input = new ArrayList<String>();
			input.add("");
		} else {
			input = Arrays.asList(ref);
		}
		
		List<Busca> keywords = new ArrayList<Busca>();
		String uuid = UUID.randomUUID().toString();
		
		for(String i : input) {
			Busca b = new Busca();
			b.setTermo(i);
			b.setUuid(uuid);
			keywords.add(b);
		}
		
		dao.save(keywords);
		
		return uuid;
	}

	public void excluir(String uuid) {
		dao.deleteByUuid(uuid);
	}
	
}