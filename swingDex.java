/*
try {
}catch (FileNotFoundException e) {System.out.println("File not found");}
catch (IOException ex) {Logger.getLogger(swingDex.class.getName()).log(Level.SEVERE, null, ex);}
*/
//import java.io.*;
import java.awt.*;
//import java.util.*;
import javax.swing.*;
//import java.awt.event.*;
//import java.util.logging.*;
public class swingDex{
   // Variables
    private final JPanel headerPanel, middlePanel, bottomPanel;
    private final JFrame mainFrame;
    // Call Window
    public static void main(String[] args){
        swingDex logs = new swingDex();
        logs.test();
    }
    // Window Constructor
    public swingDex(){
        // Set Main Frame
        mainFrame = new JFrame("Azeez's SWING Tester");
        mainFrame.setSize(600,600);
        mainFrame.setLayout(new GridLayout(3, 1));
        // Add Panels
        mainFrame.add(headerPanel = new JPanel(new FlowLayout()));
        mainFrame.add(middlePanel = new JPanel(new FlowLayout()));
        mainFrame.add(bottomPanel = new JPanel(new FlowLayout()));
    }public void test(){
        // Completionist
        headerPanel.add(new JLabel("Pokedex Completionist"));
        JButton[] caughtSeen = {new JButton("Caught"),new JButton("Seen")};
        //Image
        JLabel pokemonImage = new JLabel("Charjabug");
        pokemonImage.setIcon(makeImage("snow.png"));
        // Add to Panels
        bottomPanel.add(pokemonImage);
        bottomPanel.add(caughtSeen[0]);
        bottomPanel.add(caughtSeen[1]);
        // Normal Operations for Mainframe
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBackground(Color.BLACK);
        mainFrame.setForeground(Color.BLACK);
        mainFrame.setVisible(true);
    }public ImageIcon makeImage(String name){
        return new ImageIcon(new ImageIcon(name).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    }
}