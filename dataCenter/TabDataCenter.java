package dataCenter;

/**
 * Traite la sauvegarde de données d'un tableau
 */
public class TabDataCenter extends AbstactDataCenter {
    private String[][] table;

    public TabDataCenter(int nbLigne, int nbColonne) {
        //TODO public TabDataCenter(int nbLigne, int nbColonne)
    }


    /**
     * Ajoute ou remplace la formule de la cellule à la ligne et à la colonne indiquée.
     * Si la cellule est vide ("") l'information est ajouté.
     * Si la cellule n'est pas vide (ou différent de "") la formule est remplacée
     *
     * @param ligne
     * @param colonne
     * @param newFormula
     */
    @Override
    public void addOrReplace(int ligne, int colonne, String newFormula) {
        //TODO public void addOrReplace(int ligne, int colonne, String newFormula)
    }

    /**
     * Retirer la formule à la ligne et à la colonne indiquée.
     *
     * @param ligne
     * @param colonne
     */
    @Override
    public void remove(int ligne, int colonne) {
        //TODO public void remove(int ligne, int colonne)
    }


    /**
     * Récupère la formule de la cellule à (ligne, colonne) dans la collection.
     * Si c'est une formule, elle NE sera PAS interprétée.
     * Si l'élément n'est pas trouvé, on retourne une chaine vide. ("")
     * (0,0) est la coordonnée de la cellule A1.
     *
     * @param ligne de la cellule
     * @param col   de la cellule
     * @return la valeur de la cellule à (ligne, colonne). Le contenu N’est PAS interprété. Ex =A1+1
     */
    @Override
    public String getFormula(int ligne, int col) {
        //TODO public String getFormula(int ligne, int col)
        return null;
    }

    /**
     * Enlève toutes les données de la structure de données
     */
    @Override
    public void removeAllData() {
        //TODO public void removeAllData()
    }

    /**
     * Retourne le nombre de cellules stockées dans la structure de données.
     * Pour un tableau, on retourne le nombre de cellules non vide.
     * @return nombre de cellules stockées dans la structure de données
     */
    @Override
    public int getSize() {
        //TODO public int getSize()
        return 0;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{String[][]" +
                ", " + super.toString() +
                '}';
    }
}
