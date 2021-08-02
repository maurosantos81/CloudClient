/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud.pacotes;

import me.mauro.cloud.User;

/**
 *
 * @author user
 */
public class DownloadPacote extends Pacote {

    private final String nome;

    public DownloadPacote(int identifier, User user, String nome) {
        super(identifier, user);
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int getComando() {
        return Pacote.DOWNLOAD;
    }
}
