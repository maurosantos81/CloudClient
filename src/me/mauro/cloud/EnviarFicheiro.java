/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import me.mauro.cloud.pacotes.UploadPacote;
import me.mauro.cloud.pacotes.Pacote;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author user
 */
public class EnviarFicheiro {

    public void enviar(File file, User user) throws FileNotFoundException, InterruptedException {
        final double maxSize = Runtime.getRuntime().freeMemory() * 0.25;

        int identifier = Pacote.nextIdentifier();
        int fragment = 0;
        int read;
        byte[] buffer = new byte[(int) maxSize];

        try (Socket socket = new Socket(Client.IP, Client.PORT);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {

            FileInputStream fis = new FileInputStream(file);
            System.out.println("Enviando o ficheiro");
            while ((read = fis.read(buffer)) != -1) {
                oos.writeObject(
                        new UploadPacote(identifier, fragment++, read, buffer, file.getName(), fis.available() != 0, user));
                oos.flush();

                Thread.sleep((long) (maxSize * Math.pow(10, -6)));
            }

            System.out.println("Envio terminado!");
            fis.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
