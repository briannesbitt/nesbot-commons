package com.nesbot.commons.lang;

public class Parser
{
   public static long tryParseLong(String s)
   {
      return tryParseLong(s, 0L);
   }
   public static long tryParseLong(String s, long def)
   {
      if (Strings.isNullOrEmpty(s))
      {
         return def;
      }
      try
      {
         return Long.parseLong(s);
      }
      catch (NumberFormatException e)
      {
         return def;
      }
   }
   public static int tryParseInt(String s)
   {
      return tryParseInt(s, 0);
   }
   public static int tryParseInt(String s, int def)
   {
      if (Strings.isNullOrEmpty(s))
      {
         return def;
      }
      try
      {
         return Integer.parseInt(s);
      }
      catch (NumberFormatException e)
      {
         return def;
      }
   }
}