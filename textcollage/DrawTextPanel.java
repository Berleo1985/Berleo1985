package com.textcollage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
public class DrawTextPanel extends JPanel {
    private ArrayList<DrawTextItem> theStrings; // changed to an ArrayList<DrawTextItem> !
    private Color currentTextColor = Color.BLACK; // Color applied to new strings.
    private Canvas canvas; // the drawing area.
    private JTextField input; // where the user inputs the string that will be added to the canvas
    private SimpleFileChooser fileChooser; // for letting the user select fles
    private JMenuBar menuBar; // a menu bar with command that afect this panel
    private MenuHandler menuHandler; // a listener that responds whenever the user selects a menu command
    private JMenuItem undoMenuItem; // the "Remove Item" command from the edit menu
    private class Canvas extends JPanel {
        Canvas() {
            setPreferredSize( new Dimension(800,600) );
            setBackground(Color.WHITE);
            setFont( new Font( "Times New Roman", Font.BOLD, 24 ));
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            if (theStrings != null)
                for (DrawTextItem s: theStrings)
                    s.draw(g);
        }
    }
    private class MenuHandler implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            doMenuCommand( evt.getActionCommand());
        }
    }
    public DrawTextPanel() {
        fileChooser = new SimpleFileChooser();
        undoMenuItem = new JMenuItem("Remove Item");
        undoMenuItem.setEnabled(false);
        menuHandler = new MenuHandler();
        setLayout(new BorderLayout(3,3));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.add(new JLabel("Add String (Word or Words: "));
        input = new JTextField("CS 1103 UNIT 6 PA", 40);
        bottom.add(input);
        add(bottom, BorderLayout.SOUTH);
        canvas.addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                doMousePress( e );
            }
        } );
    }
    public void doMousePress( MouseEvent e ) {
        if (e.isMetaDown()) { //right click to remove an item
            removeItem();
            return;
        }
        String text = input.getText().trim();
        if (text.length() == 0) {
            input.setText("CS 1103 UNIT 6 PA");
            text = "CS 1103 UNIT 6 PA ";
        }
        DrawTextItem s = new DrawTextItem( text, e.getX(), e.getY() );
        s.setTextColor(currentTextColor);
        int randomChoice = (int)(Math.random()*5);
        int fontStyle;
        switch (randomChoice) {
            case 0: fontStyle = Font.ITALIC; break;
            case 1: fontStyle = Font.BOLD; break;
            default: fontStyle = Font.ITALIC + Font.BOLD;
        }
        s.setFont( new Font( "Times New Roman", fontStyle, (int)(Math.random()*12+8) ));

        s.setMagnification((int)(Math.random()*4+1));
        if (Math.random() > 0.3)
            s.setBorder(true);
        s.setRotationAngle(Math.random()*360);
        s.setTextTransparency(Math.random()*0.25);
        if (Math.random() > 0.5)
            s.setBackground(new Color((float)Math.random(), (float)Math.random(),
                    (float)Math.random()));
        s.setBackgroundTransparency(Math.random()*0.90+0.10);
        if (theStrings == null)
            theStrings = new ArrayList<DrawTextItem>();
        theStrings.add(s); // Set this string as the ONLY string to be drawn on the canvas!
        undoMenuItem.setEnabled(true);
        canvas.repaint();
    }
    public JMenuBar getMenuBar() {
        if (menuBar == null) {
            menuBar = new JMenuBar();
            String commandKey;
            if (System.getProperty("mrj.version") == null)
                commandKey = "control ";
            else
                commandKey = "meta "; // command key for MacOS
            JMenu fleMenu = new JMenu("File");
            menuBar.add(fleMenu);
            JMenuItem saveItem = new JMenuItem("Save...");
            saveItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "N"));
            saveItem.addActionListener(menuHandler);
            fleMenu.add(saveItem);
            JMenuItem openItem = new JMenuItem("Open...");
            openItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "O"));
            openItem.addActionListener(menuHandler);
            fleMenu.add(openItem);
            fleMenu.addSeparator();
            JMenuItem saveImageItem = new JMenuItem("Save Image...");
            saveImageItem.addActionListener(menuHandler);
            fleMenu.add(saveImageItem);
            JMenu editMenu = new JMenu("Edit");
            menuBar.add(editMenu);
            undoMenuItem.addActionListener(menuHandler); // undoItem was created in the constructor
            undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "Z"));
            editMenu.add(undoMenuItem);
            editMenu.addSeparator();
            JMenuItem clearItem = new JMenuItem("Clear");
            clearItem.addActionListener(menuHandler);
            editMenu.add(clearItem);
            JMenu optionsMenu = new JMenu("Options");
            menuBar.add(optionsMenu);
            JMenuItem colorItem = new JMenuItem("Set Text Color...");
            colorItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "T"));
            colorItem.addActionListener(menuHandler);
            optionsMenu.add(colorItem);
            JMenuItem bgColorItem = new JMenuItem("Set Background Color...");
            bgColorItem.addActionListener(menuHandler);
            optionsMenu.add(bgColorItem);
        }
        return menuBar;
    }
    private void doMenuCommand(String command) {
        if (command.equals("Save...")) { // save all the string info to a fle
            saveFile();
        }
        else if (command.equals("Open...")) { // read a previously saved fle, and reconstruct the list of strings
            openFile();
            canvas.repaint(); // (you'll need this to make the new list of strings take efect)
        }
        else if (command.equals("Clear")) { // remove all strings
            theStrings = null; // Remove the ONLY string from the canvas.
            undoMenuItem.setEnabled(false);
            canvas.repaint();
        }
        else if (command.equals("Remove Item"))
            removeItem();
        else if (command.equals("Set Text Color...")) {
            Color c = JColorChooser.showDialog(this, "Select Text Color", currentTextColor);
            if (c != null)
                currentTextColor = c;
        }
        else if (command.equals("Set Background Color...")) {
            Color c = JColorChooser.showDialog(this, "Select Background Color",
                    canvas.getBackground());
            if (c != null) {
                canvas.setBackground(c);
                canvas.repaint();
            }
        }
        else if (command.equals("Save Image...")) { // save a PNG image of the drawing area
            File imageFile = fileChooser.getOutputFile(this, "Select Image File Name",
                    "textimage.png");
            if (imageFile == null)
                return;
            try {
// Because the image is not available, I will make a new BuferedImage and
// draw the same data to the BuferedImage as is shown in the panel.
// A BuferedImage is an image that is stored in memory, not on the screen.
// There is a convenient method for writing a BuferedImage to a fle.
                BufferedImage image = new BufferedImage(canvas.getWidth(),canvas.getHeight(),
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = image.getGraphics();
                g.setFont(canvas.getFont());
                canvas.paintComponent(g); // draws the canvas onto the BuferedImage, not thescreen!
                boolean ok = ImageIO.write(image, "PNG", imageFile); // write to the fle
                if (ok == false)
                    throw new Exception("PNG format not supported (this shouldn't happen!).");
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Sorry, an error occurred while trying to save the image:\n" + e);
            }
        }
    }

    private void removeItem() {
        if (theStrings.size() > 0)
            theStrings.remove(theStrings.size()-1); // remove the most recently added string
        if (theStrings.size() == 0)
        undoMenuItem.setEnabled(false);
        canvas.repaint();
    }
    private void saveFile() {
        File saveAs = fileChooser.getOutputFile(this, "Save As", "TextCollageTrial.txt");
        try {
            PrintWriter out = new PrintWriter(saveAs);
            out.println("New text collage fle");
            out.println(canvas.getBackground().getRed());
            out.println(canvas.getBackground().getGreen());
            out.println(canvas.getBackground().getBlue());
            if (theStrings != null)
                for (DrawTextItem s: theStrings) {
                    out.println("theString:");
                    out.println(s.getString());
                    out.println(s.getX());
                    out.println(s.getY());
                    out.println(s.getFont().getName());
                    out.println(s.getFont().getStyle());
                    out.println(s.getFont().getSize());
                    out.println(s.getTextColor().getRed());
                    out.println(s.getTextColor().getGreen());
                    out.println(s.getTextColor().getBlue());
                    out.println(s.getTextTransparency());
                    if (s.getBackground() == null) {
                        out.println("-1");
                        out.println("-1");
                        out.println("-1");
                    }
                    else {
                        out.println(s.getBackground().getRed());
                        out.println(s.getBackground().getGreen());
                        out.println(s.getBackground().getBlue());
                    }
                    out.println(s.getBackgroundTransparency());
                    out.println(s.getBorder());
                    out.println(s.getMagnification());
                    out.println(s.getRotationAngle());
                }
            out.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Can't write to the fle \"" + saveAs + "\".");
            System.out.println("Error message: " + e);
        }
    }
    private void openFile() {
        File openFile = fileChooser.getInputFile(this, "Open Saved File");
        try {
            Scanner in = new Scanner(openFile);
            if (!in.nextLine().equals("New text collage fle")) {
                JOptionPane.showMessageDialog(this, "Not a valid fle \"" + openFile + "\".");
                return;
            }
            Color savedBg = new Color(in.nextInt(), in.nextInt(), in.nextInt());
            ArrayList<DrawTextItem> newStrings = new ArrayList<DrawTextItem>();
            DrawTextItem newItem;
            in.nextLine(); //skip to the next line before read a new line
            while (in.hasNext() && in.nextLine().equals("theString:")) {
                newItem = new DrawTextItem(in.nextLine(),
                        in.nextInt(), in.nextInt());
                in.nextLine(); //skip to the next line before read a new line
                newItem.setFont(new Font(in.nextLine(), in.nextInt(), in.nextInt()));
                newItem.setTextColor(new Color(in.nextInt(), in.nextInt(), in.nextInt()));
                newItem.setTextTransparency(in.nextDouble());
                int r = in.nextInt();
                int g = in.nextInt();
                int b = in.nextInt();
                if (r == -1)
                    newItem.setBackground(null);
                else
                    newItem.setBackground(new Color(r, g, b));
                newItem.setBackgroundTransparency(in.nextDouble());
                newItem.setBorder(in.nextBoolean());
                newItem.setMagnification(in.nextDouble());
                newItem.setRotationAngle(in.nextDouble());
                in.nextLine();
                newStrings.add(newItem);
            }
            canvas.setBackground(savedBg);
            theStrings = newStrings;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Can't read the fle \"" + openFile + "\".");
            System.out.println("Error message: " + e);
        }
    }
}
