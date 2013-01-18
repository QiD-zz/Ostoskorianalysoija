/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assosiaatiokone;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author hurvittelu
 */
public class OstosLista extends ArrayList{
    
    public OstosLista()
    {
    }
    
    public OstosLista(Object o)
    {
        this.add(o);
    }
    
    public OstosLista(Collection c)
    {
        this.addAll(c);
    }
    
    
    @Override
    public boolean equals(Object obj) {
        
        Boolean totuus = false;
       
        
        if (obj instanceof OstosLista)
        {
            OstosLista toinen = (OstosLista)obj;
            if (this.size() <= toinen.size())
            {
                OstosLista tmp = new OstosLista(this);
                for (int i = 0; i < toinen.size(); i++) {
                    for (int j = 0; j < tmp.size(); j++) {
                        Object a = toinen.get(i);
                        Object b = tmp.get(j);
                     //   System.out.println("a: " + a);
                      //  System.out.println("b: " + b);
                        if (a.equals(b))
                        {
                            tmp.remove(a);
                        }
                        
                    }
                    
                }
                if (tmp.isEmpty())
                {
                    totuus = true;
                }
            }
            return totuus;
        }
        else
        {
            return totuus;
        }
            
    }
    
    public void yhdista(OstosLista toinen)
    {
        OstosLista lisattavat = new OstosLista();
        for (int i = 0; i < toinen.size(); i++) {
            lisattavat.add(toinen.get(i));
            
        }
        for (int i = 0; i < lisattavat.size(); i++) {
            this.add(lisattavat.get(i));
            
        }
        for (int i = 0; i < this.size()-1; i++) {
            if (this.get(i).equals(this.get(i+1)))
            {
                this.remove(i+1);
            }
            
        }
      /*  OstosLista lisattavat = new OstosLista();
        for (int i = 0; i < toinen.size(); i++) {
            Boolean onJo = false;
            for (int j = 0; j < this.size(); j++) {
                Object a = toinen.get(i);
                Object b = this.get(j);
                System.out.println("Yhdista a: " + a);
                System.out.println("Yhdista b: " + b);
                if (a.equals(b))
                {
                    onJo = true;
                }
                
            }
            if (!onJo)
            {
                lisattavat.add(toinen.get(i));
            }
            
        }
        for (int i = 0; i < lisattavat.size(); i++) {
            this.add(lisattavat.get(i));
            
        }*/
    }
    
}
