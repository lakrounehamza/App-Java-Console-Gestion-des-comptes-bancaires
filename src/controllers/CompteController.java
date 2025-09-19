package controllers;

import models.Compte;
import services.CompteService;
import services.OperationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CompteController {
    private CompteService service;
    private OperationService serviceOp ;

    public CompteController() {
        this.service = new CompteService();
        this.serviceOp   =  new OperationService();
    }

    public void createCompte(String typeCompt, double value) {
        this.service.createCompte(typeCompt, value);
    }

    public HashMap getAllCompt() {
        HashMap  comptes   ;
         comptes  =service.getAllCompt();
         System.out.print(comptes.size());
        for (Object entry : comptes.entrySet()) {
            Map.Entry<String, Compte> e = (Map.Entry<String, Compte>) entry;
            String key = e.getKey();
            Compte compte = e.getValue();
            ArrayList    list  =  serviceOp.getOperationByCodeCompte(key);
            compte.setListeOperations(list);
        }
         return    comptes;
    }

    public void updateSolde(Compte newCompt, double montant) {
        newCompt.retirer(montant);
        service.upadateSolde(newCompt.getCode(), newCompt.getSolde());
    }

}
