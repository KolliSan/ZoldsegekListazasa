package zoldsegSwing;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NovenyFoprogram {

	private Noveny ujNoveny;
	private Noveny modositNoveny;
	List<Noveny> novenyek;
	private JFrame frmNoveny;
	DefaultListModel<Noveny> listModel;
	private JList lstAdatmegjelenites;
	private JTextField txtNev;
	private JTextField txtPalantazas;
	private JTextField txtKiultetes;
	private JTextField txtKompatibilis;
	private JTextField txtNemKompatibilis;

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
		
		frmNoveny.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				Object[] opciok = { "Igen", "Nem" };
				if (JOptionPane.showOptionDialog(frmNoveny, "Biztosan kilép?", "Kilépés", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, opciok, opciok[1]) == JOptionPane.YES_OPTION) {
					
				
					System.exit(0);
				}

			}
		});
		
		frmNoveny.setTitle("Növények információi");
		frmNoveny.setBounds(100, 100, 842, 575);
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
						//lstAdatmegjelenites.clearSelection();
					}
				}

				//egy kattintással a listaban kivalasztott noveny adatait beallitjuk a szovegdobozokba (modositas miatt):
				 if (e.getClickCount() == 1) {
					 
					 txtNev.setText(kivalasztottNoveny.getNev());
					 txtNev.setEnabled(false);
					 txtPalantazas.setText(kivalasztottNoveny.getPalanta());
					 txtKiultetes.setText(kivalasztottNoveny.getKiultet());
					 txtKompatibilis.setText(kivalasztottNoveny.getKompatibilis());
					 txtNemKompatibilis.setText(kivalasztottNoveny.getEllentet());
					 
				 }
				 
				 
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

		JButton btnKilepes = new JButton("Kilépés");
		btnKilepes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frmNoveny.dispatchEvent(new WindowEvent(frmNoveny, WindowEvent.WINDOW_CLOSING));
			}
		});
		btnKilepes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnKilepes.setBounds(478, 382, 173, 74);
		frmNoveny.getContentPane().add(btnKilepes);

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
		
		JButton btnUjNoveny = new JButton("Új növény");
		btnUjNoveny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ujNoveny();
			}
		});
		btnUjNoveny.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUjNoveny.setBounds(478, 212, 173, 74);
		frmNoveny.getContentPane().add(btnUjNoveny);
		
		txtNev = new JTextField();
		txtNev.setBounds(661, 168, 144, 20);
		frmNoveny.getContentPane().add(txtNev);
		txtNev.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Név");
		lblNewLabel.setBounds(661, 143, 49, 14);
		frmNoveny.getContentPane().add(lblNewLabel);
		
		JLabel lblPalntzs = new JLabel("Palántázás");
		lblPalntzs.setBounds(661, 199, 122, 14);
		frmNoveny.getContentPane().add(lblPalntzs);
		
		txtPalantazas = new JTextField();
		txtPalantazas.setColumns(10);
		txtPalantazas.setBounds(661, 224, 144, 20);
		frmNoveny.getContentPane().add(txtPalantazas);
		
		JLabel lblKiltets = new JLabel("Kiültetés");
		lblKiltets.setBounds(661, 255, 122, 14);
		frmNoveny.getContentPane().add(lblKiltets);
		
		txtKiultetes = new JTextField();
		txtKiultetes.setColumns(10);
		txtKiultetes.setBounds(661, 278, 144, 20);
		frmNoveny.getContentPane().add(txtKiultetes);
		
		JLabel lblKompatibilis = new JLabel("Kompatibilis");
		lblKompatibilis.setBounds(661, 314, 122, 14);
		frmNoveny.getContentPane().add(lblKompatibilis);
		
		txtKompatibilis = new JTextField();
		txtKompatibilis.setColumns(10);
		txtKompatibilis.setBounds(661, 339, 144, 20);
		frmNoveny.getContentPane().add(txtKompatibilis);
		
		JLabel lblNemKompatibilis = new JLabel("Nem kompatibilis");
		lblNemKompatibilis.setBounds(661, 370, 122, 14);
		frmNoveny.getContentPane().add(lblNemKompatibilis);
		
		txtNemKompatibilis = new JTextField();
		txtNemKompatibilis.setColumns(10);
		txtNemKompatibilis.setBounds(661, 393, 144, 20);
		frmNoveny.getContentPane().add(txtNemKompatibilis);

	}

	private void modositas() throws SQLException {
		// !!!! A combobox mindig egy object, amit kasztolni kell abba a tipusba, amire
		// szuksegunk van

		if (lstAdatmegjelenites.getSelectedIndex() != -1) {

			Noveny modositandoNoveny = (Noveny) lstAdatmegjelenites.getSelectedValue();
			int modNovenyIndex = lstAdatmegjelenites.getSelectedIndex();
			//Noveny modositottNoveny = new Noveny(novenyek.get(1).getNev(), novenyek.get(2).getPalanta(),
				//	novenyek.get(3).getKiultet(), novenyek.get(4).getKompatibilis(), novenyek.get(5).getEllentet());
			Noveny modositottNoveny = new Noveny(txtNev.getText(), txtPalantazas.getText(), 
					txtKiultetes.getText(), txtKompatibilis.getText(), txtNemKompatibilis.getText());
			modositottNoveny.setNev(modositandoNoveny.getNev());//szovegdobozokbol atveve az adat

			ZoldsegFajlkezeles.novenyModositasa(modositottNoveny);//ide atteve
			novenyek.set(novenyek.indexOf(modositandoNoveny), modositottNoveny);
			listModel.set(modNovenyIndex, modositottNoveny);
			txtNev.setText("");
			txtNev.setEnabled(true);
			txtPalantazas.setText("");
			txtKiultetes.setText("");
			txtKompatibilis.setText("");
			txtNemKompatibilis.setText("");

			/*int kivalasztottIndex = lstAdatmegjelenites.getSelectedIndex();
			if (kivalasztottIndex!=-1) {
				listModel.remove(kivalasztottIndex);
			}*/
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
			txtNev.setText("");
			txtNev.setEnabled(true);
			txtPalantazas.setText("");
			txtKiultetes.setText("");
			txtKompatibilis.setText("");
			txtNemKompatibilis.setText("");

		}
	}

		private void ujNoveny() {
			
			
			try {
				
				//if (!novenyek.get(1).getNev().isBlank()) {
				if (txtNev.isEnabled()==true) {
					/*ujNoveny = new Noveny(novenyek.get(1).getNev(), novenyek.get(2).getPalanta(), novenyek.get(3).getKiultet(),
							novenyek.get(4).getKompatibilis(), novenyek.get(5).getEllentet());*/
					
					ujNoveny = new Noveny(txtNev.getText(), txtPalantazas.getText(), 
							txtKiultetes.getText(), txtKompatibilis.getText(), txtNemKompatibilis.getText());
					
					ZoldsegFajlkezeles.ujNovenyFelvitele(ujNoveny);
					novenyek.add(ujNoveny);//ezt is beletettem
					listModel.addElement(ujNoveny);
					txtNev.setText("");
					txtPalantazas.setText("");
					txtKiultetes.setText("");
					txtKompatibilis.setText("");
					txtNemKompatibilis.setText("");
					
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
