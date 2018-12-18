package com.selenium.project;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Amazon Sitesi Kullanici Adi: amazonseleniumtest@gmail.com 
 * Sifre: amazonseleniumtest1!
 * 
 * @author MehmetBesli063
 */

// Kullanici Adi: amazonseleniumtest@gmail.com
// Sifre: amazonseleniumtest1!

public class WebPage {

	private WebDriver driver;
	private String productUrl;

	public static void main(String[] args) {
		WebPage amazonWebPage = new WebPage();
		amazonWebPage.amazonWebPageTestRun();
	}

	private void amazonWebPageTestRun() {
		String urunAdi = "samsung";
		try {
			initializeChromeBrowser();
			gotoAmazonMainPage();
			gotoLoginPage();
			doLoginAction();
			searchProduct(urunAdi);
			validateProductSearchPage(urunAdi);
			goToSecondResultPage();
			validateSecondPage();
			selectThirdProduct();
			productCorrectAddress();
			clickAddToListButton();
			compareTwoProduct();
			viewWishList();
			clickDeletedButton();

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			// son adim olarak browser kapatmak için
			if (driver != null)
				driver.quit();
			// driver.close();
		}
	}

	private void clickDeletedButton() {
		driver.findElement(By.xpath("//input[@name=\"submit.deleteItem\"]")).click();
		driver.findElement(By.xpath("//div[(@class='a-alert-content') and (text()='Deleted')]"));
		System.out.println("Wish listte eklenen urun silindi");
	}

	private void viewWishList() {
		driver.findElement(By.id("WLHUC_viewlist")).click();
	}

	private void productCorrectAddress() {
		productUrl = driver.findElement(By.id("productTitle")).getText();
		System.out.println(productUrl);
	}

	private void compareTwoProduct() {
		// girilen urun ayni değil mi kontrolu
		String urunTitle = driver
				.findElement(By.xpath("//*[@id=\"WLHUC_info\"]/div[1]/ul/li[2]/table/tbody/tr[1]/td/a")).getText();
		if (urunTitle.toLowerCase().contains(productUrl.toLowerCase())) {
			System.out.println("Urun karsilastirma basarili");
		} else {
			throw new WebDriverException("Urun karsilastirma basarisiz");
		}
	}

	private void clickAddToListButton() {
		driver.findElement(By.id("add-to-wishlist-button")).click();
		driver.findElement(By.xpath("//span[contains(@id,'atwl-list-name') and contains(text(),'Wish List')]")).click();
	}

	private void selectThirdProduct() {
		driver.findElement(By.xpath("//*[@id=\"s-results-list-atf\"]/li[3]/div/div/div/div[1]/div/div")).click();
	}

	private void validateSecondPage() {
		if (driver.getCurrentUrl().contains("page=2")) {
			System.out.println("2. sayfadayiz");
		} else {
			throw new WebDriverException("2. sayfada degilsiniz");
		}
	}

	private void goToSecondResultPage() {
		driver.findElement(By.xpath("//*[@id=\"pagn\"]/span[3]/a")).click();
	}

	private void validateProductSearchPage(String urunAdi) {
		if (driver.getCurrentUrl().contains("field-keywords=" + urunAdi)) {
			System.out.println(urunAdi + " search sayfasindayiz");
		} else {
			throw new WebDriverException("ilgili arama yapilamadi");
		}
	}

	private void searchProduct(String urunAdi) {
		System.out.println("searchProduct metoda girdi");
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(urunAdi);
		driver.findElement(By.className("nav-input")).click();
	}

	private void gotoLoginPage() {
		driver.findElement(By.id("nav-link-accountList")).click();
	}

	private void doLoginAction() {
		driver.findElement(By.id("ap_email")).sendKeys("amazonseleniumtest@gmail.com");
		driver.findElement(By.id("ap_password")).sendKeys("amazonseleniumtest1!");
		driver.findElement(By.id("signInSubmit")).click();
	}

	private void gotoAmazonMainPage() {
		driver.get(" http://www.amazon.com");
		if (driver.getCurrentUrl().contains("https://www.amazon.com")) {
			System.out.println("Amazon ana sayfasina gidildi");
		} else {
			throw new WebDriverException("ilgili sayfaya acilamadi");
		}
	}

	private void initializeChromeBrowser() {
		String path = System.getProperty("user.dir");
		System.out.println("Proje path : " + path);
		System.setProperty("webdriver.chrome.driver", path + "\\libs\\chromedriver.exe");
		driver = new ChromeDriver();
		// tum elementler icin maksimum 15 bekleyeck
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
}
