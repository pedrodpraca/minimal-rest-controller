package br.uva.model.clinica.especialidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author csiqueira
 */
@Entity
public class Especialidade implements Serializable, Comparable<Especialidade> {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String nome;

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

	@Override
	public int compareTo(Especialidade o) {
		return Objects.equals(o.id, this.id) ? 0 : this.nome.compareTo(o.nome);
	}
	
}
