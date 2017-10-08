//JUnit 4 test class
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class TestJunit1 {
   String message = "Robert";	
   MessageUtil messageUtil = new MessageUtil(message);
   
   @BeforeClass public static void beforeClass() {
      System.out.println("in before TestJunit1 class");
   }
   @AfterClass public static void  afterClass() {
      System.out.println("in after TestJunit1 class");
   }
   @Before public void before() {
      System.out.println("in before TestJunit1 test case");
   }
   @After public void after() {
      System.out.println("in after TestJunit1 test case");
   }
   @Test public void testPrintMessage() {	
      System.out.println("Inside testPrintMessage()");    
      assertEquals(message, messageUtil.printMessage());     
   }
   @Test public void testSalutationMessage() {
	  System.out.println("Inside testSalutationMessage()");
	  message = "Hi!" + "Robert";
	  assertEquals(message,messageUtil.salutationMessage());
   }
}
//in before TestJunit1 class
//in before TestJunit1 test case
//Inside testSalutationMessage()
//Hi!Robert
//in after TestJunit1 test case
//in before TestJunit1 test case
//Inside testPrintMessage()
//Robert
//in after TestJunit1 test case
//in after TestJunit1 class