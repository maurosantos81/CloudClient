/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud.pacotes;

import java.io.Serializable;
import me.mauro.cloud.User;

/**
 *
 * @author user
 */
public abstract class Pacote implements Serializable {

    private static final long serialVersionUID = 1L;

    public final static int UPLOAD = 0;
    public final static int DOWNLOAD = 1;
    public final static int LIST_FILES = 2;

    private static int identifierNum = 1;

    private final int identifier;
    private final User user;

    public Pacote(int identifier, User user) {
        if (identifier < 0) {
            throw new IllegalArgumentException("O identifier deve ser maior ou igual a 0");
        }

        if (user == null) {
            throw new IllegalArgumentException("User não pode ser null");
        }

        this.identifier = identifier;
        this.user = user;
    }

    public int getIdentifier() {
        return identifier;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.identifier;
        result = prime * result + this.user.hashCode();
        return result;
    }

    public static int nextIdentifier() {
        return identifierNum++;
    }

    public abstract int getComando();
}
