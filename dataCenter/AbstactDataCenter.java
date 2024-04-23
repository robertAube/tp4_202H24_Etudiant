package dataCenter;

import utilTableur.Calculeur;

import java.util.HashSet;
import java.util.Set;


public abstract class AbstactDataCenter implements IDataCenter {
    /**
     * Correspond au nombre d'accès en lecture à la structure de données.
     */
    private int nbGet = 0;

    /**
     * Correspond au nombre de modifications dans la structure de données.
     */
    private int nbSet = 0;

    /**
     * Correspond au nom de la structure de données courante.
     * Exemple : Tableau
     */
    private String nomStructureCourante = "Tableau";

    /**
     * C'est un set de toutes les cellules non-vides (ou différentes de "")
     * dans la structure de données.
     * La fenêtre du tableur en a de besoin pour procéder au rafraîchissement.
     * Ce set doit donc être toujours maintenue à jour.
     */
    private Set<CellKey> setCellARafraichir;

    public AbstactDataCenter() {
        setCellARafraichir = new HashSet<>();
    }

    /**
     * Mets à jour la formule sans la calculer dans la structure de données.
     * Exemple : dans les collections =1+1 est sauvegardé plutôt que 2
     * Si l'élément à ligne, colonne <b>n'existe pas</b>, il l'ajoute
     * Si l'élément à ligne, colonne <b>existe</b> déjà, il le remplace
     * Si newFormula est une chaine vide, on considère que c'est une absence de données et
     * la formule est alors supprimée dans la structure.
     * (0,0) est la coordonnée de la cellule A1.
     *
     * @param ligne      de la cellule
     * @param col        de la cellule
     * @param newFormula la nouvelle formule ou valeur qui sera placée dans la cellule à la (ligne, colonne)
     * @return la nouvelle valeur qui sera placée dans la cellule à la (ligne, colonne)
     */
    @Override
    public void set(int ligne, int col, String newFormula) {
        //TODO public void set(int ligne, int col, String newCellValue)
    }

    /**
     * Ajoute ou remplace la formule de la cellule à la ligne et à la colonne indiquée
     * dans la structure de données.
     * Si la cellule est vide ("") l'information est ajouté.
     * Si la cellule n'est pas vide (ou différent de "") la formule est remplacée
     * @param ligne
     * @param colonne
     * @param newFormula
     */
    public abstract void addOrReplace(int ligne, int colonne, String newFormula);

    /**
     * Retirer la formule à la ligne et à la colonne indiquée.
     * @param ligne
     * @param colonne
     */
    public abstract void remove(int ligne, int colonne);


    /**
     * Récupère la valeur de la cellule à (ligne, colonne) dans la collection.
     * Si c'est une formule, elle sera interprétée.
     * Une formule commence par un "="
     * Une erreur dans une formule retourne <b>"#Erreur"</b>
     *
     * @param ligne de la cellule
     * @param colonne de la cellule
     * @return la valeur de la cellule à (ligne, colonne)
     */
    @Override
    public String getValue(int ligne, int colonne) {
        String str = "";
        //TODO public String getValue(int ligne, int colonne)
        return str;
    }

    /**
     * Accesseur de setCellARafraichir
     *
     * @return setCellARafraichir
     */
    @Override
    public Set<CellKey> getSetCellARafraichir() {
        return setCellARafraichir;
    }

    /**
     * Si la nouvelle valeur est ajoutée à ligne, colonne,
     * on l'ajoute la clé ligne, colonne à <b>setCellARafraichir</b> (seulement, s'il est n'est pas présente)
     *
     * Si on efface une cellule (une cellule est effacée si on y place une chaine vide),
     * on efface ligne, colonne de <b>setCellARafraichir</b>
     *
     * Si une valeur à ligne, colonne est modifiée à la structure de données du tableur,
     * celle-ci devrait déjà être présente et, on ne l'ajoute pas à <b>listCellARafraichir</b>
     * @param ligne
     * @param colonne
     * @param newCellValue
     */
    private void setCellARafraichir(int ligne, int colonne, String newCellValue) {
        //TODO private void setCellARafraichir(int ligne, int colonne, String newCellValue)
    }

    /**
     * Vide le set "Cellules à rafraîchir"
     */
    private void setCellARafraichirRemoveAll() {
        //TODO private void setCellARafraichirRemoveAll()
    }

    /**
     * Traiter une formule
     * @param formule la formule à remplacer
     * @return la valeur du calcul.
     */
    private double traiterFormule(String formule) {
        formule = remplacerLesReferences(formule);
        return Calculeur.calculer(formule.substring(1));
    }

    /**
     * Remplace toutes les références (exemple A1) dans la formule par la valeur calculée grâce à getValue().
     * Si une référence est vide, on considère que la valeur de la cellule est 0.
     * Exemple :
     * si en entrée il y a la formule "=a1+b1" et a1 vaut "1" et b1 vaut "=a1+1",
     * cette méthode retourne "=1+2"
     * @param formule avec des références
     * @return la formule remplacée par les valeurs calculées.
     */
    private String remplacerLesReferences(String formule) {
        //TODO private String remplacerLesReferences(String formule)
        return formule;
    }

    /**
     * Retourne true si la valeur à ligne, colonne est vide.
     * On considère qu'une cellule est vide, si getVelue(ligne, colonne) retourne un chaine vide "".
     * @param ligne
     * @param colonne
     * @return true si la valeur à ligne, colonne est vide.
     */
    @Override
    public boolean estVide(int ligne, int colonne) {
        return getValue(ligne, colonne) == "";
    }

    /**
     * Retourne une chaine de la forme (nbElement=1200, nbGet=400, nbSet=10, nbCellARafraichir=2)
     * @return Retourne une chaine de la forme (nbElement=1200, nbGet=400, nbSet=10, nbCellARafraichir=2)
     */
    @Override
    public String toString() {
        return "(nbElement=" + getSize() +
                ", nbGet=" + nbGet +
                ", nbSet=" + nbSet +
                ", nbCellARafraichir=" + setCellARafraichir.size() +
                ')';
    }
}
