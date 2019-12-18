/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author sahar
 */
public class Vente {
    private int id;
    private int produit;
    private float pu;
    private int qte;
    private float subtotal;
    
    

    public Vente(int produit, float pu, int qte,float subtotal) {
        this.produit = produit;
        this.pu = pu;
        this.qte = qte;
        this.subtotal=subtotal;
        
    }

    @Override
    public String toString() {
        return  produit + "   " + pu + "   " + qte + "   " + subtotal ;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public int getId() {
        return id;
    }

    public int getProduit() {
        return produit;
    }

    public float getPu() {
        return pu;
    }

    public int getQte() {
        return qte;
    }
    
    
           
    
}
