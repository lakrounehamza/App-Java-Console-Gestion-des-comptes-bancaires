package models;

import java.util.ArrayList;

public class CompteEpargne extends Compte {
    private double tauxInteret;

    public CompteEpargne(String code, double tauxInteret) {
        super.setCode(code);
        this.solde = 0;
        this.listeOperations = new ArrayList<>();
        this.tauxInteret = tauxInteret;
    }

    public CompteEpargne(String code, double tauxInteret, double solde) {
        super.setCode(code);
        this.solde = solde;
        this.listeOperations = new ArrayList<>();
        this.tauxInteret = tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public void retirer(double montant) {
        if (solde < montant) {
            System.out.print("Erreur : solde insuffisant pour effectuer cette opération ");
        } else {
            solde -= montant;
            System.out.print("Opération effectuée avec succès");
        }
    }


    @Override
    public double calculerInteret() {
        return solde * tauxInteret / 100;
    }

    public void afficherDetails() {
        System.out.println("***************************\t\tBienvenue dans votre compte\t\t***************************");
        System.out.println(" \t\t type de compte\t\t: compte Epargne");
        System.out.println(toString());
        System.out.print("tauxInteret   de  compte  :"+tauxInteret);

    }


}
