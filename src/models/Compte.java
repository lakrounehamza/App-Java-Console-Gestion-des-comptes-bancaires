package models;

import java.util.ArrayList;
import java.util.List;

public abstract class Compte {
    protected String code;
    protected double solde;
    protected ArrayList<Operation> listeOperations;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<Operation> getListeOperations() {
        return listeOperations;
    }

    public void setListeOperations(ArrayList<Operation> listeOperations) {
        this.listeOperations = listeOperations;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public String toString() {
        return "\ncode de compte\t\t:" + code
                + "\nsolde de compte\t\t:" + solde + "\n";
    }

    public abstract double calculerInteret();

    public abstract void retirer(double montant);
}
