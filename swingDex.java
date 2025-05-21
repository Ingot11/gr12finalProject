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
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add Panels
        mainFrame.add(headerPanel = new JPanel(new FlowLayout()));
        mainFrame.add(middlePanel = new JPanel(new FlowLayout()));
        mainFrame.add(bottomPanel = new JPanel(new FlowLayout()));
    }

    // Pokedex Window
    public void pokedex(){
        // Completionist Title and Caught and Seen Buttons
        headerPanel.add(new JLabel("Pokedex Completionist"));
        JButton[] caughtSeen = {new JButton("Caught"),new JButton("Seen")};
        // Test Pokemon Object
        Pokemon testPokemon = new Pokemon("Charjabug","737");
        // Generation and DLC Selector
        JComboBox generationSelector = new JComboBox<>(new String[]{"Kanto","Johto","Hoenn","Sinnoh/Hisui","Unova","Kalos","Alola","Galar","Paldea"}),
        dlcSelector = new JComboBox<>(new String[]{"Red and Blue + Remakes","Yellow","Let's Go Games"});
        // Generation Chooser Action Listener
        generationSelector.addActionListener((ActionEvent e) -> {
            String[] generation = {"None"};
            switch(generationSelector.getSelectedIndex()){ // Choose Generation minus 1
                case 0 /*Kanto*/ -> generation = new String[]{"Red and Blue + Remakes","Yellow","Let's Go Games"};
                case 1 /*Johto*/ -> generation = new String[]{"Gold and Silver","HeartGold and SoulSilver"};
                case 2 /*Hoenn*/ -> generation = new String[]{"Ruby and Sapphire","Emerald","Omega Ruby and Alpha Sapphire"};
                case 3 /*Sinnoh+Hisui*/ -> generation = new String[]{"Diamond and Pearl","Platinum","Legends Arceus"};
                case 4 /*Unova*/ -> generation = new String[]{"Black and White","Black 2 and White 2"};
                case 5 /*Kalos*/ -> generation = new String[]{"X and Y","Legends ZA"};
                case 6 /*Alola*/ -> generation = new String[]{"Sun and Moon","Ultra Sun and Ultra Moon"};
                case 7 /*Galar*/ -> generation = new String[]{"Sword and Shield","Isle of Armor","Crown Tundra"};
                case 8 /*Paldea*/ -> generation = new String[]{"Scarlet and Violet","Kitakami","Indigo Disk"};
            }
            dlcSelector.setModel(new DefaultComboBoxModel<>(generation));
            dlcSelector.setSelectedIndex(0);
            mainFrame.revalidate(); // Resets mainFrame
        });
        //Image
        JTextField idTest = new JTextField(3);
        JLabel pokemonImage = new JLabel(testPokemon.name),
        idTestLabel = new JLabel("Input Pokedex Number:");
        middlePanel.add(idTestLabel);
        middlePanel.add(idTest);
        idTest.addActionListener((ActionEvent e) -> {pokemonImage.setIcon(makeImage(idTest.getText()));});
        pokemonImage.setIcon(makeImage(testPokemon.national));
        // Add to Panels
        headerPanel.add(generationSelector);
        headerPanel.add(dlcSelector);
        bottomPanel.add(pokemonImage);
        bottomPanel.add(caughtSeen[0]);
        bottomPanel.add(caughtSeen[1]);
        // Normal Operations for Mainframe
        headerPanel.setBackground(Color.WHITE);
        middlePanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(Color.WHITE);
        mainFrame.setVisible(true);
    }
    //Get Image with Website
    public ImageIcon makeImage(String dexNumber){
        try{Integer.valueOf(dexNumber);}
        catch(NumberFormatException e){dexNumber = "001";}
        switch (dexNumber.length()) {
            case 0 -> dexNumber = "001";
            case 1 -> dexNumber = "00" + dexNumber;
            case 2 -> dexNumber = "0" + dexNumber;
            case 4 -> dexNumber = (Double.parseDouble(dexNumber) > 1025) ? "001" : dexNumber;
        }
        try {
            BufferedImage image = ImageIO.read(new URL("https://serebii.net/scarletviolet/pokemon/new/" + dexNumber + ".png"));
            return new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        }catch (MalformedURLException e) {}
        catch (IOException ex) {Logger.getLogger(swingDex.class.getName()).log(Level.SEVERE, null, ex);}
        return null;
    }
}