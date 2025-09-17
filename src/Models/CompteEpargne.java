package Models;

import java.util.ArrayList;
import java.util.Random;

public class CompteEpargne extends Compt {
    public CompteEpargne(){
        Random  rand  =  new Random();
        this.code = "CPT-" + String.format("%05d", rand.nextInt(100000));
        this.solde = 0;
        this.listeOperations   =   new ArrayList<>();

    }
    public void  setCode (){
        Random  rand  =  new Random();
        String   code   = "CPT-" + String.format("%05d", rand.nextInt(100000));

    }
    public   void retirer(){}
    public      void calculerInteret(){}
    public    void afficherDetails(){}
}
