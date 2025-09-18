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
        System.out.println("\t\t\tPour fermer le programme numéro -1");
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
        int choix1;
        do {
            choix1 = scan.nextInt();
        } while (choix1 != 1 && choix1 != 2);
        switch (choix1) {
            case 1:
                ;
            case 2:
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
        System.out.println("\t\t\tPour revenir au menu principal, entrez '5'.");
        System.out.println("\t\t\tPour fermer le programme, entrez '6'.");
        int choix;
        do {
            System.out.print("entre le choix  : ");
            choix = scan.nextInt();
            switch (choix) {
                case 1:
                    System.out.println("******************************************************************************\n  tout  les operation   \n");
                    for (Operation operation : list)
                        System.out.println(operation.toString());
                    ;
                    break;
                case 2:operationRetrait();break;
            }
        } while (choix < 1 || choix > 6);
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
        //Compte compte = (Compte) comptes.get(compteConnect);
        //compteController.updateSolde(compte,  montant);
    }

}

