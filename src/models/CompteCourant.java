package models;

import java.util.ArrayList;

public class CompteCourant extends Compte {
    private double decouvert;

    public CompteCourant(String code) {
        super.setCode(code);
        this.solde = 0;
        decouvert = 0;
        this.listeOperations = new ArrayList<>();
    }

    public CompteCourant(String code, Double decouvert) {
        super.setCode(code);
        this.solde = 0;
        this.decouvert = decouvert;
        this.listeOperations = new ArrayList<>();
    }

    public void setDecouvert(double decouvert) {
        this.decouvert = decouvert;
    }

    public double getDecouvert() {
        return decouvert;
    }

    public void retirer(double montant) {
    }

    public double calculerInteret() {
        return 0;
    }

    public void afficherDetails() {
        System.out.println("***************************\t\tBienvenue dans votre compte\t\t***************************");
        System.out.println(" \t\t type de compte\t\t: compte Courant");
        System.out.println(toString());
        System.out.print("decouvert  : "+decouvert);
    }
}
