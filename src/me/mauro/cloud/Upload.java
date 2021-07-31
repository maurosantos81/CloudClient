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
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author user
 */
public class Upload {

    private final static int MAXIMUM_PAYLOAD_SIZE = 10000000;
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

        int transferido = 0;

        int readlen = (int) Math.min(MAXIMUM_PAYLOAD_SIZE, file.length());
        System.out.println(" " +Files.size(file.toPath())  );
//        while (fis.read(buffer) != -1) {
        while (fis.read(buffer, 0, readlen) != -1) {
            Pacote pkt = new Pacote(fragment, offset, buffer.clone(), file.getName(), Pacote.UPLOAD, offset + readlen < file.length());
            offset += MAXIMUM_PAYLOAD_SIZE;
            transferido += readlen;
            result.add(writeObject(pkt));

//            readlen = (int) ((fragment + 2) * MAXIMUM_PAYLOAD_SIZE > file.length() ? file.length() - (fragment + 1) * MAXIMUM_PAYLOAD_SIZE : MAXIMUM_PAYLOAD_SIZE);
//            System.out.println("fragment " + fragment);
//            System.out.println("offset " + (fragment + 1) * MAXIMUM_PAYLOAD_SIZE);
//            System.out.println("transferido " + transferido);
//            System.out.println("buffer " + buffer.length);
//            System.out.println("jj " + (file.length() - (fragment) * MAXIMUM_PAYLOAD_SIZE));
//            System.out.println("readlen " + readlen);
//            System.out.println();

            fragment++;
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
