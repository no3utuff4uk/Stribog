/*
 * Implementation of hash function Stribog (GOST R 34.11.2012)
 */
package ru.no3utuff4uk.stribog.task;
import ru.no3utuff4uk.stribog.*;
import java.util.Arrays;

/**
 *
 * @author no3utuff4uk
 */
public class StribogTask extends StribogImpl{
    private boolean outputMode;
    public StribogTask(boolean _outputMode)
    {
        outputMode = _outputMode;
        super.init(_outputMode);
    }
    
    public void getHashPart(byte[] messagePart)
    {
        hashPart(messagePart);
    }

    public byte[] getH() {
        if(outputMode)
            return h;
        return Arrays.copyOf(h, 32);
    }
}
