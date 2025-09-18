package controllers;

import services.OperationService;

public class OperationController {
    private OperationService   service ;
    public OperationController(){
        service = new OperationService();
    }
    public void createRetrait(String distination ,String code,double montant){
        service.createRetrait(distination ,code,montant);
    }
    public void createVersement(String source ,String  code  ,double  montant){
        service.createVersement(source,code,montant);
    }
}
