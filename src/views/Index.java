package views;

import controllers.CompteController;
import controllers.OperationController;
import models.Compte;
import models.CompteCourant;
import models.CompteEpargne;
import models.Operation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class Index {
    private HashMap comptes;
    private CompteController compteController;
    private String compteConnect;
    private Scanner scan;
    private OperationController operationController;

    public Index() {
        compteController = new CompteController();
        operationController = new OperationController();
        comptes = compteController.getAllCompt();
        scan = new Scanner(System.in);
        int choix;
        System.out.println("\t\t*****************************  espace   de  coniction  *****************************");
        System.out.println("\t\t\tPour accéder au compte numéro 1");
        System.out.println("\t\t\tPour créer un compte numéro 2");
//        System.out.println("\t\t\tPour fermer le programme numéro -1");
        do {
            System.out.print("donnez  moi  votre le choix : ");
            choix = scan.nextInt();
        } while (choix != 1 && choix != 2 && choix != -1);
        if (choix == -1) {
            System.exit(0);
        }
        switch (choix) {
            case 1:
                connected();
                break;
            case 2:
                createCompte();
                break;
        }
    }

    public void createCompte() {
        System.out.println("\t\t*****************************  espace   create compte  *****************************");
        System.out.println("donnez le  type de   compte");
        System.out.println("compte  courant 1 ");
        System.out.println("compte Epargne  2");
        double value;
        int choix1;
        do {
            choix1 = scan.nextInt();
        } while (choix1 != 1 && choix1 != 2);
        switch (choix1) {
            case 1:
                System.out.print("entre le decouvert ");
                value = scan.nextDouble();
                String  codeCompte  =compteController.createCompte("compte Epargne",value);
                System.out.print("compte create  avec sucsse  code ce  votre compte :'"+codeCompte+"'");
                Index index1  = new Index();
                ;
            case 2:
                System.out.print("entre le tauxInteret ");
                value = scan.nextDouble();
                String  codeCompte2 =compteController.createCompte("compte Courant",value);
                System.out.print("compte create  avec sucsse  code ce  votre compte :'"+codeCompte2+"'");

                Index index2  =  new Index();
                ;
        }
    }

    public void connected() {
        System.out.println("\t\t*****************************  conection  *****************************");
        System.out.print("Entrez le code du compte : ");
        while (true) {
            String code = scan.nextLine();
            if (comptes.containsKey(code)) {
                compteConnect = code;
                break;
            }
            System.out.print("ce Code  n'exeste pas  entre  autre   code  : ");
        }
        profile();

    }

    public void profile() {
        Compte compte = (Compte) comptes.get(compteConnect);
        if (compte instanceof CompteCourant) {
            CompteCourant compteCourant = (CompteCourant) compte;
            compteCourant.afficherDetails();
            System.out.print("\nles cinq dernire operation ");
            ArrayList<Operation> list = compteCourant.getListeOperations();
            int conter = 0;
            list.sort(Comparator.comparing(Operation::getDate));
            for (Operation operation : list) {
                System.out.println(operation.toString());
                conter++;
                if (conter == 5)
                    break;
            }
            menuProfile(compte.getListeOperations());
        } else if (compte instanceof CompteEpargne) {
            CompteEpargne compteEpargne = (CompteEpargne) compte;
            compteEpargne.afficherDetails();

            System.out.println("\nles  dernire operation ");
            ArrayList<Operation> list = compteEpargne.getListeOperations();

            int conter = 0;
            list.sort(Comparator.comparing(Operation::getDate));
            for (Operation operation : list) {
                System.out.println(operation.toString());
                conter++;
                if (conter == 5)
                    break;
            }
        }
        menuProfile(compte.getListeOperations());
    }

    public void menuProfile(ArrayList<Operation> list) {
        System.out.println("\t\t***********************************************");
        System.out.println("\t\t\tPour afficher toutes les opérations, entrez '1'.");
        System.out.println("\t\t\tPour faire un retrait, entrez '2'.");
        System.out.println("\t\t\tPour faire un versement, entrez '3'.");
        System.out.println("\t\t\tPour retourner, entrez '4'.");
        //System.out.println("\t\t\tPour revenir au menu principal, entrez '5'.");
//        System.out.println("\t\t\tPour fermer le programme, entrez '5'.");
        int choix;
        do {
            System.out.print("entre le choix  : ");
            choix = scan.nextInt();
        } while (choix < 1 || choix > 4);
        switch (choix) {
            case 1:
                System.out.println("******************************************************************************\n  tout  les operation   \n");
                for (Operation operation : list)
                    System.out.println(operation.toString());
                ;
                break;
            case 2:operationRetrait();break;
            case 3:operationVersement();break;
            case 4: Index index = new Index();
        }
    }

    public void operationRetrait() {
        double montant=0;
        scan.nextLine();
        System.out.print("entre le distination :");
        String distination = scan.nextLine();


        System.out.print("entre le     montant  :");
        //montant = Double.parseDouble(scan.nextLine());
        montant = scan.nextDouble();
        System.out.print("trmine "+montant);
        operationController.createRetrait(distination, compteConnect, montant);
        Compte compte = (Compte) comptes.get(compteConnect);
        compteController.updateSolde(compte,  montant);
    }

    public void operationVersement() {
        double montant = 0;
        scan.nextLine();
        System.out.print("Entrez la source : ");
        String source = scan.nextLine();
        System.out.print("Entrez le montant : ");
        montant = scan.nextDouble();
        System.out.println("Montant saisi : " + montant);
        operationController.createVersement(source, compteConnect, montant);

         Compte compte = (Compte) comptes.get(compteConnect);
         compteController.updateSolde(compte, montant);
    }

}

