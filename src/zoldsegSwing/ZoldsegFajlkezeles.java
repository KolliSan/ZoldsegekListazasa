package zoldsegSwing;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.sql.Connection;

public class ZoldsegFajlkezeles {

	public static Connection kapcsolat;
	private static PreparedStatement sqlUtasitas;

	public static void csatlakozas() throws SQLException {

		try {

			String connectionURL = "jdbc:mysql://localhost:3306/zoldsegek_db?&useSSL=false";

			kapcsolat = (Connection) DriverManager.getConnection(connectionURL, "root", "kólasprite");

		} catch (Exception e) {

			throw new SQLException("Sikertelen kapcsolat " + e.getMessage());

		}
		// return kapcsolat;

	}

	public static void kapcsolatBontas() throws SQLException {
		try {
			kapcsolat.close();
		} catch (Exception e) {
			throw new SQLException("A kapcsolat bontása sikertelen" + e.getMessage());
		}

	}

	public static List<Noveny> felhasznalokBeolvasasa() throws SQLException {

		try {
			List<Noveny> novenyek = new ArrayList<Noveny>();

			sqlUtasitas = kapcsolat.prepareStatement("SELECT * FROM novenyek");

			ResultSet res = sqlUtasitas.executeQuery();
			while (res.next()) {
				novenyek.add(new Noveny(res.getString("nev"), res.getString("palantazas"), res.getString("kiultetes"),
						res.getString("kompatibilis"), res.getString("nemkompatibilis")));
			}

			res.close();

			return novenyek;

		} catch (Exception e) {
			throw new SQLException("A felhasználó beolvasása DB hiba miatt sikertelen, mert: " + e.getMessage());
		}
	}

	public static void ujNovenyFelvitele(Noveny ujNoveny) throws SQLException {

		try {
			sqlUtasitas = kapcsolat.prepareStatement(
					"INSERT INTO novenyek (nev,palantazas,kiultetes,kompatibilis,nemkompatibilis) VALUES (?,?,?,?,?)");
			sqlUtasitas.setString(1, ujNoveny.getNev());
			sqlUtasitas.setString(2, ujNoveny.getPalanta());
			sqlUtasitas.setString(3, ujNoveny.getKiultet());
			sqlUtasitas.setString(4, ujNoveny.getKompatibilis());
			sqlUtasitas.setString(5, ujNoveny.getEllentet());

			sqlUtasitas.executeUpdate();
			

		} catch (Exception e) {

			throw new SQLException("A növény felvitele DB hiba miatt sikertelen!" + e.getMessage());
		}
	}

	public static List<Noveny> adatokBetoltese() throws SQLException {

		List<Noveny> novenyek = new ArrayList<Noveny>();

		try {
			sqlUtasitas = kapcsolat.prepareStatement("SELECT * FROM novenyek");
			ResultSet result = sqlUtasitas.executeQuery();

			while (result.next()) {
				novenyek.add(new Noveny(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5)));
			}

			result.close();

			return novenyek;

		} catch (SQLException e) {

			throw new SQLException("Az növények beolvasása DB hiba miatt sikertelen!" + e.getMessage());
		}

	}

	public static void novenyModositasa(Noveny modositNoveny) throws SQLException {

		try {
			sqlUtasitas = kapcsolat.prepareStatement(
					"UPDATE novenyek SET palantazas = ?, kiultetes = ?, kompatibilis = ?, nemkompatibilis = ? WHERE nev = ?");
			
			sqlUtasitas.setString(1, modositNoveny.getPalanta());
			sqlUtasitas.setString(2, modositNoveny.getKiultet());
			sqlUtasitas.setString(3, modositNoveny.getKompatibilis());
			sqlUtasitas.setString(4, modositNoveny.getEllentet());
			sqlUtasitas.setString(5, modositNoveny.getNev());
			
	
			sqlUtasitas.executeUpdate();
			sqlUtasitas.clearParameters();

		} catch (Exception e) {

			throw new SQLException("A növény módosítása DB hiba miatt sikertelen!" + e.getMessage());
		}

	}

	public static void novenyTorlese(Noveny torlendoNoveny) throws SQLException {

		try {
			sqlUtasitas = kapcsolat.prepareStatement("DELETE FROM novenyek WHERE nev=?");
			sqlUtasitas.setString(1, torlendoNoveny.getNev());

			sqlUtasitas.executeUpdate();
			sqlUtasitas.clearParameters();

		} catch (SQLException e) {

			throw new SQLException("A növény törlése nem lehetséges DB hiba miatt!" + e.getMessage());

		}

	}

}
