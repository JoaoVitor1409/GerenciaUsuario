package application;

import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import admin.UsuarioCRUD;
import entidades.Usuario;

public class TelaPrincipal extends JFrame{
	JLabel lblBuscar;
	JTextField txtBuscar;
	JTable tabela;
	DefaultTableModel modelo;
	JScrollPane sp;
	JButton btnInserir, btnAtualizar, btnDeletar, btnBuscar;
	JPanel pnBuscar, pnBtns;
	
	public TelaPrincipal() {
		lblBuscar = new JLabel("CPF:");
		
		txtBuscar = new JTextField("Digite o CPF" ,15);
		
		btnInserir = new JButton("Inserir");
		btnAtualizar = new JButton("Atualizar");
		btnDeletar = new JButton("Deletar");
		btnBuscar = new JButton("Buscar");		
		
		inserirTabela();
		
		pnBuscar = new JPanel(new FlowLayout());
		pnBuscar.add(lblBuscar); pnBuscar.add(txtBuscar); pnBuscar.add(btnBuscar);
		add(pnBuscar, BorderLayout.NORTH);
				
		pnBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 30,0));
		pnBtns.add(btnInserir); pnBtns.add(btnAtualizar); pnBtns.add(btnDeletar);
		add(pnBtns, BorderLayout.SOUTH);
		
		setTitle("Usuarios");
		setSize(700,300);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		requestFocus();
		
		Btns b = new Btns();
		btnBuscar.addActionListener(b);
		txtBuscar.addActionListener(b);
		btnInserir.addActionListener(b);
		btnAtualizar.addActionListener(b);
		btnDeletar.addActionListener(b);
		
		txtBuscar.addFocusListener(new Foco());
	}
	
	class Btns implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			if(ev.getSource()==btnBuscar || ev.getSource()==txtBuscar) {
				for(int i=0; i<modelo.getRowCount(); i+=0) {
					modelo.removeRow(i);
				}
				if(txtBuscar.getText().equals("") || txtBuscar.getText().equals("Digite o CPF")) {
					addTodasLinhas();
				}else {
					addLinha();
				}
			}else if(ev.getSource() == btnInserir) {				
				dispose();
				new TelaInsercao();
			}else if(ev.getSource() == btnAtualizar) {
				dispose();
				new TelaAtualizacao();
			}else {
				dispose();
				new TelaExclusao();
			}
				
		}
	}
	
	class Foco implements FocusListener{
		public void focusGained(FocusEvent e) {
			txtBuscar.setText("");
		}
		public void focusLost(FocusEvent e) {
			if(txtBuscar.getText().equals("")) {
				txtBuscar.setText("Digite o CPF");
			}
		}
	}
	
	private void inserirTabela() {
		List<Usuario> usuarios = new ArrayList<>();
		try {
			usuarios = UsuarioCRUD.readAll();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro, por favor contate o suporte !");
			e.printStackTrace();
		}
		String dados[][] = new String[usuarios.size()][5];
		String colunas[] = {"Nome", "CPF", "Data de Nascimento", "Sexo", "Ativo"};
		
		modelo = new DefaultTableModel(dados, colunas) {
			public boolean isCellEditable(int rowIndex, int mColIndex){ 
				return false; 
			}			
		};
		tabela = new JTable(modelo);
		
		tabela.getTableHeader().setResizingAllowed(false);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumn colNome = tabela.getColumnModel().getColumn(0); 
		colNome.setPreferredWidth(250);
		
		TableColumn colCpf = tabela.getColumnModel().getColumn(1); 
		colCpf.setPreferredWidth(150);
		
		TableColumn colData = tabela.getColumnModel().getColumn(2); 
		colData.setPreferredWidth(150);
		
		TableColumn colSexo = tabela.getColumnModel().getColumn(3); 
		colSexo.setPreferredWidth(66);
		
		TableColumn colAtivo = tabela.getColumnModel().getColumn(4); 
		colAtivo.setPreferredWidth(65);
		
		
		sp = new JScrollPane(tabela);
	
		if(usuarios.size() > 0) {
			for(int i=0; i<usuarios.size(); i++){
				dados[i][0] = usuarios.get(i).getNome();
				dados[i][1] = usuarios.get(i).getCpf();
				dados[i][2] = usuarios.get(i).getDataFormatada();
				dados[i][3] = usuarios.get(i).getSexo();
				dados[i][4] = "" + usuarios.get(i).isAtivo();
				
				
				tabela.setValueAt(dados[i][0], i, 0);
				tabela.setValueAt(dados[i][1], i, 1);
				tabela.setValueAt(dados[i][2], i, 2);
				tabela.setValueAt(dados[i][3], i, 3);
				tabela.setValueAt(dados[i][4], i, 4);
			}			
		}
		add(sp);
	}
	
	private void addTodasLinhas() {
		List<Usuario> usuarios = new ArrayList<>();
		try {
			usuarios = UsuarioCRUD.readAll();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro, por favor contate o suporte !");
			e.printStackTrace();
		}
		String dados[][] = new String[usuarios.size()][5];
		if(usuarios.size() > 0) {
			for(int i=0; i<usuarios.size(); i++){
				dados[i][0] = usuarios.get(i).getNome();
				dados[i][1] = usuarios.get(i).getCpf();
				dados[i][2] = usuarios.get(i).getDataFormatada();
				dados[i][3] = usuarios.get(i).getSexo();
				dados[i][4] = ""+usuarios.get(i).isAtivo();
				
				modelo.addRow(dados[i]);
			}			
		}
	}
	
	private void addLinha() {
		String cpf;
		if(txtBuscar.getText().length() == 14){
			cpf = txtBuscar.getText().replace(".", "");
			cpf = cpf.replace("-", "");
		}else {
			cpf = txtBuscar.getText();
		}
		
		try {
			Usuario u = UsuarioCRUD.readForCpf(cpf);
			if(u.getId() == 0) {
				JOptionPane.showMessageDialog(null, "Nenhum usuário encontrado");
				return;
			}
			String dados[][] = {{u.getNome(), cpf, u.getDataFormatada(), u.getSexo(), "" + u.isAtivo()}};
			modelo.addRow(dados[0]);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro, por favor contate o suporte !");
			e.printStackTrace();
			return;
		}
	}
	
	public static void main(String args[]) {
		new TelaPrincipal();
	}
}
