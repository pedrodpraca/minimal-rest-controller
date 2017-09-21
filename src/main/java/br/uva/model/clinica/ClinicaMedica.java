package br.uva.model.clinica;

import br.uva.model.clinica.especialidades.Especialidade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author csiqueira
 */
@Entity
public class ClinicaMedica implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String nome;

	@ManyToMany
	@JsonIgnore
	private Set<Especialidade> especialidades;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoAtendimento tipoAtendimento;

	@Column(nullable = false)
	private String endereco;

	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ExameMedico> exames;

	@OneToMany(cascade = CascadeType.ALL)
	private List<MedicoClinica> medicos;

	@Column
	private String email;
	
	@ElementCollection
	private Set<String> telefones;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	@JsonProperty("exames")
	public List<String> getNomesExames() {
		List<String> ret = new ArrayList<String>();
		if (exames != null && exames.size() > 0) {
			for (ExameMedico e : exames) {
				ret.add(e.getNome());
			}
		}
		return ret;
	}

	@JsonProperty("especialidades")
	public List<String> getNomesEspecialidades() {
		List<String> ret = new ArrayList<String>();
		if (especialidades != null && especialidades.size() > 0) {
			for (Especialidade e : especialidades) {
				ret.add(e.getNome());
			}
		}
		return ret;
	}
	
	public ClinicaMedica() {
		this.especialidades = new TreeSet<Especialidade>();
		this.telefones = new TreeSet<String>();
		this.exames = new ArrayList<ExameMedico>();
		this.medicos = new ArrayList<MedicoClinica>();
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public TipoAtendimento getTipoAtendimento() {
		return tipoAtendimento;
	}

	public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
		this.tipoAtendimento = tipoAtendimento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<ExameMedico> getExames() {
		return exames;
	}

	public void setExames(List<ExameMedico> exames) {
		this.exames = exames;
	}

	public List<MedicoClinica> getMedicoClinica() {
		return medicos;
	}

	public void setMedicoClinica(List<MedicoClinica> medicoClinica) {
		this.medicos = medicoClinica;
	}

	public List<MedicoClinica> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<MedicoClinica> medicos) {
		this.medicos = medicos;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
