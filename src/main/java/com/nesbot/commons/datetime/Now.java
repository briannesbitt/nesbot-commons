package com.nesbot.commons.datetime;

public class Now
{
   public static Dater now()
   {
      return Dater.now();
   }
   public static long millis()
   {
      return System.currentTimeMillis();
   }
   public static long timestamp()
   {
      return millis() / 1000;
   }
   public static int millisecond()
   {
      return Dater.now().millisecond();
   }
   public static int second()
   {
      return Dater.now().second();
   }
   public static int minute()
   {
      return Dater.now().minute();
   }
   public static int hour()
   {
      return Dater.now().hour();
   }
   public static int month()
   {
      return Dater.now().month();
   }
   public static int date()
   {
      return Dater.now().date();
   }
   public static int year()
   {
      return Dater.now().year();
   }
}