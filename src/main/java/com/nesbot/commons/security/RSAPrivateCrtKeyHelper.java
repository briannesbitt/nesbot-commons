package com.nesbot.commons.security;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.JCERSAPrivateCrtKey;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.encoders.Base64;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.Security;

public class RSAPrivateCrtKeyHelper
{
   public static RSAPrivateCrtKeyParameters generate()
   {
      return generate(BigInteger.valueOf(65537), new SecureRandom(), 1024, 80);
   }
   public static RSAPrivateCrtKeyParameters generate(BigInteger publicExponent, SecureRandom secureRandom, int keySize, int certainty)
   {
      RSAKeyPairGenerator r = new RSAKeyPairGenerator();
      r.init(new RSAKeyGenerationParameters(publicExponent, secureRandom, keySize, certainty));
      AsymmetricCipherKeyPair keys = r.generateKeyPair();
      return (RSAPrivateCrtKeyParameters)keys.getPrivate();
   }

   public static RSAPrivateCrtKeyParameters fromPem(String pemFile) throws IOException
   {
      Security.addProvider(new BouncyCastleProvider());
      FileReader reader = new FileReader(pemFile);

      try
      {
         KeyPair kp = (KeyPair) new PEMReader(reader).readObject();
         JCERSAPrivateCrtKey privateCrtKey = (JCERSAPrivateCrtKey)kp.getPrivate();

         return new RSAPrivateCrtKeyParameters(privateCrtKey.getModulus(), privateCrtKey.getPublicExponent(), privateCrtKey.getPrivateExponent() ,privateCrtKey.getPrimeP(), privateCrtKey.getPrimeQ(), privateCrtKey.getPrimeExponentP(), privateCrtKey.getPrimeExponentQ(), privateCrtKey.getCrtCoefficient());
      }
      finally
      {
         reader.close();
      }
   }
   public static void toDotNetXmlBase64(RSAPrivateCrtKeyParameters key, String fileName) throws IOException
   {
      String modulus = encodeDotNetBase64(key.getModulus());
      String e = encodeDotNetBase64(key.getPublicExponent());
      String p = encodeDotNetBase64(key.getP());
      String q = encodeDotNetBase64(key.getQ());
      String dp = encodeDotNetBase64(key.getDP());
      String dq = encodeDotNetBase64(key.getDQ());
      String d = encodeDotNetBase64(key.getExponent());
      String inverseq = encodeDotNetBase64(key.getQInv());

      String format = "<RSAKeyValue><Modulus>%s</Modulus><Exponent>%s</Exponent><P>%s</P><Q>%s</Q><DP>%s</DP><DQ>%s</DQ><InverseQ>%s</InverseQ><D>%s</D></RSAKeyValue>";
      String xml = String.format(format, modulus, e, p, q, dp, dq, inverseq, d);
      writeToFile(xml, fileName);
   }
   public static void toPublicDotNetXmlBase64(RSAPrivateCrtKeyParameters key, String fileName) throws IOException
   {
      String modulus = encodeDotNetBase64(key.getModulus());
      String e = encodeDotNetBase64(key.getPublicExponent());
      String xml = String.format("<RSAKeyValue><Modulus>%s</Modulus><Exponent>%s</Exponent></RSAKeyValue>", modulus, e);
      writeToFile(xml, fileName);
   }
   private static String encodeDotNetBase64(BigInteger bi)
   {
      return new String(Base64.encode(BigIntegers.asUnsignedByteArray(bi)));
   }
   private static void writeToFile(String s, String fileName) throws IOException
   {
      PrintWriter writer = new PrintWriter(fileName);
      try
      {
         writer.print(s);
      }
      finally
      {
         writer.close();
      }
   }
}