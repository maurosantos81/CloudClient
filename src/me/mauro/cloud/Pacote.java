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
public class Pacote implements Serializable {

    private static final long serialVersionUID = 1L;
    public final static int UPLOAD = 0;
    public final static int DOWNLOAD = 1;

    private static int identifierNum = 1;

    private final int identifier;
    private final int fragment;
    private final int fragmentOffset;
    private final boolean moreFragments;

    private final byte[] fileBytes;
    private final String name;
    private final int comando;
    private final User user;

    public Pacote(int identifier, int fragment, int fragmentOffset, byte[] fileBytes, String name, int comando, boolean moreFragments, User user) {
        this.identifier = identifier;
        this.fragment = fragment;
        this.fragmentOffset = fragmentOffset;
        this.moreFragments = moreFragments;
        this.fileBytes = fileBytes;
        this.name = name;
        this.comando = comando;
        this.user = user;
    }

    public int getComando() {
        return comando;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public int getIdentifier() {
        return identifier;
    }

    public int getFragment() {
        return fragment;
    }

    public String getName() {
        return name;
    }

    public int getFragmentOffset() {
        return fragmentOffset;
    }

    public boolean haveMoreFragments() {
        return this.moreFragments;
    }

    public User getUser() {
        return user;
    }

    public static int nextIdentifier() {
        return identifierNum++;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.identifier;
        result = prime * result + this.user.hashCode();
        return result;
    }
}
