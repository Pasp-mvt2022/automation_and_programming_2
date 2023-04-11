package selenium;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.rmi.UnexpectedException;

import static org.junit.Assert.assertEquals;

public class MailChimpSignUpStepdefs {

    MailChimpSignUpDriver mailChimpSignUpDriver;

    @Before
    public void setUp() {
        mailChimpSignUpDriver = new MailChimpSignUpDriver();
    }

    @After
    public void tearDown() {
        mailChimpSignUpDriver.driver.quit();
    }

    @Given("I opened Mailchimp signup in {string}")
    public void iOpenedMailchimpSignupIn(String browser) {
        mailChimpSignUpDriver.setDriver(browser);
        mailChimpSignUpDriver.openPage();
        assertEquals("Signup | Mailchimp", mailChimpSignUpDriver.getPageTitle());
    }

    @And("I have entered an emailAddress {string}")
    public void iHaveEnteredAnEmailAddress(String emailAddress) {
        String expected = "";
        if (emailAddress.equals("true")) {
            expected = mailChimpSignUpDriver.setEmail();
        }
        assertEquals(expected, mailChimpSignUpDriver.getEmail());
    }

    @And("I have entered a {string} username that is {string}")
    public void iHaveEnteredAUsernameThatIs(String random, String tooLong) {
        String expected = mailChimpSignUpDriver.setUsername(random, tooLong);
        assertEquals(expected, mailChimpSignUpDriver.getUsername());
    }

    @And("I have entered an {string} password")
    public void iHaveEnteredAnPassword(String password) {
        mailChimpSignUpDriver.setPassword(password);
        assertEquals(password, mailChimpSignUpDriver.getPassword());
    }

    @When("^I click signup$")
    public void iClickSignup() {
        mailChimpSignUpDriver.clickSignUp();
    }

    @Then("the account creation is {string}")
    public void theAccountCreationIs(String created) {
        try {
            assertEquals(created, mailChimpSignUpDriver.getCreated());
        } catch (UnexpectedException e) {
            throw new RuntimeException(e);
        }
    }
}
