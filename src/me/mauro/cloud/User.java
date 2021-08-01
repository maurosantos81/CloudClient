/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String nome;
    private String password;

    public User(String nome, String password) {
        this.nome = nome;
        this.password = password;
    }

    public String getNome() {
        return this.nome;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.nome.hashCode();
        result = prime * result + this.password.hashCode();
        return result;
    }
}
