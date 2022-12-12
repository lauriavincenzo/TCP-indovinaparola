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
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import static java.nio.file.Files.lines;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Server {

    static List<ClientHandler> clients;
    ServerSocket serverSocket;
    static int numOfUsers = 0;
    Socket socket;
    static String p = "";
    Indovina indovina;
    static public List<String> nome;
    static public List<Integer> mosse;
    
    public static String[] vettore;

    public Server() {
        clients = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(Constants.PORT);
        } catch (IOException ex) {
            log("Server : " + ex.getMessage());
        }

        indovina = new Indovina();
        nome = new ArrayList<>();
        mosse = new ArrayList<>();
        vettore = new String[10];
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.watiConnection();

    }

    private void watiConnection() {
        log("Server Running...");
//        p=aprifile();
        System.out.println(p);
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException ex) {
                log("waitConnection : " + ex.getMessage());
            }

            log("Client accepted : " + socket.getInetAddress());
            numOfUsers++;

            ClientHandler handler = new ClientHandler(socket, "user" + numOfUsers, indovina);

            Thread thread = new Thread(handler);
            addClient(handler);
            thread.start();
        }
    }

    public static void ordinaclassifica() {
        for (int i = 0; i < nome.size()-1; i++) {
            for (int j = 0; j < nome.size() - 2; j++) {
                if (mosse.get(i) > mosse.get(i + 1)) {
                    Integer temp = mosse.get(i);
                    mosse.set(i, mosse.get(i + 1));
                    mosse.set(i + 1, temp);
                    String temp2 = nome.get(i);
                    nome.set(i, nome.get(i + 1));
                    nome.set(i + 1, temp2);
                }
            }
        }
        if(nome.size()>10)
        {
         for (int i = 0; i < 10; i++) {
            
            vettore[i] = nome.get(i) + "," + mosse.get(i);
         }
        }else{
           for (int i = 0; i < nome.size(); i++) {
            vettore[i] = nome.get(i) + "," + mosse.get(i);
         }     
       }   
        
       }

    

    public static void leggi() {
        char virgola = ',';

        File f = new File("src/" + "top10" + ".txt");
//        ArrayList<String> list = new ArrayList<>();
        try {
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
//                list.add(line);

                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == virgola) {

                       nome.add(line.substring(0, i));
                       int j=i+1;
                       mosse.add(Integer.parseInt(line.substring(j, line.length())));
                       
//                        System.out.println(line.substring(i+1, line.length())+"-");
//                        System.out.println(line.substring(0, i)+"-");
                    }
                }
            }
            s.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        ordinaclassifica();
        salva(f);
    }

    public static void salva(File f) {
        try {
            PrintWriter printwriter = new PrintWriter(f);
            for (String i : vettore) {
                printwriter.println(i);
            }
            printwriter.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<ClientHandler> getClients() {
        return clients;
    }

    private void addClient(ClientHandler client) {
        clients.add(client);
    }

    private void log(String message) {
        System.out.println(message);
    }
}
