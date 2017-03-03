/*
 * Implementation of hash function Stribog (GOST R 34.11.2012)
 */
package ru.no3utuff4uk.stribog.task;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javafx.concurrent.Task;

/**
 *
 * @author no3utuff4uk
 */
public class HashTask extends Task<byte[]>{

    private final File file;
    private final boolean hashMode;
    
    public HashTask(File _file, boolean _hashMode) {
        file = _file;
        hashMode = _hashMode;
    }
    
    
    @Override
    protected byte[] call() throws Exception {
        StribogTask hash;
        if(hashMode)
            hash = new StribogTask(true);
        else
            hash = new StribogTask(false);
        byte[] buffer = new byte[64];
        try(RandomAccessFile IStream = new RandomAccessFile(file, "r");)
        { 
            long lengthMax = IStream.length();
            long length = lengthMax;
            for(;length >= 64; length -=64)
            {
                IStream.seek(length - 64);
                IStream.read(buffer, 0, 64);

                hash.getHashPart(buffer);
                updateProgress(lengthMax - length, lengthMax);
                if(isCancelled())
                    return null;
            }
            if(length != 0)
            {
                IStream.seek(0);
                buffer = new byte[(int)length];
                IStream.read(buffer, 0, (int)length);
                hash.getHashPart(buffer);
            }
            updateProgress(lengthMax, lengthMax);
        }
        catch(IOException exception)
        {
            System.err.println(exception.toString());
        }
        return hash.getH();
    }

}
