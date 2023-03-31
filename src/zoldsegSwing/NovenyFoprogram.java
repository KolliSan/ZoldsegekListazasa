package zoldsegSwing;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NovenyFoprogram {

	private Noveny ujNoveny;
	private Noveny modositNoveny;
	List<Noveny> novenyek;
	private JFrame frmNoveny;
	DefaultListModel<Noveny> listModel;
	private JList lstAdatmegjelenites;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NovenyFoprogram window = new NovenyFoprogram();
					window.frmNoveny.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NovenyFoprogram() {
		initialize();

		try {
			ZoldsegFajlkezeles.csatlakozas();
			novenyek = ZoldsegFajlkezeles.felhasznalokBeolvasasa();
			listaHozzarendeles();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frmNoveny, e.getMessage(), "Hiba!", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNoveny = new JFrame();
		frmNoveny.setTitle("Növények információi");
		frmNoveny.setBounds(100, 100, 727, 575);
		frmNoveny.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNoveny.getContentPane().setLayout(null);

		novenyek = new ArrayList<Noveny>();

		lstAdatmegjelenites = new JList();
		lstAdatmegjelenites.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Noveny kivalasztottNoveny = (Noveny) lstAdatmegjelenites.getSelectedValue();
				if (e.getClickCount() == 2) {
					if (lstAdatmegjelenites.getSelectedIndex() != -1) {

						String uzenet = kivalasztottNoveny.getNev() + " \npalántázás hónapja(i): "
								+ kivalasztottNoveny.getPalanta() + "\nkiültetés hónapja(i):  "
								+ kivalasztottNoveny.getKiultet() + "\nezekkel a növényekkel kompatibilis: "
								+ kivalasztottNoveny.getKompatibilis() + "\nezekkel a növényekkel nem kompatibilis: "
								+ kivalasztottNoveny.getEllentet();

						JOptionPane.showMessageDialog(frmNoveny, uzenet, "Infó", JOptionPane.INFORMATION_MESSAGE);
						lstAdatmegjelenites.clearSelection();
					}
				}

				/*
				 * if (e.getClickCount() == 1) {
				 * 
				 * txtId.setText(kivalasztottOra.getId().toString());
				 * txtMegnevezes.setText(kivalasztottOra.getMegnevezes());
				 * cmbTipus.setSelectedItem(kivalasztottOra.getTipus());
				 * SpinnerAr.setValue(kivalasztottOra.getAr());
				 * checkboxVizallo.setSelected(kivalasztottOra.isVizallo()); }
				 */
			}
		});
		lstAdatmegjelenites.setFont(new Font("Arial", Font.PLAIN, 15));
		lstAdatmegjelenites.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstAdatmegjelenites.setBounds(10, 11, 436, 514);
		frmNoveny.getContentPane().add(lstAdatmegjelenites);

		JButton btnTorles = new JButton("Növény törlése");
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
		btnTorles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnTorles.setBounds(478, 127, 173, 74);
		frmNoveny.getContentPane().add(btnTorles);

		JButton btnFelvitel = new JButton("Új növény hozzáadása");
		btnFelvitel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnFelvitel.setBounds(478, 212, 173, 74);
		frmNoveny.getContentPane().add(btnFelvitel);

		JButton btnModositas = new JButton("Növény módosítása");
		btnModositas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					modositas();
				} catch (SQLException e1) {

					JOptionPane.showMessageDialog(frmNoveny,
							"A növény módosítása DB hiba miatt sikertelen!" + e1.getMessage(), "Hiba",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModositas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnModositas.setBounds(478, 297, 173, 74);
		frmNoveny.getContentPane().add(btnModositas);
		
		JButton btnMentes = new JButton("Mentés");
		btnMentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ellenorzottMentes();
			}
		});
		btnMentes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnMentes.setBounds(478, 382, 173, 74);
		frmNoveny.getContentPane().add(btnMentes);

	}

	private void modositas() throws SQLException {
		// !!!! A combobox mindig egy object, amit kasztolni kell abba a tipusba, amire
		// szuksegunk van

		if (lstAdatmegjelenites.getSelectedIndex() != -1) {

			Noveny modositandoNoveny = (Noveny) lstAdatmegjelenites.getSelectedValue();
			int modNovenyIndex = lstAdatmegjelenites.getSelectedIndex();
			Noveny modositottNoveny = new Noveny(novenyek.get(1).getNev(), novenyek.get(2).getPalanta(),
					novenyek.get(3).getKiultet(), novenyek.get(4).getKompatibilis(), novenyek.get(5).getEllentet());
			modositottNoveny.setNev(modositandoNoveny.getNev());

			novenyek.set(novenyek.indexOf(modositandoNoveny), modositottNoveny);
			listModel.set(modNovenyIndex, modositottNoveny);

			ZoldsegFajlkezeles.novenyModositasa(modositottNoveny);
		}

		else {
			JOptionPane.showMessageDialog(frmNoveny, "Nincs kiválasztva elem!", "Figyelmeztetés",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void listaHozzarendeles() {

		listModel = new DefaultListModel<Noveny>();
		for (Noveny noveny : novenyek) {

			listModel.addElement(noveny);

		}
		lstAdatmegjelenites.setModel(listModel);
	}

	private void torles() throws SQLException {

		if (lstAdatmegjelenites.getSelectedIndex() != -1
				&& (JOptionPane.showOptionDialog(frmNoveny, "Biztosan kitörli?", "Törlés", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null) == JOptionPane.YES_OPTION)) {

			Noveny torlendoNoveny = (Noveny) lstAdatmegjelenites.getSelectedValue();

			ZoldsegFajlkezeles.novenyTorlese(torlendoNoveny);
			novenyek.remove(torlendoNoveny);
			listModel.remove(lstAdatmegjelenites.getSelectedIndex());

		}
	}

		private void ellenorzottMentes() {
			
			
			try {
				
				if (!novenyek.get(1).getNev().isBlank()) {
					
					ujNoveny = new Noveny(novenyek.get(1).getNev(), novenyek.get(2).getPalanta(), novenyek.get(3).getKiultet(),
							novenyek.get(4).getKompatibilis(), novenyek.get(5).getEllentet());
					
					listModel.addElement(ujNoveny);
					ZoldsegFajlkezeles.ujNovenyFelvitele(ujNoveny);
				}
				
				else {
					JOptionPane.showMessageDialog(frmNoveny, "Megnevezés megadása kötelező!"
							,"Figyelmeztetés",JOptionPane.WARNING_MESSAGE);
				}
				
			} catch (IllegalArgumentException e) {
				
				JOptionPane.showMessageDialog(frmNoveny, e.getMessage(),"Figyelmeztetés",JOptionPane.WARNING_MESSAGE);
				
			} 
			
			catch (SQLException e) {
				
				JOptionPane.showMessageDialog(frmNoveny, e.getMessage(),"Hiba",JOptionPane.ERROR_MESSAGE);
				ujNoveny = null;
			}
			
		}
	}

