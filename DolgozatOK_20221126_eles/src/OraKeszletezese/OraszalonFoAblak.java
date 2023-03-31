package OraKeszletezese;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import OraKeszletezese.FajtaOsztaly.OraTipus;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OraszalonFoAblak {

	private JFrame frmrabolt;
	private JTextField txtMegnevezes;
	private JComboBox cmbTipus;
	private JSpinner SpinnerAr;
	private JCheckBox checkboxVizallo;
	private Ora ujOra;
	private Ora modositOra;
	
	List<Ora> orak;
	DefaultListModel<Ora> listaModel;
	private JList listaMegjelenit;
	private JTextField txtId;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OraszalonFoAblak window = new OraszalonFoAblak();
					window.frmrabolt.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public OraszalonFoAblak() throws SQLException {
		initialize();
		
		try {
			
			ABKezeloOraszalon.kapcsolodas();
			orak = ABKezeloOraszalon.adatokBetoltese();
			listaMegjelenites();
			
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(frmrabolt, e.getMessage(), "Adatbázis hiba!", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		frmrabolt = new JFrame();
		frmrabolt.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int valasz = JOptionPane.showOptionDialog(frmrabolt, "Biztosan kilép?", "kilépés",JOptionPane.YES_NO_OPTION
						,JOptionPane.QUESTION_MESSAGE,null,null,null);
				
				if (valasz == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				
			}
		});
		frmrabolt.setTitle("Órabolt");
		frmrabolt.setBounds(100, 100, 765, 441);
		frmrabolt.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmrabolt.getContentPane().setLayout(null);
		
		orak = new ArrayList<Ora>();
		
		listaMegjelenit = new JList();
		listaMegjelenit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Ora kivalasztottOra = (Ora)listaMegjelenit.getSelectedValue();
				
				if (e.getClickCount() == 2) {
					if (listaMegjelenit.getSelectedIndex() != -1) {
						
						String uzenet = String.valueOf(kivalasztottOra.getId()) + ", " + kivalasztottOra.getMegnevezes() 
						+ ", " + (OraTipus)kivalasztottOra.getTipus() + 
								", " + String.valueOf(kivalasztottOra.getAr()) + " Ft, " + 
								((kivalasztottOra.isVizallo())?"vízálló":"nem vízálló");
						
						JOptionPane.showMessageDialog(frmrabolt, uzenet, "Infó",JOptionPane.INFORMATION_MESSAGE);
						
						listaMegjelenit.clearSelection();
				}
				}
				
				if (e.getClickCount() == 1) {
					
					txtId.setText(kivalasztottOra.getId().toString());
					txtMegnevezes.setText(kivalasztottOra.getMegnevezes());
					cmbTipus.setSelectedItem(kivalasztottOra.getTipus());
					SpinnerAr.setValue(kivalasztottOra.getAr());
					checkboxVizallo.setSelected(kivalasztottOra.isVizallo());
				}
			}
		});
		listaMegjelenit.setBounds(349, 32, 362, 349);
		frmrabolt.getContentPane().add(listaMegjelenit);
		
		JLabel lblMegnevezes = new JLabel("Megnevezés");
		lblMegnevezes.setBounds(34, 76, 114, 14);
		frmrabolt.getContentPane().add(lblMegnevezes);
		
		JLabel lblTipus = new JLabel("Típus");
		lblTipus.setEnabled(false);
		lblTipus.setBounds(34, 116, 94, 14);
		frmrabolt.getContentPane().add(lblTipus);
		
		JLabel lblAr = new JLabel("Ár");
		lblAr.setEnabled(false);
		lblAr.setBounds(34, 162, 94, 14);
		frmrabolt.getContentPane().add(lblAr);
		
		JLabel lblVzll = new JLabel("Vízálló");
		lblVzll.setEnabled(false);
		lblVzll.setBounds(34, 213, 94, 14);
		frmrabolt.getContentPane().add(lblVzll);
		
		txtMegnevezes = new JTextField();
		txtMegnevezes.setBounds(137, 73, 148, 20);
		frmrabolt.getContentPane().add(txtMegnevezes);
		txtMegnevezes.setColumns(10);
		
		cmbTipus = new JComboBox();
		cmbTipus.setModel(new DefaultComboBoxModel(OraTipus.values()));
		cmbTipus.setBounds(138, 112, 147, 22);
		frmrabolt.getContentPane().add(cmbTipus);
		
		SpinnerAr = new JSpinner();
		SpinnerAr.setBounds(138, 162, 148, 20);
		frmrabolt.getContentPane().add(SpinnerAr);
		
		checkboxVizallo = new JCheckBox("");
		checkboxVizallo.setBounds(134, 209, 29, 23);
		frmrabolt.getContentPane().add(checkboxVizallo);
		
		
		
		JButton btnMentes = new JButton("Mentés");
		btnMentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ellenorzottMentes();
				
			}

		});
		
		
		
		btnMentes.setBounds(196, 209, 89, 23);
		frmrabolt.getContentPane().add(btnMentes);
		
		JButton btnKilepes = new JButton("Kilépés");
		btnKilepes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frmrabolt.dispatchEvent(new WindowEvent(frmrabolt,WindowEvent.WINDOW_CLOSING));
			}
		});
		btnKilepes.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnKilepes.setForeground(Color.RED);
		btnKilepes.setBounds(112, 304, 120, 77);
		frmrabolt.getContentPane().add(btnKilepes);
		
		JLabel lblTermekLista = new JLabel("Terméklista:");
		lblTermekLista.setHorizontalAlignment(SwingConstants.CENTER);
		lblTermekLista.setBounds(349, 11, 362, 14);
		frmrabolt.getContentPane().add(lblTermekLista);
		
		JButton btnModositas = new JButton("Módosítás");
		btnModositas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					modositas();
				} catch (SQLException e1) {
					
					JOptionPane.showMessageDialog(frmrabolt, "Az óra módosítása DB hiba miatt sikertelen!",
							"Hiba",JOptionPane.ERROR_MESSAGE);	
				}
			}

		});
		btnModositas.setBounds(39, 253, 109, 23);
		frmrabolt.getContentPane().add(btnModositas);
		
		txtId = new JTextField();
		txtId.setEnabled(false);
		txtId.setBounds(136, 32, 149, 20);
		frmrabolt.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(34, 35, 49, 14);
		frmrabolt.getContentPane().add(lblId);
		
		JButton btnTorles = new JButton("Törlés");
		btnTorles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
					try {
						torles();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}

			
		});
		btnTorles.setBounds(176, 253, 109, 23);
		frmrabolt.getContentPane().add(btnTorles);
		
		
	}
	
	private void torles() throws SQLException {
	
			if (listaMegjelenit.getSelectedIndex() != -1 && (JOptionPane.showOptionDialog(frmrabolt, 
					"Biztosan kitörli?", "Törlés", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null)==JOptionPane.YES_OPTION)) {
				
				Ora torlendoOra = (Ora)listaMegjelenit.getSelectedValue();
				
				ABKezeloOraszalon.oraTorlese(torlendoOra);
				orak.remove(torlendoOra);
				listaModel.remove(listaMegjelenit.getSelectedIndex());
				
			}	
				
	}
	
	private void modositas() throws SQLException {
		//!!!! A combobox mindig egy object, amit kasztolni kell abba a tipusba, amire szuksegunk van
		
		if (listaMegjelenit.getSelectedIndex() != -1) {
			
			Ora modositandoOra = (Ora)listaMegjelenit.getSelectedValue();
			int modOraIndex = listaMegjelenit.getSelectedIndex();
			Ora modositottOra = new Ora(txtMegnevezes.getText(), (OraTipus)cmbTipus.getSelectedItem(), (int)SpinnerAr.getValue(), checkboxVizallo.isSelected());
			modositottOra.setId(modositandoOra.getId());
			
			orak.set(orak.indexOf(modositandoOra), modositottOra);
			listaModel.set(modOraIndex, modositottOra);
			
			
			ABKezeloOraszalon.OraModositasa(modositottOra);
		}
		
		else {
			JOptionPane.showMessageDialog(frmrabolt, "Nincs kiválasztva elem!","Figyelmeztetés",JOptionPane.WARNING_MESSAGE);
		}
	}	

	private void listaMegjelenites() {
		listaModel = new DefaultListModel<Ora>();
		
		for (Ora item : orak) {
			
			listaModel.addElement(item);
			
		}
		listaMegjelenit.setModel(listaModel);
		
	}

	private void ellenorzottMentes() {
		
		
		try {
			
			if (!txtMegnevezes.getText().isBlank()) {
				
				ujOra = new Ora(txtMegnevezes.getText(), (OraTipus.konvertalas(cmbTipus.getSelectedItem().toString())), (int)SpinnerAr.getValue(), checkboxVizallo.isSelected());
				orak.add(ujOra);
				
				listaModel.addElement(ujOra);
				ABKezeloOraszalon.ujOraFelvitele(ujOra);
			}
			
			else {
				JOptionPane.showMessageDialog(frmrabolt, "Megnevezés megadása kötelező!"
						,"Figyelmeztetés",JOptionPane.WARNING_MESSAGE);
			}
			
		} catch (IllegalArgumentException e) {
			
			JOptionPane.showMessageDialog(frmrabolt, e.getMessage(),"Figyelmeztetés",JOptionPane.WARNING_MESSAGE);
			
		} 
		
		catch (SQLException e) {
			
			JOptionPane.showMessageDialog(frmrabolt, e.getMessage(),"Hiba",JOptionPane.ERROR_MESSAGE);
			ujOra = null;
		}
		
	}
}
