/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import me.mauro.cloud.pacotes.Pacote;
import me.mauro.cloud.pacotes.UploadPacote;

/**
 *
 * @author user
 */
public class ReceberFicheiro {

    public static void receber(Pacote pacote, Socket socket, ObjectInputStream ois) {
        UploadPacote pkt = (UploadPacote) pacote;
        File file = getFile(pkt);

        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            createDir(file);
            fos.write(pkt.getFileBytes(), 0, pkt.getReadBytes());

            while (pkt.haveMoreFragments()) {
                pkt = (UploadPacote) ois.readObject();
                fos.write(pkt.getFileBytes(), 0, pkt.getReadBytes());
                fos.flush();
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void createDir(File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    //exemplo: caso ja existe um ficheiro com o nome "bbb.txt", a funçao gera o nome "bbb(1).txt"
    private static File getFile(UploadPacote pacote) {
        String name = Server.STORAGE_PATH + pacote.getUser().getNome() + "\\" + pacote.getName();

        int i = 1;
        String[] split = name.split("\\.");
        String extension = split[split.length - 1];

        File file = new File(name);
        while (file.exists() && !file.isDirectory()) {
            file = new File(name.replace("." + extension,
                    String.format("(%d).%s", i++, extension)));
        }

        return file;
    }
}
