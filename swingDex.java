import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.logging.*;
public class swingDex{
   // Window Variables
    private final JPanel headerPanel, middlePanel, bottomPanel;
    private final JFrame mainFrame;
    // Call Window
    public static void main(String[] args){
        swingDex dex = new swingDex();
        dex.pokedex();
    }
    // Window Constructor
    public swingDex(){
        // Set Main Frame
        mainFrame = new JFrame("Pokedex List");
        mainFrame.setSize(600,600);
        mainFrame.setLayout(new GridLayout(3, 1));
        // Add Panels
        mainFrame.add(headerPanel = new JPanel(new FlowLayout()));
        mainFrame.add(middlePanel = new JPanel(new FlowLayout()));
        mainFrame.add(bottomPanel = new JPanel(new FlowLayout()));
    }

    // Pokedex Window
    public void pokedex(){
        // Completionist Title
        headerPanel.add(new JLabel("Pokedex Completionist"));
        // Pokemon Object
        Pokemon charge = new Pokemon("Charjabug","737");
        // Caught and Seen Buttons
        JButton[] caughtSeen = {new JButton("Caught"),new JButton("Seen")};
        // Generation and DLC Selector
        JComboBox generationSelector = new JComboBox<>(new String[]{"Gen 1","Gen 2","Gen 3","Gen 4","Gen 5","Gen 6","Gen 7","Gen 8","Gen 9"}),
        dlcSelector = new JComboBox<>(new String[]{"Red and Blue + Remakes","Yellow","Let's Go Games"});
        // Generation Chooser Action Listener
        generationSelector.addActionListener((ActionEvent e) -> {
            String[] generation = {"None"};
            switch(generationSelector.getSelectedIndex()){ // Choose Generation minus 1
                case 0 /*Kanto*/ -> {generation = new String[]{"Red and Blue + Remakes","Yellow","Let's Go Games"};}
                case 1 /*Johto*/ -> {generation = new String[]{"Gold and Silver","HeartGold and SoulSilver"};}
                case 2 /*Hoenn*/ -> {generation = new String[]{"Ruby and Sapphire","Emerald","Omega Ruby and Alpha Sapphire"};}
                case 3 /*Sinnoh*/ -> {generation = new String[]{"Diamond and Pearl","Platinum","Legends Arceus"};}
                case 4 /*Unova*/ -> {generation = new String[]{"Black and White","Black 2 and White 2"};}
                case 5 /*Kalos*/ -> {generation = new String[]{"X and Y","Legends ZA"};}
                case 6 /*Alola*/ -> {generation = new String[]{"Sun and Moon","Ultra Sun and Ultra Moon"};}
                case 7 /*Galar*/ -> {generation = new String[]{"Sword and Shield","Isle of Armor","Crown Tundra"};}
                case 8 /*Paldea*/ -> {generation = new String[]{"Scarlet and Violet","Kitakami","Indigo Disk"};}
            }
            dlcSelector.setModel(new DefaultComboBoxModel<>(generation));
            dlcSelector.setSelectedIndex(0);
            mainFrame.revalidate(); // Resets mainFrame
        });
        //Image
        JLabel pokemonImage = new JLabel(charge.name);
        pokemonImage.setIcon(makeImage(charge.national));
        // Add to Panels
        middlePanel.add(generationSelector);
        middlePanel.add(dlcSelector);
        bottomPanel.add(pokemonImage);
        bottomPanel.add(caughtSeen[0]);
        bottomPanel.add(caughtSeen[1]);
        // Normal Operations for Mainframe
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        headerPanel.setBackground(Color.WHITE);
        middlePanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(Color.WHITE);
        mainFrame.setVisible(true);
    }
    //Get Image with Website
    public ImageIcon makeImage(String dexNumber){
        try {
            BufferedImage image = ImageIO.read(new URL("https://serebii.net/scarletviolet/pokemon/new/"+dexNumber+".png"));
            return new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        }catch (MalformedURLException e) {
        }catch (IOException ex) {Logger.getLogger(swingDex.class.getName()).log(Level.SEVERE, null, ex);}
        return null;
        
    }
}