package dataCenter;

import java.io.Serializable;

/**
 * Les instances de la classe interne CellKey sont utilisées
 * comme clé de <b>hash</b> pour accéder à la valeur d'une cellule
 * stockée dans cellValues.
 */
public class CellKey //TODO  implements Comparable<CellKey>
{
    private int ligne;
    private int colonne;

    public CellKey(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    //TODO les méthodes equals, hashcode

    @Override
    public String toString() {
        return "CellKey{" +
                "ligne=" + ligne +
                ", colonne=" + colonne +
                '}';
    }
}
