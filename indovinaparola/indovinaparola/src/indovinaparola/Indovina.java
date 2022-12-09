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
    
    public void Indovina(){
        String filename="parole.txt";
        String pathToFile=Paths.get(filename).toAbsolutePath().toString();
        BufferedReader br;
        
        try {
            br = new BufferedReader(new FileReader(pathToFile));
            Stream<String> lines = br.lines();
            parola = lines.skip(util.Random(0, 661563)).findFirst().get();
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Indovina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            parola="";
        }
        
        System.out.println(parola);
    }

    public String getParola() {
        return parola;
    }
    
    
    

    }
