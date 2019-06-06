package me.xiaff.crawler.acmfellow.crawler;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Export bib from dblp
 * Created by Dell-lcw on 2017/4/1.
 */
@Component
public class DBLPDownloader {
    private WebDriver driver;


    public DBLPDownloader() {
        if (driver != null) {
            return;
        }
        System.setProperty("webdriver.chrome.driver","D:\\install\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> ids = IOUtils.readLines(new FileInputStream("E:\\Dell-lcw\\Documents\\DBLP-ACM_FELLOW\\IEEE-Fellows.txt"), "UTF-8");
        DBLPDownloader downloader = new DBLPDownloader();
        int count = 0;
        for (String id : ids) {
            count++;
            System.out.println(count + ": Searching " + id + " ...");
            downloader.getAuthorPapers(id);
        }
    }

    public void getAuthorPapers(String authorName) throws IOException {
        driver.get("http://dblp.uni-trier.de/search?q=" + URLEncoder.encode(authorName, "utf-8"));

        List<WebElement> searchResultLiList = driver.findElements(By.xpath("//*[@id='completesearch-authors']/div/ul/li"));
        int size = searchResultLiList.size();
        WebElement link;
        if (size == 0) {
            IOUtils.write(authorName + "\n", new FileOutputStream("Fellow-Not-Found.txt", true), "utf-8");
            return;
        } else if (size > 1) {
//            IOUtils.write(id + "\n", new FileOutputStream("ACM-Distinguished-Multi-Found.txt", true), "utf-8");
//            return;
//            link = driver.findElement(By.xpath("//*[@id=\"completesearch-authors\"]/div/ul/li[1]/a"));
        } else {
            link = driver.findElement(By.xpath("//*[@id=\"completesearch-authors\"]/div/ul/li/a"));
            System.out.println("  [" + link.getText() + "]");
            link.click();
        }

        new WebDriverWait(driver, 300).until(
                (ExpectedCondition<Boolean>) webDriver -> !webDriver.getTitle().contains("earch for")
        );

        WebElement exportIcon = driver.findElement(By.xpath("//*/li[@class='export drop-down']/div[1]/a"));
        exportIcon.click();

        List<WebElement> bibElements = driver.findElements(By.className("verbatim"));
        List<String> bibStrList = bibElements.stream()
                .map(WebElement::getText).collect(Collectors.toList());
        FileUtils.writeLines(new File("fellow-bib/" + authorName + ".bib"), "utf-8", bibStrList);
    }
}
