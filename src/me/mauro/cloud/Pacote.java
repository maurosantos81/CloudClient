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
    
    private static int identifierNum = 0;

    private final int identifier;
    private final int fragment;
    private final int fragmentOffset;
    private final boolean moreFragments;

    private final byte[] fileBytes;
    private final String name;
    private final int comando;

    public Pacote(int fragment, int fragmentOffset, byte[] fileBytes, String name, int comando, boolean moreFragments) {
        this.identifier = identifierNum;
        this.fragment = fragment;
        this.fragmentOffset = fragmentOffset;
        this.moreFragments = moreFragments;
        this.fileBytes = fileBytes;
        this.name = name;
        this.comando = comando;
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
    
    public boolean haveMoreFragments(){
        return this.moreFragments;
    }
}
