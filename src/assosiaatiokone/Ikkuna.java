/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assosiaatiokone;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author hurvittelu
 */
public class Ikkuna extends JFrame {
    
    private JComboBox asiat;

    private String[] aasiat = {"peruna", "makkara", "juusto"};
    private JScrollPane pane;
    private JTextArea area;
    private JPanel ylapaneeli;
    private JButton lisaa;
    private JTextField kentta;
    private JRadioButton comboOn;
    private JRadioButton kenttaOn;
    private ButtonGroup radioRyhma;
    private DefaultComboBoxModel boxmodel;
    private JButton tietokantaan;
    private JButton tietokannanTiedot;
    private JPanel alapaneeli;
    private JButton tallenna;
            
    private Tietokanta tietokanta;
    private Kori kori;
    
    public Ikkuna()
    {
        super("lol");
        this.setPreferredSize(new Dimension(800, 600));
        this.setLayout(new BorderLayout());
        
        try
        {
            tietokanta = (Tietokanta) avaaTietokanta();
        } catch (Exception e)
        {
            e.printStackTrace();
            tietokanta = new Tietokanta();
        }
        
        
        area = new JTextArea(30, 30);
        pane = new JScrollPane(area);
        ylapaneeli = new JPanel();
        alapaneeli= new JPanel();

        try
        {
            boxmodel = (DefaultComboBoxModel) lataaOstosAsiat();
        } 
        catch (Exception e)
        {
            boxmodel = new DefaultComboBoxModel(aasiat);
        }
         
        asiat = new JComboBox(boxmodel);
        asiat.setPreferredSize(new Dimension(100, 30));
        lisaa = new JButton("Lisää ostos koriin");
        comboOn = new JRadioButton();
        comboOn.setSelected(rootPaneCheckingEnabled);
        kenttaOn = new JRadioButton();
        radioRyhma = new ButtonGroup();
        radioRyhma.add(comboOn);
        radioRyhma.add(kenttaOn);
        kentta = new JTextField(null, 10);
        kentta.setEnabled(false);
        tietokantaan = new JButton("Tietokantaan");
        tietokannanTiedot = new JButton("Tietokannan tiedot");
        tallenna = new JButton("Tallenna");
        
        Kuuntelija kuulo = new Kuuntelija();
        comboOn.addItemListener(kuulo);
        kenttaOn.addItemListener(kuulo);
        tietokantaan.addActionListener(kuulo);
        tietokannanTiedot.addActionListener(kuulo);
        tallenna.addActionListener(kuulo);
                
        lisaa.addActionListener(kuulo);
        
        alapaneeli.add(tietokantaan);
        alapaneeli.add(tietokannanTiedot);
        alapaneeli.add(tallenna);
        ylapaneeli.add(comboOn);
        ylapaneeli.add(asiat);
        ylapaneeli.add(kenttaOn);
        ylapaneeli.add(kentta);
        ylapaneeli.add(lisaa);
        
        this.add(ylapaneeli, BorderLayout.NORTH);
        this.add(pane,BorderLayout.CENTER);
        this.add(alapaneeli, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
    }
       
    
    private class Kuuntelija implements ActionListener, ItemListener{

    @Override
    public void actionPerformed(ActionEvent e) {
       
        
        if (e.getSource().equals(lisaa))
        {
             if (kori == null)
            {
                kori = new Kori();
            }
            if (comboOn.isSelected())
            {
                String asia = (String)asiat.getSelectedItem();
                area.append(asia+", ");
                kori.lisaaAsia(asia);
            }
            else
            {
                if (!kentta.getText().equals(""))
                {
                    String asia = kentta.getText();
                    boxmodel.addElement(asia);
                    area.append(asia+", ");
                    kentta.setText("");
                    kori.lisaaAsia(asia);
                }
            }
       
        }
       
        if (e.getSource().equals(tietokantaan))
        {
            kori = poistaDublikaatit(kori);
            tietokanta.addKori(kori);
            area.append("\nLisättiin tietokantaan kori: ");
            ArrayList tiedot = kori.getKori();
            for (int i = 0; i < tiedot.size(); i++) {
                area.append(tiedot.get(i)+", ");
                
            }
            area.append("\n");
            kori = null;
        }
        if (e.getSource().equals(tietokannanTiedot))               
        {                   
            ArrayList<Kori> korit = tietokanta.getKorit();
            area.append("Tietokannassa on tällä hetkellä seuraavat korit: \n");
            for (int i = 0; i < korit.size(); i++) {
                
                Kori kori = korit.get(i);
                area.append((i+1)+". kori: "+kori.toString()+"\n");               
            }      
        }
        
        if (e.getSource().equals(tallenna))  
        {
                try {
                    tallennaTietokanta();
                } catch (FileNotFoundException ex) {
                    
                } catch (IOException ex) {
                   
                }
        }
    }
    
    private Kori poistaDublikaatit(Kori k)
    {
        k.jarjestaKori();
        for (int i = 0; i < k.getKori().size()-1; i++) {
            if (k.getAsia(i).equals(k.getAsia(i+1)))
            {
                k.poistaAsia(i);
                i--;
            }          
        }
        
        return k;
    }

        @Override
        public void itemStateChanged(ItemEvent e) {
           
            if (e.getSource().equals(comboOn))
            {
            
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    asiat.setEnabled(true);
                    kentta.setEnabled(false);
          
                } 
            }
            
            if (e.getSource().equals(kenttaOn))  {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    asiat.setEnabled(false);
                    kentta.setEnabled(true);         
                }               
            }           
        }      
    }
    
    public void tallennaTietokanta() throws FileNotFoundException, IOException
    {
        FileOutputStream fos = new FileOutputStream("db.obj");
        FileOutputStream fos2 = new FileOutputStream("asiat.obj");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
        oos.writeObject(tietokanta);
        oos2.writeObject(boxmodel);
        oos.close();
        oos2.close();
        tietokanta.laskeSaannot();
    }
    
    public Object avaaTietokanta() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream("db.obj");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object db = ois.readObject();
        ois.close();
        return db;
    }
    
    public Object lataaOstosAsiat() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream("asiat.obj");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object asiat = ois.readObject();
        ois.close();
        return asiat;
    }
    
}
    
