package business;

import java.util.HashMap;

import persistence.SQLManager;

public class TitreEnqueteurDB extends TitreEnqueteur {

	/**
	 * Correspondence with the table in the database
	 */
	public static final String table = "titre_enqueteur";
	
	@Override
	public int create() {
		SQLManager sql = SQLManager.getConnection();
		
		// Prepare the query
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("libelle", this.libelle);
		
		// Run the query
		sql.insert(table,map);
		
		// Get the id of this new CorpsEnqueteur
		this.id = sql.getLastID();
		return this.id;
	}

	@Override
	public void update() {
		SQLManager sqlManager = SQLManager.getConnection();
		
		// Prepare the query
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("libelle", this.libelle);
		String where = "id = "+this.id;
		
		// Run the query
		sqlManager.update(table, map, where);
	}

	@Override
	public void delete() {
		SQLManager sqlManager = SQLManager.getConnection();
		
		// Prepare the query
		String where = "id = "+this.id;
		
		// Run the query
		sqlManager.delete(table, where);
	}

}
