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
    private static swingDex dex;
    // Call Window
    public static void main(String[] args){
        dex = new swingDex();
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
        HashMap<Integer,Pokemon> pokemonList = new HashMap<>();
        Pokemon testPokemon = new Pokemon("Charjabug",737);
        pokemonList.put(737, testPokemon);
        // Generation and DLC Selector
        JComboBox<String> generationSelector = new JComboBox<>(new String[]{"Kanto","Johto","Hoenn","Sinnoh/Hisui","Unova","Kalos","Alola","Galar","Paldea"}),
        dlcSelector = new JComboBox<>(new String[]{"Red and Blue + Remakes","Yellow","Let's Go Games"});
        // Generation Chooser Action Listener
        generationSelector.addActionListener((ActionEvent e) -> {
            // Choose Generation
            String[][] generation = {{"Regional","Yellow","Let's Go Games"}, /*Kanto*/
            {"Regional","HeartGold and SoulSilver"}, /*Johto*/
            {"Regional","Emerald","Omega Ruby and Alpha Sapphire"}, /*Hoenn*/
            {"Regional","Platinum","Legends Arceus"}, /*Sinnoh + Hisui*/
            {"Regional","Black 2 and White 2"}, /*Unova*/
            {"Regional","Legends ZA"}, /*Kalos*/
            {"Regional","Ultra Sun and Ultra Moon"}, /*Alola*/
            {"Regional","Isle of Armor","Crown Tundra"}, /*Galar*/
            {"Regional","Kitakami","Indigo Disk"}}; /*Paldea*/
            if(generationSelector.getSelectedIndex() > generation.length) generationSelector.setSelectedIndex(0);
            dlcSelector.setModel(new DefaultComboBoxModel<>(generation[generationSelector.getSelectedIndex()]));
            dlcSelector.setSelectedIndex(0);
            mainFrame.revalidate(); // Resets mainFrame
        });
        //Image
        JTextField idTest = new JTextField(3);
        JLabel pokemonImage = new JLabel(testPokemon.name),
        idTestLabel = new JLabel("Input Pokedex Number:");
        middlePanel.add(idTestLabel);
        middlePanel.add(idTest);
        idTest.addActionListener((ActionEvent e) -> {
            try{pokemonImage.setIcon(makeImage(Integer.parseInt(idTest.getText()),"Shiny"));}
            catch(NumberFormatException a){}
        });
        pokemonImage.setIcon(makeImage(pokemonList.get(737).national,"Default"));
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
    //Get image from Serebii website
    public ImageIcon makeImage(int dexNumber, String form){
        String dexString = (dexNumber > 1025) ? "001" : (dexNumber > 99) ? "" + dexNumber : (dexNumber > 9) ? "0" + dexNumber : (dexNumber > 0) ? "00" + dexNumber : "001",
        linkText = form.equals("Shiny") ? "Shiny/SV/new/" : "scarletviolet/pokemon/new/";
        try {
            BufferedImage image = ImageIO.read(new URI("https://serebii.net/" + linkText + dexString + ".png").toURL());
            return new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        }catch (URISyntaxException e) {}
        catch (IOException ex) {Logger.getLogger(swingDex.class.getName()).log(Level.SEVERE, null, ex);}
        return null;
    }
}