package com.nesbot.commons.datetime;

import com.nesbot.commons.tests.TestHelpers;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class TestNow
{
   @Test
   public void testPrivateCtor()
   {
      TestHelpers.assertPrivateNoArgsCtor(Now.class);
   }
   @Test
   public void testMilliseconds()
   {
      // wow this sucks, it doesn't pass when running with cobertura because of the instrumentation lag
      // guess I have to add a buffer
      // but really!! it passes when running without cobertura
      long expected = System.currentTimeMillis();
      long actual = Now.millis();

      if (expected == actual)
      {
         assertEquals(expected, actual);
         return;
      }

      assertTrue((actual - expected) < 10);
   }
   @Test
   public void testTimestamp()
   {
      assertEquals(System.currentTimeMillis()/1000, Now.timestamp());
   }
   @Test
   public void testMillisecond()
   {
      // wow this sucks, it doesn't pass when running with cobertura because of the instrumentation lag
      // guess I have to add a buffer
      // but really!! it passes when running without cobertura
      long expected = Long.valueOf(new SimpleDateFormat("S").format(new Date()));
      long actual = Now.millisecond();

      if (expected == actual)
      {
         assertEquals(expected, actual);
         return;
      }

      assertTrue((actual - expected) < 10);
   }
   @Test
   public void testSecond()
   {
      assertEquals(Long.valueOf(new SimpleDateFormat("s").format(new Date())).longValue(), Now.second());
   }
   @Test
   public void testMinute()
   {
      assertEquals(Long.valueOf(new SimpleDateFormat("m").format(new Date())).longValue(), Now.minute());
   }
   @Test
   public void testHour()
   {
      assertEquals(Long.valueOf(new SimpleDateFormat("H").format(new Date())).longValue(), Now.hour());
   }
   @Test
   public void testDate()
   {
      assertEquals(Long.valueOf(new SimpleDateFormat("d").format(new Date())).longValue(), Now.date());
   }
   @Test
   public void testMonth()
   {
      assertEquals(Long.valueOf(new SimpleDateFormat("M").format(new Date())).longValue(), Now.month());
   }
   @Test
   public void testYear()
   {
      assertEquals(Long.valueOf(new SimpleDateFormat("yyyy").format(new Date())).longValue(), Now.year());
   }
}