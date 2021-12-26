package application;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import admin.UsuarioCRUD;
import entidades.Usuario;

public class TelaAtualizacao extends JFrame{
	JLabel lblTitulo, lblTituloC, lblCpf, lblNome, lblDataNasc, lblSexo;
	JTextField txtCpf, txtNome, txtDataNasc;
	JComboBox<String> cbSexo;
	JButton btnVoltar, btnAtualizar;
	JPanel pnInfo, pnBtns, pnTitulo;
	
	public TelaAtualizacao() {
		lblTitulo = new JLabel("Os campos não preenchidos(exceto o cpf)");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloC = new JLabel("será pertencido os valores no banco!");
		lblTituloC.setHorizontalAlignment(SwingConstants.CENTER);
		lblCpf = new JLabel("Insira o CPF:");
		lblNome = new JLabel("Insira o Nome:");		
		lblDataNasc = new JLabel("Nascimento:");
		lblSexo = new JLabel("Insira o Sexo:");
		
		txtCpf = new JTextField("Digite o CPF do cadastrado", 25);
		txtNome = new JTextField("Digite o Nome a ser alterado", 25);		
		txtDataNasc = new JTextField("Digite a Data de Nascimento a ser alterada", 25);
		String generos[] = {"Não atualizar","Masculino", "Feminino", "Outros"};
		cbSexo = new JComboBox<String>(generos);
		
		btnVoltar = new JButton("Voltar");
		btnAtualizar = new JButton("Atualizar");
		
		pnTitulo = new JPanel(new GridLayout(2,1));
		pnTitulo.add(lblTitulo); 
		pnTitulo.add(lblTituloC);		
		add(pnTitulo, BorderLayout.NORTH);
		
		pnInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		pnInfo.add(lblCpf); pnInfo.add(txtCpf); 
		pnInfo.add(lblNome); pnInfo.add(txtNome);
		pnInfo.add(lblDataNasc); pnInfo.add(txtDataNasc); 
		pnInfo.add(lblSexo); pnInfo.add(cbSexo);
		
		add(pnInfo);
		
		pnBtns = new JPanel(new FlowLayout());
		pnBtns.add(btnVoltar); pnBtns.add(btnAtualizar);
		
		add(pnBtns, BorderLayout.SOUTH);
		
		setTitle("Atualização de Usuarios");
		setSize(400,250);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		Btns b = new Btns();
		btnVoltar.addActionListener(b);
		btnAtualizar.addActionListener(b);
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
				if(txtCpf.getText().equals("") || txtCpf.getText().equals("Digite o CPF do cadastrado")) {
					JOptionPane.showMessageDialog(null, "Campo CPF não pode ser vazio!");
					txtCpf.requestFocus();
				}else {
					Usuario u = new Usuario();
					
					String cpf;
					if(txtCpf.getText().length() == 14){
						cpf = txtCpf.getText().replace(".", "");
						cpf = cpf.replace("-", "");
						u.setCpf(cpf);
					}else if(txtCpf.getText().length() == 11){
						u.setCpf(txtCpf.getText());
					}else {
						JOptionPane.showMessageDialog(null, "Por favor, digite um CPF válido");
						return;
					}

					
					if(!txtNome.getText().equals("") && !txtNome.getText().equals("Digite o Nome a ser alterado")) {
						u.setNome(txtNome.getText());
					}
					if(!txtDataNasc.getText().equals("") && !txtDataNasc.getText().equals("Digite a Data de Nascimento a ser alterada")) {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							sdf.setLenient(false);
							u.setDataNasc(sdf.parse(txtDataNasc.getText()));
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Insira uma data válida!");
							txtDataNasc.requestFocus();
							return;						
						}
					}					
					if(!cbSexo.getSelectedItem().equals("Não atualizar")) {
						if(cbSexo.getSelectedItem().equals("Masculino")) {
							u.setSexo("M");
						}else if(cbSexo.getSelectedItem().equals("Feminino")) {
							u.setSexo("F");
						}else {
							u.setSexo("O");
						}
					}
					try {
						UsuarioCRUD.update(u);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Erro, por favor contate o suporte !");
						e.printStackTrace();
						return;
					}
					JOptionPane.showMessageDialog(null, "Usuario atualizado com sucesso!");
					txtNome.setText("Digite o Nome a ser alterado");
					txtCpf.setText("Digite o CPF do cadastrado");
					txtDataNasc.setText("Digite a Data de Nascimento a ser alterada");
					requestFocus();					
				}
			}
				
		}
	}
	
	class Foco implements FocusListener{
		public void focusGained(FocusEvent e) {
			if(e.getSource() == txtNome) {
				if(txtNome.getText().equals("Digite o Nome a ser alterado")) {
					txtNome.setText("");
				}
			}else if(e.getSource() == txtCpf) {
				if(txtCpf.getText().equals("Digite o CPF do cadastrado")) {
					txtCpf.setText("");
				}
			}else {
				if(txtDataNasc.getText().equals("Digite a Data de Nascimento a ser alterada")) {
					txtDataNasc.setText("");
				}
			}
		}
		public void focusLost(FocusEvent e) {			
			if(e.getSource() == txtNome) {
				if(txtNome.getText().equals("")) {
					txtNome.setText("Digite o Nome a ser alterado");
				}
			}else if(e.getSource() == txtCpf) {
				if(txtCpf.getText().equals("")) {
					txtCpf.setText("Digite o CPF do cadastrado");
				}
			}else {
				if(txtDataNasc.getText().equals("")) {
					txtDataNasc.setText("Digite a Data de Nascimento a ser alterada");
				}
			}
		}
	}
}
