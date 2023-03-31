package OraKeszletezese;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import OraKeszletezese.FajtaOsztaly.OraTipus;

//import com.mysql.jdbc.Statement;

public class ABKezeloOraszalon {
	
	private static Connection sqlKapcsolat;
	
	private static PreparedStatement sqlUtasitas;
	
	public static void kapcsolodas () throws SQLException {
		
		try {
			
			String connectionURL = "jdbc:mysql://localhost:3306/oraszalondb?autoReconnect=true&useSSL=false";
			
			sqlKapcsolat = (Connection)DriverManager.getConnection(connectionURL,"root","Vagyok1125");
			
		} catch (Exception e) {
			throw new SQLException("Kapcsolódás sikertelen!");
		}
	
	}
	
	public static void bontas() throws SQLException {
		
		try {
			
			sqlKapcsolat.close();
			
		} catch (Exception e) {
			throw new SQLException("Bontás sikertelen!");
		}
	}
	
	public static void ujOraFelvitele (Ora ujOra) throws SQLException {
		
		try {
			sqlUtasitas = sqlKapcsolat.prepareStatement("INSERT INTO oranyilvantartas (megnevezes,tipus,ar,vizallo) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			sqlUtasitas.setString(1, ujOra.getMegnevezes());
			sqlUtasitas.setString(2, String.valueOf(ujOra.getTipus()));
			sqlUtasitas.setInt(3, ujOra.getAr());
			sqlUtasitas.setBoolean(4, ujOra.isVizallo());
			
			sqlUtasitas.executeUpdate();
			
			ResultSet res = sqlUtasitas.getGeneratedKeys();
			if (res.next()) {
				ujOra.setId(res.getInt(1));
			}
			
			sqlUtasitas.clearParameters();
			
			/*sqlUtasitas = sqlKapcsolat.prepareStatement("SELECT * FROM oranyilvantartas WHERE ID=?");
			sqlUtasitas.setInt(1, ujOra.getId());
			
			sqlUtasitas.executeUpdate();*/
			
		} catch (Exception e) {
			
			throw new SQLException("Az óra felvitele DB hiba miatt sikertelen!");
		}
	}

	public static List<Ora> adatokBetoltese() throws SQLException {
		
		List<Ora> orak = new ArrayList<Ora>();
		
		try {
			sqlUtasitas = sqlKapcsolat.prepareStatement("SELECT * FROM oranyilvantartas");
			ResultSet result = sqlUtasitas.executeQuery();
			
			while (result.next()) {
				orak.add(new Ora(result.getString(1),OraTipus.konvertalas(result.getString(2).toString()),
						result.getInt(3),result.getBoolean(4),result.getInt(5)));
			}
			
			result.close();
			
			return orak;
			
		} catch (SQLException e) {
			
			throw new SQLException("Az órák beolvasása DB hiba miatt sikertelen!");
		}
		
		
	}

	public static void OraModositasa(Ora modositOra) throws SQLException {
		
		try {
			sqlUtasitas = sqlKapcsolat.prepareStatement("UPDATE oranyilvantartas SET megnevezes = ?, tipus = ?, ar = ?, vizallo = ? WHERE ID=?");
			sqlUtasitas.setString(1, modositOra.getMegnevezes());
			sqlUtasitas.setString(2, String.valueOf(modositOra.getTipus()));
			sqlUtasitas.setInt(3, modositOra.getAr());
			sqlUtasitas.setBoolean(4, modositOra.isVizallo());
			sqlUtasitas.setInt(5, modositOra.getId());
			
			/*
			 * sqlUtasitas.setString(1, ujOra.getMegnevezes());
			sqlUtasitas.setString(2, String.valueOf(ujOra.getTipus()));
			sqlUtasitas.setInt(3, ujOra.getAr());
			sqlUtasitas.setBoolean(4, ujOra.isVizallo());
			 */
			
			sqlUtasitas.executeUpdate();
			sqlUtasitas.clearParameters();
			
			
		} catch (Exception e) {
			
			throw new SQLException("Az óra módosítása DB hiba miatt sikertelen!");
		}
		
	}

	public static void oraTorlese(Ora torlendoOra) throws SQLException {
		
		try {
			sqlUtasitas = sqlKapcsolat.prepareStatement("DELETE FROM oranyilvantartas WHERE ID=?");
			sqlUtasitas.setInt(1, torlendoOra.getId());
			
			sqlUtasitas.executeUpdate();
			sqlUtasitas.clearParameters();
			
		} catch (SQLException e) {
			
			throw new SQLException("Az óra törlése nem lehetséges DB hiba miatt!");
			
		}
		
	}
	
	
	

}
