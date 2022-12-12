/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package indovinaparola;

/**
 *
 * @author sireci_edoardo
 */
//user a cui mandi il messaggio, #messaggio, vedi a capo:
//user2#ciao
//messaggio indirizzato a user2

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    static List<ClientHandler> clients;
    ServerSocket serverSocket;
    static int numOfUsers = 0;
    Socket socket;
    static String p="";
    Indovina indovina;
    static public  List<String> nomiutenti;
    static public List<Integer> nmosse;
    
    
    public Server(){
        clients = new ArrayList<>();
        try{
            serverSocket = new ServerSocket(Constants.PORT);
        }catch(IOException ex){
            log("Server : " + ex.getMessage());
        }      
        
        indovina=new Indovina();
        nomiutenti=new ArrayList<>();
        nmosse=new ArrayList<>();
        
    }
        
    
    public static void main(String[] args){
        Server server = new Server();
        server.watiConnection();
        
        
        //crei lista con nome e punteggio
        
    }
    
    private void watiConnection(){
        log("Server Running...");
//        p=aprifile();
        System.out.println(p);
        while(true){
            try{
                socket = serverSocket.accept();
            }catch(IOException ex){
                log("waitConnection : " + ex.getMessage());
            }
            
            log("Client accepted : " + socket.getInetAddress());
            numOfUsers++;
            
            ClientHandler handler = new ClientHandler(socket, "user" + numOfUsers,indovina);
            
            Thread thread = new Thread(handler);
            addClient(handler);
            thread.start();
        }
    }
    
    
    public  static List<ClientHandler> getClients(){
        return clients;
    }

    private void addClient(ClientHandler client){
        clients.add(client);
    }
    private void log(String message) {
        System.out.println(message)        ;
    }
}
