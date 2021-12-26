package admin;

import java.sql.*;
import java.util.*;
import admin.ConnectionFactory;
import entidades.Usuario;

public class UsuarioCRUD {
	public static void insert(Usuario u) throws Exception {
		String sql = "INSERT INTO Usuario(UsuarioNome, UsuarioCPF, UsuarioDataNasc, UsuarioSexo, UsuarioAtivo)"
				+ "VALUES(?, ?, ?, ?, ?)";
		
		try(
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
		){
			pst.setString(1, u.getNome());
			pst.setString(2, u.getCpf());
			pst.setDate(3, new java.sql.Date(u.getDataNasc().getTime()));
			pst.setString(4, u.getSexo());
			pst.setBoolean(5, true);
			pst.executeUpdate();
		}
	}
	
	public static List<Usuario> readAll() throws Exception{
		String sql = "SELECT * FROM Usuario WHERE UsuarioAtivo=1 ORDER BY UsuarioNome";
		
		try(
			Connection con = ConnectionFactory.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
		){
			List<Usuario> usuarios = new ArrayList<>();
			while(rs.next()) {
				Usuario u = new Usuario();
				u.setId(rs.getInt("UsuarioID"));
				u.setNome(rs.getString("UsuarioNome"));
				u.setCpf(rs.getString("UsuarioCPF"));
				u.setDataNasc(rs.getDate("UsuarioDataNasc"));
				u.setSexo(rs.getString("UsuarioSexo"));
				u.setAtivo(rs.getBoolean("UsuarioAtivo"));
				usuarios.add(u);
			}
			return usuarios;	
		}		
	}
	
	public static Usuario readForCpf(String cpf) throws Exception {
		String sql = "SELECT * FROM Usuario WHERE UsuarioCPF=?";
		
		try(
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
		){
			pst.setString(1, cpf);
			try(
				ResultSet rs = pst.executeQuery();
			){
				Usuario u = new Usuario();
				if(rs.next()) {
					u.setId(rs.getInt("UsuarioID"));
					u.setNome(rs.getString("UsuarioNome"));
					u.setCpf(rs.getString("UsuarioCPF"));;
					u.setDataNasc(rs.getDate("UsuarioDataNasc"));
					u.setSexo(rs.getString("UsuarioSexo"));
					u.setAtivo(rs.getBoolean("UsuarioAtivo"));
					
					return u;
				}
				return u;				
			}						
		}
	}
	
	
	public static boolean update(Usuario u) throws Exception{
		String sql = "UPDATE Usuario SET UsuarioNome=?, UsuarioDataNasc=?, UsuarioSexo=? WHERE UsuarioCPF=?";

		try(
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
		){
			Usuario u2 = readForCpf(u.getCpf());
			if(u.getNome() != null) {
				pst.setString(1, u.getNome());
			}else {
				pst.setString(1, u2.getNome());
			}
			
			if(u.getDataNasc() != null) {
				pst.setDate(2, new java.sql.Date(u.getDataNasc().getTime()));
			}else {
				pst.setDate(2, new java.sql.Date(u2.getDataNasc().getTime()));
			}
			
			if(u.getSexo() != null) {
				pst.setString(3, u.getSexo());
			}else {
				pst.setString(3, u2.getSexo());
			}
			
			pst.setString(4, u.getCpf());
			
			int resposta = pst.executeUpdate();
			if(resposta == 1) {
				return true;
			}
			return false;
		}
	}
	
	public static boolean delete(String cpf) throws Exception {
		String sql = "UPDATE Usuario SET UsuarioAtivo=? WHERE UsuarioCPF=?";
		
		try(
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
		){
			pst.setBoolean(1, false);
			pst.setString(2, cpf);
			int resposta = pst.executeUpdate();
			if(resposta == 1) {
				return true;
			}
			 return false;
		}
	}
}
