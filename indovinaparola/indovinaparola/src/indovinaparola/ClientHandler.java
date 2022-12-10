/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package indovinaparola;

/**
 *
 * @author sireci_edoardo
 */
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable{
    final Socket socket;
    final Scanner scan;
    String name;
    boolean isLosggedIn;
    String ptemporanea="";
    Indovina indovina;
    
    private DataInputStream input;
    private DataOutputStream output;
    
    public ClientHandler(Socket socket, String name,Indovina indovina){
        this.socket = socket;
        scan = new Scanner(System.in);
        this.name = name;
        this.indovina=indovina;
        isLosggedIn = true;
        String provvisorio=indovina.getParola();
        for(int i=0;i<provvisorio.length();i++)
        {
            ptemporanea+="*";
        }
        
        
        try{
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            
        }catch(IOException ex){
            log("ClientHander : " + ex.getMessage());
        }
    }
    @Override
    public void run() {
        String received;
        String tmp="";
        write(output, "Your name : " + name);
        for(ClientHandler c : Server.getClients()){
            tmp+=c.name+",";
        }
        write(output, "Client attivi : " + tmp);
        while(true){
            received = read();
            if(received.equalsIgnoreCase(Constants.LOGOUT)){
                this.isLosggedIn = false;
                closeSocket();
                closeStreams();
                break;
            }
            
            forwardToClient(received);
        }
        closeStreams();
    }
    
    private void forwardToClient(String received){
        // username # message
//        StringTokenizer tokenizer = new StringTokenizer(received, "#");
//        String recipient = tokenizer.nextToken().trim();
//        String message = tokenizer.nextToken().trim();

        //ptemporanea è la parola attuale in gioco, che aggiorni man mano nel gioco
        //riempi parola è il metodo per sostituire gli * ai caratteri inseriti
        ptemporanea=indovina.controllaparola(ptemporanea, received);
        for(ClientHandler c : Server.getClients()){
            if(c.isLosggedIn){
                write(c.output,ptemporanea);
                log(received);
            }
        }
        
    }
    
    private String read(){
        String line = "";
        try {
            line = input.readUTF();
        } catch (IOException ex) {
            log("read : " + ex.getMessage());
        }
        return line;
    }
    
    private void write(DataOutputStream output , String message){
        try {
            output.writeUTF(message);
        } catch (IOException ex) {
            log("write : " + ex.getMessage());
        }
    }
    
    private void closeStreams() {
        try {
            this.input.close();
            this.output.close();
        } catch (IOException ex) {
            log("closeStreams : " + ex.getMessage());
        }
    }
  
    private void closeSocket(){
        try{
            socket.close();
        }catch(IOException ex){
            log("closeSocket : " + ex.getMessage());
        }
    }
   
    private void log(String msg){
        System.out.println(msg);
    }
}
