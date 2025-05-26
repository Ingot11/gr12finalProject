import java.io.*;
import java.net.*;
import java.awt.*;
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
    private static Pokemon[] lists;
    // Pokedex Window
    public void pokedex(){
        lists = new Pokemon[1025];
        // Get Pokemon from dex.txt
        try(BufferedReader buffRead = new BufferedReader(new FileReader("dex.txt"))){
            String line;
            while ((line = buffRead.readLine()) != null) {
                String[] lines = line.split(", ");
                Pokemon temp = new Pokemon(lines[0], Integer.parseInt(lines[1]));
                lists[Integer.parseInt(lines[1])-1] = temp;
                int[] dexNumber = new int[29];
                for(int i=1; i<dexNumber.length; i++) dexNumber[i-1] = Integer.parseInt(lines[i]);
                temp.inputDex(dexNumber);
            }
            buffRead.close();
        }catch (FileNotFoundException | NumberFormatException e) {System.out.println("File not found or Number Format");}
        catch (IOException ex) {Logger.getLogger(swingDex.class.getName()).log(Level.SEVERE, null, ex);}
        // Completionist Title and Caught and Seen Buttons
        headerPanel.add(new JLabel("Pokedex Completionist"));
        JButton[] caughtSeen = {new JButton("Caught"), new JButton("Seen")};
        // Generation and DLC Selector
        JComboBox<String> generationSelector = new JComboBox<>(new String[]{"Kanto", "Johto", "Hoenn", "Sinnoh/Hisui", "Unova", "Kalos", "Alola", "Galar", "Paldea"}),
        dlcSelector = new JComboBox<>(new String[]{"Regional","Yellow","Let's Go Games"});
        // Generation Chooser Action Listener
        generationSelector.addActionListener((ActionEvent e) -> {
            // Choose Generation
            String[][] generation = {{"Regional", "Yellow", "Let's Go Games"}, /*Kanto*/
            {"Regional", "HeartGold and SoulSilver"}, /*Johto*/
            {"Regional", "Emerald","Omega Ruby and Alpha Sapphire"}, /*Hoenn*/
            {"Regional", "Platinum","Legends Arceus"}, /*Sinnoh + Hisui*/
            {"Regional", "Black 2 and White 2"}, /*Unova*/
            {"Regional", "Central", "Coastal", "Mountain", "Legends ZA"}, /*Kalos*/
            {"Regional", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Regional", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula","Ultra Poni"}, /*Alola*/
            {"Regional", "Isle of Armor", "Crown Tundra"}, /*Galar*/
            {"Regional", "Kitakami", "Indigo Disk"}}; /*Paldea*/
            if(generationSelector.getSelectedIndex() > generation.length) generationSelector.setSelectedIndex(0);
            dlcSelector.setModel(new DefaultComboBoxModel<>(generation[generationSelector.getSelectedIndex()]));
            dlcSelector.setSelectedIndex(0);
            mainFrame.revalidate(); // Resets mainFrame
        });
        //Image
        JTextField idTest = new JTextField(3);
        JLabel pokemonImage = new JLabel(lists[737-1].name),
        idTestLabel = new JLabel("Input Pokedex Number:");
        middlePanel.add(idTestLabel);
        middlePanel.add(idTest);
        idTest.addActionListener((ActionEvent e) -> {
            try{
                pokemonImage.setIcon(makeImage(Integer.parseInt(idTest.getText()),"Shiny"));
                if(Integer.parseInt(idTest.getText())<=1025) pokemonImage.setText(lists[Integer.parseInt(idTest.getText())-1].name);
                else pokemonImage.setText(lists[0].name);
            }
            catch(NumberFormatException a){}
        });
        pokemonImage.setIcon(makeImage(lists[737-1].national,"Default"));
        // Add to Panels
        headerPanel.add(generationSelector);
        headerPanel.add(dlcSelector);
        bottomPanel.add(pokemonImage);
        bottomPanel.add(caughtSeen[0]);
        bottomPanel.add(caughtSeen[1]);
        // Normal Operations for Mainframe
        for(Component i:mainFrame.getComponents()) i.setBackground(Color.WHITE);
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