package excel;

import java.io.FileInputStream;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class excelproj {
	String data[][]=null;
	@DataProvider(name="datapro")
	String[][]  credentials() throws BiffException, IOException
	{data=readExcel();
		return data;
	}
	String[][] readExcel() throws BiffException, IOException
	{
		FileInputStream excel= new FileInputStream("D:\\excel_t.xls");
		Workbook workbook=Workbook.getWorkbook(excel);
		Sheet sheet=workbook.getSheet(0);
		int row_count=sheet.getRows();
		int col_count=sheet.getColumns();
		String[][] data1=new String[row_count-1][col_count];
		for(int i=1;i<row_count;i++)//rows
		{
			for(int j=0;j<col_count;j++)//columns
			{data1[i-1][j]=sheet.getCell(j,i).getContents();
			}
		}
		return data1;  
	}
	@Test(dataProvider="datapro")
	void LoginUsingJXL(String uname1,String pwd1) throws Exception
	{   WebDriverManager.chromedriver().setup();
		ChromeOptions co = new ChromeOptions();
		co.addArguments("--remote-allow-origins=*");
		WebDriver driver=new ChromeDriver(co);
		driver.get("https://opensource-demo.orangehrmlive.com/");
		driver.manage().window().maximize();
		Thread.sleep(3000);
		WebElement uname=driver.findElement(By.name("username"));
		Thread.sleep(3000);
		WebElement pword=driver.findElement(By.name("password"));
		WebElement loginBtn=driver.findElement(By.xpath("//*[@class='oxd-button oxd-button--medium oxd-button--main orangehrm-login-button']"));
		uname.sendKeys(uname1);
		pword.sendKeys(pwd1);
		loginBtn.click();
	}

  }