package OraKeszletezese;

import OraKeszletezese.FajtaOsztaly.OraTipus;

public class Ora {
	
	
	private String megnevezes;
	private OraTipus tipus;
	private int ar;
	private boolean vizallo;
	private Integer id;
	
	
	public Ora(String megnevezes, OraTipus tipus, int ar, boolean vizallo) {
		this.megnevezes = megnevezes;
		this.tipus = tipus;
		this.ar = ar;
		this.vizallo = vizallo;
	}
	
	public Ora(String megnevezes, OraTipus tipus, int ar, boolean vizallo, Integer id) {
		
		this.megnevezes = megnevezes;
		this.tipus = tipus;
		this.ar = ar;
		this.vizallo = vizallo;
		this.id = id;
	}
	
	
	public Ora(String megnevezes) {
		this.megnevezes = megnevezes;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		if (this.id==null) {
			this.id = id;
		}
		else {
			throw new IllegalStateException("Az id csak 1x kaphat értéket!");
		}
		
	}
	
	public String getMegnevezes() {
		return megnevezes;
	}


	

	public void setMegnevezes(String megnevezes) {
		this.megnevezes = megnevezes;
	}


	public OraTipus getTipus() {
		return tipus;
	}


	public void setTipus(OraTipus tipus) {
		this.tipus = tipus;
	}


	public int getAr() {
		return ar;
	}


	public void setAr(int ar) {
		this.ar = ar;
	}


	public boolean isVizallo() {
		return vizallo;
	}


	public void setVizallo(boolean vizallo) {
		this.vizallo = vizallo;
	}

	public String toString() {
		return megnevezes;
	}
	
}
