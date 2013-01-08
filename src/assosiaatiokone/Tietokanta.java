/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assosiaatiokone;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author hurvittelu
 */
public class Tietokanta implements Serializable {
    
    private ArrayList<Kori> rivit;
    private final double SUPPORT = 0.6;
    private int koko;
    
    public Tietokanta()
    {
        rivit = new ArrayList();
        koko = 0;
    }
    
    public void addKori(Kori rivi)
    {
        rivit.add(rivi);
        koko++;
    }
    
    public Object getKori(int index)
    {
        return rivit.get(index);
    }
    
    public ArrayList<Kori> getKorit()
    {
        return rivit;
    }
    
    public void laskeSaannot()
    {
        ArrayList esineet = new ArrayList();
        for (int i = 0; i < rivit.size(); i++) {
            Kori k = rivit.get(i);
            for (int j = 0; j < k.getKori().size(); j++) {
                Object uusiAsia = k.getAsia(j);
                Boolean onJo = false;
                for (int l = 0; l < esineet.size(); l++) {
                    if (uusiAsia.equals(esineet.get(l)))
                    {
                        onJo = true;
                    }
                    
                }
                if (!onJo)
                {
                    esineet.add(uusiAsia);
                }
                
            }
            
        }
        ArrayList<OstosLista> yleiset = new ArrayList();

        int kierros = 1;
        int montakoEhdokasta = esineet.size();
        ArrayList<OstosLista> ehdokkaat = new ArrayList();
        for (int i = 0; i < esineet.size(); i++) {
            OstosLista a = new OstosLista(esineet.get(i));
            ehdokkaat.add(a);
            System.out.println(a);
        }
        Boolean etsitaanEdelleen = !(ehdokkaat.isEmpty());
        while (etsitaanEdelleen)
        {
            ArrayList<OstosLista> vanhatEhdokkaat = new ArrayList();
            ArrayList<OstosLista> uudetYleiset = new ArrayList();
            for (int i = 0; i < ehdokkaat.size(); i++) {
                OstosLista ehdokas = ehdokkaat.get(i);
                int osumia = 0;
                Boolean loytyi = false;
                for (int j = 0; j < rivit.size(); j++) {
                    Kori k = rivit.get(j);         

                    if (ehdokas.equals(k.getKori()))
                    {
                        System.out.println("Ehdokkuus: " + ehdokas);
                        osumia++;
                    }

                }
                System.out.println(ehdokas+ "Osumat: "+ osumia + "S: "+ ((double)(osumia)/ (double)(koko) >= SUPPORT));
                if ((double)(osumia)/ (double)(koko) >= SUPPORT)
                {
                    System.out.println("OLEN YLEINEN: " + ehdokas);
                    yleiset.add(ehdokas);
                    uudetYleiset.add(ehdokas);
                }
                
            }
            for (int i = 0; i < uudetYleiset.size(); i++) {
                System.out.println("OLEN UUSIN YLEINEN: " + uudetYleiset.get(i));
                
            }
            for (int i = 0; i < yleiset.size(); i++) {
                System.out.println("KAIKKI YLEISET: " + yleiset.get(i));
                
            }
            kierros++;
            ehdokkaat.clear();
            ArrayList<OstosLista> uudetEhdokkaat = uudetYleiset;
            for (int i = 0; i < uudetEhdokkaat.size(); i++) {
                for (int j = i+1; j < uudetEhdokkaat.size(); j++) {
                    System.out.println("YHDISTÄN: " + uudetEhdokkaat.get(i)+" JA "+ uudetEhdokkaat.get(j));

                    OstosLista e = new OstosLista(uudetEhdokkaat.get(i));
                    e.yhdista(uudetEhdokkaat.get(j));
                    System.out.println("TULI: " + e);

                    ehdokkaat.add(e);
                     
                }                
            }
            for (int i = 0; i < ehdokkaat.size(); i++) {
                if (ehdokkaat.get(i).size()> kierros)
                {
                    ehdokkaat.remove(i);
                }
                
            }
            if (ehdokkaat.isEmpty())
            {
                etsitaanEdelleen = false;
            }


        }
        for (int i = 0; i < yleiset.size(); i++) {
            System.out.println(yleiset.get(i));
            
            
        }
    }
}
