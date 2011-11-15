package com.nesbot.commons.lang;

import com.nesbot.commons.tests.TestHelpers;
import org.junit.Test;

import java.nio.ByteOrder;

import static org.junit.Assert.*;

public class TestConvert
{
   @Test
   public void testStaticPrivateCtors()
   {
      TestHelpers.assertPrivateNoArgsCtor(Convert.class);
   }
   @Test
   public void testToBytesShortLE()
   {
      byte[] buf = Convert.toBytes((short)1, ByteOrder.LITTLE_ENDIAN);
      assertEquals(2, buf.length);
      assertEquals(buf[0], 1);
      assertEquals(buf[1], 0);
   }
   @Test
   public void testToBytesShortBE()
   {
      byte[] buf = Convert.toBytes((short)1, ByteOrder.BIG_ENDIAN);
      assertEquals(2, buf.length);
      assertEquals(buf[0], 0);
      assertEquals(buf[1], 1);
   }
   @Test
   public void testToBytesIntLE()
   {
      byte[] buf = Convert.toBytes((int)1, ByteOrder.LITTLE_ENDIAN);
      assertEquals(4, buf.length);
      assertEquals(buf[0], 1);
      assertEquals(buf[1], 0);
      assertEquals(buf[2], 0);
      assertEquals(buf[3], 0);
   }
   @Test
   public void testToBytesIntBE()
   {
      byte[] buf = Convert.toBytes((int)1, ByteOrder.BIG_ENDIAN);
      assertEquals(4, buf.length);
      assertEquals(buf[0], 0);
      assertEquals(buf[1], 0);
      assertEquals(buf[2], 0);
      assertEquals(buf[3], 1);
   }
   @Test
   public void testToBytesLongLE()
   {
      byte[] buf = Convert.toBytes((long)1, ByteOrder.LITTLE_ENDIAN);
      assertEquals(8, buf.length);
      assertEquals(buf[0], 1);
      assertEquals(buf[1], 0);
      assertEquals(buf[2], 0);
      assertEquals(buf[3], 0);
      assertEquals(buf[4], 0);
      assertEquals(buf[5], 0);
      assertEquals(buf[6], 0);
      assertEquals(buf[7], 0);
   }
   @Test
   public void testToBytesLongBE()
   {
      byte[] buf = Convert.toBytes((long)1, ByteOrder.BIG_ENDIAN);
      assertEquals(8, buf.length);
      assertEquals(buf[0], 0);
      assertEquals(buf[1], 0);
      assertEquals(buf[2], 0);
      assertEquals(buf[3], 0);
      assertEquals(buf[4], 0);
      assertEquals(buf[5], 0);
      assertEquals(buf[6], 0);
      assertEquals(buf[7], 1);
   }
   @Test
   public void testToBytesString()
   {
      byte[] buf = Convert.toBytes("brian");
      assertEquals(5, buf.length);
      assertEquals(buf[0], 98);
      assertEquals(buf[1], 114);
      assertEquals(buf[2], 105);
      assertEquals(buf[3], 97);
      assertEquals(buf[4], 110);
   }

   @Test
   public void testToShortLE()
   {
      byte[] buf = {1, 0};
      assertEquals((short)1, Convert.toShort(buf, ByteOrder.LITTLE_ENDIAN));
   }
   @Test
   public void testToShortBE()
   {
      byte[] buf = {0, 1};
      assertEquals((short)1, Convert.toShort(buf, ByteOrder.BIG_ENDIAN));
   }
   @Test
   public void testToIntLE()
   {
      byte[] buf = {1, 0, 0, 0};
      assertEquals(1, Convert.toInt(buf, ByteOrder.LITTLE_ENDIAN));
   }
   @Test
   public void testToIntBE()
   {
      byte[] buf = {0, 0, 0, 1};
      assertEquals(1, Convert.toInt(buf, ByteOrder.BIG_ENDIAN));
   }
   @Test
   public void testToLongLE()
   {
      byte[] buf = {1, 0, 0, 0, 0, 0, 0, 0};
      assertEquals(1L, Convert.toLong(buf, ByteOrder.LITTLE_ENDIAN));
   }
   @Test
   public void testToLongBE()
   {
      byte[] buf = {0, 0, 0, 0, 0, 0, 0, 1};
      assertEquals(1L, Convert.toLong(buf, ByteOrder.BIG_ENDIAN));
   }
   @Test
   public void testToString()
   {
      byte[] buf = {98, 114, 105, 97, 110};
      assertEquals("brian", Convert.toString(buf));
   }

   @Test
   public void testToReadableBase32()
   {
      String hello = "hellothisdivby3:-)";
      String encoded = Convert.encodeReadableBase32(Convert.toBytes(hello));

      assertEquals("F3LQEFKRR6F3NR5EKNR8EBWM59AQLB", encoded);
      assertEquals(hello, Convert.toString(Convert.decodeReadableBase32(encoded)));
   }
}