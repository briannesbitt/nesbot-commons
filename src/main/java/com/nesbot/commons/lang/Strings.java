package com.nesbot.commons.lang;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Strings
{
   public static final String NL = System.getProperty("line.separator");
   public static final String PS = File.pathSeparator;
   public static final String FS = File.separator;

   public static boolean isNullOrEmpty(String s)
   {
      return (s == null || s.length() == 0);
   }
   public static List<String> split(String source, String separator)
   {
      return split(source, separator, -1);
   }
   public static List<String> split(String source, String separator, int limit)
   {
      if (source == null)
      {
         return new ArrayList<String>();
      }
      int sourceLength = source.length();
      if (sourceLength == 0)
      {
         return new ArrayList<String>();
      }
      List<String> parts = new ArrayList<String>();

      int lastIndex = 0, nextIndex = 0;
      int sepLength = separator.length();
      while ((nextIndex = source.indexOf(separator, lastIndex)) != -1)
      {
         parts.add(source.substring(lastIndex, nextIndex));
         lastIndex = nextIndex + sepLength;
         if (limit > 0 && parts.size() >= (limit - 1))
         {
            break;
         }
      }
      parts.add(source.substring(lastIndex));
      return parts;
   }
   public static String stripWhiteSpace(String orig)
   {
      return replace(orig, " ", "");
   }
   public static String replace(String orig, String from, String to)
   {
      if (isNullOrEmpty(orig) || isNullOrEmpty(from))
      {
         return orig;
      }

      if (to == null)
      {
         to = "";
      }

      int fromLength = from.length();
      int start = orig.indexOf(from);

      if (start == -1)
      {
         return orig;
      }

      StringBuffer buffer;

      if (to.length() >= fromLength)
      {
         if (from.equals(to))
         {
            return orig;
         }
         buffer = new StringBuffer(orig.length());
      }
      else
      {
         buffer = new StringBuffer();
      }

      char[] origChars = orig.toCharArray();

      int copyFrom = 0;
      while (start != -1)
      {
         buffer.append(origChars, copyFrom, start - copyFrom);
         buffer.append(to);
         copyFrom = start + fromLength;
         start = orig.indexOf(from, copyFrom);
      }

      buffer.append(origChars, copyFrom, origChars.length - copyFrom);
      return buffer.toString();
   }
   public static String repeat(String s, int times)
   {
      StringBuilder sb = new StringBuilder(s.length() * times);
      for (int i = 0; i < times; i++)
      {
         sb.append(s);
      }
      return sb.toString();
   }
   public static String format(String format, Object... o)
   {
      int cnt = o.length;

      if (cnt == 0)
      {
         return format;
      }

      for (int i = 0; i < cnt; i++)
      {
         format = replace(format, "{" + i + "}", o[i].toString());
      }
      return format;
   }

   public static String urlEncode(String v) throws UnsupportedEncodingException
   {
      return URLEncoder.encode(v, "UTF-8");
   }

   public static String randAsciiString(int length)
   {
      if (length <= 0)
      {
         throw new IllegalArgumentException("Length must be > 0.");
      }

      StringBuilder buf = new StringBuilder();

      while (length-- != 0)
      {
         char ch = (char) Compute.rand(33, 126);
         buf.append(ch);
      }
      return buf.toString();
   }
   public static String randAlphaString(int length)
   {
      return randAlphaString(length, false);
   }
   public static String randAlphaString(int length, boolean includeUpper)
   {
      if (length <= 0)
      {
         throw new IllegalArgumentException("Length must be > 0.");
      }

      StringBuilder buf = new StringBuilder();

      while (length-- != 0)
      {
         char ch = (char) Compute.rand(97, 122);

         if (includeUpper)
         {
            if (Compute.rand(1) == 1)
            {
               ch = Character.toUpperCase(ch);
            }
         }
         buf.append(ch);
      }
      return buf.toString();
   }
   public static String[] arrayPush(String[] src, String element)
   {
      String[] dest = new String[src.length + 1];
      System.arraycopy(src, 0, dest, 0, src.length);
      dest[src.length] = element;
      return dest;
   }
}