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

    public void upload(File file) throws IOException {
        new Upload().action(file);
    }

}
