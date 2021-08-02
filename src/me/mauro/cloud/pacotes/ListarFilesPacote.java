/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud.pacotes;

import java.util.List;
import me.mauro.cloud.User;

/**
 *
 * @author user
 */
public class ListarFilesPacote extends Pacote {

    private List lista;

    public ListarFilesPacote(int identifier, User user, List lista) {
        super(identifier, user);
        this.lista = lista;
    }

    public List getLista() {
        return lista;
    }

    @Override
    public int getComando() {
        return Pacote.LIST_FILES;
    }
}
