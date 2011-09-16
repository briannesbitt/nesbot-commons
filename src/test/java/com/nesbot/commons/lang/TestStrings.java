package com.nesbot.commons.lang;

import com.nesbot.commons.tests.TestHelpers;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestStrings
{
   @Test
   public void testStaticPrivateCtors()
   {
      TestHelpers.assertPrivateNoArgsCtor(Strings.class);
   }
   @Test
   public void testIsNullOrEmptyWithNullString()
   {
      assertTrue(Strings.isNullOrEmpty(null));
   }
   @Test
   public void testIsNullOrEmptyWithEmptyString()
   {
      assertTrue(Strings.isNullOrEmpty(""));
   }
   @Test
   public void testIsNullOrEmptyOk()
   {
      assertFalse(Strings.isNullOrEmpty("a"));
   }
   @Test
   public void testIsNullOrEmptyOk2()
   {
      assertFalse(Strings.isNullOrEmpty("ab"));
   }
   @Test
   public void testSplitWithNull()
   {
      String splitMe = null;

      List<String> result = Strings.split(splitMe, "nf");
      assertNotNull(result);
      assertEquals(0, result.size());
   }
   @Test
   public void testSplitWithEmpty()
   {
      String splitMe = "";

      List<String> result = Strings.split(splitMe, "nf");
      assertNotNull(result);
      assertEquals(0, result.size());
   }
   @Test
   public void testSplitSeperatorNotFound()
   {
      String splitMe = "Brian Nesbitt";

      List<String> result = Strings.split(splitMe, "nf");
      assertEquals(1, result.size());
      assertEquals(splitMe, result.get(0));
   }
   @Test
   public void testSplitNoLimit()
   {
      String splitMe = "a||b||c||d||e||f||g||h||i";

      List<String> result = Strings.split(splitMe, "||");
      assertEquals(9, result.size());
      assertEquals("a", result.get(0));
      assertEquals("b", result.get(1));
      assertEquals("c", result.get(2));
      assertEquals("d", result.get(3));
      assertEquals("e", result.get(4));
      assertEquals("f", result.get(5));
      assertEquals("g", result.get(6));
      assertEquals("h", result.get(7));
      assertEquals("i", result.get(8));
   }
   @Test
   public void testSplitWithLimit()
   {
      String splitMe = "asepbsepcsepdsepesepfsepgsephsep";

      List<String> result = Strings.split(splitMe, "sep", 3);
      assertEquals(3, result.size());
      assertEquals("a", result.get(0));
      assertEquals("b", result.get(1));
      assertEquals("csepdsepesepfsepgsephsep", result.get(2));
   }
}