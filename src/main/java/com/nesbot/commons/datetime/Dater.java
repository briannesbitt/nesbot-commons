package com.nesbot.commons.datetime;

import com.nesbot.commons.lang.Strings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Dater implements Cloneable
{
   public static final long millisecondsPerSecond = 1000;
   public static final long secondsPerMinute = 60;
   public static final long secondsPerHour = secondsPerMinute * 60;
   public static final long secondsPerDay = secondsPerHour * 24;
   public static final long milliSecondsPerDay = secondsPerDay * millisecondsPerSecond;

   private Calendar _cal;

   private Dater()
   {
      _cal = GregorianCalendar.getInstance();
   }
   private Dater(long  timestamp)
   {
      this();
      reset(timestamp);
   }
   private Dater(Calendar calendar)
   {
      _cal = calendar;
   }

   /* Factory methods */
   public static Dater now()
   {
      return new Dater();
   }
   public static Dater create(Dater dater)
   {
      return createFromMillis(dater.millis());
   }
   public static Dater createFromMillis(long millis)
   {
      return new Dater().resetFromMillis(millis);
   }
   public static Dater create(long timestamp)
   {
      return new Dater(timestamp);
   }
   public static Dater create(int year, int month, int date)
   {
      return create(year, month, date, 0, 0, 0);
   }
   public static Dater create(int year, int month, int date, int hour, int minute, int second)
   {
      Calendar cal = GregorianCalendar.getInstance();
      cal.set(year, month - 1, date, hour, minute, second);
      return new Dater(cal);
   }
   public static Dater create(String yyyyMmDd)
   {
      return create(yyyyMmDd, "-");
   }
   public static Dater create(String yyyyMmDd, String glue)
   {
      List<String> parts = Strings.split(yyyyMmDd, glue, 3);
      return create(Integer.valueOf(parts.get(0)), Integer.valueOf(parts.get(1)), Integer.valueOf(parts.get(2)));
   }

   public Dater reset()
   {
      return resetFromMillis(Now.millis());
   }
   public Dater reset(long timestamp)
   {
      _cal.setTimeInMillis(timestamp * millisecondsPerSecond);
      return this;
   }
   public Dater resetFromMillis(long millis)
   {
      _cal.setTimeInMillis(millis);
      return this;
   }

   /* Getters / Setters */
   public int year()
   {
      return _cal.get(Calendar.YEAR);
   }
   public Dater year(int year)
   {
      _cal.set(Calendar.YEAR, year);
      return this;
   }
   public int month()
   {
      return _cal.get(Calendar.MONTH) + 1;
   }
   public Dater month(int month)
   {
      _cal.set(Calendar.MONTH, month - 1);
      return this;
   }
   public int date()
   {
      return _cal.get(Calendar.DATE);
   }
   public Dater date(int date)
   {
      _cal.set(Calendar.DATE, date);
      return this;
   }
   public int hour()
   {
      return _cal.get(Calendar.HOUR_OF_DAY);
   }
   public Dater hour(int hour)
   {
      _cal.set(Calendar.HOUR_OF_DAY, hour);
      return this;
   }
   public int minute()
   {
      return _cal.get(Calendar.MINUTE);
   }
   public Dater minute(int minute)
   {
      _cal.set(Calendar.MINUTE, minute);
      return this;
   }
   public int second()
   {
      return _cal.get(Calendar.SECOND);
   }
   public Dater second(int second)
   {
      _cal.set(Calendar.SECOND, second);
      return this;
   }
   public int millisecond()
   {
      return _cal.get(Calendar.MILLISECOND);
   }
   public Dater millisecond(int millisecond)
   {
      _cal.set(Calendar.MILLISECOND, millisecond);
      return this;
   }
   public int dateOfYear()
   {
      return _cal.get(Calendar.DAY_OF_YEAR);
   }
   public int dayOfWeek()
   {
      return _cal.get(Calendar.DAY_OF_WEEK);
   }

   //the current time as UTC milliseconds from the epoch.
   public long millis()
   {
      return _cal.getTimeInMillis();
   }
   //the current time as UTC seconds from the epoch.
   public long timestamp()
   {
      return millis() / millisecondsPerSecond;
   }

   public Dater addDay()
   {
      return addDays(1);
   }
   public Dater addDays(int d)
   {
      return this.date(this.date() + d);
   }
   public Dater addMonth()
   {
      return addMonths(1);
   }
   public Dater addMonths(int m)
   {
      return this.month(this.month() + m);
   }
   public Dater addYear()
   {
      return addYears(1);
   }
   public Dater addYears(int y)
   {
      return this.year(this.year() + y);
   }

   public Dater startOfDay()
   {
      hour(0);
      minute(0);
      second(0);
      return this;
   }
   public Dater endOfDay()
   {
      hour(23);
      minute(59);
      second(59);
      return this;
   }
   public Dater startOfMonth()
   {
      date(1);
      startOfDay();
      return this;
   }
   public Dater endOfMonth()
   {
      addMonth();
      startOfMonth();
      addDays(-1);
      endOfDay();
      return this;
   }

   public String toDateString()
   {
      return toString("yyyy-MM-dd");
   }
   public String yyyymmdd()
   {
      return toString("yyyyMMdd");
   }
   public String toRFC822()
   {
      return toString("EEE, d MMM yyyy HH:mm:ss Z");
   }
   public String toString(String pattern)
   {
      return new SimpleDateFormat(pattern).format(new Date(millis()));
   }

   @Override
   public String toString()
   {
      return toString("EEE, dd MMM yyyy HH:mm:ss Z");
   }
   public boolean isLessThan(Dater dater)
   {
      return millis() < dater.millis();
   }
   public boolean isGreaterThan(Dater dater)
   {
      return millis() > dater.millis();
   }
   @Override
   public boolean equals(Object obj)
   {
      if (obj instanceof Dater)
      {
         return (_cal.equals(((Dater) obj)._cal));
      }
      return false;
   }
   @Override
   public Object clone() throws CloneNotSupportedException
   {
      Dater dater = (Dater) super.clone();
      dater._cal = (Calendar) _cal.clone();
      return dater;
   }

   /*
   * These are helper functions to store a date time in a 32 bit integer without rolling over.
   * Its precise to the nearest hour as thats all that will fit in 32 bits.
   * Its good until year 2147 after that it rolls over... but I am ok with that.
   *
   * This is usually more than sufficient for something like logging where the space/memory savings might matter.
   */
   public static int toYyyymmddhh(Dater dater)
   {
      int d;
      d  = dater.year() * 1000000;
      d += dater.month() * 10000;
      d += dater.date() * 100;
      d += dater.hour();
      return d;
   }
   public static Dater fromYyyymmddhh(int d)
   {
      int year = d / 1000000;
      int month = (d / 10000) % year;
      int date = d / 100 % (year * 100 + month);
      int hour = d % (year * 10000 + month * 100 + date);

      return Dater.create(year, month, date, hour, 0, 0);
   }
}