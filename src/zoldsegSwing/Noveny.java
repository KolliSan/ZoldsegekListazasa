package zoldsegSwing;

public class Noveny {

	private String nev;
	private String palanta;
	private String kiultet;
	private String kompatibilis;
	private String ellentet;
	
	
	public Noveny(String nev, String palanta, String kiultet, String kompatibilis, String ellentet) {
		super();
		this.nev = nev;
		this.palanta = palanta;
		this.kiultet = kiultet;
		this.kompatibilis = kompatibilis;
		this.ellentet = ellentet;
	}


	public String getNev() {
		return nev;
	}


	public String getPalanta() {
		return palanta;
	}


	public String getKiultet() {
		return kiultet;
	}


	public String getKompatibilis() {
		return kompatibilis;
	}


	public String getEllentet() {
		return ellentet;
	}


	public void setNev(String nev) {
		this.nev = nev;
	}


	public void setPalanta(String palanta) {
		this.palanta = palanta;
	}


	public void setKiultet(String kiultet) {
		this.kiultet = kiultet;
	}


	public void setKompatibilis(String kompatibilis) {
		this.kompatibilis = kompatibilis;
	}


	public void setEllentet(String ellentet) {
		this.ellentet = ellentet;
	}


	@Override
	public String toString() {
		return "Növény neve: " + nev;
				
				/*+ ", palántázás hónapja(i): " + palanta + ", kiültetés hónapja(i): " 
	+ kiultet + ", ezekkel a növényekkel kompatibilis: " + kompatibilis
				+ ", ezzel a növényekkel nem kompatibilis: " + ellentet;*/
	}
	
	
	
}
