package com.nesbot.commons.lang;

public class Compute
{
   public static float round(float f, int places)
   {
      double factor = Math.pow(10, places);
      long result = Math.round(f * factor);
      return (float)((double)result / factor);
   }
   public static double round(double f, int places)
   {
      double factor = Math.pow(10, places);
      long result = Math.round(f * factor);
      return (double)result / factor;
   }
   public static int rand(int maximumInclusive)
   {
      return rand(0, maximumInclusive);
   }
   public static int rand(int minimumInclusive, int maximumInclusive)
   {
      long range = maximumInclusive - minimumInclusive + 1;
      long fraction = (long)(range * Math.random());
      return (int)(minimumInclusive + fraction);
   }
}