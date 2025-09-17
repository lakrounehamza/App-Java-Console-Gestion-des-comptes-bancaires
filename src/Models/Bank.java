package Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bank {
    private HashMap<String,Compt>       listeCompt;
    private   List   listInterdte;

    public   Bank () {
        this.listeCompt = new HashMap<String, Compt>();
        listInterdte = new ArrayList<String>();
    }

    public List getListInterdte() {
        return listInterdte;
    }
    public HashMap getListeCompt (){
        return   this.listeCompt;
    }
    public   void  setListeCompt(HashMap list){
        this.listeCompt =  list;
    }
    public  void   setListInterdte(List  list){
        this.listInterdte   =   list;
    }
}
