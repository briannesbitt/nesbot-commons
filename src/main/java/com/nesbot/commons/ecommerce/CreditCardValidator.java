package com.nesbot.commons.ecommerce;

import com.nesbot.commons.datetime.Dater;
import com.nesbot.commons.datetime.Now;
import com.nesbot.commons.lang.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This is from the http://commons.apache.org/validator/ package and included here for simplicity.
 * This is provided with a few modifications,.
 *
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * <p>Perform credit card validations.</p>
 * <p>
 * By default, all supported card types are allowed.  You can specify which
 * cards should pass validation by configuring the validation options.  For
 * example,<br/><code>CreditCardValidator ccv = new CreditCardValidator(CreditCardValidator.AMEX + CreditCardValidator.VISA);</code>
 * configures the validator to only pass American Express and Visa cards.
 * If a card type is not directly supported by this class, you can implement
 * the CreditCardType interface and pass an instance into the
 * <code>addAllowedCardType</code> method.
 * </p>
 * For a similar implementation in Perl, reference Sean M. Burke's
 * <a href="http://www.speech.cs.cmu.edu/~sburke/pub/luhn_lib.html">script</a>.
 * More information is also available
 * <a href="http://www.merriampark.com/anatomycc.htm">here</a>.
 *
 */
public class CreditCardValidator
{
   /**
    * Option specifying that no cards are allowed.  This is useful if
    * you want only custom card types to validate so you turn off the
    * default cards with this option.
    * <br/>
    * <pre>
    * CreditCardValidator v = new CreditCardValidator(CreditCardValidator.NONE);
    * v.addAllowedCardType(customType);
    * v.isValid(aCardNumber);
    * </pre>
    *
    * @since Validator 1.1.2
    */
   public static final int NONE = 0;
   /**
    * Option specifying that American Express cards are allowed.
    */
   public static final int AMEX = 1 << 0;
   /**
    * Option specifying that Visa cards are allowed.
    */
   public static final int VISA = 1 << 1;
   /**
    * Option specifying that Mastercard cards are allowed.
    */
   public static final int MASTERCARD = 1 << 2;
   /**
    * Option specifying that Discover cards are allowed.
    */
   public static final int DISCOVER = 1 << 3;
   /**
    * The CreditCardTypes that are allowed to pass validation.
    */

   public final static CreditCardValidator VisaMastercardValidator = new CreditCardValidator(VISA + MASTERCARD);

   private List<CreditCardType> cardTypes = new ArrayList<CreditCardType>();
   /**
    * Create a new CreditCardValidator with default options.
    */
   public CreditCardValidator()
   {
      this(AMEX + VISA + MASTERCARD + DISCOVER);
   }
   /**
    * Create a new CreditCardValidator with the specified options.
    *
    * @param options Pass in
    *                CreditCardValidator.VISA + CreditCardValidator.AMEX to specify that
    *                those are the only valid card types.
    */
   public CreditCardValidator(int options)
   {
      super();

      if ((options & VISA) > 0)
      {
         this.cardTypes.add(new Visa());
      }
      if ((options & AMEX) > 0)
      {
         this.cardTypes.add(new Amex());
      }
      if ((options & MASTERCARD) > 0)
      {
         this.cardTypes.add(new Mastercard());
      }
      if ((options & DISCOVER) > 0)
      {
         this.cardTypes.add(new Discover());
      }
   }
   /**
    * Checks if the field is a valid credit card number.
    *
    * @param card The card number to validate.
    * @return Whether the card number is valid.
    */
   public boolean isValid(String card)
   {
      if ((card == null) || (card.length() < 13) || (card.length() > 19))
      {
         return false;
      }
      if (!this.luhnCheck(card))
      {
         return false;
      }
      for (CreditCardType type : this.cardTypes)
      {
         if (type.matches(card))
         {
            return true;
         }
      }
      return false;
   }
   /**
    * Add an allowed CreditCardType that participates in the card
    * validation algorithm.
    *
    * @param type The type that is now allowed to pass validation.
    * @since Validator 1.1.2
    */
   public void addAllowedCardType(CreditCardType type)
   {
      this.cardTypes.add(type);
   }
   /**
    * Checks for a valid credit card number.
    *
    * @param cardNumber Credit Card Number.
    * @return Whether the card number passes the luhnCheck.
    */
   protected boolean luhnCheck(String cardNumber)
   {
      // number must be validated as 0..9 numeric first!!
      int digits = cardNumber.length();
      int oddOrEven = digits & 1;
      long sum = 0;
      for (int count = 0; count < digits; count++)
      {
         int digit = 0;
         try
         {
            digit = Integer.parseInt(cardNumber.charAt(count) + "");
         }
         catch (NumberFormatException e)
         {
            return false;
         }
         if (((count & 1) ^ oddOrEven) == 0)
         { // not
            digit *= 2;
            if (digit > 9)
            {
               digit -= 9;
            }
         }
         sum += digit;
      }
      return (sum == 0) ? false : (sum % 10 == 0);
   }

   /* Added the following methods */

   public boolean isExpired(int expireYear, int expireMonth)
   {
      if (Now.year() > expireYear)
      {
         return true;
      }
      if (Now.year() == expireYear && Now.month() > expireMonth)
      {
         return true;
      }
      return false;
   }
   //checks if the card will expire this month
   public boolean isExpiring(int expireYear, int expireMonth)
   {
      return (Now.year() == expireYear && Now.month() == expireMonth);
   }
   //checks if the card will be expiring in the next 3 months
   public boolean willExpireSoon(int expireYear, int expireMonth)
   {
      return willExpireSoon(expireYear, expireMonth, 3);
   }
   //checks if the card will be expiring in the next X months
   public boolean willExpireSoon(int expireYear, int expireMonth, int nextMonths)
   {
      if (nextMonths <= 2)
      {
         throw new IllegalArgumentException("Must be at least in the next 2 months, otherwise just use isExpired() or isExpiring() alone.");
      }

      //already expired
      if (isExpired(expireYear, expireMonth))
      {
         return false;
      }
      //expiring this month
      if (isExpiring(expireYear, expireMonth))
      {
         return true;
      }

      nextMonths -= 2;

      // check the (nextMonths - 1)
      Dater d = Dater.now().startOfMonth();

      while (nextMonths-- > 0)
      {
         d.addMonth();

         //does it expire?
         if (d.year() == expireYear && d.month() == expireMonth)
         {
            return true;
         }
      }
      return false;
   }
   //checks if the card will be expire way too far in the future to be real
   public boolean isFuturistic(int expireYear)
   {
      return (expireYear - Now.year()) > 10;
   }

   public String mask(String card)
   {
      return mask(card, 'x');
   }
   public String mask(String card, char mask)
   {
      String smask = String.valueOf(mask);

      if (Strings.isNullOrEmpty(card))
      {
         return "";
      }
      if (card.length() <= 8)
      {
         return Strings.repeat(smask, 8);
      }

      StringBuilder ret = new StringBuilder(card.substring(0, 4));
      ret.append(Strings.repeat(smask, card.length() - 8));
      ret.append(card.substring(card.length() - 4));
      return ret.toString();
   }

   /* Added the above methods */


   /**
    * CreditCardType implementations define how validation is performed
    * for one type/brand of credit card.
    *
    * @since Validator 1.1.2
    */
   public interface CreditCardType
   {
      /**
       * Returns true if the card number matches this type of credit
       * card.  Note that this method is <strong>not</strong> responsible
       * for analyzing the general form of the card number because
       * <code>CreditCardValidator</code> performs those checks before
       * calling this method.  It is generally only required to valid the
       * length and prefix of the number to determine if it's the correct
       * type.
       *
       * @param card The card number, never null.
       * @return true if the number matches.
       */
      boolean matches(String card);
   }
   /**
    * Change to support Visa Carte Blue used in France
    * has been removed - see Bug 35926
    */
   private class Visa implements CreditCardType
   {
      private static final String PREFIX = "4";
      public boolean matches(String card)
      {
         return (
            card.substring(0, 1).equals(PREFIX)
               && (card.length() == 13 || card.length() == 16));
      }
   }
   private class Amex implements CreditCardType
   {
      private static final String PREFIX = "34,37,";
      public boolean matches(String card)
      {
         String prefix2 = card.substring(0, 2) + ",";
         return ((PREFIX.indexOf(prefix2) != -1) && (card.length() == 15));
      }
   }
   private class Discover implements CreditCardType
   {
      private static final String PREFIX = "6011";
      public boolean matches(String card)
      {
         return (card.substring(0, 4).equals(PREFIX) && (card.length() == 16));
      }
   }
   private class Mastercard implements CreditCardType
   {
      private static final String PREFIX = "51,52,53,54,55,";
      public boolean matches(String card)
      {
         String prefix2 = card.substring(0, 2) + ",";
         return ((PREFIX.indexOf(prefix2) != -1) && (card.length() == 16));
      }
   }
}
