/*
 * Implementation of hash function Stribog (GOST R 34.11.2012)
 */
package ru.no3utuff4uk.stribog;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 *
 * @author no3utuff4uk
 */
public class StribogImpl implements Stribog{
    
    protected byte[] h;
    private byte[] sigma;
    private byte[] N;
    private final byte[] N512 = {
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x00
    };
    private final byte[] N0 = {
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };
    
    private final byte[] Pi = {
        (byte) 0xFC, (byte) 0xEE, (byte) 0xDD, (byte) 0x11, (byte) 0xCF, (byte) 0x6E, (byte) 0x31, (byte) 0x16, (byte) 0xFB, (byte) 0xC4, (byte) 0xFA, (byte) 0xDA, (byte) 0x23, (byte) 0xC5, (byte) 0x04, (byte) 0x4D,
        (byte) 0xE9, (byte) 0x77, (byte) 0xF0, (byte) 0xDB, (byte) 0x93, (byte) 0x2E, (byte) 0x99, (byte) 0xBA, (byte) 0x17, (byte) 0x36, (byte) 0xF1, (byte) 0xBB, (byte) 0x14, (byte) 0xCD, (byte) 0x5F, (byte) 0xC1,
        (byte) 0xF9, (byte) 0x18, (byte) 0x65, (byte) 0x5A, (byte) 0xE2, (byte) 0x5C, (byte) 0xEF, (byte) 0x21, (byte) 0x81, (byte) 0x1C, (byte) 0x3C, (byte) 0x42, (byte) 0x8B, (byte) 0x01, (byte) 0x8E, (byte) 0x4F,
        (byte) 0x05, (byte) 0x84, (byte) 0x02, (byte) 0xAE, (byte) 0xE3, (byte) 0x6A, (byte) 0x8F, (byte) 0xA0, (byte) 0x06, (byte) 0x0B, (byte) 0xED, (byte) 0x98, (byte) 0x7F, (byte) 0xD4, (byte) 0xD3, (byte) 0x1F,
        (byte) 0xEB, (byte) 0x34, (byte) 0x2C, (byte) 0x51, (byte) 0xEA, (byte) 0xC8, (byte) 0x48, (byte) 0xAB, (byte) 0xF2, (byte) 0x2A, (byte) 0x68, (byte) 0xA2, (byte) 0xFD, (byte) 0x3A, (byte) 0xCE, (byte) 0xCC,
        (byte) 0xB5, (byte) 0x70, (byte) 0x0E, (byte) 0x56, (byte) 0x08, (byte) 0x0C, (byte) 0x76, (byte) 0x12, (byte) 0xBF, (byte) 0x72, (byte) 0x13, (byte) 0x47, (byte) 0x9C, (byte) 0xB7, (byte) 0x5D, (byte) 0x87,
        (byte) 0x15, (byte) 0xA1, (byte) 0x96, (byte) 0x29, (byte) 0x10, (byte) 0x7B, (byte) 0x9A, (byte) 0xC7, (byte) 0xF3, (byte) 0x91, (byte) 0x78, (byte) 0x6F, (byte) 0x9D, (byte) 0x9E, (byte) 0xB2, (byte) 0xB1,
        (byte) 0x32, (byte) 0x75, (byte) 0x19, (byte) 0x3D, (byte) 0xFF, (byte) 0x35, (byte) 0x8A, (byte) 0x7E, (byte) 0x6D, (byte) 0x54, (byte) 0xC6, (byte) 0x80, (byte) 0xC3, (byte) 0xBD, (byte) 0x0D, (byte) 0x57,
        (byte) 0xDF, (byte) 0xF5, (byte) 0x24, (byte) 0xA9, (byte) 0x3E, (byte) 0xA8, (byte) 0x43, (byte) 0xC9, (byte) 0xD7, (byte) 0x79, (byte) 0xD6, (byte) 0xF6, (byte) 0x7C, (byte) 0x22, (byte) 0xB9, (byte) 0x03,
        (byte) 0xE0, (byte) 0x0F, (byte) 0xEC, (byte) 0xDE, (byte) 0x7A, (byte) 0x94, (byte) 0xB0, (byte) 0xBC, (byte) 0xDC, (byte) 0xE8, (byte) 0x28, (byte) 0x50, (byte) 0x4E, (byte) 0x33, (byte) 0x0A, (byte) 0x4A,
        (byte) 0xA7, (byte) 0x97, (byte) 0x60, (byte) 0x73, (byte) 0x1E, (byte) 0x00, (byte) 0x62, (byte) 0x44, (byte) 0x1A, (byte) 0xB8, (byte) 0x38, (byte) 0x82, (byte) 0x64, (byte) 0x9F, (byte) 0x26, (byte) 0x41,
        (byte) 0xAD, (byte) 0x45, (byte) 0x46, (byte) 0x92, (byte) 0x27, (byte) 0x5E, (byte) 0x55, (byte) 0x2F, (byte) 0x8C, (byte) 0xA3, (byte) 0xA5, (byte) 0x7D, (byte) 0x69, (byte) 0xD5, (byte) 0x95, (byte) 0x3B,
        (byte) 0x07, (byte) 0x58, (byte) 0xB3, (byte) 0x40, (byte) 0x86, (byte) 0xAC, (byte) 0x1D, (byte) 0xF7, (byte) 0x30, (byte) 0x37, (byte) 0x6B, (byte) 0xE4, (byte) 0x88, (byte) 0xD9, (byte) 0xE7, (byte) 0x89,
        (byte) 0xE1, (byte) 0x1B, (byte) 0x83, (byte) 0x49, (byte) 0x4C, (byte) 0x3F, (byte) 0xF8, (byte) 0xFE, (byte) 0x8D, (byte) 0x53, (byte) 0xAA, (byte) 0x90, (byte) 0xCA, (byte) 0xD8, (byte) 0x85, (byte) 0x61,
        (byte) 0x20, (byte) 0x71, (byte) 0x67, (byte) 0xA4, (byte) 0x2D, (byte) 0x2B, (byte) 0x09, (byte) 0x5B, (byte) 0xCB, (byte) 0x9B, (byte) 0x25, (byte) 0xD0, (byte) 0xBE, (byte) 0xE5, (byte) 0x6C, (byte) 0x52,
        (byte) 0x59, (byte) 0xA6, (byte) 0x74, (byte) 0xD2, (byte) 0xE6, (byte) 0xF4, (byte) 0xB4, (byte) 0xC0, (byte) 0xD1, (byte) 0x66, (byte) 0xAF, (byte) 0xC2, (byte) 0x39, (byte) 0x4B, (byte) 0x63, (byte) 0xB6
        };
    private final byte[] Tau ={
        0, 8,  16, 24, 32, 40, 48, 56,
        1, 9,  17, 25, 33, 41, 49, 57,
        2, 10, 18, 26, 34, 42, 50, 58,
        3, 11, 19, 27, 35, 43, 51, 59,
        4, 12, 20, 28, 36, 44, 52, 60,
        5, 13, 21, 29, 37, 45, 53, 61,
        6, 14, 22, 30, 38, 46, 54, 62,
        7, 15, 23, 31, 39, 47, 55, 63
        };
    
    private final byte[][] A  = {
        {(byte) 0x8e, (byte) 0x20, (byte) 0xfa, (byte) 0xa7, (byte) 0x2b, (byte) 0xa0, (byte) 0xb4, (byte) 0x70}, {(byte) 0x47, (byte) 0x10, (byte) 0x7d, (byte) 0xdd, (byte) 0x9b, (byte) 0x50, (byte) 0x5a, (byte) 0x38}, 
        {(byte) 0xad, (byte) 0x08, (byte) 0xb0, (byte) 0xe0, (byte) 0xc3, (byte) 0x28, (byte) 0x2d, (byte) 0x1c}, {(byte) 0xd8, (byte) 0x04, (byte) 0x58, (byte) 0x70, (byte) 0xef, (byte) 0x14, (byte) 0x98, (byte) 0x0e},
        {(byte) 0x6c, (byte) 0x02, (byte) 0x2c, (byte) 0x38, (byte) 0xf9, (byte) 0x0a, (byte) 0x4c, (byte) 0x07}, {(byte) 0x36, (byte) 0x01, (byte) 0x16, (byte) 0x1c, (byte) 0xf2, (byte) 0x05, (byte) 0x26, (byte) 0x8d}, 
        {(byte) 0x1b, (byte) 0x8e, (byte) 0x0b, (byte) 0x0e, (byte) 0x79, (byte) 0x8c, (byte) 0x13, (byte) 0xc8}, {(byte) 0x83, (byte) 0x47, (byte) 0x8b, (byte) 0x07, (byte) 0xb2, (byte) 0x46, (byte) 0x87, (byte) 0x64},
        {(byte) 0xa0, (byte) 0x11, (byte) 0xd3, (byte) 0x80, (byte) 0x81, (byte) 0x8e, (byte) 0x8f, (byte) 0x40}, {(byte) 0x50, (byte) 0x86, (byte) 0xe7, (byte) 0x40, (byte) 0xce, (byte) 0x47, (byte) 0xc9, (byte) 0x20},
        {(byte) 0x28, (byte) 0x43, (byte) 0xfd, (byte) 0x20, (byte) 0x67, (byte) 0xad, (byte) 0xea, (byte) 0x10}, {(byte) 0x14, (byte) 0xaf, (byte) 0xf0, (byte) 0x10, (byte) 0xbd, (byte) 0xd8, (byte) 0x75, (byte) 0x08},
        {(byte) 0x0a, (byte) 0xd9, (byte) 0x78, (byte) 0x08, (byte) 0xd0, (byte) 0x6c, (byte) 0xb4, (byte) 0x04}, {(byte) 0x05, (byte) 0xe2, (byte) 0x3c, (byte) 0x04, (byte) 0x68, (byte) 0x36, (byte) 0x5a, (byte) 0x02}, 
        {(byte) 0x8c, (byte) 0x71, (byte) 0x1e, (byte) 0x02, (byte) 0x34, (byte) 0x1b, (byte) 0x2d, (byte) 0x01}, {(byte) 0x46, (byte) 0xb6, (byte) 0x0f, (byte) 0x01, (byte) 0x1a, (byte) 0x83, (byte) 0x98, (byte) 0x8e},
        {(byte) 0x90, (byte) 0xda, (byte) 0xb5, (byte) 0x2a, (byte) 0x38, (byte) 0x7a, (byte) 0xe7, (byte) 0x6f}, {(byte) 0x48, (byte) 0x6d, (byte) 0xd4, (byte) 0x15, (byte) 0x1c, (byte) 0x3d, (byte) 0xfd, (byte) 0xb9}, 
        {(byte) 0x24, (byte) 0xb8, (byte) 0x6a, (byte) 0x84, (byte) 0x0e, (byte) 0x90, (byte) 0xf0, (byte) 0xd2}, {(byte) 0x12, (byte) 0x5c, (byte) 0x35, (byte) 0x42, (byte) 0x07, (byte) 0x48, (byte) 0x78, (byte) 0x69},
        {(byte) 0x09, (byte) 0x2e, (byte) 0x94, (byte) 0x21, (byte) 0x8d, (byte) 0x24, (byte) 0x3c, (byte) 0xba}, {(byte) 0x8a, (byte) 0x17, (byte) 0x4a, (byte) 0x9e, (byte) 0xc8, (byte) 0x12, (byte) 0x1e, (byte) 0x5d},
        {(byte) 0x45, (byte) 0x85, (byte) 0x25, (byte) 0x4f, (byte) 0x64, (byte) 0x09, (byte) 0x0f, (byte) 0xa0}, {(byte) 0xac, (byte) 0xcc, (byte) 0x9c, (byte) 0xa9, (byte) 0x32, (byte) 0x8a, (byte) 0x89, (byte) 0x50},
        {(byte) 0x9d, (byte) 0x4d, (byte) 0xf0, (byte) 0x5d, (byte) 0x5f, (byte) 0x66, (byte) 0x14, (byte) 0x51}, {(byte) 0xc0, (byte) 0xa8, (byte) 0x78, (byte) 0xa0, (byte) 0xa1, (byte) 0x33, (byte) 0x0a, (byte) 0xa6}, 
        {(byte) 0x60, (byte) 0x54, (byte) 0x3c, (byte) 0x50, (byte) 0xde, (byte) 0x97, (byte) 0x05, (byte) 0x53}, {(byte) 0x30, (byte) 0x2a, (byte) 0x1e, (byte) 0x28, (byte) 0x6f, (byte) 0xc5, (byte) 0x8c, (byte) 0xa7},
        {(byte) 0x18, (byte) 0x15, (byte) 0x0f, (byte) 0x14, (byte) 0xb9, (byte) 0xec, (byte) 0x46, (byte) 0xdd}, {(byte) 0x0c, (byte) 0x84, (byte) 0x89, (byte) 0x0a, (byte) 0xd2, (byte) 0x76, (byte) 0x23, (byte) 0xe0}, 
        {(byte) 0x06, (byte) 0x42, (byte) 0xca, (byte) 0x05, (byte) 0x69, (byte) 0x3b, (byte) 0x9f, (byte) 0x70}, {(byte) 0x03, (byte) 0x21, (byte) 0x65, (byte) 0x8c, (byte) 0xba, (byte) 0x93, (byte) 0xc1, (byte) 0x38},
        {(byte) 0x86, (byte) 0x27, (byte) 0x5d, (byte) 0xf0, (byte) 0x9c, (byte) 0xe8, (byte) 0xaa, (byte) 0xa8}, {(byte) 0x43, (byte) 0x9d, (byte) 0xa0, (byte) 0x78, (byte) 0x4e, (byte) 0x74, (byte) 0x55, (byte) 0x54},
        {(byte) 0xaf, (byte) 0xc0, (byte) 0x50, (byte) 0x3c, (byte) 0x27, (byte) 0x3a, (byte) 0xa4, (byte) 0x2a}, {(byte) 0xd9, (byte) 0x60, (byte) 0x28, (byte) 0x1e, (byte) 0x9d, (byte) 0x1d, (byte) 0x52, (byte) 0x15},
        {(byte) 0xe2, (byte) 0x30, (byte) 0x14, (byte) 0x0f, (byte) 0xc0, (byte) 0x80, (byte) 0x29, (byte) 0x84}, {(byte) 0x71, (byte) 0x18, (byte) 0x0a, (byte) 0x89, (byte) 0x60, (byte) 0x40, (byte) 0x9a, (byte) 0x42},
        {(byte) 0xb6, (byte) 0x0c, (byte) 0x05, (byte) 0xca, (byte) 0x30, (byte) 0x20, (byte) 0x4d, (byte) 0x21}, {(byte) 0x5b, (byte) 0x06, (byte) 0x8c, (byte) 0x65, (byte) 0x18, (byte) 0x10, (byte) 0xa8, (byte) 0x9e},
        {(byte) 0x45, (byte) 0x6c, (byte) 0x34, (byte) 0x88, (byte) 0x7a, (byte) 0x38, (byte) 0x05, (byte) 0xb9}, {(byte) 0xac, (byte) 0x36, (byte) 0x1a, (byte) 0x44, (byte) 0x3d, (byte) 0x1c, (byte) 0x8c, (byte) 0xd2}, 
        {(byte) 0x56, (byte) 0x1b, (byte) 0x0d, (byte) 0x22, (byte) 0x90, (byte) 0x0e, (byte) 0x46, (byte) 0x69}, {(byte) 0x2b, (byte) 0x83, (byte) 0x88, (byte) 0x11, (byte) 0x48, (byte) 0x07, (byte) 0x23, (byte) 0xba},
        {(byte) 0x9b, (byte) 0xcf, (byte) 0x44, (byte) 0x86, (byte) 0x24, (byte) 0x8d, (byte) 0x9f, (byte) 0x5d}, {(byte) 0xc3, (byte) 0xe9, (byte) 0x22, (byte) 0x43, (byte) 0x12, (byte) 0xc8, (byte) 0xc1, (byte) 0xa0}, 
        {(byte) 0xef, (byte) 0xfa, (byte) 0x11, (byte) 0xaf, (byte) 0x09, (byte) 0x64, (byte) 0xee, (byte) 0x50}, {(byte) 0xf9, (byte) 0x7d, (byte) 0x86, (byte) 0xd9, (byte) 0x8a, (byte) 0x32, (byte) 0x77, (byte) 0x28},
        {(byte) 0xe4, (byte) 0xfa, (byte) 0x20, (byte) 0x54, (byte) 0xa8, (byte) 0x0b, (byte) 0x32, (byte) 0x9c}, {(byte) 0x72, (byte) 0x7d, (byte) 0x10, (byte) 0x2a, (byte) 0x54, (byte) 0x8b, (byte) 0x19, (byte) 0x4e}, 
        {(byte) 0x39, (byte) 0xb0, (byte) 0x08, (byte) 0x15, (byte) 0x2a, (byte) 0xcb, (byte) 0x82, (byte) 0x27}, {(byte) 0x92, (byte) 0x58, (byte) 0x04, (byte) 0x84, (byte) 0x15, (byte) 0xeb, (byte) 0x41, (byte) 0x9d},
        {(byte) 0x49, (byte) 0x2c, (byte) 0x02, (byte) 0x42, (byte) 0x84, (byte) 0xfb, (byte) 0xae, (byte) 0xc0}, {(byte) 0xaa, (byte) 0x16, (byte) 0x01, (byte) 0x21, (byte) 0x42, (byte) 0xf3, (byte) 0x57, (byte) 0x60}, 
        {(byte) 0x55, (byte) 0x0b, (byte) 0x8e, (byte) 0x9e, (byte) 0x21, (byte) 0xf7, (byte) 0xa5, (byte) 0x30}, {(byte) 0xa4, (byte) 0x8b, (byte) 0x47, (byte) 0x4f, (byte) 0x9e, (byte) 0xf5, (byte) 0xdc, (byte) 0x18},
        {(byte) 0x70, (byte) 0xa6, (byte) 0xa5, (byte) 0x6e, (byte) 0x24, (byte) 0x40, (byte) 0x59, (byte) 0x8e}, {(byte) 0x38, (byte) 0x53, (byte) 0xdc, (byte) 0x37, (byte) 0x12, (byte) 0x20, (byte) 0xa2, (byte) 0x47},
        {(byte) 0x1c, (byte) 0xa7, (byte) 0x6e, (byte) 0x95, (byte) 0x09, (byte) 0x10, (byte) 0x51, (byte) 0xad}, {(byte) 0x0e, (byte) 0xdd, (byte) 0x37, (byte) 0xc4, (byte) 0x8a, (byte) 0x08, (byte) 0xa6, (byte) 0xd8},
        {(byte) 0x07, (byte) 0xe0, (byte) 0x95, (byte) 0x62, (byte) 0x45, (byte) 0x04, (byte) 0x53, (byte) 0x6c}, {(byte) 0x8d, (byte) 0x70, (byte) 0xc4, (byte) 0x31, (byte) 0xac, (byte) 0x02, (byte) 0xa7, (byte) 0x36},
        {(byte) 0xc8, (byte) 0x38, (byte) 0x62, (byte) 0x96, (byte) 0x56, (byte) 0x01, (byte) 0xdd, (byte) 0x1b}, {(byte) 0x64, (byte) 0x1c, (byte) 0x31, (byte) 0x4b, (byte) 0x2b, (byte) 0x8e, (byte) 0xe0, (byte) 0x83}
        };  
    
    private final byte [][] C = {
        {
        (byte) 0xb1,(byte) 0x08,(byte) 0x5b,(byte) 0xda,(byte) 0x1e,(byte) 0xca,(byte) 0xda,(byte) 0xe9,(byte) 0xeb,(byte) 0xcb,(byte) 0x2f,(byte) 0x81,(byte) 0xc0,(byte) 0x65,(byte) 0x7c,(byte) 0x1f,
        (byte) 0x2f,(byte) 0x6a,(byte) 0x76,(byte) 0x43,(byte) 0x2e,(byte) 0x45,(byte) 0xd0,(byte) 0x16,(byte) 0x71,(byte) 0x4e,(byte) 0xb8,(byte) 0x8d,(byte) 0x75,(byte) 0x85,(byte) 0xc4,(byte) 0xfc,
        (byte) 0x4b,(byte) 0x7c,(byte) 0xe0,(byte) 0x91,(byte) 0x92,(byte) 0x67,(byte) 0x69,(byte) 0x01,(byte) 0xa2,(byte) 0x42,(byte) 0x2a,(byte) 0x08,(byte) 0xa4,(byte) 0x60,(byte) 0xd3,(byte) 0x15,
        (byte) 0x05,(byte) 0x76,(byte) 0x74,(byte) 0x36,(byte) 0xcc,(byte) 0x74,(byte) 0x4d,(byte) 0x23,(byte) 0xdd,(byte) 0x80,(byte) 0x65,(byte) 0x59,(byte) 0xf2,(byte) 0xa6,(byte) 0x45,(byte) 0x07
        },
        {
        (byte) 0x6f,(byte) 0xa3,(byte) 0xb5,(byte) 0x8a,(byte) 0xa9,(byte) 0x9d,(byte) 0x2f,(byte) 0x1a,(byte) 0x4f,(byte) 0xe3,(byte) 0x9d,(byte) 0x46,(byte) 0x0f,(byte) 0x70,(byte) 0xb5,(byte) 0xd7,
        (byte) 0xf3,(byte) 0xfe,(byte) 0xea,(byte) 0x72,(byte) 0x0a,(byte) 0x23,(byte) 0x2b,(byte) 0x98,(byte) 0x61,(byte) 0xd5,(byte) 0x5e,(byte) 0x0f,(byte) 0x16,(byte) 0xb5,(byte) 0x01,(byte) 0x31,        
        (byte) 0x9a,(byte) 0xb5,(byte) 0x17,(byte) 0x6b,(byte) 0x12,(byte) 0xd6,(byte) 0x99,(byte) 0x58,(byte) 0x5c,(byte) 0xb5,(byte) 0x61,(byte) 0xc2,(byte) 0xdb,(byte) 0x0a,(byte) 0xa7,(byte) 0xca,
        (byte) 0x55,(byte) 0xdd,(byte) 0xa2,(byte) 0x1b,(byte) 0xd7,(byte) 0xcb,(byte) 0xcd,(byte) 0x56,(byte) 0xe6,(byte) 0x79,(byte) 0x04,(byte) 0x70,(byte) 0x21,(byte) 0xb1,(byte) 0x9b,(byte) 0xb7
        },
        {
        (byte) 0xf5,(byte) 0x74,(byte) 0xdc,(byte) 0xac,(byte) 0x2b,(byte) 0xce,(byte) 0x2f,(byte) 0xc7,(byte) 0x0a,(byte) 0x39,(byte) 0xfc,(byte) 0x28,(byte) 0x6a,(byte) 0x3d,(byte) 0x84,(byte) 0x35,
        (byte) 0x06,(byte) 0xf1,(byte) 0x5e,(byte) 0x5f,(byte) 0x52,(byte) 0x9c,(byte) 0x1f,(byte) 0x8b,(byte) 0xf2,(byte) 0xea,(byte) 0x75,(byte) 0x14,(byte) 0xb1,(byte) 0x29,(byte) 0x7b,(byte) 0x7b,
        (byte) 0xd3,(byte) 0xe2,(byte) 0x0f,(byte) 0xe4,(byte) 0x90,(byte) 0x35,(byte) 0x9e,(byte) 0xb1,(byte) 0xc1,(byte) 0xc9,(byte) 0x3a,(byte) 0x37,(byte) 0x60,(byte) 0x62,(byte) 0xdb,(byte) 0x09,
        (byte) 0xc2,(byte) 0xb6,(byte) 0xf4,(byte) 0x43,(byte) 0x86,(byte) 0x7a,(byte) 0xdb,(byte) 0x31,(byte) 0x99,(byte) 0x1e,(byte) 0x96,(byte) 0xf5,(byte) 0x0a,(byte) 0xba,(byte) 0x0a,(byte) 0xb2
        },
        {
        (byte) 0xef,(byte) 0x1f,(byte) 0xdf,(byte) 0xb3,(byte) 0xe8,(byte) 0x15,(byte) 0x66,(byte) 0xd2,(byte) 0xf9,(byte) 0x48,(byte) 0xe1,(byte) 0xa0,(byte) 0x5d,(byte) 0x71,(byte) 0xe4,(byte) 0xdd,
        (byte) 0x48,(byte) 0x8e,(byte) 0x85,(byte) 0x7e,(byte) 0x33,(byte) 0x5c,(byte) 0x3c,(byte) 0x7d,(byte) 0x9d,(byte) 0x72,(byte) 0x1c,(byte) 0xad,(byte) 0x68,(byte) 0x5e,(byte) 0x35,(byte) 0x3f,
        (byte) 0xa9,(byte) 0xd7,(byte) 0x2c,(byte) 0x82,(byte) 0xed,(byte) 0x03,(byte) 0xd6,(byte) 0x75,(byte) 0xd8,(byte) 0xb7,(byte) 0x13,(byte) 0x33,(byte) 0x93,(byte) 0x52,(byte) 0x03,(byte) 0xbe,
        (byte) 0x34,(byte) 0x53,(byte) 0xea,(byte) 0xa1,(byte) 0x93,(byte) 0xe8,(byte) 0x37,(byte) 0xf1,(byte) 0x22,(byte) 0x0c,(byte) 0xbe,(byte) 0xbc,(byte) 0x84,(byte) 0xe3,(byte) 0xd1,(byte) 0x2e
        },
        {
        (byte) 0x4b,(byte) 0xea,(byte) 0x6b,(byte) 0xac,(byte) 0xad,(byte) 0x47,(byte) 0x47,(byte) 0x99,(byte) 0x9a,(byte) 0x3f,(byte) 0x41,(byte) 0x0c,(byte) 0x6c,(byte) 0xa9,(byte) 0x23,(byte) 0x63,
        (byte) 0x7f,(byte) 0x15,(byte) 0x1c,(byte) 0x1f,(byte) 0x16,(byte) 0x86,(byte) 0x10,(byte) 0x4a,(byte) 0x35,(byte) 0x9e,(byte) 0x35,(byte) 0xd7,(byte) 0x80,(byte) 0x0f,(byte) 0xff,(byte) 0xbd,
        (byte) 0xbf,(byte) 0xcd,(byte) 0x17,(byte) 0x47,(byte) 0x25,(byte) 0x3a,(byte) 0xf5,(byte) 0xa3,(byte) 0xdf,(byte) 0xff,(byte) 0x00,(byte) 0xb7,(byte) 0x23,(byte) 0x27,(byte) 0x1a,(byte) 0x16,
        (byte) 0x7a,(byte) 0x56,(byte) 0xa2,(byte) 0x7e,(byte) 0xa9,(byte) 0xea,(byte) 0x63,(byte) 0xf5,(byte) 0x60,(byte) 0x17,(byte) 0x58,(byte) 0xfd,(byte) 0x7c,(byte) 0x6c,(byte) 0xfe,(byte) 0x57
        },
        {
        (byte) 0xae,(byte) 0x4f,(byte) 0xae,(byte) 0xae,(byte) 0x1d,(byte) 0x3a,(byte) 0xd3,(byte) 0xd9,(byte) 0x6f,(byte) 0xa4,(byte) 0xc3,(byte) 0x3b,(byte) 0x7a,(byte) 0x30,(byte) 0x39,(byte) 0xc0,
        (byte) 0x2d,(byte) 0x66,(byte) 0xc4,(byte) 0xf9,(byte) 0x51,(byte) 0x42,(byte) 0xa4,(byte) 0x6c,(byte) 0x18,(byte) 0x7f,(byte) 0x9a,(byte) 0xb4,(byte) 0x9a,(byte) 0xf0,(byte) 0x8e,(byte) 0xc6,
        (byte) 0xcf,(byte) 0xfa,(byte) 0xa6,(byte) 0xb7,(byte) 0x1c,(byte) 0x9a,(byte) 0xb7,(byte) 0xb4,(byte) 0x0a,(byte) 0xf2,(byte) 0x1f,(byte) 0x66,(byte) 0xc2,(byte) 0xbe,(byte) 0xc6,(byte) 0xb6,
        (byte) 0xbf,(byte) 0x71,(byte) 0xc5,(byte) 0x72,(byte) 0x36,(byte) 0x90,(byte) 0x4f,(byte) 0x35,(byte) 0xfa,(byte) 0x68,(byte) 0x40,(byte) 0x7a,(byte) 0x46,(byte) 0x64,(byte) 0x7d,(byte) 0x6e
        },
        {
        (byte) 0xf4,(byte) 0xc7,(byte) 0x0e,(byte) 0x16,(byte) 0xee,(byte) 0xaa,(byte) 0xc5,(byte) 0xec,(byte) 0x51,(byte) 0xac,(byte) 0x86,(byte) 0xfe,(byte) 0xbf,(byte) 0x24,(byte) 0x09,(byte) 0x54,
        (byte) 0x39,(byte) 0x9e,(byte) 0xc6,(byte) 0xc7,(byte) 0xe6,(byte) 0xbf,(byte) 0x87,(byte) 0xc9,(byte) 0xd3,(byte) 0x47,(byte) 0x3e,(byte) 0x33,(byte) 0x19,(byte) 0x7a,(byte) 0x93,(byte) 0xc9,
        (byte) 0x09,(byte) 0x92,(byte) 0xab,(byte) 0xc5,(byte) 0x2d,(byte) 0x82,(byte) 0x2c,(byte) 0x37,(byte) 0x06,(byte) 0x47,(byte) 0x69,(byte) 0x83,(byte) 0x28,(byte) 0x4a,(byte) 0x05,(byte) 0x04,
        (byte) 0x35,(byte) 0x17,(byte) 0x45,(byte) 0x4c,(byte) 0xa2,(byte) 0x3c,(byte) 0x4a,(byte) 0xf3,(byte) 0x88,(byte) 0x86,(byte) 0x56,(byte) 0x4d,(byte) 0x3a,(byte) 0x14,(byte) 0xd4,(byte) 0x93
        },
        {
        (byte) 0x9b,(byte) 0x1f,(byte) 0x5b,(byte) 0x42,(byte) 0x4d,(byte) 0x93,(byte) 0xc9,(byte) 0xa7,(byte) 0x03,(byte) 0xe7,(byte) 0xaa,(byte) 0x02,(byte) 0x0c,(byte) 0x6e,(byte) 0x41,(byte) 0x41,
        (byte) 0x4e,(byte) 0xb7,(byte) 0xf8,(byte) 0x71,(byte) 0x9c,(byte) 0x36,(byte) 0xde,(byte) 0x1e,(byte) 0x89,(byte) 0xb4,(byte) 0x44,(byte) 0x3b,(byte) 0x4d,(byte) 0xdb,(byte) 0xc4,(byte) 0x9a,
        (byte) 0xf4,(byte) 0x89,(byte) 0x2b,(byte) 0xcb,(byte) 0x92,(byte) 0x9b,(byte) 0x06,(byte) 0x90,(byte) 0x69,(byte) 0xd1,(byte) 0x8d,(byte) 0x2b,(byte) 0xd1,(byte) 0xa5,(byte) 0xc4,(byte) 0x2f,
        (byte) 0x36,(byte) 0xac,(byte) 0xc2,(byte) 0x35,(byte) 0x59,(byte) 0x51,(byte) 0xa8,(byte) 0xd9,(byte) 0xa4,(byte) 0x7f,(byte) 0x0d,(byte) 0xd4,(byte) 0xbf,(byte) 0x02,(byte) 0xe7,(byte) 0x1e
        },
        {
        (byte) 0x37,(byte) 0x8f,(byte) 0x5a,(byte) 0x54,(byte) 0x16,(byte) 0x31,(byte) 0x22,(byte) 0x9b,(byte) 0x94,(byte) 0x4c,(byte) 0x9a,(byte) 0xd8,(byte) 0xec,(byte) 0x16,(byte) 0x5f,(byte) 0xde,
        (byte) 0x3a,(byte) 0x7d,(byte) 0x3a,(byte) 0x1b,(byte) 0x25,(byte) 0x89,(byte) 0x42,(byte) 0x24,(byte) 0x3c,(byte) 0xd9,(byte) 0x55,(byte) 0xb7,(byte) 0xe0,(byte) 0x0d,(byte) 0x09,(byte) 0x84,
        (byte) 0x80,(byte) 0x0a,(byte) 0x44,(byte) 0x0b,(byte) 0xdb,(byte) 0xb2,(byte) 0xce,(byte) 0xb1,(byte) 0x7b,(byte) 0x2b,(byte) 0x8a,(byte) 0x9a,(byte) 0xa6,(byte) 0x07,(byte) 0x9c,(byte) 0x54,
        (byte) 0x0e,(byte) 0x38,(byte) 0xdc,(byte) 0x92,(byte) 0xcb,(byte) 0x1f,(byte) 0x2a,(byte) 0x60,(byte) 0x72,(byte) 0x61,(byte) 0x44,(byte) 0x51,(byte) 0x83,(byte) 0x23,(byte) 0x5a,(byte) 0xdb
        },
        {
        (byte) 0xab,(byte) 0xbe,(byte) 0xde,(byte) 0xa6,(byte) 0x80,(byte) 0x05,(byte) 0x6f,(byte) 0x52,(byte) 0x38,(byte) 0x2a,(byte) 0xe5,(byte) 0x48,(byte) 0xb2,(byte) 0xe4,(byte) 0xf3,(byte) 0xf3,
        (byte) 0x89,(byte) 0x41,(byte) 0xe7,(byte) 0x1c,(byte) 0xff,(byte) 0x8a,(byte) 0x78,(byte) 0xdb,(byte) 0x1f,(byte) 0xff,(byte) 0xe1,(byte) 0x8a,(byte) 0x1b,(byte) 0x33,(byte) 0x61,(byte) 0x03,
        (byte) 0x9f,(byte) 0xe7,(byte) 0x67,(byte) 0x02,(byte) 0xaf,(byte) 0x69,(byte) 0x33,(byte) 0x4b,(byte) 0x7a,(byte) 0x1e,(byte) 0x6c,(byte) 0x30,(byte) 0x3b,(byte) 0x76,(byte) 0x52,(byte) 0xf4,
        (byte) 0x36,(byte) 0x98,(byte) 0xfa,(byte) 0xd1,(byte) 0x15,(byte) 0x3b,(byte) 0xb6,(byte) 0xc3,(byte) 0x74,(byte) 0xb4,(byte) 0xc7,(byte) 0xfb,(byte) 0x98,(byte) 0x45,(byte) 0x9c,(byte) 0xed
        },
        {
        (byte) 0x7b,(byte) 0xcd,(byte) 0x9e,(byte) 0xd0,(byte) 0xef,(byte) 0xc8,(byte) 0x89,(byte) 0xfb,(byte) 0x30,(byte) 0x02,(byte) 0xc6,(byte) 0xcd,(byte) 0x63,(byte) 0x5a,(byte) 0xfe,(byte) 0x94,
        (byte) 0xd8,(byte) 0xfa,(byte) 0x6b,(byte) 0xbb,(byte) 0xeb,(byte) 0xab,(byte) 0x07,(byte) 0x61,(byte) 0x20,(byte) 0x01,(byte) 0x80,(byte) 0x21,(byte) 0x14,(byte) 0x84,(byte) 0x66,(byte) 0x79,
        (byte) 0x8a,(byte) 0x1d,(byte) 0x71,(byte) 0xef,(byte) 0xea,(byte) 0x48,(byte) 0xb9,(byte) 0xca,(byte) 0xef,(byte) 0xba,(byte) 0xcd,(byte) 0x1d,(byte) 0x7d,(byte) 0x47,(byte) 0x6e,(byte) 0x98,
        (byte) 0xde,(byte) 0xa2,(byte) 0x59,(byte) 0x4a,(byte) 0xc0,(byte) 0x6f,(byte) 0xd8,(byte) 0x5d,(byte) 0x6b,(byte) 0xca,(byte) 0xa4,(byte) 0xcd,(byte) 0x81,(byte) 0xf3,(byte) 0x2d,(byte) 0x1b
        },
        {
        (byte) 0x37,(byte) 0x8e,(byte) 0xe7,(byte) 0x67,(byte) 0xf1,(byte) 0x16,(byte) 0x31,(byte) 0xba,(byte) 0xd2,(byte) 0x13,(byte) 0x80,(byte) 0xb0,(byte) 0x04,(byte) 0x49,(byte) 0xb1,(byte) 0x7a,
        (byte) 0xcd,(byte) 0xa4,(byte) 0x3c,(byte) 0x32,(byte) 0xbc,(byte) 0xdf,(byte) 0x1d,(byte) 0x77,(byte) 0xf8,(byte) 0x20,(byte) 0x12,(byte) 0xd4,(byte) 0x30,(byte) 0x21,(byte) 0x9f,(byte) 0x9b,
        (byte) 0x5d,(byte) 0x80,(byte) 0xef,(byte) 0x9d,(byte) 0x18,(byte) 0x91,(byte) 0xcc,(byte) 0x86,(byte) 0xe7,(byte) 0x1d,(byte) 0xa4,(byte) 0xaa,(byte) 0x88,(byte) 0xe1,(byte) 0x28,(byte) 0x52,
        (byte) 0xfa,(byte) 0xf4,(byte) 0x17,(byte) 0xd5,(byte) 0xd9,(byte) 0xb2,(byte) 0x1b,(byte) 0x99,(byte) 0x48,(byte) 0xbc,(byte) 0x92,(byte) 0x4a,(byte) 0xf1,(byte) 0x1b,(byte) 0xd7,(byte) 0x20
        }
        };
    
    /**
     * Инициализация полей
     * @param outputMode 
     */
    protected void init(boolean outputMode)
    {
        h = new byte[64];
        if(!outputMode)
            Arrays.fill(h, (byte)0x01);
        else
            Arrays.fill(h, (byte)0x00);
        
        N = new byte[64];
        Arrays.fill(N, (byte)0x00);
        
        sigma = new byte[64];
        Arrays.fill(sigma, (byte)0x00);
    }
    
    @Override
    public byte[] getHash(File file, boolean outputMode) {
        init(outputMode);
        byte[] buffer = new byte[64];
        try(RandomAccessFile IStream = new RandomAccessFile(file, "r");)    //Так удобней читать с конца файла
        { 
            long length = IStream.length();
            for(;length >= 64; length -=64)
            {
                IStream.seek(length - 64);
                IStream.read(buffer, 0, 64);
                
                hashPart(buffer);
            }
            if(length != 0)
            {
                IStream.seek(0);
                buffer = new byte[(int)length];
                IStream.read(buffer, 0, (int)length);
                hashPart(buffer);
            }
        }
        catch(IOException exception)
        {
            System.err.println(exception.toString());
        }
        if(outputMode)
            return h;
        return Arrays.copyOf(h, 32);
    }

    @Override
    public byte[] getHash(String file, boolean outputMode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public byte[] getHash(byte[] message, boolean outputMode) {
        init(outputMode);
        return generateHash(message, outputMode);
    }
    
    /**
     * Обрабатывает блок данных
     * @param messagePart блок данных 512 бит или меньше
     */
    protected void hashPart(byte[] messagePart)
    {
        if(messagePart.length == 64)
        {
            h = compression(N, messagePart, h);
            N = addModule(N, N512);
            sigma = addModule(sigma, messagePart);
        }
        else
        {
            int inc = 0;
            int length = messagePart.length * 8;
            
            //Дополнение до 512 бит по алгоритму
            byte[] tmpMessage = new byte[64];
            Arrays.fill(tmpMessage, (byte) 0x00);
            tmpMessage[64 - messagePart.length - 1] = (byte) 0x01;
            
            for(int i = 64 - messagePart.length; i < 64; i++)
                tmpMessage[i] = messagePart[i + messagePart.length - 64];
            
            messagePart = tmpMessage;
            
            h = compression(N, messagePart, h);
        
            byte[] NMessage = new byte[64];
            Arrays.fill(NMessage, (byte) 0x00);

            
            while(length > 0)
            {
                NMessage[63 - inc] = (byte) (length & 0xff);
                length >>= 8;
                inc++;
            }

            N = addModule(N, NMessage);
            sigma = addModule(sigma, messagePart);

            h = compression(N0, N, h);
            h = compression(N0, sigma, h);
        }
    }
    
    
    /**
     * Старая версия. Создана первой для работы с массивом.
     * @param message
     * @param outputMode
     * @return хеш 
     */
    @Deprecated
    private byte[] generateHash(byte[] message, boolean outputMode)
    {        
        int length = message.length * 8;
        int inc = 0;
        
        while(length >= 512)
        {
            inc++;
            byte[] tmp = Arrays.copyOfRange(message, message.length - 64*(inc), message.length - (inc-1)*64);
            h = compression(N, tmp, h);
            N = addModule(N, N512);
            sigma = addModule(sigma, tmp);
            length -=512;
        }
        
        message = Arrays.copyOf(message, message.length - 64*inc);
        
        if(message.length < 64) //обработка сообщения длинной < 512 бит
        {
            byte[] tmpMessage = new byte[64];
            Arrays.fill(tmpMessage, (byte) 0x00);
            tmpMessage[64 - message.length - 1] = (byte) 0x01;
            
            for(int i = 64 - message.length; i < 64; i++)
                tmpMessage[i] = message[i + message.length - 64];
            
            message = tmpMessage;
        }
            
        h = compression(N, message, h);
        
        byte[] NMessage = new byte[64];
        Arrays.fill(NMessage, (byte) 0x00);
        
        inc = 0;
        while(length > 0)
        {
            NMessage[63 - inc] = (byte) (length & 0xff);
            length >>= 8;
            inc++;
        }
        
        N = addModule(N, NMessage);
        sigma = addModule(sigma, message);

        h = compression(N0, N, h);
        h = compression(N0, sigma, h);
        
        if(outputMode)
            return h;
        return Arrays.copyOf(h, 32);
    }
    
    private byte[] addModule(byte[] a, byte[] b)
    {        
        byte[] result = new byte[64];
        int t = 0;
        
        for(int i = 63; i >= 0; i--)
        {
            t = (a[i] & 0xff) + (b[i] & 0xff)  + (t >> 8);
            result[i] = (byte) (t & 0xff);
        }
        return result;
    }
    
    private byte[] xor(byte[] a, byte[] b)
    {
        byte[] result = new byte[a.length];
        for(int i = 0; i < a.length; i++)
            result[i] = (byte) (a[i] ^ b[i]);
        return result;
    }
    
    
    /**
     *  <h1>Функция сжатия.</h1>
     * Значение хэш-кода сообщения вычисляется с использованием
     * итерационной процедуры. На каждой итерации вычисления хэш-кода используется
     * функция сжатия: V512 x V512 -> V512
     * @param N вектор инициализации
     * @param m блок данных
     * @param h часть хеша
     * @return 
     */
    private byte[] compression(byte [] N, byte[] m, byte[] h)
    {
        
        byte[] K;
        K = xor(h, N);
        K = Stransformation(K);
        K = Ptransformation(K);
        K = Ltransformation(K);
        
        byte[] t = Etransformation(K, m);
        t = xor(h, t);
        byte[] result = xor(t, m);
            
        return result;
    }
    
    /**
     * Часть функции сжатия. Состоит из 12 раундов.
     * Фактически блочный шифр.
     * @param K промежуточный вектор
     * @param m блок данных 512 бит
     * @return 
     */
    private byte[] Etransformation(byte[] K, byte[] m)
    {
        byte[] state;
        
        state = xor(K, m);
        for(int i = 0; i < 12; i++)
        {
            state = Stransformation(state);
            state = Ptransformation(state);
            state = Ltransformation(state);

            K = KeyShedule(K, i);
            state = xor(state, K);
        }
        
        return state;
    }
    
    /**
     * Генерация ключа для каждой итерации
     * @param K
     * @param i индекс итеррационной константы
     * @return 
     */
    private byte[] KeyShedule(byte[] K, int i)
    {
        K = xor(K, C[i]);
        
        K = Stransformation(K);
        K = Ptransformation(K);
        K = Ltransformation(K);
        
        return K;
    }
    
    /**
     * <h1>Нелинейное биективное преобразование множества двоичных векторов</h1>
     * Заменяет значения элементов в соответствие с таблицей
     * @param a блок данных
     * @return 
     */
    private byte[] Stransformation(byte[] a)
    {        
        byte[] result = new byte[64];
        
        for(int i = 0; i < 64; i++)
            result[i] = Pi[(a[i] & 0xFF)];
        
        return result;
    }
    
    /**
     * <h1>Функция перестановки.</h1>
     * Переставляет элементы массива в соответствие с таблицой
     * @param a блок данных
     * @return 
     */
    private byte[] Ptransformation(byte[] a)
    {
        byte[] result = new byte[64];
        
        for(int i = 0; i < 64; i++)
            result[i] = a[Tau[i]];
        
        return result;
    }
    
    /**
     * <h1>Линейное преобразование множества двоичных векторов</h1>
     * Умножение справа на матрицу А над полем GF(2). Работает долго... 
     * @param a блок данных
     * @return 
     */
    private byte[] Ltransformation(byte[] a)
    {
        byte[] result = new byte[64];
        for(int i = 0; i < 8; i++)
        {
            for(int k = 0; k < 8; k++)
            {
                if((a[i*8+k] & (0x80)) != 0)
                {
                    result[8*i] ^= A[k*8+0][0];
                    result[8*i+1] ^= A[k*8+0][1];
                    result[8*i+2] ^= A[k*8+0][2];
                    result[8*i+3] ^= A[k*8+0][3];
                    result[8*i+4] ^= A[k*8+0][4];
                    result[8*i+5] ^= A[k*8+0][5];
                    result[8*i+6] ^= A[k*8+0][6];
                    result[8*i+7] ^= A[k*8+0][7];
                }
                if((a[i*8+k] & (0x40)) != 0)
                    {
                    result[8*i] ^= A[k*8+1][0];
                    result[8*i+1] ^= A[k*8+1][1];
                    result[8*i+2] ^= A[k*8+1][2];
                    result[8*i+3] ^= A[k*8+1][3];
                    result[8*i+4] ^= A[k*8+1][4];
                    result[8*i+5] ^= A[k*8+1][5];
                    result[8*i+6] ^= A[k*8+1][6];
                    result[8*i+7] ^= A[k*8+1][7];
                }
                if((a[i*8+k] & (0x20)) != 0)
                    {
                    result[8*i] ^= A[k*8+2][0];
                    result[8*i+1] ^= A[k*8+2][1];
                    result[8*i+2] ^= A[k*8+2][2];
                    result[8*i+3] ^= A[k*8+2][3];
                    result[8*i+4] ^= A[k*8+2][4];
                    result[8*i+5] ^= A[k*8+2][5];
                    result[8*i+6] ^= A[k*8+2][6];
                    result[8*i+7] ^= A[k*8+2][7];
                }
                if((a[i*8+k] & (0x10)) != 0)
                    {
                    result[8*i] ^= A[k*8+3][0];
                    result[8*i+1] ^= A[k*8+3][1];
                    result[8*i+2] ^= A[k*8+3][2];
                    result[8*i+3] ^= A[k*8+3][3];
                    result[8*i+4] ^= A[k*8+3][4];
                    result[8*i+5] ^= A[k*8+3][5];
                    result[8*i+6] ^= A[k*8+3][6];
                    result[8*i+7] ^= A[k*8+3][7];
                }
                if((a[i*8+k] & (0x8)) != 0)
                    {
                    result[8*i] ^= A[k*8+4][0];
                    result[8*i+1] ^= A[k*8+4][1];
                    result[8*i+2] ^= A[k*8+4][2];
                    result[8*i+3] ^= A[k*8+4][3];
                    result[8*i+4] ^= A[k*8+4][4];
                    result[8*i+5] ^= A[k*8+4][5];
                    result[8*i+6] ^= A[k*8+4][6];
                    result[8*i+7] ^= A[k*8+4][7];
                }
                if((a[i*8+k] & (0x4)) != 0)
                    {
                    result[8*i] ^= A[k*8+5][0];
                    result[8*i+1] ^= A[k*8+5][1];
                    result[8*i+2] ^= A[k*8+5][2];
                    result[8*i+3] ^= A[k*8+5][3];
                    result[8*i+4] ^= A[k*8+5][4];
                    result[8*i+5] ^= A[k*8+5][5];
                    result[8*i+6] ^= A[k*8+5][6];
                    result[8*i+7] ^= A[k*8+5][7];
                }
                if((a[i*8+k] & (0x2)) != 0)
                    {
                    result[8*i] ^= A[k*8+6][0];
                    result[8*i+1] ^= A[k*8+6][1];
                    result[8*i+2] ^= A[k*8+6][2];
                    result[8*i+3] ^= A[k*8+6][3];
                    result[8*i+4] ^= A[k*8+6][4];
                    result[8*i+5] ^= A[k*8+6][5];
                    result[8*i+6] ^= A[k*8+6][6];
                    result[8*i+7] ^= A[k*8+6][7];
                }
                if((a[i*8+k] & (0x1)) != 0)
                    {
                    result[8*i] ^= A[k*8+7][0];
                    result[8*i+1] ^= A[k*8+7][1];
                    result[8*i+2] ^= A[k*8+7][2];
                    result[8*i+3] ^= A[k*8+7][3];
                    result[8*i+4] ^= A[k*8+7][4];
                    result[8*i+5] ^= A[k*8+7][5];
                    result[8*i+6] ^= A[k*8+7][6];
                    result[8*i+7] ^= A[k*8+7][7];
                }
            }
        }
        return result;
    }
    
}
