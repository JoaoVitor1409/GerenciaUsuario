package application;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import admin.UsuarioCRUD;
import entidades.Usuario;

public class TelaExclusao extends JFrame{
	JLabel lblCpf;
	JTextField txtCpf;
	JButton btnVoltar, btnDeletar;
	JPanel pnInfo, pnBtns;
	
	public TelaExclusao() {
		lblCpf = new JLabel("Insira o CPF:");
		
		txtCpf = new JTextField("Digite o CPF do cadastrado", 20);
		
		btnVoltar = new JButton("Voltar");
		btnDeletar = new JButton("Deletar");
		
		pnInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		pnInfo.add(lblCpf); pnInfo.add(txtCpf); 
		
		add(pnInfo);
		
		pnBtns = new JPanel(new FlowLayout());
		pnBtns.add(btnVoltar); pnBtns.add(btnDeletar);
		
		add(pnBtns, BorderLayout.SOUTH);
		
		setTitle("Deleção de Usuarios");
		setSize(250,100);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		Btns b = new Btns();
		btnVoltar.addActionListener(b);
		btnDeletar.addActionListener(b);
		txtCpf.addActionListener(b);
		
		txtCpf.addFocusListener(new Foco());
		
		requestFocus();
	}
	
	class Btns implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			if(ev.getSource()==btnVoltar) {
				dispose();
				new TelaPrincipal();
			}else {
				if(txtCpf.getText().equals("") || txtCpf.getText().equals("Digite o CPF")) {
					JOptionPane.showMessageDialog(null, "Campo CPF não pode ser vazio!");
					txtCpf.requestFocus();
				}else {
					String cpf;
					if(txtCpf.getText().length() == 14){
						cpf = txtCpf.getText().replace(".", "");
						cpf = cpf.replace("-", "");
					}else {
						cpf = txtCpf.getText();
					}
					
					try {
						UsuarioCRUD.delete(cpf);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Erro, por favor contate o suporte !");
						e.printStackTrace();
						return;
					}
					JOptionPane.showMessageDialog(null, "Usuario deletado com sucesso!");
					txtCpf.setText("Digite o CPF do cadastrado");
					requestFocus();
				}
			}
				
		}
	}
	
	class Foco implements FocusListener{
		public void focusGained(FocusEvent e) {
			if(txtCpf.getText().equals("Digite o CPF do cadastrado")) {
				txtCpf.setText("");
			}
		}
		public void focusLost(FocusEvent e) {			
			if(txtCpf.getText().equals("")) {
				txtCpf.setText("Digite o CPF do cadastrado");
			}
		}
	}
}
