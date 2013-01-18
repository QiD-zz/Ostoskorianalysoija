/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assosiaatiokone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author hurvittelu
 */
public class Kori implements Serializable{
    
    private OstosLista asiat;
    
    public Kori()
    {
        asiat = new OstosLista();
    }
    
    public Kori(OstosLista lista)
    {
        Collections.sort(lista);
        for (int i = 0; i < lista.size()-1; i++)
        {
            if (lista.get(i).equals(lista.get(i+1)))
            {
                lista.remove(i);
                i--;
            }          
        }
        asiat = lista;
    }
    
    public void lisaaAsia(String asia)
    {
        asiat.add(asia);
    }
    
    public OstosLista getKori()
    {
        return asiat;
    }
    
    public Object getAsia(int i)
    {
        return asiat.get(i);
    }
    
    @Override
    public String toString()
    {
        String tiedot = "";
        for (int i = 0; i < asiat.size(); i++) {
            tiedot = tiedot.concat(asiat.get(i).toString()+", ");
            
        }
        return tiedot;
    }
    
    public void jarjestaKori()
    {
        Collections.sort(asiat);
    }

    public void poistaAsia(int i) {
        asiat.remove(i);
    }
    
}
