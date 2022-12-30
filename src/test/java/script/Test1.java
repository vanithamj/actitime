package script;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import generic.BaseTest;
import generic.Utility;
import page.HomePage;



public class Test1 extends BaseTest
{
	
	@Test
	public void testA()
	{
		
		String v=Utility.getXlData(XLPATH,"test1", 0, 0);
		Reporter.log("From xl:"+v,true);
		
		String title = driver.getTitle();
		Reporter.log(title,true);
		
		HomePage homePage=new HomePage(driver);
		String label = homePage.getLabel();
		Reporter.log(label,true);
		Assert.assertEquals(label,"CONTACT U");
		
	//	Assert.assertEquals(label,"Contact Me");
		
		
		
	}

}
