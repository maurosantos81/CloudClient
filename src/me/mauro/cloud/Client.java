/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.File;
import java.io.IOException;

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

    public void upload(File file) throws IOException {
        new Upload().enviar(file, this.user);
    }

}
