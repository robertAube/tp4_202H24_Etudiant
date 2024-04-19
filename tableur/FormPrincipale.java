package tableur;

import javax.swing.*;

/**
 * Démarre le formulaire tableur
 * @version 2.1 - (Robert Aubé)
 */
public class FormPrincipale {
    /**
     * Créer l'interface graphique et l'afficher.
     * For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Disable boldface controls.
        UIManager.put("swing.boldMetal", false);

        //Create and set up the window.
        JFrame frame = new JFrame("Mon Chiffrier");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        Tableur newContentPane = new Tableur();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        createAndShowGUI();
                    }
                });
    }}
