package models;

import java.util.Date;

public class Versement extends Operation {
    protected String source ;
    public Versement(int numero , double montant, Date date,String source){
        this.date=date;
        this.numero=numero;
        this.montant=montant;
        this.source=source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "type de operatoin  :  versement  avec source  :  "+source+"\n"+super.toString();
    }
}
