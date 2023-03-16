package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src//test//resources//features//demoBlazeBDD.feature",
		glue = {"stepsDefs"},
		monochrome=true,
	    dryRun=false,
		plugin= {"pretty",
		        "html:target//Reports//HTMLReport.html",
//		        "json:target//Reports//JsonReport.json",
//		        "usage:target//Reports//UsageReport"
//		        "rerun:target//FailedScenarios.txt"		
		}
	)

public class DemoBlazeRunner extends AbstractTestNGCucumberTests{



}
