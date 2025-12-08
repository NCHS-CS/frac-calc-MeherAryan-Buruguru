// Meher Aryan Buruguru
// Period 6
// Fraction Calculator Project
// Description: This is a Calculator, in which you can enter any type of value, and a certain operation will be done to it. 

import java.util.*;

// This is a calculator that processes input, parses that input into operators and operands, and returns a value based on the operation and the operands. 
public class FracCalc {

   // It is best if we have only one console object for input
   public static Scanner console = new Scanner(System.in);
   
   // This main method will loop through user input and then call the
   // correct method to execute the user's request for help, test, or
   // the mathematical operation on fractions. or, quit.
   // DO NOT CHANGE THIS METHOD!!
   public static void main(String[] args) {
   
      // initialize to false so that we start our loop
      boolean done = false;
      
      // When the user types in "quit", we are done.
      while (!done) {
         // prompt the user for input
         String input = getInput();
         
         // special case the "quit" command
         if (input.equalsIgnoreCase("quit")) {
            done = true;
         } else if (!UnitTestRunner.processCommand(input, FracCalc::processCommand)) {
        	   // We allowed the UnitTestRunner to handle the command first.
            // If the UnitTestRunner didn't handled the command, process normally.
            String result = processCommand(input);
            
            // print the result of processing the command
            System.out.println(result);
         }
      }
      
      System.out.println("Goodbye!");
      console.close();
   }

   // Prompt the user with a simple, "Enter: " and get the line of input.
   // Return the full line that the user typed in.
   public static String getInput() {
      System.out.print("Enter: "); 
      return console.nextLine();
   }
   
   // processCommand will process every user command except for "quit".
   // It will return the String that should be printed to the console.
   // This method won't print anything.
   // DO NOT CHANGE THIS METHOD!!!
   public static String processCommand(String input) {

      if (input.equalsIgnoreCase("help")) {
         return provideHelp();
      }
      
      // if the command is not "help", it should be an expression.
      // Of course, this is only if the user is being nice.
      return processExpression(input);
   }
   
   // Lots work for this project is handled in here.
   // Of course, this method will call LOTS of helper methods
   // so that this method can be shorter.
   // This will calculate the expression and RETURN the answer.
   // This will NOT print anything!
   // Input: an expression to be evaluated
   //    Examples: 
   //        1/2 + 1/2
   //        2_1/4 - 0_1/8
   //        1_1/8 * 2
   // Return: the fully reduced mathematical result of the expression
   //    Value is returned as a String. Results using above examples:
   //        1
   //        2 1/8
   //        2 1/4
   public static String processExpression(String input) {
      // TODO: implement this method!
      int operandNum = 0;
      String operand1 = "";
      String operator = "";
      String operand2 = "";
      int denominator = 0;
      int numerator = 0;
      int whole = 0;
      Scanner parser = new Scanner(input);
      while (parser.hasNext()){
         if (operandNum == 0){
         operand1 = parser.next();
         operandNum++;
         }
         else if (operandNum == 1){
         operator = parser.next();
         operandNum++;
         }
         else if (operandNum == 2){
         operand2 = parser.next();
         operandNum++;
         }
         else{
            break;
         }         
      }
      
      if (operand2.length() == 1){
         whole = Character.getNumericValue(operand2.charAt(0));
         denominator = 1;
      }
      
      else if (operand2.charAt(1) == '/' || operand2.charAt(2) == '/' && operand2.length() > 1){
            denominator = Integer.parseInt(operand2.substring(operand2.indexOf('/') + 1));
            numerator = Integer.parseInt(operand2.substring(0, operand2.indexOf('/')));
         }
      else if (operand2.charAt(1) == '_' && operand2.length() > 1){
         whole = Character.getNumericValue(operand2.charAt(0));
         denominator = Character.getNumericValue(operand2.charAt(4));
         numerator = Character.getNumericValue(operand2.charAt(2));
      }

      else if ((operand2.charAt(2) == '_' || operand2.charAt(3) == '_') && operand2.length() > 1){
        whole = Integer.parseInt(operand2.substring(0, operand2.indexOf('_')));
        numerator = Integer.parseInt(operand2.substring(operand2.indexOf('_') + 1, operand2.indexOf('/')));
        denominator = Integer.parseInt(operand2.substring(operand2.indexOf('/') + 1));
      }

      if (Integer.toString(numerator).charAt(0) == '-' && Integer.toString(denominator).charAt(0) == '-'){
         numerator = Math.abs(numerator);
         denominator = Math.abs(denominator);
      } 
      
      
        return "Op:" + operator + " Whole:" + whole + " Num:" + numerator + " Den:" + denominator;

   }
   
   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
      // TODO: Update this help text!
     
      return "This is a Fraction Calculator. Enter any type of value, and notice your output";
   }
}

