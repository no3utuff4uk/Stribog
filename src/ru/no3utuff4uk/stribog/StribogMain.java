/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.no3utuff4uk.stribog;

import java.io.File;

/**
 *
 * @author torne
 */
public class StribogMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Stribog hash = new StribogImpl();
        
        byte[] result;
//        byte[] message = {
//            (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, (byte) 0x37, (byte) 0x36, 
//            (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, 
//            (byte) 0x37, (byte) 0x36, (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30, 
//            (byte) 0x39, (byte) 0x38, (byte) 0x37, (byte) 0x36, (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, 
//            (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, (byte) 0x37, (byte) 0x36, (byte) 0x35, (byte) 0x34, 
//            (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, (byte) 0x37, (byte) 0x36, 
//            (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, 
//            (byte) 0x37, (byte) 0x36, (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30};
//        result = hash.getHash(message, false);
//        for(byte tmp: result)
//            System.out.print(Integer.toHexString(tmp & 0xff) + " ");
        
        File file = new File("./12344444.bmp");
        result = hash.getHash(file, false);
        for(byte tmp: result)
            System.out.print(Integer.toHexString(tmp & 0xff) + " ");    
    }
    
}
