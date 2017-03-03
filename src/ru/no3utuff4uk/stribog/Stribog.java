/*
 * Implementation of hash function Stribog (GOST R 34.11.2012)
 */
package ru.no3utuff4uk.stribog;

import java.io.File;

/**
 *
 * @author no3utuff4uk
 */
public interface Stribog {
    /**
     * 
     * @param file файл, для которого надо получить хеш
     * @param outputMode выбор длины хеша: <br><blockquote> true - 512 <br> false - 256</blockquote>
     * @return 256 или 512 разрядный хеш
     */
    public byte[] getHash(File file, boolean outputMode);
    
    /**
     * 
     * @param file файл, для которого надо получить хеш
     * @param outputMode выбор длины хеша: <br><blockquote> true - 512 <br> false - 256</blockquote>
     * @return 256 или 512 разрядный хеш
     */
    public byte[] getHash(String file, boolean outputMode);
    
    /**
     * 
     * @param message массив, для которого надо получить хеш
     * @param outputMode выбор длины хеша: <br><blockquote> true - 512 <br> false - 256</blockquote>
     * @return 256 или 512 разрядный хеш
     */
    public byte[] getHash(byte[] message, boolean outputMode);
    
}
