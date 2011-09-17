package com.nesbot.commons.datetime;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class TestDater
{
   @Test
   public void testCreate()
   {
      Dater d = Dater.now();
      assertFalse(Dater.create(d) == d);
      assertTrue(Dater.create(d).millis() == d.millis());
      assertEquals(Now.year(), d.year());
      assertEquals(Now.month(), d.month());
      assertEquals(Now.date(), d.date());
      assertEquals(Now.hour(), d.hour());
      assertEquals(Now.minute(), d.minute());
      assertEquals(Now.second(), d.second());
      d = Dater.create(2010, 3, 1);
      assertEquals(2010, d.year());
      assertEquals(3, d.month());
      assertEquals(1, d.date());
      assertEquals(0, d.hour());
      assertEquals(0, d.minute());
      assertEquals(0, d.second());
      d = Dater.create(2010, 3, 1, 5, 6, 7);
      assertEquals(2010, d.year());
      assertEquals(3, d.month());
      assertEquals(1, d.date());
      assertEquals(5, d.hour());
      assertEquals(6, d.minute());
      assertEquals(7, d.second());
      d = Dater.create(2010, 1, 32, 5, 6, 7);
      assertEquals(2010, d.year());
      assertEquals(2, d.month());
      assertEquals(1, d.date());
      assertEquals(5, d.hour());
      assertEquals(6, d.minute());
      assertEquals(7, d.second());
      d = Dater.create(2010, 1, 1, 25, 6, 7);
      assertEquals(2010, d.year());
      assertEquals(1, d.month());
      assertEquals(2, d.date());
      assertEquals(1, d.hour());
      assertEquals(6, d.minute());
      assertEquals(7, d.second());
      d = Dater.create("1975-5-21");
      assertEquals(1975, d.year());
      assertEquals(5, d.month());
      assertEquals(21, d.date());
      assertEquals(0, d.hour());
      assertEquals(0, d.minute());
      assertEquals(0, d.second());
      d = Dater.create("1975-05-3");
      assertEquals(1975, d.year());
      assertEquals(5, d.month());
      assertEquals(3, d.date());
      assertEquals(0, d.hour());
      assertEquals(0, d.minute());
      assertEquals(0, d.second());
      d = Dater.create("1975|05|21", "|");
      assertEquals(1975, d.year());
      assertEquals(5, d.month());
      assertEquals(21, d.date());
      assertEquals(0, d.hour());
      assertEquals(0, d.minute());
      assertEquals(0, d.second());
   }
   @Test
   public void testCreateFromMillis()
   {
      Dater d = Dater.createFromMillis(Now.millis());
      assertEquals(Now.year(), d.year());
      assertEquals(Now.month(), d.month());
      assertEquals(Now.date(), d.date());
      assertEquals(Now.hour(), d.hour());
      assertEquals(Now.minute(), d.minute());
      assertEquals(Now.second(), d.second());
   }
   @Test
   public void testCreateFromTimestamp()
   {
      Dater d = Dater.create(Now.timestamp());
      assertEquals(Now.year(), d.year());
      assertEquals(Now.month(), d.month());
      assertEquals(Now.date(), d.date());
      assertEquals(Now.hour(), d.hour());
      assertEquals(Now.minute(), d.minute());
      assertEquals(Now.second(), d.second());
   }
   @Test
   public void testClone()
   {
      Dater d1 = Dater.now();
      Dater d2 = Dater.now();
      Dater d3 = d2.addDay();
      assertTrue(d2.date() == d3.date());
      assertTrue(d2 == d3);
      assertTrue(d1.date() != d2.date());
      assertTrue(d1 != d2);
      try
      {
         Dater clone = (Dater) d1.clone();
         assertTrue(clone != d1);
         assertTrue(d1.millis() == clone.millis());
         d1.addDay();
         assertFalse(d1.millis() == clone.millis());
      }
      catch (Exception ex)
      {
         fail(ex.getClass().getName() + " : " + ex.getMessage());
      }
   }
   @Test
   public void testAddDays()
   {
      Dater d;
      d = Dater.create(2010, 1, 1);
      d.addDay();
      assertEquals(2010, d.year());
      assertEquals(1, d.month());
      assertEquals(2, d.date());
      d.addDays(-2);
      assertEquals(2009, d.year());
      assertEquals(12, d.month());
      assertEquals(31, d.date());
      d.addDay();
      assertEquals(2010, d.year());
      assertEquals(1, d.month());
      assertEquals(1, d.date());
      d = Dater.create(2010, 1, 1);
      d.addDays(2);
      assertEquals(2010, d.year());
      assertEquals(1, d.month());
      assertEquals(3, d.date());
      d = Dater.create(2010, 1, 1);
      d.addDays(30);
      assertEquals(2010, d.year());
      assertEquals(1, d.month());
      assertEquals(31, d.date());
      d = Dater.create(2010, 1, 1);
      d.addDays(31);
      assertEquals(2010, d.year());
      assertEquals(2, d.month());
      assertEquals(1, d.date());
      d = Dater.create(2010, 12, 31);
      d.addDay();
      assertEquals(2011, d.year());
      assertEquals(1, d.month());
      assertEquals(1, d.date());
   }
   @Test
   public void testAddMonths()
   {
      Dater d;
      d = Dater.create(2010, 1, 1);
      d.addMonth();
      assertEquals(2010, d.year());
      assertEquals(2, d.month());
      assertEquals(1, d.date());
      d = Dater.create(2010, 1, 1);
      d.addMonths(2);
      assertEquals(2010, d.year());
      assertEquals(3, d.month());
      assertEquals(1, d.date());
      d = Dater.create(2010, 1, 31);
      d.addMonth();
      assertEquals(2010, d.year());
      assertEquals(3, d.month());
      assertEquals(3, d.date());
      Dater dm = Dater.create(2010, 1, 31);
      dm.month(2);
      assertEquals(2010, d.year());
      assertEquals(3, d.month());
      assertEquals(3, d.date());
      d = Dater.create(2010, 1, 1);
      d.addMonths(12);
      assertEquals(2011, d.year());
      assertEquals(1, d.month());
      assertEquals(1, d.date());
      d = Dater.create(2010, 12, 31);
      d.addMonth();
      assertEquals(2011, d.year());
      assertEquals(1, d.month());
      assertEquals(31, d.date());
   }
   @Test
   public void testAddYears()
   {
      Dater d;
      d = Dater.create(2010, 1, 1);
      d.addYear();
      assertEquals(2011, d.year());
      assertEquals(1, d.month());
      assertEquals(1, d.date());
      d = Dater.create(2010, 1, 1);
      d.addYears(2);
      assertEquals(2012, d.year());
      assertEquals(1, d.month());
      assertEquals(1, d.date());
      d = Dater.create(2010, 1, 31);
      d.addYear();
      assertEquals(2011, d.year());
      assertEquals(1, d.month());
      assertEquals(31, d.date());
      d = Dater.create(2010, 1, 31);
      d.year(2012);
      assertEquals(2012, d.year());
      assertEquals(1, d.month());
      assertEquals(31, d.date());
      d = Dater.create(2010, 1, 1);
      d.addYears(12);
      assertEquals(2022, d.year());
      assertEquals(1, d.month());
      assertEquals(1, d.date());
      d = Dater.create(2010, 12, 31);
      d.addYear();
      assertEquals(2011, d.year());
      assertEquals(12, d.month());
      assertEquals(31, d.date());
      d = Dater.create(2008, 2, 29);
      d.addYear();
      assertEquals(2009, d.year());
      assertEquals(3, d.month());
      assertEquals(1, d.date());
      d = Dater.create(2010, 12, 31);
      d.addYears(-1);
      assertEquals(2009, d.year());
      assertEquals(12, d.month());
      assertEquals(31, d.date());
      d = Dater.create(2010, 12, 31);
      d.addYears(-7);
      assertEquals(2003, d.year());
      assertEquals(12, d.month());
      assertEquals(31, d.date());
   }
   @Test
   public void testReset()
   {
      Dater d;
      d = Dater.create(2002, 2, 4);
      assertEquals(2002, d.year());
      assertEquals(2, d.month());
      assertEquals(4, d.date());
      d.reset();
      assertEquals(Now.year(), d.year());
      assertEquals(Now.month(), d.month());
      assertEquals(Now.date(), d.date());
      d = Dater.create(2010, 5, 21);
      d.reset(d.timestamp() - (365 * Dater.secondsPerDay));
      assertEquals(2009, d.year());
      assertEquals(5, d.month());
      assertEquals(21, d.date());
      d = Dater.create(2010, 5, 21);
      d.reset(d.timestamp() - (1 * Dater.secondsPerDay));
      assertEquals(2010, d.year());
      assertEquals(5, d.month());
      assertEquals(20, d.date());
   }
   @Test
   public void testSetHour()
   {
      Dater d = Dater.now();
      d.hour(3);
      assertEquals(3, d.hour());

      int date = d.date();
      d.hour(26);
      assertEquals(2, d.hour());
      assertEquals(date + 1, d.date());
   }
   @Test
   public void testSetMinute()
   {
      Dater d = Dater.now();
      d.minute(34);
      assertEquals(34, d.minute());

      d.minute(65);
      assertEquals(5, d.minute());
   }
   @Test
   public void testSetSecond()
   {
      Dater d = Dater.now();
      d.second(59);
      assertEquals(59, d.second());

      d.second(61);
      assertEquals(1, d.second());
   }
   @Test
   public void testDateOfYear()
   {
      Dater d = Dater.create(2011, 1, 5);
      assertEquals(5, d.dateOfYear());

      d = Dater.create(2011, 12, 30);
      assertEquals(364, d.dateOfYear());
   }
   @Test
   public void testDayOfWeek()
   {
      Dater d = Dater.create(2000, 1, 5);
      assertEquals(Calendar.WEDNESDAY, d.dayOfWeek());

      d = Dater.create(2011, 9, 11);
      assertEquals(Calendar.SUNDAY, d.dayOfWeek());

      d = Dater.create(2011, 9, 10);
      assertEquals(Calendar.SATURDAY, d.dayOfWeek());
   }
   @Test
   public void testStartOfDay()
   {
      Dater d = Dater.now();
      d.startOfDay();
      assertEquals(0, d.hour());
      assertEquals(0, d.minute());
      assertEquals(0, d.second());
   }
   @Test
   public void testEndOfDay()
   {
      Dater d = Dater.now();
      d.endOfDay();
      assertEquals(23, d.hour());
      assertEquals(59, d.minute());
      assertEquals(59, d.second());
   }
   @Test
   public void testStartOfMonth()
   {
      Dater d = Dater.create(2011, 9, 5);
      d.startOfMonth();
      assertEquals(2011, d.year());
      assertEquals(9, d.month());
      assertEquals(1, d.date());
      assertEquals(0, d.hour());
      assertEquals(0, d.minute());
      assertEquals(0, d.second());

      d.month(8);
      d.date(1);
      d.startOfMonth();
      assertEquals(2011, d.year());
      assertEquals(8, d.month());
      assertEquals(1, d.date());
      assertEquals(0, d.hour());
      assertEquals(0, d.minute());
      assertEquals(0, d.second());
   }
   @Test
   public void testEndOfMonth()
   {
      Dater d = Dater.create(2011, 1, 5);
      d.endOfMonth();
      assertEquals(2011, d.year());
      assertEquals(1, d.month());
      assertEquals(31, d.date());
      assertEquals(23, d.hour());
      assertEquals(59, d.minute());
      assertEquals(59, d.second());

      d.month(2);
      d.date(11);
      d.endOfMonth();
      assertEquals(2011, d.year());
      assertEquals(2, d.month());
      assertEquals(28, d.date());
      assertEquals(23, d.hour());
      assertEquals(59, d.minute());
      assertEquals(59, d.second());

      d.year(2008);
      d.month(2);
      d.date(15);
      d.endOfMonth();
      assertEquals(2008, d.year());
      assertEquals(2, d.month());
      assertEquals(29, d.date());
      assertEquals(23, d.hour());
      assertEquals(59, d.minute());
      assertEquals(59, d.second());
   }
   @Test
   public void testToDateString()
   {
      Dater d = Dater.create(2011, 1, 5);
      assertEquals("2011-01-05", d.toDateString());
   }
   @Test
   public void testToTimestamp()
   {
      assertEquals(System.currentTimeMillis() / 1000, Dater.now().timestamp());
   }
   @Test
   public void testyyyymmdd()
   {
      Dater d = Dater.create(2011, 1, 5);
      assertEquals("20110105", d.yyyymmdd());
      d = Dater.create(2011, 12, 15);
      assertEquals("20111215", d.yyyymmdd());
   }
   @Test
   public void testToStringWithPattern()
   {
      Dater d = Dater.create(2011, 1, 5, 10, 11, 12);
      assertEquals("2011 2 5 Wed 10:11:12", d.toString("yyyy w D E H:m:s"));
   }
   @Test
   public void testToString()
   {
      Dater d = Dater.create(2011, 1, 5, 10, 11, 12);
      assertEquals("Wed, 05 Jan 2011 10:11:12 -0500", d.toString());
      d = Dater.create(2011, 9, 11, 5, 6, 2);
      assertEquals("Sun, 11 Sep 2011 05:06:02 -0400", d.toString());
   }
   @Test
   public void testIsLessThan() throws CloneNotSupportedException
   {
      Dater d = Dater.create(2011, 1, 5, 10, 11, 12);
      Dater d2 = ((Dater) d.clone()).addDays(1);

      assertTrue(d.isLessThan(d2));
      assertFalse(d2.isLessThan(d));
   }
   @Test
   public void testIsGreaterThan() throws CloneNotSupportedException
   {
      Dater d = Dater.create(2011, 1, 5, 10, 11, 12);
      Dater d2 = ((Dater) d.clone()).addDays(1);

      assertTrue(d2.isGreaterThan(d));
      assertFalse(d.isGreaterThan(d2));
   }
   @Test
   public void testEquals() throws CloneNotSupportedException
   {
      Dater d = Dater.create(2011, 1, 5, 10, 11, 12);
      Dater d2 = ((Dater) d.clone()).addDays(1);

      assertFalse(d.equals(new Object()));
      assertFalse(d.equals(d2));
      assertTrue(d.equals(d2.addDays(-1)));
   }
   @Test
   public void testTimestamp()
   {
      assertEquals(System.currentTimeMillis() / 1000, Now.timestamp());
   }
}