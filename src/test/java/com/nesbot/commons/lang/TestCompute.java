package com.nesbot.commons.lang;

import com.nesbot.commons.tests.TestHelpers;
import org.junit.Test;
import org.junit.runner.Computer;

import static org.junit.Assert.*;

public class TestCompute
{
   @Test
   public void testStaticPrivateCtors()
   {
      TestHelpers.assertPrivateNoArgsCtor(Compute.class);
   }
   @Test
   public void testRoundFloatUpHundreths()
   {
      assertEquals(49500f, Compute.round(49481.45500f, -2), 0);
   }
   @Test
   public void testRoundFloatUpAtZero()
   {
      assertEquals(2f, Compute.round(1.50f, 0), 0);
   }
   @Test
   public void testRoundFloatUp()
   {
      assertEquals(1.46f, Compute.round(1.45500f, 2), 0);
   }
   @Test
   public void testRoundFloatDown()
   {
      assertEquals(1.445f, Compute.round(1.4449f, 3), 0);
   }
   @Test
   public void testRoundDoubleUpHundreths()
   {
      assertEquals(49500, Compute.round(49481.45500, -2), 0);
   }
   @Test
   public void testRoundDoubleUpAtZero()
   {
      assertEquals(2, Compute.round(1.50, 0), 0);
   }
   @Test
   public void testRoundDoubleUp()
   {
      assertEquals(1.46, Compute.round(1.45500, 2), 0);
   }
   @Test
   public void testRoundDoubleDown()
   {
      assertEquals(1.445, Compute.round(1.4449, 3), 0);
   }
   @Test
   public void testRand()
   {
      int r;
      r = Compute.rand(-2);
      assertTrue(-2 <= r && r <= 0);
      r = Compute.rand(-2);
      assertTrue(-2 <= r && r <= 0);
      r = Compute.rand(-2);
      assertTrue(-2 <= r && r <= 0);
      r = Compute.rand(-2);
      assertTrue(-2 <= r && r <= 0);
      r = Compute.rand(-2);
      assertTrue(-2 <= r && r <= 0);
      r = Compute.rand(-2);
      assertTrue(-2 <= r && r <= 0);
      r = Compute.rand(-2);
      assertTrue(-2 <= r && r <= 0);
      r = Compute.rand(-2);
      assertTrue(-2 <= r && r <= 0);
      r = Compute.rand(-2);
      assertTrue(-2 <= r && r <= 0);
      r = Compute.rand(-2);
      assertTrue(-2 <= r && r <= 0);

      r = Compute.rand(2);
      assertTrue(0 <= r && r <= 2);
      r = Compute.rand(2);
      assertTrue(0 <= r && r <= 2);
      r = Compute.rand(2);
      assertTrue(0 <= r && r <= 2);
      r = Compute.rand(2);
      assertTrue(0 <= r && r <= 2);
      r = Compute.rand(2);
      assertTrue(0 <= r && r <= 2);
      r = Compute.rand(2);
      assertTrue(0 <= r && r <= 2);
      r = Compute.rand(2);
      assertTrue(0 <= r && r <= 2);
      r = Compute.rand(2);
      assertTrue(0 <= r && r <= 2);
      r = Compute.rand(2);
      assertTrue(0 <= r && r <= 2);
      r = Compute.rand(2);
      assertTrue(0 <= r && r <= 2);

   }
   @Test
   public void testRandWithLowHigh()
   {
      int r;
      r = Compute.rand(-1, 1);
      assertTrue(-1 <= r && r <= 1);
      r = Compute.rand(-1, 1);
      assertTrue(-1 <= r && r <= 1);
      r = Compute.rand(-1, 1);
      assertTrue(-1 <= r && r <= 1);
      r = Compute.rand(-1, 1);
      assertTrue(-1 <= r && r <= 1);
      r = Compute.rand(-1, 1);
      assertTrue(-1 <= r && r <= 1);
      r = Compute.rand(-1, 1);
      assertTrue(-1 <= r && r <= 1);
      r = Compute.rand(-1, 1);
      assertTrue(-1 <= r && r <= 1);
      r = Compute.rand(-1, 1);
      assertTrue(-1 <= r && r <= 1);
      r = Compute.rand(-1, 1);
      assertTrue(-1 <= r && r <= 1);
      r = Compute.rand(-1, 1);
      assertTrue(-1 <= r && r <= 1);

      r = Compute.rand(1, 1);
      assertTrue(r == 1);
      r = Compute.rand(1, 1);
      assertTrue(r == 1);
      r = Compute.rand(1, 1);
      assertTrue(r == 1);
   }
   @Test
   public void testRandRandomness()
   {
      assertTrue(Compute.rand() != Compute.rand());
      assertTrue(Compute.rand() != Compute.rand());
      assertTrue(Compute.rand() != Compute.rand());
      assertTrue(Compute.rand() != Compute.rand());
      assertTrue(Compute.rand() != Compute.rand());
      assertTrue(Compute.rand() != Compute.rand());
      assertTrue(Compute.rand() != Compute.rand());
      assertTrue(Compute.rand() != Compute.rand());
      assertTrue(Compute.rand() != Compute.rand());
      assertTrue(Compute.rand() != Compute.rand());
   }
}