package com.nesbot.commons.lang;

import com.nesbot.commons.tests.TestHelpers;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestParser
{
   @Test
   public void testPrivateCtor()
   {
      TestHelpers.assertPrivateNoArgsCtor(Parser.class);
   }
   @Test
   public void testTryParseLongBlankString()
   {
      assertEquals(0L, Parser.tryParseLong(null));
      assertEquals(0L, Parser.tryParseLong(""));
   }
   @Test
   public void testTryParseLongInvalidFormat()
   {
      assertEquals(0L, Parser.tryParseLong("a"));
   }
   @Test
   public void testTryParseLongValidZero()
   {
      assertEquals(0L, Parser.tryParseLong("0"));
   }
   @Test
   public void testTryParseLongValid()
   {
      assertEquals(45L, Parser.tryParseLong("45"));
      assertEquals(Long.MAX_VALUE, Parser.tryParseLong(String.valueOf(Long.MAX_VALUE)));
   }
   @Test
   public void testTryParseLongDefaultOutOfRange()
   {
      assertEquals(2, Parser.tryParseLong(String.valueOf(Long.MAX_VALUE)+"9999"), 2);
   }
   @Test
   public void testTryParseLongDefaultInvalid()
   {
      assertEquals(89L, Parser.tryParseLong("a", 89));
   }
   @Test
   public void testTryParseLongDefaultValid()
   {
      assertEquals(45L, Parser.tryParseLong("45", 189));
   }
   @Test
   public void testTryParseIntBlankString()
   {
      assertEquals(0L, Parser.tryParseInt(null));
      assertEquals(0L, Parser.tryParseInt(""));
   }
   @Test
   public void testTryParseIntInvalidFormat()
   {
      assertEquals(0L, Parser.tryParseInt("a"));
   }
   @Test
   public void testTryParseIntValidZero()
   {
      assertEquals(0L, Parser.tryParseInt("0"));
   }
   @Test
   public void testTryParseIntValid()
   {
      assertEquals(45L, Parser.tryParseInt("45"));
   }
   @Test
   public void testTryParseIntDefaultOutOfRange()
   {
      assertEquals(2, Parser.tryParseInt(String.valueOf(Long.MAX_VALUE), 2));
   }
   @Test
   public void testTryParseIntDefaultInvalid()
   {
      assertEquals(89L, Parser.tryParseInt("a", 89));
   }
   @Test
   public void testTryParseIntDefaultValid()
   {
      assertEquals(45L, Parser.tryParseInt("45", 189));
   }
}