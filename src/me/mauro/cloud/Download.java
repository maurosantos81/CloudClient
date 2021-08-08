/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.mauro.cloud.pacotes.DownloadPacote;
import me.mauro.cloud.pacotes.Pacote;

/**
 *
 * @author M4UR0
 */
public class Download {

    public void donwload(User user, String fileName) {
        try (Socket socket = new Socket(Client.IP, Client.PORT);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            //enviar objeto
            oos.writeObject(new DownloadPacote(Pacote.nextIdentifier(), user, fileName));

            ReceberFicheiro.receber(pacote, socket, ois);
            
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
