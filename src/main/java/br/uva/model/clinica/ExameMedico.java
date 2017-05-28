package br.uva.model.clinica;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author csiqueira
 */
@Entity
public class ExameMedico implements Serializable {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String nome;

	@Column
	private String informacoesTecnicas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getInformacoesTecnicas() {
		return informacoesTecnicas;
	}

	public void setInformacoesTecnicas(String informacoesTecnicas) {
		this.informacoesTecnicas = informacoesTecnicas;
	}
	
}