/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author user
 */
public class CloudClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = Client.getInstance(new User("mauro", "mauro"));
//        client.upload(new File("C:\\Users\\user\\Desktop\\filme.mp4"));
//        client.upload(new File("C:\\Users\\M4UR0\\Desktop\\Pastas\\What every body say.pdf"));
        client.upload(new File("C:\\Users\\M4UR0\\Downloads\\Krav Maga - Caveira.rar"));
//        client.upload(new File("C:\\Users\\M4UR0\\Downloads\\Rocket Power CyanideSB.zip"));
//        client.upload(new File("C:\\Users\\user\\Desktop\\Resumo Redes.pdf"));

//        Socket socket = new Socket(Client.IP, Client.PORT);
//        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//
//        for (int i = 0; i < 10; i++) {
//            oos.writeInt(i);
////            oos.flush();
//        }
//
//        oos.close();
//        socket.close();
    }

}
