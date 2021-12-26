package application;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import admin.UsuarioCRUD;
import entidades.Usuario;

public class TelaInsercao extends JFrame{
	JLabel lblNome, lblCpf, lblDataNasc, lblSexo;
	JTextField txtNome, txtCpf, txtDataNasc;
	JComboBox cbSexo;
	JButton btnVoltar, btnInserir;
	JPanel pnInfo, pnBtns;
	
	public TelaInsercao() {
		lblNome = new JLabel("Insira o Nome:");
		lblCpf = new JLabel("Insira o CPF:");
		lblDataNasc = new JLabel("Nascimento:");
		lblSexo = new JLabel("Insira o Sexo:");
		
		txtNome = new JTextField("Digite o Nome", 20);
		txtCpf = new JTextField("Digite o CPF", 20);
		txtDataNasc = new JTextField("Digite a Data de Nascimento", 20);
		String generos[] = {"Masculino", "Feminino", "Outros"};
		cbSexo = new JComboBox<String>(generos);
		
		btnVoltar = new JButton("Voltar");
		btnInserir = new JButton("Inserir");
		
		pnInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		pnInfo.add(lblNome); pnInfo.add(txtNome);
		pnInfo.add(lblCpf); pnInfo.add(txtCpf); 
		pnInfo.add(lblDataNasc); pnInfo.add(txtDataNasc); 
		pnInfo.add(lblSexo); pnInfo.add(cbSexo);
		
		add(pnInfo);
		
		pnBtns = new JPanel(new FlowLayout());
		pnBtns.add(btnVoltar); pnBtns.add(btnInserir);
		
		add(pnBtns, BorderLayout.SOUTH);
		
		setTitle("Cadastro de Usuarios");
		setSize(329,190);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		Btns b = new Btns();
		btnVoltar.addActionListener(b);
		btnInserir.addActionListener(b);
		txtNome.addActionListener(b);
		txtCpf.addActionListener(b);
		txtDataNasc.addActionListener(b);
		
		Foco f = new Foco();
		txtNome.addFocusListener(f);
		txtCpf.addFocusListener(f);
		txtDataNasc.addFocusListener(f);
		
		requestFocus();
	}
	
	class Btns implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			if(ev.getSource()==btnVoltar) {
				dispose();
				new TelaPrincipal();
			}else {
				if(txtNome.getText().equals("") || txtNome.getText().equals("Digite o Nome")) {
					JOptionPane.showMessageDialog(null, "Campo Nome não pode ser vazio!");
					txtNome.requestFocus();
				}else if(txtCpf.getText().equals("") || txtCpf.getText().equals("Digite o CPF")) {
					JOptionPane.showMessageDialog(null, "Campo CPF não pode ser vazio!");
					txtCpf.requestFocus();
				}else if(txtDataNasc.getText().equals("") || txtDataNasc.getText().equals("Digite a Data de Nascimento")) {
					JOptionPane.showMessageDialog(null, "Campo Nascimento não pode ser vazio!");
					txtDataNasc.requestFocus();
				}else {
					Usuario u = new Usuario();
					Usuario u2 = new Usuario();
					u.setNome(txtNome.getText());
					
					String cpf;
					if(txtCpf.getText().length() == 14){
						cpf = txtCpf.getText().replace(".", "");
						cpf = cpf.replace("-", "");
					}else if(txtCpf.getText().length() == 11){
						cpf = txtCpf.getText();
					}else {
						JOptionPane.showMessageDialog(null, "Por favor, digite um CPF válido");
						return;
					}
					
					u2.setCpf(cpf);

					try {
						u2 = UsuarioCRUD.readForCpf(u2.getCpf());
						
						if(u2.getNome() == null) {
							u.setCpf(cpf);
						}else {
							JOptionPane.showMessageDialog(null, "Erro, CPF já cadastrado no sistema!");
							return;
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro, por favor contate o suporte!");
						e1.printStackTrace();
						return;
					}
					
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						sdf.setLenient(false);
						u.setDataNasc(sdf.parse(txtDataNasc.getText()));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Insira uma data válida!");
						txtDataNasc.requestFocus();
						return;						
					}
					if(cbSexo.getSelectedItem().equals("Masculino")) {
						u.setSexo("M");
					}else if(cbSexo.getSelectedItem().equals("Feminino")) {
						u.setSexo("F");
					}else {
						u.setSexo("O");
					}
					
					try {
						UsuarioCRUD.insert(u);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Erro, por favor contate o suporte !");
						e.printStackTrace();
						return;
					}
					JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso!");
					txtNome.setText("Digite o Nome");
					txtCpf.setText("Digite o CPF");
					txtDataNasc.setText("Digite a Data de Nascimento");
					requestFocus();
				}
			}
				
		}
	}
	
	class Foco implements FocusListener{
		public void focusGained(FocusEvent e) {
			if(e.getSource() == txtNome) {
				if(txtNome.getText().equals("Digite o Nome")) {
					txtNome.setText("");
				}
			}else if(e.getSource() == txtCpf) {
				if(txtCpf.getText().equals("Digite o CPF")) {
					txtCpf.setText("");
				}
			}else {
				if(txtDataNasc.getText().equals("Digite a Data de Nascimento")) {
					txtDataNasc.setText("");
				}
			}
		}
		public void focusLost(FocusEvent e) {			
			if(e.getSource() == txtNome) {
				if(txtNome.getText().equals("")) {
					txtNome.setText("Digite o Nome");
				}
			}else if(e.getSource() == txtCpf) {
				if(txtCpf.getText().equals("")) {
					txtCpf.setText("Digite o CPF");
				}
			}else {
				if(txtDataNasc.getText().equals("")) {
					txtDataNasc.setText("Digite a Data de Nascimento");
				}
			}
		}
	}
}
