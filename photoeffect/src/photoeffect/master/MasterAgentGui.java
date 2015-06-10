package photoeffect.master;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Basic chat user interface.
 */
public class MasterAgentGui extends JFrame
{
    private static final long serialVersionUID = 1L;
    protected JPanel pMain;
    protected JPanel pButton;
    protected DisplayPanel currentImagePanel;
    protected JList<String> imageQueue;
    protected JList<String> blurredList;
    protected JTextArea currentImageURL;
    protected JButton applyButton;

    /**
     * Create the user interface
     */
    public MasterAgentGui(String name, ActionListener windowClosing)
    {
        super(name);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        /* 1. MAIN PANEL SETTING */
        // 1.1 Foto
        currentImagePanel = new DisplayPanel();
        currentImagePanel.setSize(600, 600);

        // 1.2 Lista laterale
        imageQueue = new JList<String>();
        // Listmodel to modify stuff
        DefaultListModel<String> imageModel = new DefaultListModel<String>();
        imageQueue.setModel(imageModel);

        /****** test images */
        LinkedList<String> immagini = new LinkedList<String>();
        BufferedReader b;
        FileReader f;
        try
        {
            f = new FileReader("src" + File.separator + "prova.txt");
            b = new BufferedReader(f);

            String s;
            while (true)
            {
                s = b.readLine();
                if (s == null)
                    break;
                immagini.add("src" + File.separator + s);
            }
            b.close();
            f.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        // String[] immagini = { ".\\src\\image1.jpg", ".\\src\\image2.jpg",
        // ".\\src\\image3.jpg" };
        while (!immagini.isEmpty())
            ((DefaultListModel<String>) imageQueue.getModel()).addElement(immagini.removeFirst());

        /****** FINE */

        // selection lisetener
        imageQueue.addListSelectionListener(new ListSelectionListener()
        {

            public void valueChanged(ListSelectionEvent arg0)
            {
                blurredList.clearSelection();
                // When you select an item from This list, Deletes the selection
                // The other list
                if (!imageQueue.isSelectionEmpty())
                {
                    String display = imageQueue.getSelectedValue();
                    currentImageURL.setText(display);
                    currentImagePanel.setImage(display);
                    currentImagePanel.repaint();
                }
            }

        });

        // 1.3 Lista Blurred Image
        blurredList = new JList<String>();
        // associandovi un ListModel la si pu� manipolare (anche passandolo nel
        // costruttore)

        DefaultListModel<String> blurredModel = new DefaultListModel<String>();
        blurredList.setModel(blurredModel);

        // listener: selezionando un immagine dalla lista la visualizza nel
        // pannello
        blurredList.addListSelectionListener(e -> {
            imageQueue.clearSelection();
            // quando si seleziona un item da
            // questa lista
            // si cancella la selezione
            // dell'altra lista
                if (!blurredList.isSelectionEmpty())
                {
                    String display = blurredList.getSelectedValue();
                    currentImageURL.setText(display);
                    currentImagePanel.setImage(display);
                    currentImagePanel.repaint();
                }
            });

        // 1.4 Pannello Liste
        JPanel plisteUp = new JPanel();
        JPanel plisteCenter = new JPanel();
        plisteUp.setLayout(new GridLayout(1, 2, 4, 2)); // row, cols, hspace,
                                                        // wspaces
        plisteUp.add(new JLabel("Original Images", JLabel.CENTER));
        plisteUp.add(new JLabel("Blurred Images", JLabel.CENTER));
        plisteCenter.setLayout(new GridLayout(1, 2, 2, 2)); // row, cols,
                                                            // hspace, wspaces
        plisteCenter.add(imageQueue);
        plisteCenter.add(blurredList);
        JPanel pliste = new JPanel(new BorderLayout());

        pliste.add(plisteUp, BorderLayout.NORTH);
        pliste.add(plisteCenter, BorderLayout.CENTER);

        currentImageURL = new JTextArea(imageQueue.getSelectedValue());

        pMain = new JPanel();
        pMain.setLayout(new BorderLayout());
        pMain.add(currentImagePanel, BorderLayout.CENTER);
        pMain.add(pliste, BorderLayout.EAST);
        pMain.add(currentImageURL, BorderLayout.SOUTH);

        applyButton = new JButton("APPLY");
        pButton = new JPanel();
        pButton.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        pButton.add(applyButton);

        this.setLayout(new BorderLayout());
        this.getContentPane().add(pMain, BorderLayout.CENTER);
        this.getContentPane().add(pButton, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                windowClosing.actionPerformed(null);
            }
        });

        pack();
        setVisible(true);

        imageQueue.setSelectedIndex(0);
    }

    public void changeImage(BufferedImage image)
    {
        currentImagePanel.setImage(image);
        currentImagePanel.repaint();
        currentImageURL.setText("image changed: " + System.nanoTime());
    }

    protected void addInBlurredList(String blurredImageFilename)
    {
        ((DefaultListModel<String>) blurredList.getModel()).addElement(blurredImageFilename);
    }

}