package Models;

import java.util.List;
import java.util.Random;

public abstract class Compt {
    protected    String  code;
    protected  double solde;
    protected   double   typeCompt;
    protected List<Operation> listeOperations;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        Random rand  =  new Random();
        String   newCode   = "CPT-" + String.format("%05d", rand.nextInt(100000));
     }

    public List<Operation> getListeOperations() {
        return listeOperations;
    }

    public void setListeOperations(List<Operation> listeOperations) {
        this.listeOperations = listeOperations;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
}
