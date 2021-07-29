/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud.client;

/**
 *
 * @author user
 */
public enum Comandos {

    UPLOAD(new Upload());

    private final Comando controller;

    private Comandos(Comando controller) {
        this.controller = controller;
    }

    public Comando getController() {
        return controller;
    }
}
