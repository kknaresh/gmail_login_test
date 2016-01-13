package com.dlab;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;

public class GmailSignInTest {
    final String pass = JOptionPane.showInputDialog(JOptionPane.getRootFrame(),
            "Enter the gmail password", null, JOptionPane.PLAIN_MESSAGE);
    WebDriver driver = new FirefoxDriver();
    WebDriverWait wait = new WebDriverWait(driver, 30);


    @Test
    public void gmailLoginShouldBeSuccessful() {
        //WebDriverWait wait = new WebDriverWait(driver,30);
        //  Go to Gmail
        driver.get("http://gmail.com");
        //  Fill in Username
        WebElement usernameTextBox = driver.findElement(By.id("Email"));
        usernameTextBox.clear();
        usernameTextBox.sendKeys("naresh.kalakanti@gmail.com");
        //  Click Next
        WebElement usernameNextButton = driver.findElement(By.id("next"));
        usernameNextButton.click();
        //  Fill in Password
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Passwd")));
        WebElement passwordTextBox = driver.findElement(By.id("Passwd"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys(pass);
        // Uncheck 'Stay Signed In'
        WebElement staySignedInCheckbox = driver.findElement(By.id("PersistentCookie"));
        String staySignedInCheckedAttribute = staySignedInCheckbox.getAttribute("checked");
        //System.out.println("staySignedInCheckedAttribute = "+staySignedInCheckedAttribute);   DEBUG
        //System.out.println(staySignedInCheckedAttribute); DEBUG
        if (staySignedInCheckedAttribute.equals("true")) {
            staySignedInCheckbox.click();
            //staySignedInCheckedAttribute =  staySignedInCheckbox.getAttribute("checked"); DEBUG
            //System.out.println("staySignedInCheckedAttribute after click = "+staySignedInCheckedAttribute);   DEBUG
        }
        //  Click Sign In
        WebElement signInButton = driver.findElement(By.id("signIn"));
        signInButton.click();
        //  Verify Sign In
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Inbox")));
        Assert.assertTrue("Inbox should exist", driver.findElement(By.partialLinkText("Inbox")).getSize().getWidth() > 0);
        //  Sign Out
        WebElement profilePicture = driver.findElement(By.cssSelector(".gb_Za.gbii"));
        profilePicture.click();
        WebElement signOutLink = driver.findElement(By.id("gb_71"));
        signOutLink.click();
        //  Verify Sign Out
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Email")));
        Assert.assertTrue("Should find email field if signed out", driver.findElement(By.id("Email")).isDisplayed());
    }

    @Test
    //This test assumes that the gmail email list has no unread messages in it to begin with.  Check before first run.
    public void gmailSendAndReceiveEmailTest() {
        //All from previous example test
        //  Go to Gmail
        driver.get("http://gmail.com");
        //  Fill in Username

        WebElement usernameTextBox = WaitAndFindID("Email"); //driver.findElement(By.id("Email"));
        usernameTextBox.clear();
        usernameTextBox.sendKeys("naresh.kalakanti@gmail.com");
        //  Click Next
        WebElement usernameNextButton = WaitAndFindID("next");   //driver.findElement(By.id("next"));
        usernameNextButton.click();
        //  Fill in Password
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Passwd")));
        WebElement passwordTextBox = WaitAndFindID("Passwd");    //driver.findElement(By.id("Passwd"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys(pass);
        // Uncheck 'Stay Signed In'
        WebElement staySignedInCheckbox = driver.findElement(By.id("PersistentCookie"));
        String staySignedInCheckedAttribute = staySignedInCheckbox.getAttribute("checked");
        //System.out.println("staySignedInCheckedAttribute = "+staySignedInCheckedAttribute);   DEBUG
        //System.out.println(staySignedInCheckedAttribute); DEBUG
        if (staySignedInCheckedAttribute.equals("true")) {
            staySignedInCheckbox.click();
            //staySignedInCheckedAttribute =  staySignedInCheckbox.getAttribute("checked"); DEBUG
            //System.out.println("staySignedInCheckedAttribute after click = "+staySignedInCheckedAttribute);   DEBUG
        }
        //  Click Sign In
        WebElement signInButton = driver.findElement(By.id("signIn"));
        signInButton.click();
        //  Verify Sign In
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Inbox")));
        Assert.assertTrue("Inbox should exist", driver.findElement(By.partialLinkText("Inbox")).getSize().getWidth() > 0);
        //New content here
        //  Click Compose
        WebElement composeButton = driver.findElement(By.cssSelector("div[role='button'][gh='cm']"));
        composeButton.click();
        //  Fill in Recipient
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("textarea[name='to']")));
        WebElement toTextArea = WaitAndFindCss("textarea[name='to']");   //driver.findElement(By.cssSelector("textarea[name='to']"));
        toTextArea.clear();
        toTextArea.sendKeys("naresh.kalakanti@gmail.com");
        //  Fill in Subject
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='subjectbox']")));
        WebElement subjectTextArea = WaitAndFindCss("input[name='subjectbox']");    //driver.findElement(By.cssSelector("input[name='subjectbox']"));
        final String subject = "Gmail Send Email Test";
        subjectTextArea.clear();
        subjectTextArea.sendKeys(subject);
        //  Fill in Email Body
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[aria-label='Message Body']")));
        WebElement emailBodyTextEntry = WaitAndFindCss("div[aria-label='Message Body']");   //driver.findElement(By.cssSelector("div[aria-label='Message Body']"));
        final String body = "Hello Email World";
        //apparently, "!" doesn't work with sendkeys very well.  See https://code.google.com/p/selenium/issues/detail?id=6411 for updated info.
        //System.out.println("body: "+body);    //debug
        //System.out.println("body as char array: "+body.toCharArray());    //debug
        emailBodyTextEntry.clear();
        emailBodyTextEntry.sendKeys(body);

        //emailBodyTextEntry.sendKeys(new CharSequence[]{Charset.forName("UTF-8").encode(body)});
        //  Click Send
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[aria-label*='Send'][aria-label*='Enter']")));
        WebElement emailSendButton = WaitAndFindCss("div[aria-label*='Send'][aria-label*='Enter']");    //driver.findElement(By.cssSelector("div[aria-label*='Send'][aria-label*='Enter']"));
        emailSendButton.click();
        //  Click Inbox Again
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Inbox")));
        //WebElement inboxLinkage =
        driver.findElement(By.partialLinkText("Inbox")).click();
        //inboxLinkage.click();
        //  Click Email
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='y6'] span[id] b")));
        WebElement newEmail = WaitAndFindCss("div[class='y6'] span[id] b"); //driver.findElement(By.cssSelector("div[class='y6'] span[id] b"));
        newEmail.click();
        //  Verify the email subject and body are both correct
        //WebElement subjectArea = driver.findElement(By.cssSelector());
        WebElement subjectArea = WaitAndFindCss("h2[class='hP']");
        Assert.assertEquals("Subject should be the same", subject, subjectArea.getText());
        //WebElement bodyArea = driver.findElement(By.cssSelector());
        WebElement bodyArea = WaitAndFindCss("div[class='a3s'] div[dir='ltr']");
        Assert.assertEquals("Email Body Text should be the same", body, bodyArea.getText());
        //  Sign out
        //Recycled Sign Out
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".gb_Za.gbii")));
        WebElement profilePicture = WaitAndFindCss(".gb_Za.gbii");  //driver.findElement(By.cssSelector(".gb_Za.gbii"));
        profilePicture.click();
        WebElement signOutLink = driver.findElement(By.id("gb_71"));
        signOutLink.click();
//        //  Verify Sign Out
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Email")));
//        Assert.assertTrue("Should find email field if signed out", driver.findElement(By.id("Email")).isDisplayed());
    }

    @After
    public void Cleanup() {
        driver.quit();
    }

    public WebElement WaitAndFindCss(String cssString) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssString)));
        return driver.findElement(By.cssSelector(cssString));
    }

    public WebElement WaitAndFindID(String idString) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idString)));
        return driver.findElement(By.id(idString));
    }
}

