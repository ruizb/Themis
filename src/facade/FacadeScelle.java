/**
 * @author Benoit Ruiz
 */

package facade;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import persistence.DBFactory;
import persistence.Factory;
import business.affaire.Affaire;
import business.affaire.Scelle;
import business.affaire.ScelleManager;
import business.affaire.ScelleManagerDB;

public class FacadeScelle extends FacadeAbstraite {
	
	// ATTRIBUTES
	
	/**
	 * Manager to load entitites
	 */
	private ScelleManager sMng;
	
	// CONSTRUCTOR
	
	public FacadeScelle() {}
	
	// METHODS
	
	/**
	 * Add a new Scelle
	 * @param: Scelle attributes
	 */
	public Scelle ajouterScelle(int numProces, Date dateRecup, String lieuRecup, String comment, Affaire idAffaire, Scelle idScelle) {
		
		Factory fac = DBFactory.getInstance();
		Scelle newScelle = fac.createScelle();
		
		newScelle.setNumProces(numProces);
		newScelle.setDateRecup(dateRecup);
		newScelle.setLieuRecup(lieuRecup);
		newScelle.setComment(comment);
		newScelle.setIdAffaire(idAffaire);
		newScelle.setIdScelle(idScelle);
		
		newScelle.create();
		
		return newScelle;
		
	}
	
	/**
	 * Edit a Scelle
	 * @param: Scelle attributes
	 */
	public void modifierScelle(Scelle scelle, int numProces, Date dateRecup, String lieuRecup, String comment, Affaire idAffaire, Scelle idScelle) {
		
		scelle.setNumProces(numProces);
		scelle.setDateRecup(dateRecup);
		scelle.setLieuRecup(lieuRecup);
		scelle.setComment(comment);
		scelle.setIdAffaire(idAffaire);
		scelle.setIdScelle(idScelle);
		
		scelle.update();
		
	}
	
	/**
	 * Delete a Scelle
	 * @param: scelle: Entity to delete
	 */
	public void supprimerScelle(Scelle scelle) throws Exception {
		scelle.delete();
	}
	
	/**
	 * Get HashMap containing data of a specific Scelle
	 * @param: scelle: Entity to consult
	 */
	public HashMap<String, Object> consulterScelle(Scelle scelle) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		result.put("id", scelle.getId());
		result.put("num_proces", scelle.getNumProces());
		result.put("date_recup", df.format(scelle.getDateRecup()));
		result.put("lieu_recup", scelle.getLieuRecup());
		result.put("comment", scelle.getComment());
		result.put("id_affaire", scelle.getIdAffaire());
		result.put("id_scelle", scelle.getIdScelle());
		
		return result;
	}

	/**
	 * Get ArrayList<String> containing a toString for each Scelle
	 * 
	 * @param: listeScelles: ArrayList to read
	 */
	public ArrayList<String> getApercu(ArrayList<Scelle> listeScelles) {
		
		ArrayList<String> listeApercus = new ArrayList<String>();
		Iterator<Scelle> it = listeScelles.iterator();
		
		while(it.hasNext()) {
			listeApercus.add(it.next().toString());
		}
		
		return listeApercus;
		
	}
	
	/**
	 * Load an ArrayList of Scelle using a filter
	 * 
	 * @param: filter: condition to respect in the query
	 */
	public ArrayList<Scelle> chargerScelle(HashMap<String, String> filter)
	{
		sMng = new ScelleManagerDB();
		sMng.loadScelle(filter);
		return sMng.getListeScelles();
	}
	
}
