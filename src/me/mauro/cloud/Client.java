/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author user
 */
public class Client {

    public static final String IP = "localhost";
    public static final int PORT = 53152;

    public static Client instance;
    private final User user;

    private Client(User user) {
        this.user = user;
    }

    //Só o primeiro user é registado na instancia.
    public static Client getInstance(User user) {
        if (instance == null) {
            instance = new Client(user);
        }

        return instance;
    }

    public void upload(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new IllegalArgumentException("O file é invalido");
        }

        new EnviarFicheiro().enviar(file, this.user);
    }

    public List<String> getFilesList() throws IOException, ClassNotFoundException {
        return new ListServerFiles().listar(this.user);
    }

    public void download(String nome) throws IOException, ClassNotFoundException {
        new ReceberFicheiro().action(nome, this.user);
    }

}
