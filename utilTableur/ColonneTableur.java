package utilTableur;

import dataCenter.CellKey;

/**
 * Fait la gestion des noms de cellules dans un tableur dont les colonnes
 * sont identifiés par des lettres.
 * Dont A est la 1er colonne, B la 2ième, ... jusqu'à Z puis AA, AB etc.
 * La colonne A à la valeur 0.
 */
public class ColonneTableur {
    /**
     * Retourne l'équivalant entier d'un nom de colonne (Exemple A).
     * <b>A</b> étant la colonne <b>0</b>.
     * @param nomColonne nom de colonne (Exemple A)
     * @return l'équivalant entier de la colonne
     */
    public static int toNumber(String nomColonne) {
        int no = 0;
        for (char c: nomColonne.toCharArray()) {
            no = no * 26 + (Character.toUpperCase(c) - ('A' - 1));
        }
        return no - 1;
    }

    /**
     * Retourne le nom d'une colonne (Exemple AB) à partir d'un numéro de colonne.
     * <b>0</b> étant la colonne <b>A</b>.
     * @param noColonne numéro de colonne (Exemple 0)
     * @return le nom de la colonne (Exemple A)
     */
    public static String toName(int noColonne) {
        StringBuilder sb = new StringBuilder();
        while (noColonne-- > 0) {
            sb.append((char) ('A' + (noColonne % 26)));
            noColonne /= 26;
        }
        return sb.reverse().toString();
    }

    /**
     * Convertit un nom de cellule en termes de ligne colonne
     * @param nomCellule un nom de cellule (Exemple : A2)
     * @return une clé de cellule CellKey en termes de ligne colonne
     */
    public static CellKey convertirNomCell_A_LigneColonne(String nomCellule) {
        StringBuilder sbLetter = new StringBuilder();
        StringBuilder sbNumber = new StringBuilder();
        boolean renduAuChiffre = false;
        nomCellule = nomCellule.toUpperCase();
        for (int i = 0; i < nomCellule.length(); i++) {
            if (Character.isLetter(nomCellule.charAt(i))) {
                sbLetter.append(nomCellule.charAt(i));
                if (renduAuChiffre) {
                    throw new IllegalArgumentException("Le nom d'une cellule doit d'abord avoir des lettres puis des chiffres : " + nomCellule);
                }
            } else if (Character.isDigit(nomCellule.charAt(i))) {
                sbNumber.append(nomCellule.charAt(i));
                renduAuChiffre = true;
            } else
                throw new IllegalArgumentException("Le nom d'une cellule ne contient que des lettres et des chiffres : " + nomCellule);
        }
        return new CellKey(Integer.parseInt(sbNumber.toString()) - 1, toNumber(sbLetter.toString()));
    }
}
