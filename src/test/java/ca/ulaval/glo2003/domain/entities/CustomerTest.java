package ca.ulaval.glo2003.domain.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class CustomerTest {

       @Test
       public void givenCustomer_whenGetName_thenReturnsName() {
         String name = "Johnny";
         Customer customer = new Customer(name, "johnnycash@hotmail.com", "4181234567");

         String result = customer.getName();

         assertThat(result).isEqualTo(name);
       }

       @Test
       public void givenCustomer_whenGetEmail_thenReturnsEmail() {
         String email = "johnnycash@hotmail.com";
         Customer customer = new Customer("Johnny", email, "4181234567");

         String result = customer.getEmail();

         assertThat(result).isEqualTo(email);
       }

       @Test
       public void givenCustomer_whenGetPhoneNumber_thenReturnsPhoneNumber() {
         String phoneNumber = "4181234567";
         Customer customer = new Customer("Johnny", "johnnycash@hotmail.com", phoneNumber);

         String result = customer.getPhoneNumber();

         assertEquals(phoneNumber, result);
       }

       @Test
       public void givenCustomer_whenEqualsWithSameCustomer_thenReturnsTrue() {
         Customer customer = new Customer("Johnny", "johnnycash@hotmail.com", "4181234567");
         Customer sameCustomer = new Customer("Johnny", "johnnycash@hotmail.com", "4181234567");

         boolean result = customer.equals(sameCustomer);

         assertTrue(result);
       }

       @Test
       public void givenCustomer_whenEqualsWithDifferentCustomer_thenReturnsFalse() {
         Customer customer = new Customer("Johnny", "johnnycash@hotmail.com", "4181234567");
         Customer differentCustomer = new Customer("John", "johnnycash@hotmail.com", "4181234567");

         boolean result = customer.equals(differentCustomer);

         assertFalse(result);
       }

       @Test
       public void givenCustomer_whenEqualsWithNull_thenReturnsFalse() {
         Customer customer = new Customer("Johnny", "johnnycash@hotmail.com", "4181234567");

         boolean result = customer.equals(null);

         assertFalse(result);
       }

       @Test
       public void givenCustomer_whenEqualsWithDifferentClass_thenReturnsFalse() {
         Customer customer = new Customer("Johnny", "johnnycash@hotmail.com", "4181234567");

         boolean result = customer.equals(new Object());

         assertFalse(result);
    }
}
