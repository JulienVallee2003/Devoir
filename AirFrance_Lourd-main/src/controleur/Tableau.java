package controleur;

import javax.swing.table.AbstractTableModel;

public class Tableau extends AbstractTableModel{
	private Object donnees [] [];
	private String entetes [];
	
	public Tableau(Object[][] donnees, String[] entetes) {
		super();
		this.donnees = donnees;
		this.entetes = entetes;
	}

	@Override
	public int getRowCount() {
		
		return donnees.length;
	}

	@Override
	public int getColumnCount() {
		
		return entetes.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		return donnees[rowIndex][columnIndex];
	}

	@Override
	public String getColumnName(int column) {
	
		return entetes[column];
	}
	
	public void setDonnees(Object matrice[][]) {
		this.donnees = matrice;
		//actualiser l'affichage
		this.fireTableDataChanged();
	}
	
	
}
