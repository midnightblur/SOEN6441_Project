/**
 * The Class MessageUtil.
 */
/*
* This class prints the given message on console.
*/
public class MessageUtil {

   /** The message. */
   private String message;

   //Constructor
   /**
    * Instantiates a new message util.
    *
    * @param message the message
    */
   //@param message to be printed
   public MessageUtil(String message){
      this.message = message; 
   }

   /**
    * Prints the message.
    *
    * @return the string
    */
   // prints the message
   public String printMessage(){
      System.out.println(message);
      return message;
   }   

   /**
    * Salutation message.
    *
    * @return the string
    */
   // add "Hi!" to the message
   public String salutationMessage(){
      message = "Hi!" + message;
      System.out.println(message);
      return message;
   }   
}  	