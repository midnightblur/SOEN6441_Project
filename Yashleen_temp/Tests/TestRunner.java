//Java class that executes test classes
//and uses the results of the test cases
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) {
	  Result result;
	   
	  //run all classes stated in a test suite
      result = JUnitCore.runClasses(JunitTestSuite.class);
      //Result collects the results of each test
      for (Failure failure : result.getFailures()) {
    	 //print out the result of each result that is a failure
    	 //change some test cases so that they fail in order
    	 //to observe this
         System.out.println(failure.toString());
      }
      //print out overall result
      System.out.println(result.wasSuccessful());
      
      //OR
      
	  //run all classes stated in a set of test cases
      result = JUnitCore.runClasses(TestJunit1.class, TestJunit1.class);
      //Result collects the results of each test
      for (Failure failure : result.getFailures()) {
    	 //print out the result of each result that is a failure
     	 //change some test cases so that they fail in order
     	 //to observe this
         System.out.println(failure.toString());
      }
      //print out overall result
      System.out.println(result.wasSuccessful());  
   }
}  