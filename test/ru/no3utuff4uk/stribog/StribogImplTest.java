/*
 * Implementation of hash function Stribog (GOST R 34.11.2012)
 */
package ru.no3utuff4uk.stribog;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Андрей
 */
public class StribogImplTest {
    
    public StribogImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of getHash method, of class StribogImpl.
     */
    @Test
    public void TestFromGOST_512_1() {
        System.out.println("getHash");
        byte[] message = {
             (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, (byte) 0x37, (byte) 0x36, (byte) 0x35,
             (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, (byte) 0x37, 
             (byte) 0x36, (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x39, 
             (byte) 0x38, (byte) 0x37, (byte) 0x36, (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31,
             (byte) 0x30, (byte) 0x39, (byte) 0x38, (byte) 0x37, (byte) 0x36, (byte) 0x35, (byte) 0x34, (byte) 0x33,
             (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, (byte) 0x37, (byte) 0x36, (byte) 0x35,
             (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, (byte) 0x37,
             (byte) 0x36, (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30};
        boolean outputMode = true;
        StribogImpl instance = new StribogImpl();
        byte[] expResult = {
            (byte) 0x48, (byte) 0x6f, (byte) 0x64, (byte) 0xc1, (byte) 0x91, (byte) 0x78, (byte) 0x79, (byte) 0x41, 
            (byte) 0x7f, (byte) 0xef, (byte) 0x08, (byte) 0x2b, (byte) 0x33, (byte) 0x81, (byte) 0xa4, (byte) 0xe2, 
            (byte) 0x11, (byte) 0xc3, (byte) 0x24, (byte) 0xf0, (byte) 0x74, (byte) 0x65, (byte) 0x4c, (byte) 0x38, 
            (byte) 0x82, (byte) 0x3a, (byte) 0x7b, (byte) 0x76, (byte) 0xf8, (byte) 0x30, (byte) 0xad, (byte) 0x00, 
            (byte) 0xfa, (byte) 0x1f, (byte) 0xba, (byte) 0xe4, (byte) 0x2b, (byte) 0x12, (byte) 0x85, (byte) 0xc0, 
            (byte) 0x35, (byte) 0x2f, (byte) 0x22, (byte) 0x75, (byte) 0x24, (byte) 0xbc, (byte) 0x9a, (byte) 0xb1, 
            (byte) 0x62, (byte) 0x54, (byte) 0x28, (byte) 0x8d, (byte) 0xd6, (byte) 0x86, (byte) 0x3d, (byte) 0xcc,
            (byte) 0xd5, (byte) 0xb9, (byte) 0xf5, (byte) 0x4a, (byte) 0x1a, (byte) 0xd0, (byte) 0x54, (byte) 0x1b};

        byte[] result = instance.getHash(message, outputMode);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test of getHash method, of class StribogImpl.
     */
    @Test
    public void TestFromGOST_256_1() {
        System.out.println("getHash");
        byte[] message = {
            (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, (byte) 0x37, (byte) 0x36, 
            (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, 
            (byte) 0x37, (byte) 0x36, (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30, 
            (byte) 0x39, (byte) 0x38, (byte) 0x37, (byte) 0x36, (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, 
            (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, (byte) 0x37, (byte) 0x36, (byte) 0x35, (byte) 0x34, 
            (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, (byte) 0x37, (byte) 0x36, 
            (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x38, 
            (byte) 0x37, (byte) 0x36, (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30};
        boolean outputMode = false;
        StribogImpl instance = new StribogImpl();
        byte[] expResult = {
            (byte) 0x00, (byte) 0x55, (byte) 0x7b, (byte) 0xe5, (byte) 0xe5, (byte) 0x84, (byte) 0xfd, (byte) 0x52, 
            (byte) 0xa4, (byte) 0x49, (byte) 0xb1, (byte) 0x6b, (byte) 0x02, (byte) 0x51, (byte) 0xd0, (byte) 0x5d, 
            (byte) 0x27, (byte) 0xf9, (byte) 0x4a, (byte) 0xb7, (byte) 0x6c, (byte) 0xba, (byte) 0xa6, (byte) 0xda, 
            (byte) 0x89, (byte) 0x0b, (byte) 0x59, (byte) 0xd8, (byte) 0xef, (byte) 0x1e, (byte) 0x15, (byte) 0x9d};

        byte[] result = instance.getHash(message, outputMode);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test of getHash method, of class StribogImpl.
     */
    @Test
    public void TestFromGOST_512_2() {
        System.out.println("getHash");
        byte[] message = {
             (byte) 0xfb, (byte) 0xe2, (byte) 0xe5, (byte) 0xf0, (byte) 0xee, (byte) 0xe3, (byte) 0xc8, (byte) 0x20,
             (byte) 0xfb, (byte) 0xea, (byte) 0xfa, (byte) 0xeb, (byte) 0xef, (byte) 0x20, (byte) 0xff, (byte) 0xfb,
             (byte) 0xf0, (byte) 0xe1, (byte) 0xe0, (byte) 0xf0, (byte) 0xf5, (byte) 0x20, (byte) 0xe0, (byte) 0xed,
             (byte) 0x20, (byte) 0xe8, (byte) 0xec, (byte) 0xe0, (byte) 0xeb, (byte) 0xe5, (byte) 0xf0, (byte) 0xf2,
             (byte) 0xf1, (byte) 0x20, (byte) 0xff, (byte) 0xf0, (byte) 0xee, (byte) 0xec, (byte) 0x20, (byte) 0xf1,
             (byte) 0x20, (byte) 0xfa, (byte) 0xf2, (byte) 0xfe, (byte) 0xe5, (byte) 0xe2, (byte) 0x20, (byte) 0x2c,
             (byte) 0xe8, (byte) 0xf6, (byte) 0xf3, (byte) 0xed, (byte) 0xe2, (byte) 0x20, (byte) 0xe8, (byte) 0xe6,
             (byte) 0xee, (byte) 0xe1, (byte) 0xe8, (byte) 0xf0, (byte) 0xf2, (byte) 0xd1, (byte) 0x20, (byte) 0x2c,
             (byte) 0xe8, (byte) 0xf0, (byte) 0xf2, (byte) 0xe5, (byte) 0xe2, (byte) 0x20, (byte) 0xe5, (byte) 0xd1};
        boolean outputMode = true;
        StribogImpl instance = new StribogImpl();
        byte[] expResult = {
            (byte) 0x28, (byte) 0xfb, (byte) 0xc9, (byte) 0xba, (byte) 0xda, (byte) 0x03, (byte) 0x3b, (byte) 0x14, 
            (byte) 0x60, (byte) 0x64, (byte) 0x2b, (byte) 0xdc, (byte) 0xdd, (byte) 0xb9, (byte) 0x0c, (byte) 0x3f, 
            (byte) 0xb3, (byte) 0xe5, (byte) 0x6c, (byte) 0x49, (byte) 0x7c, (byte) 0xcd, (byte) 0x0f, (byte) 0x62, 
            (byte) 0xb8, (byte) 0xa2, (byte) 0xad, (byte) 0x49, (byte) 0x35, (byte) 0xe8, (byte) 0x5f, (byte) 0x03,
            (byte) 0x76, (byte) 0x13, (byte) 0x96, (byte) 0x6d, (byte) 0xe4, (byte) 0xee, (byte) 0x00, (byte) 0x53, 
            (byte) 0x1a, (byte) 0xe6, (byte) 0x0f, (byte) 0x3b, (byte) 0x5a, (byte) 0x47, (byte) 0xf8, (byte) 0xda, 
            (byte) 0xe0, (byte) 0x69, (byte) 0x15, (byte) 0xd5, (byte) 0xf2, (byte) 0xf1, (byte) 0x94, (byte) 0x99, 
            (byte) 0x6f, (byte) 0xca, (byte) 0xbf, (byte) 0x26, (byte) 0x22, (byte) 0xe6, (byte) 0x88, (byte) 0x1e};
        byte[] result = instance.getHash(message, outputMode);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test of getHash method, of class StribogImpl.
     */
    @Test
    public void TestFromGOST_256_2() {
        System.out.println("getHash");
        byte[] message = {
            (byte) 0xfb, (byte) 0xe2, (byte) 0xe5, (byte) 0xf0, (byte) 0xee, (byte) 0xe3, (byte) 0xc8, (byte) 0x20,
             (byte) 0xfb, (byte) 0xea, (byte) 0xfa, (byte) 0xeb, (byte) 0xef, (byte) 0x20, (byte) 0xff, (byte) 0xfb,
             (byte) 0xf0, (byte) 0xe1, (byte) 0xe0, (byte) 0xf0, (byte) 0xf5, (byte) 0x20, (byte) 0xe0, (byte) 0xed,
             (byte) 0x20, (byte) 0xe8, (byte) 0xec, (byte) 0xe0, (byte) 0xeb, (byte) 0xe5, (byte) 0xf0, (byte) 0xf2,
             (byte) 0xf1, (byte) 0x20, (byte) 0xff, (byte) 0xf0, (byte) 0xee, (byte) 0xec, (byte) 0x20, (byte) 0xf1,
             (byte) 0x20, (byte) 0xfa, (byte) 0xf2, (byte) 0xfe, (byte) 0xe5, (byte) 0xe2, (byte) 0x20, (byte) 0x2c,
             (byte) 0xe8, (byte) 0xf6, (byte) 0xf3, (byte) 0xed, (byte) 0xe2, (byte) 0x20, (byte) 0xe8, (byte) 0xe6,
             (byte) 0xee, (byte) 0xe1, (byte) 0xe8, (byte) 0xf0, (byte) 0xf2, (byte) 0xd1, (byte) 0x20, (byte) 0x2c,
             (byte) 0xe8, (byte) 0xf0, (byte) 0xf2, (byte) 0xe5, (byte) 0xe2, (byte) 0x20, (byte) 0xe5, (byte) 0xd1};

        boolean outputMode = false;
        StribogImpl instance = new StribogImpl();
        byte[] expResult = {
            (byte) 0x50, (byte) 0x8f, (byte) 0x7e, (byte) 0x55, (byte) 0x3c, (byte) 0x06, (byte) 0x50, (byte) 0x1d, 
            (byte) 0x74, (byte) 0x9a, (byte) 0x66, (byte) 0xfc, (byte) 0x28, (byte) 0xc6, (byte) 0xca, (byte) 0xc0, 
            (byte) 0xb0, (byte) 0x05, (byte) 0x74, (byte) 0x6d, (byte) 0x97, (byte) 0x53, (byte) 0x7f, (byte) 0xa8, 
            (byte) 0x5d, (byte) 0x9e, (byte) 0x40, (byte) 0x90, (byte) 0x4e, (byte) 0xfe, (byte) 0xd2, (byte) 0x9d};
        byte[] result = instance.getHash(message, outputMode);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test of getHash method, of class StribogImpl.
     */
    @Test
    public void TestFromHABR_512() {
        System.out.println("getHash");
        byte[] message = {
            (byte) 0x32,(byte) 0x31,(byte) 0x30,(byte) 0x39,(byte) 0x38,(byte) 0x37,(byte) 0x36,(byte) 0x35,(byte) 0x34,
            (byte) 0x33,(byte) 0x32,(byte) 0x31,(byte) 0x30,(byte) 0x39,(byte) 0x38,(byte) 0x37,(byte) 0x36,(byte) 0x35,
            (byte) 0x34,(byte) 0x33,(byte) 0x32,(byte) 0x31,(byte) 0x30,(byte) 0x39,(byte) 0x38,(byte) 0x37,(byte) 0x36,
            (byte) 0x35,(byte) 0x34,(byte) 0x33,(byte) 0x32,(byte) 0x31,(byte) 0x30,(byte) 0x39,(byte) 0x38,(byte) 0x37,
            (byte) 0x36,(byte) 0x35,(byte) 0x34,(byte) 0x33,(byte) 0x32,(byte) 0x31,(byte) 0x30,(byte) 0x39,(byte) 0x38,
            (byte) 0x37,(byte) 0x36,(byte) 0x35,(byte) 0x34,(byte) 0x33,(byte) 0x32,(byte) 0x31,(byte) 0x30,(byte) 0x39,
            (byte) 0x38,(byte) 0x37,(byte) 0x36,(byte) 0x35,(byte) 0x34,(byte) 0x33,(byte) 0x32,(byte) 0x31,(byte) 0x30};
        boolean outputMode = true;
        StribogImpl instance = new StribogImpl();
        byte[] expResult = {
            (byte) 0x48,(byte) 0x6F,(byte) 0x64,(byte) 0xC1,(byte) 0x91,(byte) 0x78,(byte) 0x79,(byte) 0x41,(byte) 0x7F,
            (byte) 0xEF,(byte) 0x08,(byte) 0x2B,(byte) 0x33,(byte) 0x81,(byte) 0xA4,(byte) 0xE2,(byte) 0x11,(byte) 0xC3,
            (byte) 0x24,(byte) 0xF0,(byte) 0x74,(byte) 0x65,(byte) 0x4C,(byte) 0x38,(byte) 0x82,(byte) 0x3A,(byte) 0x7B,
            (byte) 0x76,(byte) 0xF8,(byte) 0x30,(byte) 0xAD,(byte) 0x00,(byte) 0xFA,(byte) 0x1F,(byte) 0xBA,(byte) 0xE4,
            (byte) 0x2B,(byte) 0x12,(byte) 0x85,(byte) 0xC0,(byte) 0x35,(byte) 0x2F,(byte) 0x22,(byte) 0x75,(byte) 0x24,
            (byte) 0xBC,(byte) 0x9A,(byte) 0xB1,(byte) 0x62,(byte) 0x54,(byte) 0x28,(byte) 0x8D,(byte) 0xD6,(byte) 0x86,
            (byte) 0x3D,(byte) 0xCC,(byte) 0xD5,(byte) 0xB9,(byte) 0xF5,(byte) 0x4A,(byte) 0x1A,(byte) 0xD0,(byte) 0x54,
            (byte) 0x1B};
        byte[] result = instance.getHash(message, outputMode);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void TestFromHABR_256() {
        System.out.println("getHash");
        byte[] message = {
            (byte) 0x32,(byte) 0x31,(byte) 0x30,(byte) 0x39,(byte) 0x38,(byte) 0x37,(byte) 0x36,(byte) 0x35,(byte) 0x34,
            (byte) 0x33,(byte) 0x32,(byte) 0x31,(byte) 0x30,(byte) 0x39,(byte) 0x38,(byte) 0x37,(byte) 0x36,(byte) 0x35,
            (byte) 0x34,(byte) 0x33,(byte) 0x32,(byte) 0x31,(byte) 0x30,(byte) 0x39,(byte) 0x38,(byte) 0x37,(byte) 0x36,
            (byte) 0x35,(byte) 0x34,(byte) 0x33,(byte) 0x32,(byte) 0x31,(byte) 0x30,(byte) 0x39,(byte) 0x38,(byte) 0x37,
            (byte) 0x36,(byte) 0x35,(byte) 0x34,(byte) 0x33,(byte) 0x32,(byte) 0x31,(byte) 0x30,(byte) 0x39,(byte) 0x38,
            (byte) 0x37,(byte) 0x36,(byte) 0x35,(byte) 0x34,(byte) 0x33,(byte) 0x32,(byte) 0x31,(byte) 0x30,(byte) 0x39,
            (byte) 0x38,(byte) 0x37,(byte) 0x36,(byte) 0x35,(byte) 0x34,(byte) 0x33,(byte) 0x32,(byte) 0x31,(byte) 0x30};
        boolean outputMode = false;
        StribogImpl instance = new StribogImpl();
        byte[] expResult = {
            (byte) 0x00,(byte) 0x55,(byte) 0x7B,(byte) 0xE5,(byte) 0xE5,(byte) 0x84,(byte) 0xFD,(byte) 0x52,(byte) 0xA4,
            (byte) 0x49,(byte) 0xB1,(byte) 0x6B,(byte) 0x02,(byte) 0x51,(byte) 0xD0,(byte) 0x5D,(byte) 0x27,(byte) 0xF9,
            (byte) 0x4A,(byte) 0xB7,(byte) 0x6C,(byte) 0xBA,(byte) 0xA6,(byte) 0xDA,(byte) 0x89,(byte) 0x0B,(byte) 0x59,
            (byte) 0xD8,(byte) 0xEF,(byte) 0x1E,(byte) 0x15,(byte) 0x9D};
        byte[] result = instance.getHash(message, outputMode);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
