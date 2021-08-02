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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author user
 */
public class EnviarFicheiro {

    private final static int MAXIMUM_PAYLOAD_SIZE = 10000000;
    private final static String PATH = "C:\\Program Files (x86)\\M4Cloud\\";

    public void enviar(File file, User user) throws FileNotFoundException, IOException {
        try {
            for (File currentFile : fragmentarFile(file, user)) {
                Socket socket = new Socket(Client.IP, Client.PORT);
                socket.
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                Pacote pkt = readPacketFromFile(currentFile);
                oos.writeObject(pkt);

                currentFile.delete();
                oos.close();
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

    private List<File> fragmentarFile(File file, User user) throws FileNotFoundException, IOException {
        List<File> result = new LinkedList<>();
        int identifier = Pacote.nextIdentifier();
        int fragment = 0;
        int offset = 0;

        FileInputStream fis = new FileInputStream(file);

        int readlen = (int) Math.min(MAXIMUM_PAYLOAD_SIZE, file.length());
        byte[] buffer;
        while (readlen != 0) {
            buffer = new byte[readlen];
            fis.read(buffer);

            //se o nao houver espaço para mais um MAXIMUM_PAYLOAD_SIZE, utiliza-se a difrença do tamanho do ficheiro e o que ja foi analisado.
            readlen = (int) ((fragment + 2) * MAXIMUM_PAYLOAD_SIZE > file.length() ? file.length() - fragment * MAXIMUM_PAYLOAD_SIZE - readlen : MAXIMUM_PAYLOAD_SIZE);

            //colocar num file o ficheiro (para enviar) fragmentado
            UploadPacote pkt = new UploadPacote(identifier, fragment, offset, buffer.clone(), file.getName(), readlen != 0, user);
            result.add(writeObject(pkt));

            offset += readlen;
            fragment++;
        }

        fis.close();

        return result;
    }

    private File writeObject(UploadPacote pacote) throws FileNotFoundException, IOException {
        File file = File.createTempFile(PATH + pacote.getName() + pacote.getIdentifier() + "_" + pacote.getFragment(), ".tmp");
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(pacote);

        fos.close();
        oos.close();

        return file;
    }
}
