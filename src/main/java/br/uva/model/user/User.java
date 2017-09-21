package br.uva.model.user;


import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "user_id") 
    private int id;
    @Column
	@NotEmpty
	private String username;
	@Column
	@Email
	@NotEmpty
	private String email;
	@Column
	@NotEmpty
	private String password;
	@Column
	@NotEmpty
	private String nome;
	@Column
	@NotEmpty
	private String sexo;
	@Column
	@NotEmpty
	private String cpf;
	@Column
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date data;
	@Column
	@NotEmpty
	private String telefone;
	@Column
	private String celular;
	@Column
	@NotEmpty
	private String end;
	@Column
	@NotNull
	private int numend;
	@Column
	@NotEmpty
	private String cep;
	@Column
	@NotEmpty
	private String cidade;
	@Column
	@NotEmpty
	private String estado;
	@Column
	private boolean active;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
    }
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSexo(){
		return sexo;
	}
	public void setSexo(String sexo){
		this.sexo=sexo;
	}
	public String getCpf(){
		return cpf;
	}
	public void setCpf(String cpf){
		this.cpf=cpf;
	}
	public Date getData(){
		return data;
	}
	public void setData(Date data){
		this.data=data;
	}
	public String getTelefone(){
		return telefone;
	}
	public void setTelefone(String telefone){
		this.telefone=telefone;
	}
	public String getCelular(){
		return celular;
	}
	public void setCelular(String celular){
		this.celular=celular;
	}
	public String getEnd(){
		return end;
	}
	public void setEnd(String end){
		this.end=end;
	}
	public int getNumend(){
		return numend;
	}
	public void setNumend(int numend){
		this.numend=numend;
	}
	public String getCep(){
		return cep;
	}
	public void setCep(String cep){
		this.cep=cep;
	}
	public String getCidade(){
		return cidade;
	}
	public void setCidade(String cidade){
		this.cidade=cidade;
	}
	public String getEstado(){
		return estado;
	}
	public void setEstado(String estado){
		this.estado=estado;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}