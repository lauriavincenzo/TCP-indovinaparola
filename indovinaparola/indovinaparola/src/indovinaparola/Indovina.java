/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package indovinaparola;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author sireci_edoardo
 */
public class Indovina {
    
    String parola;
    
    public Indovina(){
        String filename="parole.txt";
        String pathToFile=Paths.get(filename).toAbsolutePath().toString();
        BufferedReader br;
        
        try {
            System.out.println("try");
            br = new BufferedReader(new FileReader(pathToFile));
            Stream<String> lines = br.lines();
            parola = lines.skip(util.Random(0, 7)).findFirst().get();
        } catch (FileNotFoundException ex) {
            System.out.println("catch");
            java.util.logging.Logger.getLogger(Indovina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            parola="errore";
        }
        
        System.out.println(parola);
    }

    public String getParola() {
        return parola;
    }
    public String controllaparola(String attuale,String parolainserita)
    {
        if(parolainserita.length()==1)
        {
            char carattere=parolainserita.charAt(0);
            //client ha inserito un carattere
            if(parola.contains(parolainserita))
            {
                System.out.println("bravo, hai indovinato una lettera");
                for(int i=0;i<parola.length();i++)
                {
                    if(parola.charAt(i)==carattere)
                    {
                        //scrive parola attuale fino al carattere uguale che ha inserito client
                        //scrive carattere al posto giusto
                        //scrive resto parola dalla posizione dopo al carattere in poi
                        attuale=attuale.substring(0,i)+carattere+ attuale.substring(i+1);
                    }
                }
            }else{
                System.out.println("riprova, carattere non presente");
            }
        }else{
           if(parola.equals(parolainserita))
           {
               System.out.println("Hai vinto");
               attuale=parola;
           }
        }
        return attuale;
    }
    
    
    

    }
