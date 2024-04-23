package utilTableur;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import static java.lang.Math.sqrt;

/**
 * Classe qui permet évalue une équation présentée dans une String.
 * Le résultat calculé est retourné en double.
 * Exemple d'équation pouvant être résolue :
 * ((4 - 2 ^ 3 + 1) * -sqrt(3 * 3 + 4 * 4)) / 2
 * Ici 7.5 est retournée.
 */
public class Calculeur {
    private String strEquation;
    private double reponse;

    private int pos = -1;
    private int car;

    /**
     * Prépare l'équation reçue en argument à être calculé
     *
     * @param strEquation équation à calculer
     */
    public Calculeur(String strEquation) {
        this.strEquation = strEquation;
        nextChar();
        reponse = analyserLExpression();
        if (pos < strEquation.length()) {
            throw new RuntimeException("Unexpected: " + (char) car);
        }
    }

    /**
     * Retourne le résultat de suite au calcul de l'équation
     *
     * @return le résultat de l'équation
     */
    public double getReponse() {
        return reponse;
    }

    private boolean eat(int charToEat) {
        while (car == ' ') nextChar();
        if (car == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    private void nextChar() {
        car = (++pos < strEquation.length()) ? strEquation.charAt(pos) : -1;
    }

    // Grammaire :
    // expression = terme | expression + terme | expression - terme
    // terme = facteur | terme * facteur | terme / facteur
    // facteur = + facteur | - facteur | ( expression ) | nombre | NomFonction (expression)
    // | facteur de nom de fonction | facteur ^ facteur
    private double analyserLExpression() {
        double x = analyserTerme();
        while (true) {
            if (eat('+')) x += analyserTerme(); // addition
            else if (eat('-')) x -= analyserTerme(); // soustraction
            else return x;
        }
    }

    private double analyserTerme() {
        double x = analyserFacteur();
        while (true) {
            if (eat('*')) x *= analyserFacteur(); // multiplication
            else if (eat('/')) x /= analyserFacteur(); // division
            else return x;
        }
    }

    private double analyserFacteur() {
        if (eat('+')) return +analyserFacteur(); // plus unaire
        if (eat('-')) return -analyserFacteur(); // moins unaire

        double x;
        int startPos = this.pos;
        if (eat('(')) { // parenthèses
            x = analyserLExpression();
            if (!eat(')')) {
                throw new RuntimeException("')' manquante");
            }
        } else if (('0' <= car && car <= '9') || car == '.') { // traiter les nombres
            while (('0' <= car && car <= '9') || car == '.') nextChar();
            x = Double.parseDouble(strEquation.substring(startPos, this.pos));
        } else if (Character.isLetter(car)) { // traiter les fonctions
            while (Character.isLetter(car)) {
                nextChar();
            }
            String func = strEquation.substring(startPos, this.pos);
            if (eat('(')) {
                x = analyserLExpression();
                if (!eat(')')) {
                    throw new RuntimeException("Il manque ')' après " + func);
                }
            } else {
                x = analyserFacteur();
            }
            x = calculerFonction(func, x);
        } else {
            throw new RuntimeException("Caractère non attendu: " + (char) car);
        }

        if (eat('^')) x = Math.pow(x, analyserFacteur()); // élever à la puissance

        return x;
    }

    private double calculerFonction(String func, double x) {
        double valeurFonction = 0;
        switch (func) {
            case "sqrt":
                valeurFonction = sqrt(x);
                break;
            case "sin":
                valeurFonction = Math.sin(Math.toRadians(x));
                break;
            case "cos":
                valeurFonction = Math.cos(Math.toRadians(x));
                break;
            case "tan":
                valeurFonction = Math.tan(Math.toRadians(x));
                break;
            default:
                throw new RuntimeException("Fonction inconnue: " + func);
        }
        return valeurFonction;
    }

    /**
     * Calcule une équation présentée en entrée
     * @param equation equation l'équation à calculer
     * @return le résultat de l'équation
     * @throws RuntimeException si l'équation reçue n'a pas pu être interprété et génère une erreur.
     */
    public static double calculer(String equation) throws RuntimeException {
        Calculeur cal = new Calculeur(equation);
        return (cal.getReponse());
    }

     /**
     * Calcule une équation présentée en entrée
     * note : solution alternative...
     * https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
      *
     * @param equation equation l'équation à calculer
     * @return le résultat de l'équation
     * @throws RuntimeException si l'équation reçue n'a pas pu être interprété et génère une erreur.
     */
    private static double calculerOther(String equation) {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        double resultat = 0;
        try {
            resultat =  (double) engine.eval(equation);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }

        return resultat;
    }

}
