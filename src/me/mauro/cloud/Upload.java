/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author user
 */
public class Upload {

    private final static int MAXIMUM_PAYLOAD_SIZE = 2000000;
    private final static String PATH = "C:\\Program Files (x86)\\M4Cloud\\";

    public void action(File file) throws FileNotFoundException, IOException {
        try {
            for (File currentFile : fragmentarFile(file)) {
                Socket socket = new Socket(Client.IP, Client.PORT);

                Pacote pkt = readPacketFromFile(currentFile);
                currentFile.delete();

                OutputStream os = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);

                oos.writeObject(pkt);

                oos.close();
                os.close();
                socket.close();
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Pacote readPacketFromFile(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Pacote pkt = (Pacote) ois.readObject();

        fis.close();
        ois.close();

        return pkt;
    }

    private List<File> fragmentarFile(File file) throws FileNotFoundException, IOException {
        List<File> result = new LinkedList<>();
        int identifier = Pacote.nextIdentifier();
        int fragment = 0;
        int offset = 0;

        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[MAXIMUM_PAYLOAD_SIZE];
        //substituir o read() pelo read com o quanto vou ler
        while (fis.read(buffer) != -1) {
            Pacote pkt = new Pacote(fragment++, offset, buffer.clone(), file.getName(), Pacote.UPLOAD, offset + MAXIMUM_PAYLOAD_SIZE < file.length());
            offset += MAXIMUM_PAYLOAD_SIZE;
            result.add(writeObject(pkt));
        }

        fis.close();

        return result;
    }

    private File writeObject(Pacote pacote) throws FileNotFoundException, IOException {
        File file = File.createTempFile(PATH + pacote.getName() + pacote.getIdentifier() + "_" + pacote.getFragment(), ".tmp");
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(pacote);

        fos.close();
        oos.close();

        return file;
    }
}
