package com.nesbot.commons.security;

import com.nesbot.commons.lang.Convert;
import com.nesbot.commons.lang.Strings;
import com.nesbot.commons.tests.TestHelpers;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestRSAHelper
{
   private byte[] knownKey = Convert.toBytes("52Oz)-L2?+mUqX-4J0}PHh-PKMge4y\\z");
   private byte[] knownIV = Convert.toBytes("kd_h=ge~z\"79Um.r");
   private byte[] key;
   private byte[] iv;

   @Before
   public void setupKeyAndIv()
   {
      key = Convert.toBytes(Strings.randAsciiString(32));
      iv = Convert.toBytes(Strings.randAsciiString(16));
   }

   @Test
   public void testStaticPrivateCtors()
   {
      TestHelpers.assertPrivateNoArgsCtor(RSAHelper.class);
   }
   @Test
   public void testEncrypt() throws InvalidCipherTextException
   {
      String orig = "brian nesbitt was here";
      byte[] encrypted = RSAHelper.encrypt(orig, knownKey, knownIV);

      assertEquals("VaJZSeuCEr+G0NPz9an0Rmlm63NsN43gJ31fyOkdNCk=", Convert.toString(Base64.encode(encrypted)));
   }
   @Test
   public void testDecrypt() throws InvalidCipherTextException
   {
      byte[] encrypted = Base64.decode("VaJZSeuCEr+G0NPz9an0Rmlm63NsN43gJ31fyOkdNCk=");
      assertEquals("brian nesbitt was here", Convert.toString(RSAHelper.decrypt(encrypted, knownKey, knownIV)));
   }
   @Test
   public void testEncryptDecrypt() throws InvalidCipherTextException
   {
      String orig = Strings.randAlphaString(1024);

      byte[] encrypted = RSAHelper.encrypt(orig, key, iv);

      assertEquals(orig, Convert.toString(RSAHelper.decrypt(encrypted, key, iv)));
   }
}