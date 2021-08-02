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
import java.util.List;
import me.mauro.cloud.pacotes.ListarFilesPacote;
import me.mauro.cloud.pacotes.Pacote;

/**
 *
 * @author user
 */
public class ListServerFiles {

    public List<String> listar(User user) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(Client.IP, Client.PORT);

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        oos.writeObject(new ListarFilesPacote(Pacote.nextIdentifier(), user, null));

        List<String> result = receberResponse(socket);

        oos.close();
        socket.close();

        return result;
    }

    private List<String> receberResponse(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ListarFilesPacote pacote = (ListarFilesPacote) ois.readObject();
        ois.close();
        List<String> result = pacote.getLista();
        return result;
    }

}
