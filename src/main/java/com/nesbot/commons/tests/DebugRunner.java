package com.nesbot.commons.tests;

import com.nesbot.commons.lang.ClassFinder;
import com.nesbot.commons.lang.Strings;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;

public class DebugRunner
{
   public static int outputTestResult(Result result)
   {
      StringBuilder sb = new StringBuilder();
      if (result.wasSuccessful())
      {
         sb.append("[SUCCESS] ");
      }
      else
      {
         sb.append("[FAIED] ");
      }

      sb.append(Strings.format("Tests run: {0}, Failures: {1}, Igorned: {2}, Time elapsed: {3} ms",
              result.getRunCount(),
              result.getFailureCount(),
              result.getIgnoreCount(),
              result.getRunTime())).append(Strings.NL);

      if (result.getFailureCount() > 0)
      {
         for (Failure failure : result.getFailures())
         {
            System.out.println(failure.getDescription());
            System.out.println(failure.getMessage());
            System.out.println(failure.getTrace());
         }
      }

      System.out.println(sb.toString());
      return result.getFailureCount();
   }
   public static void main(String[] args) throws ClassNotFoundException, IOException
   {
      if (args.length != 1)
      {
         System.out.println("Usage: \"packagePrefix\"  //package name where tests can be found, recursivelyn");
         System.out.println(Strings.NL + " ex.  com.nesbot.commons");
         System.exit(1);
      }

      int failed = 0;
      boolean ranSome = false;

      for (String p : args)
      {
         for (Class clazz : ClassFinder.findClasses(args[0], "Test"))
         {
            ranSome = true;
            System.out.println("Testsuite: " + clazz.getName());
            failed += outputTestResult(org.junit.runner.JUnitCore.runClasses(clazz));
         }
      }

      if (!ranSome)
      {
         System.out.println("----> NO TESTS FOUND TO RUN <----");
         System.exit(1);
         return;
      }

      if (failed == 0)
      {
         System.out.println("----> ALL TESTS PASSED <----");
         System.exit(0);
      }
      else
      {
         System.out.println(Strings.format("----> FAILURES: {0} <----", failed));
         System.exit(1);
      }
   }
}