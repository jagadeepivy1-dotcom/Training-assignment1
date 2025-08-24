package com.example;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.datafaker.Faker;

public class UserProfileFakerTest {

    private Faker faker;

    @BeforeClass(alwaysRun = true)
    public void setupClass() {
        System.out.println("--- @BeforeClass: Creating Faker instance for UserProfileFakerTest ---");
        faker = new Faker();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        System.out.println("--- @AfterClass: Tearing down UserProfileFakerTest ---");
        faker = null;
    }

    @BeforeMethod
    public void setupMethod(Method method) {
        System.out.println("\n==> Executing test: " + method.getName());
    }

    @Test(groups = {"user-data"}, description = "Test generating a valid username")
    public void testGenerateUsername() {
        String username = faker.name().username();
        System.out.println("Generated Username: " + username);
        assertNotNull(username, "Username should not be null");
        // Assert that the username contains only lowercase letters, digits, and periods.
        assertTrue(username.matches("[a-z0-9.]+"), "Username format is invalid");
    }

    @Test(groups = {"user-data"}, description = "Test generating a secure password")
    public void testGeneratePassword() {
        // Generates a password with specific constraints
        String password = faker.internet().password(8, 16, true, true, true);
        System.out.println("Generated Password: " + password);
        assertNotNull(password, "Password should not be null");
        assertTrue(password.length() >= 8 && password.length() <= 16, "Password length is out of range");
        // Assert that the password contains at least one digit, one uppercase, and one lowercase letter.
        assertTrue(password.matches(".*\\d.*"), "Password should contain at least one digit");
        assertTrue(password.matches(".*[a-z].*"), "Password should contain at least one lowercase letter");
        assertTrue(password.matches(".*[A-Z].*"), "Password should contain at least one uppercase letter");
    }

    @Test(groups = {"user-data"}, description = "Test generating a past date of birth")
    public void testGenerateDateOfBirth() {
        Date dob = faker.date().birthday(18, 65); // Age between 18 and 65
        System.out.println("Generated Date of Birth: " + dob);
        assertNotNull(dob, "Date of Birth should not be null");
        assertTrue(dob.before(new Date()), "Date of Birth must be in the past");

        // More specific assertion: check if the calculated age is within the expected range.
        LocalDate birthDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        assertTrue(age >= 18 && age <= 65, "Calculated age should be between 18 and 65");
    }

    @Test(groups = {"user-security"}, description = "Test generating a credit card number")
    public void testGenerateCreditCard() {
        String creditCard = faker.finance().creditCard();
        System.out.println("Generated Credit Card: " + creditCard);
        assertNotNull(creditCard, "Credit card number should not be null");
        // Remove hyphens and assert that the result is a string of digits with a valid length.
        String sanitizedCardNumber = creditCard.replace("-", "");
        // Credit card numbers can have different lengths (e.g., Amex is 15).
        // This assertion checks for a common range of lengths (13-19 digits).
        assertTrue(sanitizedCardNumber.matches("^\\d{13,19}$"), "Credit card should be a valid number of digits (13-19)");
    }

    @Test(groups = {"user-tech"}, description = "Test generating a browser user agent")
    public void testGenerateUserAgent() {
        String userAgent = faker.internet().userAgent();
        System.out.println("Generated User Agent: " + userAgent);
        assertNotNull(userAgent, "User agent should not be null");
        // A common pattern for user agents is to start with "Mozilla/"
        assertTrue(userAgent.startsWith("Mozilla/"), "User agent string should start with 'Mozilla/'");
    }
}