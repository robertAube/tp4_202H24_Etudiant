package dataCenter;


import java.util.Set;

/**
 * @author Robert Aubé
 * @version 2.0
 */
public interface IDataCenter {
    /**
     * Récupère la valeur de la cellule à (ligne, colonne) dans la collection.
     * Si c'est une formule, elle sera interprétée.
     * Si l'élément n'est pas trouvé, on retourne une chaine vide. ("")
     * Une formule commence par un "="
     * (0,0) est la coordonnée de la cellule A1.
     * @param ligne de la cellule
     * @param col de la cellule
     * @return la valeur CALCULÉE de la cellule à (ligne, colonne) Ex =1+1 retournerait 2
     */
    String getValue(int ligne, int col);

    /**
     * Récupère la formule de la cellule à (ligne, colonne) dans la collection.
     * Si c'est une formule, elle NE sera PAS interprétée.
     * Si l'élément n'est pas trouvé, on retourne une chaine vide. ("")
     * (0,0) est la coordonnée de la cellule A1.
     * @param ligne de la cellule
     * @param col de la cellule
     * @return la valeur de la cellule à (ligne, colonne). Le contenu N’est PAS interprété. Ex =A1+1
     */
    String getFormula(int ligne, int col);

    /**
     * Mets à jour la formule sans la calculer dans la structure de données.
     * Exemple : dans les collections =1+1 est sauvegardé plutôt que 2
     * Si l'élément à ligne, colonne <b>n'existe pas</b>, il l'ajoute
     * Si l'élément à ligne, colonne <b>existe</b> déjà, il le remplace
     * Si newFormula est une chaine vide, on considère que c'est une absence de données et
     * la formule est alors supprimée dans la structure.
     * (0,0) est la coordonnée de la cellule A1.
     * @param ligne de la cellule
     * @param col de la cellule
     * @param newFormula la nouvelle formule ou valeur qui sera placée dans la cellule à la (ligne, colonne)
     * @return la nouvelle valeur qui sera placée dans la cellule à la (ligne, colonne)
     */
    void set(int ligne, int col, String newFormula);

    /**
     * Enlève toutes les données de la structure de données
     */
    void removeAllData();

    /**
     * Retourne un Set de CellKey, des cellules à rafraichir qui contiennent une formule (commencant par =)
     * si une mise à jour des cellules est lancée.
     * Elle contient la référence ligne et colonne de toutes les cellules où il y a une formule dans le tableur.
     * Cette gestion se fait par le set(ligne, col, newCellValue). Chaque fois que l'on...
     * - ajoute une cellule, on ajoute la référence ligne, colonne à la liste.
     * - attribue la valeur "" à la cellule, on la supprime de la liste.
     * - modifie une cellule, cette liste n'est pas modifiée.
     * @return Retourne un Set de CellKey, des cellules à rafraichir
     */
    Set<CellKey> getSetCellARafraichir();

    /**
     * Retourne le nombre de cellules stockées dans la structure de données.
     * Pour un tableau, on retourne le nombre de cellules non vide.
     * @return nombre de cellules stockées dans la structure de données
     */
    int getSize();

    /**
     * Retourne true si la valeur à ligne, colonne est vide.
     * On considère qu'une cellule est vide, si getVelue(ligne, colonne) retourne un chaine vide "".
     * @param ligne
     * @param colonne
     * @return true si la valeur à ligne, colonne est vide.
     */
    boolean estVide(int ligne, int colonne);
}
