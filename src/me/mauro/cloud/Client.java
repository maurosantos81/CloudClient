/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author user
 */
public class Client {

    public static final String IP = "localhost";
    public static final int PORT = 53152;

    public void upload(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] buffer = new byte[4096];
        int read;
        while ((read = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, read);
        }

        System.out.println(bos.toByteArray().length);
        Pacote pkt = new Pacote(0, 0, bos.toByteArray(), file.getName(), Pacote.UPLOAD, false);
        new Upload().action(pkt);

        fis.close();
        bos.close();
    }

}
