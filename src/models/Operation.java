package models;

import java.util.Date;

public abstract class Operation {
    protected    int   numero   ;
    protected    double    montant ;
    protected Date date;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
    public void   setNumero(int numero){
        this.numero = numero;
    }

    public Date getDate() {
        return date;
    }

    public double getMontant() {
        return montant;
    }

    public int getNumero() {
        return numero;
    }

    public String toString() {
        return "numero  :  "+numero+"\nmontant  :  "+montant+"\ndate : "+date.toString();
    }
}
