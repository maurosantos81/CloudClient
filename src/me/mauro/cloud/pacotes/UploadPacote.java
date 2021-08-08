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
public class UploadPacote extends Pacote {

    private final int fragment;
    private final int readBytes;
    private final boolean moreFragments;
    private final byte[] fileBytes;
    private final String name;

    public UploadPacote(int identifier, int fragment, int readBytes, byte[] fileBytes, String name, boolean moreFragments, User user) {
        super(identifier, user);
        this.fragment = fragment;
        this.readBytes = readBytes;
        this.moreFragments = moreFragments;
        this.fileBytes = fileBytes;
        this.name = name;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public int getFragment() {
        return fragment;
    }

    public String getName() {
        return name;
    }

    public int getReadBytes() {
        return readBytes;
    }

    public boolean haveMoreFragments() {
        return this.moreFragments;
    }

    @Override
    public int getComando() {
        return Pacote.UPLOAD;
    }
}
