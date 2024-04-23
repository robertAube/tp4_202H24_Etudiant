package tableur;

import dataCenter.*;

import javax.swing.*;
import java.io.File;
import java.util.Set;

/**
 * @author Robert Aubé
 * @version 1.0
 */
public class CtrlDataCenter {
    /**
     * Structure mémorisant les valeurs des cellules
     */
    private IDataCenter dataCenter;
    private String nomFichier = null;

    boolean dataModifie = false; //Le fichier est considéré comme pas encore modifié au départ

    /**
     * Nombre de lignes dans la feuille
     */
    private int nbLigne;
    /**
     * Nombre de colonnes dans la feuille
     */
    private int nbColonne;

    public CtrlDataCenter(int nbLigne, int nbColonne) {
        this.nbLigne = nbLigne;
        this.nbColonne = nbColonne;

        dataCenter = new TabDataCenter(nbLigne, nbColonne); //structure par défaut
    }

    public int getNbLigne() {
        return nbLigne;
    }

    public int getNbColonne() {
        return nbColonne;
    }

    public void performMenu(String itemMenu) {
        switch (itemMenu) {
            case "Ouvrir...":
                menuOuvrir();
                break;
            case "Enregistrer":
                menuEnregistrer();
                break;
            case "Enregistrer sous...":
                menuEnregistrerSous();
                break;
            case "Fermer":
                menuFermer();
                break;

            case "Tableau":
                dataCenter = new TabDataCenter(nbLigne, nbColonne);
                break;
            //List
            case "ArrayList":
                //TODO case "ArrayList"
                break;
            case "Vector":
                //TODO case "Vector"
                break;
            case "LinkedList":
                //TODO case "LinkedList"
                break;
            //Set
            case "HashSet":
                //TODO case "HashSet"
                break;
            case "TreeSet":
                //TODO case "TreeSet"
                break;
            //Map
            case "HashMap":
                //TODO case "HashMap"
                break;
            case "TreeMap":
                //TODO case "TreeMap"
                break;
            default:
                throw new RuntimeException("Item au menu non défini : " + itemMenu);
        }
    }

    private void menuEnregistrer() {
        if (nomFichier == null) { //si le nom de fichier n'est pas défini
            menuEnregistrerSous();
        } else {
            //TODO Enregistrer le DataCenter
        }
    }

    private void menuEnregistrerSous() {
        JFileChooser choix = new JFileChooser();
        // Le répertoire source du JFileChooser est le répertoire du programme
        choix.setCurrentDirectory(new File("."));
        // Le bouton pour valider l’enregistrement portera la mention ENREGISTRER
        String approve = new String("ENREGISTRER");
        int resultatEnregistrer = choix.showDialog(choix, approve);
        if (resultatEnregistrer ==
                JFileChooser.APPROVE_OPTION) // Si l’utilisateur clique sur le bouton ENREGISTRER
        {
            nomFichier = choix.getSelectedFile().toString();
            enregisterFichier();
        }
    }

    /**
     * Enregistre le Datacenter dans le fichier identifié dans la variable nomFichier
     */
    private void enregisterFichier() {
        //TODO Enregistrer le DataCenter sous nomFichier
    }

    private void menuOuvrir() {
        JFileChooser filechoose = new JFileChooser();
        // Créer un JFileChooser
        filechoose.setCurrentDirectory(new File("."));
        // Le répertoire source du JFileChooser est le répertoire d’où est lancé notre programme
        String approve = new String("OUVRIR");
        // Le bouton pour valider l’enregistrement portera la mention OUVRIR
        int resultatOuvrir = filechoose.showDialog(filechoose, approve); // Pour afficher le JFileChooser…

        // Si l’utilisateur clique sur le bouton OUVRIR
        if (resultatOuvrir == filechoose.APPROVE_OPTION) {
            nomFichier = filechoose.getSelectedFile().toString();
            lireDataCenter("");
        }
    }

    private void menuFermer() {
        dataCenter.removeAllData();
        nomFichier = null;
    }

    /**
     * Permet de lire le fichier jSon et de le récupérer
     *
     * @param nomFichier
     */
    private void lireDataCenter(String nomFichier) {
        //TODO Récupérer le DataCenter
    }

    public Set<CellKey> getListCellARafraichir() {
        return dataCenter.getSetCellARafraichir();
    }

    public void set(int ligne, int colonne, String formuleCel) {
        dataCenter.set(ligne, colonne, formuleCel);
    }

    public String getFormula(int ligne, int colonne) {
        return dataCenter.getFormula(ligne, colonne);
    }

    public String getValue(int ligne, int colonne) {
        return dataCenter.getValue(ligne, colonne);
    }

    @Override
    public String toString() {
        return dataCenter.toString();
    }

    /**
     * Retourne le texte affiché dans le <b>À propos</b>
     *
     * @return le texte à afficher dans le <b>À propos</b>
     */
    //TODO Mettre à jour avec votre groupe et le nom des équipiers
    public String getFaitPar() {
        String strGroupe = "Groupe X";
        String strEquipe = "Robert Aub\u00E9"; //Pour voir les accents consulter http://www.kreativekorp.com/charset/encoding/CP037/ cliquez dur la lettre et prendre encodage Java
        return strGroupe + '\n' + strEquipe;
    }
}
