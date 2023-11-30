import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestTrabajoFinal {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        WebDriver chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        chromeDriver.get("https://magento.softwaretestingboard.com");

        genericTestProcess(chromeDriver, "clickRadianTee");
        addToCartPrenda(chromeDriver);
        genericTestProcess(chromeDriver, "clickCheckoutButton");
        llenarFormularioEnvio(chromeDriver);
        genericTestProcess(chromeDriver, "clickPlaceOrderButton");
        validacionesPurchase(chromeDriver);

        Thread.sleep(3000);
        chromeDriver.quit();
    }

    public static void addToCartPrenda(WebDriver chromeDriver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[option-label='Blue']")));

        WebElement divTalle = chromeDriver.findElement(By.cssSelector("div[option-label='L']"));
        divTalle.click();

        WebElement divColor = chromeDriver.findElement(By.cssSelector("div[option-label='Blue']"));
        divColor.click();

        WebElement qtyInput = chromeDriver.findElement(By.cssSelector("input[id='qty']"));
        qtyInput.clear();
        qtyInput.sendKeys("2");

        WebElement prendaButton = chromeDriver.findElement(By.cssSelector("button[title='Add to Cart']"));
        Thread.sleep(2000);
        prendaButton.click();

        WebElement shoppingCartLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'shopping cart')]")));
        shoppingCartLink.click();

    }

    public static void llenarFormularioEnvio(WebDriver chromeDriver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(20));

        WebElement tableShipping = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("table-checkout-shipping-method")));
        WebElement trShipping = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(tableShipping, By.cssSelector("tbody tr:first-child")));
        Thread.sleep(3000);
        trShipping.click();

        WebElement fieldSetEmail = chromeDriver.findElement(By.id("customer-email-fieldset"));
        WebElement emailInput = fieldSetEmail.findElement(By.cssSelector("input[name='username']"));
        emailInput.clear();
        emailInput.sendKeys("abigailwag18@gmail.com");

        WebElement countrySelector = chromeDriver.findElement(By.cssSelector("select[name='country_id']"));
        Select selectorCountry = new Select(countrySelector);
        selectorCountry.selectByValue("US");

        WebElement zipInput = chromeDriver.findElement(By.cssSelector("input[name='postcode']"));
        zipInput.clear();
        zipInput.sendKeys("33135");

        WebElement stateSelector = chromeDriver.findElement(By.cssSelector("select[name='region_id']"));
        Select selectorState = new Select(stateSelector);
        selectorState.selectByValue("18");

        WebElement firstNameInput = chromeDriver.findElement(By.cssSelector("input[name='firstname']"));
        firstNameInput.clear();
        firstNameInput.sendKeys("Camila");

        WebElement lastNameInput = chromeDriver.findElement(By.cssSelector("input[name='lastname']"));
        lastNameInput.clear();
        lastNameInput.sendKeys("Wagner");

        WebElement companyInput = chromeDriver.findElement(By.cssSelector("input[name='company']"));
        companyInput.clear();
        companyInput.sendKeys("Incluit");

        WebElement streetInput = chromeDriver.findElement(By.cssSelector("input[name='street[0]']"));
        streetInput.clear();
        streetInput.sendKeys("SW 17th Ave 204");

        WebElement cityInput = chromeDriver.findElement(By.cssSelector("input[name='city']"));
        cityInput.clear();
        cityInput.sendKeys("Miami");

        WebElement phoneInput = chromeDriver.findElement(By.cssSelector("input[name='telephone']"));
        phoneInput.clear();
        phoneInput.sendKeys("3455226666");

        WebElement nextButton = chromeDriver.findElement(By.cssSelector("button[data-role='opc-continue']"));
        nextButton.click();
    }

    public static void validacionesPurchase(WebDriver chromeDriver) {
        try{
            int cantErrores = 0;
            WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(20));

            WebElement divRegister = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("registration")));
            WebElement registerLink = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(divRegister, By.tagName("a")));

            WebElement title = chromeDriver.findElement(By.tagName("h1"));

            WebElement divOrder = chromeDriver.findElement(By.className("checkout-success"));
            WebElement spanNumberOrder = divOrder.findElement(By.cssSelector("p > span"));
            WebElement shoppingLink = divOrder.findElement(By.tagName("a"));

            String titleText = title.getText();
            String orderNumberText = spanNumberOrder.getText();

            if(titleText.equals("Thank you for your purchase!")){
                System.out.println("TITULO ✔");
            } else {
                cantErrores++;
                System.out.println("TITULO X");
            }

            if(shoppingLink != null && shoppingLink.isEnabled()) {
                System.out.println("BOTON SHOPPING ✔");
            } else {
                cantErrores++;
                System.out.println("BOTON SHOPPING X");
            }

            if(registerLink != null && registerLink.isDisplayed()) {
                System.out.println("BOTON CUENTA NUEVA ✔");
            } else {
                cantErrores++;
                System.out.println("BOTON CUENTA NUEVA X");
            }

            try {
                double number = Double.parseDouble(orderNumberText);
                System.out.println("ORDEN ES NUMERO ✔");
            } catch (NumberFormatException e) {
                cantErrores++;
                System.out.println("ORDEN ES NUMERO X");
            }

            System.out.println("CANTIDAD DE ERRORES ENCONTRADOS: " + cantErrores);

        }catch (Exception e) {
            System.out.println("No se pudo completar el Test");
        }

    }

    public static void genericTestProcess(WebDriver chromeDriver, String processType) throws InterruptedException {
        switch (processType){
            case "clickRadianTee":
                try{
                    WebElement radiantTee = chromeDriver.findElement(By.cssSelector("a[title='Radiant Tee']"));
                    radiantTee.click();
                }catch (Exception e) {
                    System.out.println("No se pudo completar el Test: Error en clickRadianTee");
                }
                break;
            case "clickCheckoutButton":
                Thread.sleep(2000);
                WebElement carritoButton = chromeDriver.findElement(By.cssSelector("button[data-role='proceed-to-checkout']"));
                carritoButton.click();
                break;
            case "clickPlaceOrderButton":
                Thread.sleep(5000);
                WebElement placeOrderButton = chromeDriver.findElement(By.cssSelector("button[title='Place Order']"));
                placeOrderButton.click();
                break;
            default:
                System.out.println("Opción no encontrada");
                break;
        }
    }
}
