package com.nesbot.commons.security;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.crypto.signers.RSADigestSigner;

public class RSAHelper
{
   private RSAHelper() {}

   public static byte[] sign(RSAPrivateCrtKeyParameters privateCrtKeyParameters, String data) throws CryptoException
   {
      return sign(privateCrtKeyParameters, data.getBytes());
   }
   public static byte[] sign(RSAPrivateCrtKeyParameters privateCrtKeyParameters, byte[] data) throws CryptoException, DataLengthException
   {
      RSADigestSigner signer = new RSADigestSigner(new SHA1Digest());
      signer.init(true, privateCrtKeyParameters);
      signer.update(data, 0, data.length);
      return signer.generateSignature();
   }

   public static byte[] encrypt(String message, byte[] key, byte[] iv) throws InvalidCipherTextException
   {
      return encrypt(message.getBytes(), key, iv);
   }
   public static byte[] encrypt(byte[] message, byte[] key, byte[] iv) throws InvalidCipherTextException
   {
      BufferedBlockCipher symmetricBlockCipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new RijndaelEngine()), new PKCS7Padding());
      ParametersWithIV rijndaelKey = new ParametersWithIV(new KeyParameter(key), iv);
      symmetricBlockCipher.reset();

      // initialize block cipher in "encryption" mode
      symmetricBlockCipher.init(true, rijndaelKey);

      byte[] ciphertext = new byte[symmetricBlockCipher.getOutputSize(message.length)];
      int len = symmetricBlockCipher.processBytes(message, 0, message.length, ciphertext, 0);
      len += symmetricBlockCipher.doFinal(ciphertext, len);

      return ciphertext;
   }
   public static byte[] decrypt(byte[] message, byte[] key, byte[] iv) throws InvalidCipherTextException
   {
      BufferedBlockCipher symmetricBlockCipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new RijndaelEngine()), new PKCS7Padding());
      ParametersWithIV rijndaelKey = new ParametersWithIV(new KeyParameter(key), iv);
      symmetricBlockCipher.reset();

      // initialize block cipher in "decryption" mode
      symmetricBlockCipher.init(false, rijndaelKey);

      byte[] ciphertext = new byte[symmetricBlockCipher.getOutputSize(message.length)];
      int len = symmetricBlockCipher.processBytes(message, 0, message.length, ciphertext, 0);
      len += symmetricBlockCipher.doFinal(ciphertext, len);

      // remove padding
      byte[] out = new byte[len];
      System.arraycopy(ciphertext, 0, out, 0, len);

      return out;
   }
}