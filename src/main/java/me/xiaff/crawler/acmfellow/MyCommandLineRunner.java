package me.xiaff.crawler.acmfellow;

import me.xiaff.crawler.acmfellow.onetime.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Arrays;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Resource
    private FindMsaAuthorPapers findMsaAuthorPapers;

    @Resource
    private SearchMsaAuthors searchMsaAuthors;

    @Resource
    private FindGoogleCitations findGoogleCitations;

    @Resource
    private SearchWikiSchoolRunner searchWikiSchoolRunner;

    @Resource
    private NormalPagePhdSearchRunner normalPagePhdSearchRunner;

    @Resource
    private FindGoogleFormalName findGoogleFormalName;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Arrays.toString(args));
        DefaultApplicationArguments arguments = new DefaultApplicationArguments(args);
        String runName = "";
        if (arguments.containsOption("run")) {
            runName = arguments.getOptionValues("run").get(0);
        }
        switch (runName) {
            case "msa-author": {
                searchMsaAuthors.run();
                break;
            }
            case "msa-author-paper": {
                findMsaAuthorPapers.run();
                break;
            }
            case "google-citation": {
                findGoogleCitations.run();
                break;
            }
            case "extra-paper-cite": {
                findGoogleCitations.finExtraPaperCites();
                break;
            }
            case "wiki-school": {
                WebDriver webDriverGoogle = new RemoteWebDriver(new URL("http://127.0.0.1:10086"),
                        DesiredCapabilities.chrome());
                WebDriver webDriverWiki = new RemoteWebDriver(new URL("http://127.0.0.1:10087"),
                        DesiredCapabilities.chrome());
                searchWikiSchoolRunner.searchACMFellow(webDriverGoogle, webDriverWiki);
                break;
            }
            case "page-school":{
                WebDriver webDriverGoogle = new RemoteWebDriver(new URL("http://127.0.0.1:10086"),
                        DesiredCapabilities.chrome());
                WebDriver webDriverWiki = new RemoteWebDriver(new URL("http://127.0.0.1:10087"),
                        DesiredCapabilities.chrome());
                normalPagePhdSearchRunner.run(webDriverGoogle, webDriverWiki);
                break;
            }
            case "formal-name":{
                findGoogleFormalName.run();
            }
            default: {
                System.err.println("I'm quitting...");
                break;
            }
        }
        System.err.println("Task finished!");
    }
}
