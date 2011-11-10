package com.nesbot.commons.lang;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Convert
{
   public static byte[] toBytes(short value)
   {
      return toBytes(value, ByteOrder.nativeOrder());
   }
   public static byte[] toBytes(short value, ByteOrder byteOrder)
   {
      ByteBuffer buffer = ByteBuffer.allocate(2).order(byteOrder);
      buffer.putShort(value);
      return buffer.array();
   }
   public static byte[] toBytes(int value)
   {
      return toBytes(value, ByteOrder.nativeOrder());
   }
   public static byte[] toBytes(int value, ByteOrder byteOrder)
   {
      ByteBuffer buffer = ByteBuffer.allocate(4).order(byteOrder);
      buffer.putInt(value);
      return buffer.array();
   }
   public static byte[] toBytes(long value)
   {
      return toBytes(value, ByteOrder.nativeOrder());
   }
   public static byte[] toBytes(long value, ByteOrder byteOrder)
   {
      ByteBuffer buffer = ByteBuffer.allocate(8).order(byteOrder);
      buffer.putLong(value);
      return buffer.array();
   }
   public static byte[] toBytes(String value)
   {
      int len = value.length();
      byte bytes[] = new byte[len];
      for(int i = 0; i < len; i++)
      {
         char c = value.charAt(i);
         bytes[i] = (byte)c;
      }
      return bytes;
   }

   public static short toShort(byte[] b)
   {
      return toShort(b, ByteOrder.nativeOrder());
   }
   public static short toShort(byte[] b, ByteOrder byteOrder)
   {
      ByteBuffer buffer = ByteBuffer.wrap(b).order(byteOrder);
      return buffer.getShort();
   }
   public static int toInt(byte[] b)
   {
      return toInt(b, ByteOrder.nativeOrder());
   }
   public static int toInt(byte[] b, ByteOrder byteOrder)
   {
      ByteBuffer buffer = ByteBuffer.wrap(b).order(byteOrder);
      return buffer.getInt();
   }

   static final char[] BASE32_TABLE = new char[]
           {
                   '2', '3', '4', '5', '6', '7', '8', '9',
                   'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                   'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
                   'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
           };
   static Map<Character, Integer> BASE32_TABLE_LOOKUP = new HashMap<Character, Integer>();
   public static String encodeReadableBase32(byte[] bytes)
   {
      char[] chars = new char[bytes.length * 2];

      int mod = bytes.length % 3;
      if (mod != 0)
      {
         throw new IllegalArgumentException("Input data incorrect. Required multiple of 3 bytes length.");
      }

      int res = bytes.length - mod;
      int inx = 0;

      for (int i = 0; i < res; i += 3)
      {
         chars[inx + 0] = BASE32_TABLE[(bytes[i] & 0xf8) >> 3];
         chars[inx + 1] = BASE32_TABLE[((bytes[i] & 0x07) << 2) | ((bytes[i + 1] & 0xc0) >> 6)];
         chars[inx + 2] = BASE32_TABLE[((bytes[i + 1] & 0x3e) >> 1)];
         chars[inx + 3] = BASE32_TABLE[((bytes[i + 1] & 0x01) << 4) | ((bytes[i + 2] & 0xf0) >> 4)];
         chars[inx + 4] = BASE32_TABLE[(bytes[i + 2] & 0x0f)];
         inx += 5;
      }

      return new String(chars);
   }
   public static byte[] decodeReadableBase32(String value)
   {
      byte[] bytes = new byte[value.length()];

      int mod = value.length() % 5;
      if (mod != 0)
      {
         throw new IllegalArgumentException("Base32 input string incorrect. Required multiple of 5 character length.");
      }

      int res = value.length() - mod;
      int inx = 0;

      long triple;

      for (int i = 0; i < res; i += 5)
      {
         triple = GetBase32Number(value.charAt(i)) << 19;
         triple = triple | ((long) GetBase32Number(value.charAt(i + 1)) << 14);
         triple = triple | ((long) GetBase32Number(value.charAt(i + 2)) << 9);
         triple = triple | ((long) GetBase32Number(value.charAt(i + 3)) << 4);
         triple = triple | ((byte) GetBase32Number(value.charAt(i + 4)));

         bytes[inx] = (byte) ((triple & 0x00ff0000) >> 16);
         bytes[inx + 1] = (byte) ((triple & 0x0000ff00) >> 8);
         bytes[inx + 2] = (byte) (triple & 0x000000ff);

         inx += 3;
      }
      return Arrays.copyOfRange(bytes, 0, inx);
   }

   private static void EnsureReverseLookupIsPopulated()
   {
      if (BASE32_TABLE_LOOKUP.size() == 0)
      {
         synchronized (BASE32_TABLE_LOOKUP)
         {
            if (BASE32_TABLE_LOOKUP.size() == 0)
            {
               for (int i = 0; i < BASE32_TABLE.length; i++)
               {
                  BASE32_TABLE_LOOKUP.put(BASE32_TABLE[i], i);
               }
            }
         }
      }
   }
   private static int GetBase32Number(char c)
   {
      EnsureReverseLookupIsPopulated();

      if (!BASE32_TABLE_LOOKUP.containsKey(c))
      {
         throw new IndexOutOfBoundsException();
      }

      return BASE32_TABLE_LOOKUP.get(c);
   }

   
}