package me.hossain.ebrahim.tests;

import me.hossain.ebrahim.drivers.BaseDriver;
import me.hossain.ebrahim.drivers.PageDriver;
import me.hossain.ebrahim.pages.GooglePage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class GoogleSearchTest extends BaseDriver {

    @BeforeClass
    public void open_url() throws InterruptedException {
        PageDriver.getCurrentDriver().get(url);

    }

    @Test
    public void shortestOrLongestResult() throws Exception {
        GooglePage googlePage = new GooglePage();
        googlePage.googleSearch("Sunday");
    }
}
