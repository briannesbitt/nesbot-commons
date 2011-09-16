package com.nesbot.commons.tests;

import org.junit.Ignore;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

@Ignore
public class TestHelpers
{
   public static void assertPrivateNoArgsCtor(final Class<?> cls)
   {
      final Constructor<?> c = cls.getDeclaredConstructors()[0];
      c.setAccessible(true);

      try
      {
         final Object n = c.newInstance((Object[]) null);
         assertNotNull(n);
      }
      catch (InstantiationException ex)
      {
         fail(ex.getMessage());
      }
      catch (IllegalAccessException ex)
      {
         fail(ex.getMessage());
      }
      catch (InvocationTargetException ex)
      {
         fail(ex.getMessage());
      }
   }
}