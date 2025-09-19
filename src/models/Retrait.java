package models;

import java.util.Date;

public class Retrait  extends Operation {
    private  String distination ;
    public Retrait(int numero , double montant , Date date ,String distination){
        this.distination  =  distination;
        this.date  =  date ;
        this.montant  =  montant;
        this.numero  =  numero;
    }

    public String getDistination() {
        return distination;
    }

    public void setDistination(String distination) {
        this.distination = distination;
    }
    @Override
    public String toString(){
        return  "type de operation  : Retrait  avec  distination :"+distination+"\n"+super.toString();
    }
}
