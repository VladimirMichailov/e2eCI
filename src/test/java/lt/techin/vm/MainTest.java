package lt.techin.vm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class MainTest {

    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://webdriveruniversity.com/To-Do-List/index.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void clickAddNewTask() {

        String newToDoTask = "nepamirsti eiti namo";
        driver.findElement(By.cssSelector("i#plus-icon")).click();
        driver.findElement(By.cssSelector("div#container > input[type='text']")).sendKeys(newToDoTask);
        driver.findElement(By.cssSelector("div#container > input[type='text']")).sendKeys(Keys.ENTER);
        assertEquals(newToDoTask, driver.findElement(By.cssSelector("ul > li:last-child")).getText());
    }

    @Test
    void markCompletedTask() {

        driver.findElement(By.cssSelector("ul > li:nth-of-type(3)")).click();
        assertEquals("completed", driver.findElement(By.cssSelector("ul > li:nth-of-type(3)")).getAttribute("class"), "Klases pavadinimai nesutampa");

        List<WebElement> toDoList = driver.findElements(By.cssSelector("#container > ul"));
        String listClassNameExpected = "";
        String listClassNameActual;
        for (int i = 0; i < toDoList.size() - 1; i++) {
            WebElement input = toDoList.get(i);
            listClassNameActual = input.getAttribute("class");
            assertEquals(listClassNameExpected, listClassNameActual, "klases vardas ne turi buti 'completed'");
        }

    }

    @Test
    void deleteTask() {

        WebElement hoverable = driver.findElement(By.cssSelector("ul > li:nth-of-type(1)"));
        new Actions(driver)
                .moveToElement(hoverable)
                .perform();

        String deletedTaskText = "Go to potion class";

        driver.findElement(By.cssSelector("li:nth-of-type(1) > span > .fa.fa-trash")).click();

        //Thread.sleep(3000);

        List<WebElement> itemList = driver.findElements(By.cssSelector("#container > ul"));

        String existingTaskText;
        for (WebElement element : itemList) {
            existingTaskText = element.getText();
            assertNotEquals(deletedTaskText, existingTaskText, "tekstai ne turi sutapti");
        }


    }


}







