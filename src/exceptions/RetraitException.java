package exceptions;

public class RetraitException  extends   Exception{
    public RetraitException(){
        super("errore dans opetaion retrait ");
    }
    public  RetraitException(String mes){
        super(mes);
    }

}
