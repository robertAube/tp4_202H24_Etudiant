package tableur;

import dataCenter.CellKey;
import utilTableur.TableCellListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.util.Locale;

/**
 * Panneau d'un tableur
 *
 * @author Robert Aubé
 * @version 1.2 - (Robert Aubé)
 */
public class Tableur extends JPanel implements ActionListener {

    /**
     * Tableaux du chiffrier
     */
    private JTable jTable;
    private JLabel txtStatusBar;
    private JLabel txtIDCellule;
    private JLabel txtContenuCellule;

    public JScrollPane scrollPane;

    TableCellListener tcl;
    /**
     * Nombre de lignes dans la feuille
     */
    public static final int NB_LIGNE = 8;
    /**
     * Nombre de colonnes dans la feuille
     */
    public static final int NB_COLONNE = 10;

    public static final int HAUTEUR_LIGNE = 23;

    /**
     * Structure de données qui conserve le contenu des cellules
     */
    private CtrlDataCenter ctrlDataCenter;

    String titresMenuData[] = {"Tableau", "ArrayList", "Vector", "LinkedList", "HashSet", "TreeSet", "HashMap", "TreeMap"};

    public Tableur() {
        super();
        this.setLocale(Locale.CANADA);
        ctrlDataCenter = new CtrlDataCenter(NB_LIGNE, NB_COLONNE);
        initLayout();
        updateStatusBar();
        jTable.changeSelection(0,0,false,false);
        tcl = new TableCellListener(jTable, actionListenerOnCell);
    }

    private void initLayout() {
        JMenuBar jMenuBar;
        JPanel jContenuCelluleBar;
        JPanel jPanelNorth;
        JScrollPane scrollPane;
        JPanel jStatusBar;

        setLayout(new BorderLayout());

        jPanelNorth = new JPanel();
        jPanelNorth.setLayout(new GridLayout(2, 1));

        jMenuBar = initMenuBar();
        jContenuCelluleBar = initContenuCelluleBar();
        jPanelNorth.add(jMenuBar);
        jPanelNorth.add(jContenuCelluleBar);
        this.add(jPanelNorth, BorderLayout.NORTH);

        scrollPane = initTable();
        this.add(scrollPane, BorderLayout.CENTER);

        jStatusBar = initStatusBar();
        this.add(jStatusBar, BorderLayout.SOUTH);

        this.addComponentListener(new ResizeListener());
    }

    private JPanel initContenuCelluleBar() {
        JPanel jContenuCelluleBar = new JPanel();

        jContenuCelluleBar.setLayout(new FlowLayout(FlowLayout.LEFT));

        txtIDCellule = new JLabel(" ");
        txtIDCellule.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        txtIDCellule.setMinimumSize(new Dimension(30, HAUTEUR_LIGNE));
        txtIDCellule.setPreferredSize(new Dimension(30, HAUTEUR_LIGNE));
        txtIDCellule.setHorizontalAlignment(SwingConstants.CENTER);

        jContenuCelluleBar.add(txtIDCellule);

        txtContenuCellule = new JLabel();
        txtContenuCellule.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        txtContenuCellule.setMinimumSize(new Dimension(30, HAUTEUR_LIGNE));
        txtContenuCellule.setHorizontalAlignment(SwingConstants.LEFT);
        jContenuCelluleBar.add(txtContenuCellule);
        setTxtContenuCelluleWidth(this);

        return jContenuCelluleBar;
    }

    private JPanel initStatusBar() {
        JPanel jStatusPanel;
// create the status bar panel and shove it down the bottom of the frame
        jStatusPanel = new JPanel();

        jStatusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jStatusPanel.setPreferredSize(new Dimension(this.getWidth(), HAUTEUR_LIGNE));
        jStatusPanel.setLayout(new BoxLayout(jStatusPanel, BoxLayout.X_AXIS));
        txtStatusBar = new JLabel();
        txtStatusBar.setHorizontalAlignment(SwingConstants.LEFT);
        jStatusPanel.add(txtStatusBar);

        return jStatusPanel;
    }

    private JMenuBar initMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        JPanel jPanelMenu;

        jPanelMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));

        jMenuBar = ajouterMenuFichier(jMenuBar);
        jMenuBar = ajouterMenuDataStructure(jMenuBar);
        jMenuBar = ajouterMenuAide(jMenuBar);

        jPanelMenu.add(jMenuBar);
        return jMenuBar;
    }

    private JMenuBar ajouterMenuFichier(JMenuBar jMenuBar) {
        JMenu menuFichier = new JMenu("Fichier");
        JMenuItem menuItem;

        String titresMenuFichier[] = {"Ouvrir...", "Enregistrer", "Enregistrer sous...", "Fermer", "Quitter"};

        for (String titreMenuItem : titresMenuFichier) {
            menuItem = new JMenuItem(titreMenuItem);
            menuFichier.add(menuItem);
            menuItem.addActionListener(this);
        }
        jMenuBar.add(menuFichier);
        return jMenuBar;
    }

    private JMenuBar ajouterMenuDataStructure(JMenuBar jMenuBar) {
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

        jMenuBar.add(menuStrucData);
        return jMenuBar;
    }

    private JMenuBar ajouterMenuAide(JMenuBar jMenuBar) {
        JMenu menuInfo = new JMenu("?");
        JMenuItem menuItem;

        menuItem = new JMenuItem("\u00C0 propos"); //http://www.kreativekorp.com/charset/encoding/CP037/
        menuInfo.add(menuItem);
        menuItem.addActionListener(this);

        jMenuBar.add(menuInfo);

        return jMenuBar;
    }

    private JScrollPane initTable() {

        JTable jTableFirstCol;
        //Table principale
        {
            TableDesCellules modeleDeTable = new TableDesCellules();
            jTable = new JTable(modeleDeTable);
            jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            jTable.setCellSelectionEnabled(true);
            jTable.setPreferredScrollableViewportSize(new Dimension(800, 500));

            jTable.getSelectionModel().addListSelectionListener(
                    new ListSelectionListener() {
                        public void valueChanged(ListSelectionEvent event) {
                            updateContenuCelluleBar();
                        }
                    });
            jTable.getColumnModel().getSelectionModel().addListSelectionListener(
                    new ListSelectionListener() {
                        public void valueChanged(ListSelectionEvent event) {
                            updateContenuCelluleBar();
                        }
                    });

        }
        //Colonne 1
        {
            TabIdentifiantDesLignes modeleFirstCol = new TabIdentifiantDesLignes();
            jTableFirstCol = new JTable(modeleFirstCol);

            //Fixer la largeur de la colonne 1
            Dimension d = jTableFirstCol.getPreferredScrollableViewportSize();
            d.width = 30;
            jTableFirstCol.setPreferredScrollableViewportSize(d);
            jTableFirstCol.setRowHeight(jTable.getRowHeight());

            //Fixer l'apprence de la colonne 1
            LookAndFeel.installColorsAndFont(jTableFirstCol,
                    "TableHeader.background",
                    "TableHeader.foreground",
                    "TableHeader.font");
        }
        scrollPane = new JScrollPane(jTable);
        scrollPane.setRowHeaderView(jTableFirstCol);
        return scrollPane;
    }

    public void actionPerformed(ActionEvent event) {
        String itemMenu = event.getActionCommand();
        switch (itemMenu) {
            case "À propos":
                JOptionPane.showMessageDialog(this,
                        "Fait par " + ctrlDataCenter.getFaitPar(),
                        "À propos du TP4 en 420-202",
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
            updateStatusBar();
        }
        jTable.repaint();
    }

    private void updateStatusBar() {
        txtStatusBar.setText(ctrlDataCenter.toString());
    }

    private void updateContenuCelluleBar() {
        String strContenu = "";
        int ligne = jTable.getSelectedRow();
        int colonne = jTable.getSelectedColumn();
        String guillemets = "";
//        guillemets = "\"";

        if (ligne != -1 && colonne != -1) {
            txtIDCellule.setText(utilTableur.ColonneTableur.toName(colonne + 1) + (ligne + 1));
            strContenu = ctrlDataCenter.getFormula(ligne, colonne);
        }
        setTxtContenuCelluleWidth(this);
        txtContenuCellule.setText(guillemets + strContenu + guillemets);
    }

    public void setTxtContenuCelluleWidth(Component component) {
        int width = component.getWidth() - 45;
        txtContenuCellule.setPreferredSize(new Dimension(width, HAUTEUR_LIGNE));
    }


    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).anyMatch(inputStr::contains);
    }

    Action actionListenerOnCell = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e)
        {
//            TableCellListener tcl = (TableCellListener)e.getSource();
//            System.out.println("Row   : " + tcl.getRow());
//            System.out.println("Column: " + tcl.getColumn());
//            System.out.println("Old   : " + tcl.getOldValue());
//            System.out.println("New   : " + tcl.getNewValue());
            updateContenuCelluleBar();
        }
    };

    class ResizeListener implements ComponentListener {
        public void componentResized(ComponentEvent e) {
            setTxtContenuCelluleWidth(e.getComponent());
        }

        @Override
        public void componentMoved(ComponentEvent e) {
        }

        @Override
        public void componentShown(ComponentEvent e) {
            setTxtContenuCelluleWidth(e.getComponent());
        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }
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
            } else {
                str = ctrlDataCenter.getValue(ligne, colonne);
            }
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
            fireTableCellUpdated(ligne, colonne); //mettre à jour la cellule
            fireTableCellUpdated(0, 0);
        }

        private void rafraichirCelluleUtilise() {
        //TODO rafraichir avec la liste de toutes les cellules qui sont dans la liste listARafraichir
        //Si une cellule est mise à jour, toutes les cellules qui y font doivent mise à jour avec
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
