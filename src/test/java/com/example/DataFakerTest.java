package com.example;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import net.datafaker.Faker;

public class DataFakerTest {

    private Faker faker;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("### @BeforeSuite: Test Suite is starting... ###");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("### @AfterSuite: Test Suite has finished. ###");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("--- @BeforeTest: Initializing Faker for the <test> tag ---");
        this.faker = new Faker();
    }

    @AfterTest
    public void afterTest() {
        System.out.println("--- @AfterTest: Cleaning up after the <test> tag ---");
        this.faker = null;
    }

    @BeforeMethod
    public void beforeEachTest() {
        System.out.println("==> Running a test method...");
    }

    @AfterMethod
    public void afterEachTest() {
        System.out.println("<== Test method finished.");
        System.out.println("--------------------");
    }

    @Test(description = "Test generating a random book title")
    public void testGenerateBook() {
        String bookTitle = faker.book().title();
        System.out.println("Generated Book Title: " + bookTitle);
        assertNotNull(bookTitle);
    }

    @Test(description = "Test generating a random Lorem Ipsum sentence")
    public void testGenerateLoremIpsum() {
        String sentence = faker.lorem().sentence(5);
        System.out.println("Generated Sentence: " + sentence);
        assertNotNull(sentence);
    }

    @Test(description = "Test generating a future date")
    public void testGenerateFutureDate() {
        Date futureDate = faker.date().future(30, TimeUnit.DAYS);
        System.out.println("Generated Future Date: " + futureDate);
        assertTrue(futureDate.after(new Date()));
    }

    @Test(description = "Test generating a random digit")
    public void testGenerateRandomDigit() {
        String digit = faker.number().digit();
        System.out.println("Generated Digit: " + digit);
        assertNotNull(digit);
        assertTrue(Integer.parseInt(digit) >= 0 && Integer.parseInt(digit) <= 9);
    }

    @Test(description = "Test generating a Chuck Norris fact")
    public void testGenerateChuckNorrisFact() {
        String fact = faker.chuckNorris().fact();
        System.out.println("Generated Fact: " + fact);
        assertNotNull(fact);
    }
}