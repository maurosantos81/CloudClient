/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

/**
 *
 * @author user
 */
public class DownloadPacote extends Pacote {

    private static final long serialVersionUID = 1L;
    public final static int UPLOAD = 0;
    public final static int DOWNLOAD = 1;

    private static int identifierNum = 1;

    private final String name;
    private final int comando;
    private final User user;

}
