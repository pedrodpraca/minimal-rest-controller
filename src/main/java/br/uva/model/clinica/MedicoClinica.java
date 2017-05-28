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
public class MedicoClinica implements Serializable {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	private String CRM;
	
	@Column
	private String nome;

	@Column
	private String titulo;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCRM() {
		return CRM;
	}

	public void setCRM(String CRM) {
		this.CRM = CRM;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}