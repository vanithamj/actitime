package script;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class DemoReport {

	public static void main(String[] args) {
		
		ExtentReports eReport=new ExtentReports();
		ExtentSparkReporter reportType=new ExtentSparkReporter("./target/MyReport.html");
		eReport.attachReporter(reportType);
		
		ExtentTest test = eReport.createTest("Test1");
		
		test.info("Hi this is info");
		test.pass("this is pass");
		test.warning("this is warning");
		test.fail("this is fail");
		
		ExtentTest test2 = eReport.createTest("Test2");
		
		test2.info("Hi this is info");
		test2.pass("this is pass");
//		test2.warning("this is warning");
//		test2.fail("this is fail");
		
		eReport.flush();
		
	}
}
