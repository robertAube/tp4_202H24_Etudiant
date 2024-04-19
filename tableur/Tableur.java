package tableur;

import dataCenter.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Locale;


/**
 * Panneau d'un tableur
 */
public class Tableur extends JPanel implements ActionListener {
    /**
     * Nombre de lignes dans la feuille
     */
    public static final int NB_LIGNE = 8;
    /**
     * Nombre de colonnes dans la feuille
     */
    public static final int NB_COLONNE = 10;
    /**
     * Tableaux du chiffrier
     */
    private JTable table;
    private JTable tableFirstCol;

    private JPanel panelMenu;

    private JMenuBar menuBar;
    private JTextArea output;


    private CtrlDataCenter ctrlDataCenter;

    String titresMenuData[] = {"Tableau", "ArrayList", "Vector", "LinkedList", "HashSet", "TreeSet", "HashMap", "TreeMap"};

    public Tableur() {
        super();
        ctrlDataCenter = new CtrlDataCenter(NB_LIGNE, NB_COLONNE);

        this.setLocale(Locale.CANADA);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //menu du haut
        ajouterMenuBar();
        add(panelMenu);

        //ajouter les 2 tables au JScrollPane
        initColTitre();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setRowHeaderView(tableFirstCol);
        add(scrollPane);


        output = new JTextArea(5, 100);
        output.setFont(new Font("Courier New", Font.PLAIN, 12) );
        output.setEditable(false);
        add(new JScrollPane(output));

        output.append(ctrlDataCenter.toString() + "\n");
    }

    private void ajouterMenuBar() {
        panelMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        menuBar = new JMenuBar();

        ajouterMenuFichier();
        ajouterMenuDataStructure();
        ajouterMenuAide();

        panelMenu.add(menuBar);
    }

    private void ajouterMenuFichier() {
        JMenu menuFichier = new JMenu("Fichier");
        JMenuItem menuItem;

        String titresMenuFichier[] = {"Ouvrir...", "Enregistrer", "Enregistrer sous...", "Fermer", "Quitter"};

        for (String titreMenuItem : titresMenuFichier) {
            menuItem = new JMenuItem(titreMenuItem);
            menuFichier.add(menuItem);
            menuItem.addActionListener(this);
        }
        menuBar.add(menuFichier);
    }

    private void ajouterMenuDataStructure() {
        JMenu menuStrucData = new JMenu("Structure de donn\u00E9es"); //http://www.kreativekorp.com/charset/encoding/CP037/

        ButtonGroup itemGroup = new ButtonGroup();

        for (String titreMenuData : titresMenuData) {
            JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem(titreMenuData);
            itemGroup.add(rbMenuItem);
            rbMenuItem.addActionListener(this);
            menuStrucData.add(rbMenuItem);
        }
        //sélectionner le bouton radio Tableau, car c'est le premier choisit par défaut
        menuStrucData.getItem(0).setSelected(true);

        menuBar.add(menuStrucData);
    }

    private void ajouterMenuAide() {
        JMenu menuInfo = new JMenu("?");
        JMenuItem menuItem;

        menuItem = new JMenuItem("\u00C0 propos");
        menuInfo.add(menuItem);
        menuItem.addActionListener(this);

        menuBar.add(menuInfo);
    }

    private void initColTitre() {
        //Table principale
        {
            TableDesCellules modeleDeTable = new TableDesCellules();
            table = new JTable(modeleDeTable);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            TabIdentifiantDesLignes modeleFirstCol = new TabIdentifiantDesLignes();
            tableFirstCol = new JTable(modeleFirstCol);

            table.setPreferredScrollableViewportSize(new Dimension(800, 500));

            table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            table.setCellSelectionEnabled(true);
        }
        //Colonne 1
        {
            //Fixer la largeur de la colonne 1
            Dimension d = tableFirstCol.getPreferredScrollableViewportSize();
            d.width = 30;
            tableFirstCol.setPreferredScrollableViewportSize(d);
            tableFirstCol.setRowHeight(table.getRowHeight());

            //Fixer l'apparence de la colonne 1
            LookAndFeel.installColorsAndFont(tableFirstCol,
                    "TableHeader.background",
                    "TableHeader.foreground",
                    "TableHeader.font");
        }
    }

    public void actionPerformed(ActionEvent event) {
        String itemMenu = event.getActionCommand();
        switch (itemMenu) {
            case "\u00C0 propos":
                JOptionPane.showMessageDialog(this,
                        "Fait par \n" + ctrlDataCenter.getFaitPar(),
                       "\u00C0 propos du TP4 en 420-202",
                        JOptionPane.INFORMATION_MESSAGE
                );
                break;
            case "Quitter":
                System.exit(0);
                break;
            default:
                ctrlDataCenter.performMenu(itemMenu);
        }
        if (stringContainsItemFromList(itemMenu, titresMenuData)) {
            output.selectAll(); // On sélectionne le texte pour le supprimer.
            output.replaceSelection("");
            output.append(ctrlDataCenter.toString() + "\n");
        }
        table.repaint();
    }

    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).anyMatch(inputStr::contains);
    }

    class TableDesCellules extends AbstractTableModel {

        private CellKey goOnEdit = null;

        public int getColumnCount() {
            return ctrlDataCenter.getNbColonne();
        }

        public int getRowCount() {
            return ctrlDataCenter.getNbLigne();
        }

        public Object getValueAt(int ligne, int colonne) {
            String str;
            if (goOnEdit != null && goOnEdit.getLigne() == ligne && goOnEdit.getColonne() == colonne) {
                //  System.out.println("getValueAt(" + ligne + ", " + colonne + ")"); //retirer le commentaire pour debugger
                str = ctrlDataCenter.getFormula(ligne, colonne);
                goOnEdit = null;
            } else
                str = ctrlDataCenter.getValue(ligne, colonne);
            return str;
        }

        // doit retourner vrai si la cellule ligne, colonne est éditable
        public boolean isCellEditable(int ligne, int colonne) {
            goOnEdit = new CellKey(ligne, colonne); //cette cellule est éditée.
            return true;
        }

        public void setValueAt(Object value, int ligne, int colonne) {
            ctrlDataCenter.set(ligne, colonne, value.toString());
            rafraichirCelluleUtilise();
            fireTableCellUpdated(ligne, colonne);
        }


        private void rafraichirCelluleUtilise() {
            //TODO rafraichir avec la liste de toutes les cellules qui sont dans le setCellARafraichir
            //ici on rafraichit uniquement la colonne cellule 0,0
            fireTableCellUpdated(0, 0);
        }
    }

    /**
     * Identifie les lignes
     */
    class TabIdentifiantDesLignes extends AbstractTableModel {

        public int getColumnCount() {
            return 1;
        }

        public int getRowCount() {
            return NB_LIGNE;
        }

        public String getColumnName(int colonne) {
            return "";
        }

        public Object getValueAt(int ligne, int colonne) {
            return ligne + 1;
        }

        public boolean isCellEditable(int ligne, int colonne) {
            return false;
        }
    }
}
