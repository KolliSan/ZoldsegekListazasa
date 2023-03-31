package OraKeszletezese;

public class FajtaOsztaly {
	
	public enum OraTipus {
		
		KARORA ("karóra"), FALIORA ("falióra"), EBRESZTOORA ("ébresztőóra"), STOPPERORA ("stopperóra");
		
		public final String tipusMeghatarozas;
		
		OraTipus (String tipusMeghatarozas) {
			
			this.tipusMeghatarozas = tipusMeghatarozas;
			
		}
		
		public String toString() {//ezt manualisan kell, ettol lesz ekezetes a legordulo lista
			return tipusMeghatarozas;
		}
		
		public static OraTipus konvertalas (String tipusMeghatarozas) {
			
			for (OraTipus item : values()) {
				
				if (item.tipusMeghatarozas.equals(tipusMeghatarozas)) {
					return item;
				}
				
			}
			
			return null;
		}
	
}

}
