package entidades;

import java.text.*;
import java.util.*;

public class Usuario {
	private int id;
	private String nome;
	private String cpf;
	private Date dataNasc;
	private String sexo;
	private boolean ativo;
	
	
	
	public int getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public Date getDataNasc() {
		return dataNasc;
	}
	
	public String getDataFormatada() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(dataNasc);
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}	
}
