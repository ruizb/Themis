package ui.affaire;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JOptionPane;

import business.affaire.Affaire;
import business.affaire.Scelle;
import business.affaire.Affaire;

public class PanelModifScelle extends PanelAjouterScelle implements ActionListener{

	private Scelle scelle;
	
	PanelModifScelle(ScelleFenetre fen, Scelle sc){
		super(fen);
		scelle = sc;
		
		HashMap<String, Object> hashMapScelle = this.fenetre.getFacadeScelle().consulterScelle(scelle);
		String lieu = (String) hashMapScelle.get("lieu_recup");
		super.inputLieuRecup.setText(lieu);
		
		Integer numPV = (Integer) hashMapScelle.get("num_proces");
		super.inputNumPV.setValue(numPV);
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date dateRecup = format.parse((String) hashMapScelle.get("date_recup"));
			super.inputDateRecup.setValue(dateRecup);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String commentaire = (String) hashMapScelle.get("comment");
		super.inputCommentaire.setText(commentaire);
		
		Affaire affaire = (Affaire) hashMapScelle.get("id_affaire");
		for(int i = 0; i < listeSelectionAffaire.getModel().getSize(); i++) {
			Affaire e = (Affaire) listeSelectionAffaire.getModel().getElementAt(i);
			if(e.equals(affaire)) {
				listeSelectionAffaire.setSelectedValue(e, true);
				break;
			}
		}
		
		Scelle scelle = (Scelle) hashMapScelle.get("id_scelle");
		for(int i = 0; i < listeSelectionScelle.getModel().getSize(); i++) {
			Scelle s = (Scelle) listeSelectionScelle.getModel().getElementAt(i);
			if(s.equals(scelle)) {
				listeSelectionAffaire.setSelectedValue(s, true);
				break;
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == boutonAnnuler)
		{			
			this.retourFenetre();
		}
		else if(e.getSource() == boutonValider){
			String lieuRecup = inputLieuRecup.getText(); Number numPV = (Number) inputNumPV.getValue(); Date dateRecup = (Date) inputDateRecup.getValue(); 
			String commentaire = inputCommentaire.getText(); Affaire affaire = (Affaire) listeSelectionAffaire.getSelectedValue();
			Scelle scelle;
			if(listeSelectionScelle.getSelectedValue() instanceof String){
				scelle = null;
			}else{
				scelle = (Scelle) listeSelectionScelle.getSelectedValue();
			}
			if(lieuRecup.equals("") || numPV == null || dateRecup == null || lieuRecup == null || affaire == null){
				JOptionPane.showMessageDialog(null, "Vous devez remplir tous les champs !", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				try {
					this.fenetre.getFacadeScelle().modifierScelle(scelle, numPV.intValue(), dateRecup, lieuRecup, commentaire, affaire, scelle);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null,"Modification reussie");
				this.retourFenetre();
			}
		}
	}
}
