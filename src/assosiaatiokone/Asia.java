/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assosiaatiokone;

/**
 *
 * @author Kuisma
 */
public class Asia {
    
    private int lkm;
    private String nimi;
    private double support;
    private double lift;
    
    public Asia(String name)
    {
        nimi = name;
        lkm = 1;
    }
    
    public Asia(String name, int nro)
    {
        nimi = name;
        lkm = nro;
    }
    
    public void lisaaLkm()
    {
        lkm++;
    }
    
    public void setSupport(double s)
    {
        support = s;
    }
    
    public double getSupport()
    {
        return support;
    }
    
    public void setlift(double l)
    {
        lift = l;
    }
    
    public double getLift()
    {
        return lift;
    }
    
    @Override
    public String toString()
    {
        return nimi;
    }
}
