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
public class CloudClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Client client = new Client();
//        client.upload(new File("C:\\Users\\M4UR0\\Desktop\\compras.txt"));
        client.upload(new File("C:\\Users\\M4UR0\\Desktop\\imprimir.pdf"));
    }

}