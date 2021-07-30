/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.IOException;
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
public class Upload implements Comando {

    private final static int MAXIMUM_PAYLOAD_SIZE = 30000;

    @Override
    public void action(Pacote pacote) {
        try {
            for (Pacote pkt : fragmentarPacote(pacote)) {
                System.out.println("tomei");
                Socket socket = new Socket(Client.IP, Client.PORT);
                OutputStream os = socket.getOutputStream();

                ObjectOutputStream oos = new ObjectOutputStream(os);

                oos.writeObject(pkt);

                oos.close();
                os.close();
                socket.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<Pacote> fragmentarPacote(Pacote pacote) {
        List<Pacote> result = new LinkedList<>();

        int fileSize = pacote.getFileBytes().length;
        int fragmentOffset = 0;

        byte[] newArray;
        if (pacote.getFileBytes().length > MAXIMUM_PAYLOAD_SIZE) {
            while (fragmentOffset < fileSize) {
                newArray = Arrays.copyOfRange(pacote.getFileBytes(), fragmentOffset, fragmentOffset + MAXIMUM_PAYLOAD_SIZE);
                result.add(new Pacote(fragmentOffset / MAXIMUM_PAYLOAD_SIZE, fragmentOffset, newArray, pacote.getName(), pacote.getComando(), 
                        fragmentOffset + MAXIMUM_PAYLOAD_SIZE < fileSize));
                fragmentOffset += MAXIMUM_PAYLOAD_SIZE;
            }
        }
        
        if (result.isEmpty()){
            result.add(pacote);
        }
        
        return result;
    }
}