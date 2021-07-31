/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author user
 */
public class Upload {

    private final static int MAXIMUM_PAYLOAD_SIZE = 5000000;
    private final static String PATH = "C:\\Program Files (x86)\\M4Cloud\\";

    public void action(File file) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] buffer = new byte[MAXIMUM_PAYLOAD_SIZE];
        int read;
        while ((read = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, read);
        }

        Pacote pkt = new Pacote(0, 0, bos.toByteArray(), file.getName(), Pacote.UPLOAD, false);

        fis.close();
        bos.close();

        try {
            for (File file : fragmentarPacote(pacote)) {
                Socket socket = new Socket(Client.IP, Client.PORT);

                Pacote pkt = readPacketFromFile(file);
                file.delete();

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

    private List<File> fragmentarPacote(Pacote pacote) throws FileNotFoundException, IOException {
        List<File> result = new LinkedList<>();

        int fileSize = pacote.getFileBytes().length;
        int fragmentOffset = 0;

        byte[] newArray;
        if (pacote.getFileBytes().length > MAXIMUM_PAYLOAD_SIZE) {
            while (fragmentOffset < fileSize) {
                //copia os bytes desde o fragment offset até à sua soma com o valor de payload maximo.
                //caso essa soma seja maior que o total de bytes, copia apenas até ai.
                newArray = Arrays.copyOfRange(pacote.getFileBytes(), fragmentOffset, Math.min(fragmentOffset + MAXIMUM_PAYLOAD_SIZE, fileSize));

                fragmentOffset += MAXIMUM_PAYLOAD_SIZE;

                File file = writeObject(new Pacote(fragmentOffset / MAXIMUM_PAYLOAD_SIZE, fragmentOffset, newArray, pacote.getName(), pacote.getComando(),
                        fragmentOffset + MAXIMUM_PAYLOAD_SIZE < fileSize));
                result.add(file);
            }
        }

        //se o pacote nao precisar de ser fragmentado
        if (result.isEmpty()) {
            File file = writeObject(pacote);
            result.add(file);
        }

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
