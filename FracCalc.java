// Meher Aryan Buruguru
// Period 6
// Fraction Calculator Project
/* Description: This is a calculator that performs arithmetic operations(+, -, *, /) on fractions. All types of fractions(Mixed, Improper, Regular) fractions 
   are compatible with this calculator. Sometimes, a fraction may need to be reduced down to it's simplest form. In order to achieve this without repeatedly 
   dividing by Prime Numbers, the Euclidean Algorithm is used instead, in order to improve efficiency drastically, and increase simplicity. 
   The Calculator is also compatible with normal integer-based arithmetic operations. */ 

// For more info about Euclidean Algorithm, visit: 
// https://en.wikipedia.org/wiki/Euclidean_algorithm

// This line allows the program to function more efficiently, as most methods in this program rely on separate classes
import java.util.*;

// This is a calculator that processes input from a user, which can include two numbers, either fractions or integers, and performs a specific arithmetic operation
public class FracCalc {

   // It is best if we have only one console object for input
   public static Scanner console = new Scanner(System.in);
   
   // This main method will loop through user input and then call the
   // correct method to execute the user's request for help, test, or
   // the mathematical operation on fractions. or, quit.
   public static void main(String[] args) {
   
      // initialize to false so that we start our loop
      boolean done = false;
      
      // When the user types in "quit", we stop the program and are done.
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
      // Printing a small message for when the user closes the program
      System.out.println("Goodbye!");
      console.close();
   }

   // Prompting the user with a simple, "Enter: " and get the line of input\
   public static String getInput() {
      System.out.print("Enter: "); 
      return console.nextLine();
   }
   
   // processCommand will process every user command except for "quit".
   // It will return the String that should be printed to the console.
   // This method won't print anything.
   public static String processCommand(String input) {

      if (input.equalsIgnoreCase("help")) {
         return provideHelp();
      }
      
      // if the command is not "help", the input value is taken in as an expression
      return processExpression(input);
   }
   

   // The processExpression method relies on other methods in this program
   // to read the different operands as well as the operator, and execute
   // specific functions on them
   public static String processExpression(String input) {
      int operandNum = 0;
      String operand1 = "";
      String operator = "";
      String operand2 = "";
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

      // Different parts of each fraction. (Note: The variables with the number '2' behind their classification actually refer to the first operand)
      int denominator = returnOperandDenominator(operand2);
      int numerator = returnOperandNumerator(operand2);
      int whole = returnOperandWhole(operand2);
      int denominator2 = returnOperandDenominator(operand1);
      int numerator2 = returnOperandNumerator(operand1);
      int whole2 = returnOperandWhole(operand1); 
      // checking whether denominators is 0 to prevent expection
      if (denominator == 0 || denominator2 == 0){
         return "cannot divide by 0";
      }
       
      // Conditionals check whether or not the negation on a specific part of the fraction needs to be dropped for both Fractions
      if ((Integer.toString(numerator).charAt(0) == '-' || Integer.toString(whole).charAt(0) == '-') && Integer.toString(denominator).charAt(0) == '-'){
         numerator = Math.abs(numerator);
         denominator = Math.abs(denominator);
         whole = Math.abs(whole);
      }

      if ((Integer.toString(numerator2).charAt(0) == '-' || Integer.toString(whole2).charAt(0) == '-') && Integer.toString(denominator2).charAt(0) == '-'){
         numerator2 = Math.abs(numerator2);
         denominator2 = Math.abs(denominator2);
         whole2 = Math.abs(whole2);
      }
      // Checking which operation to perform
      if (operator.equals("+")){      
        return addition(numerator, numerator2, denominator, denominator2, whole, whole2, operator);
      }
      else if (operator.equals("-")){      
        return subtraction(numerator, numerator2, denominator, denominator2, whole, whole2, operator);
      }
       else if (operator.equals("*")){
        return multiplication(numerator, numerator2, denominator, denominator2, whole, whole2);
      }
      else {
        return division(numerator, numerator2, denominator, denominator2, whole, whole2);
      }
   }
   
   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
      
      // Small help paragraph with all the instructions for the user. 
      return "What you are currently looking at is a special type of calculator, that reads 2 fractions, and outputs the result based on the two functions given and the \narithmetic operation specified. \nTo begin using it, type 1 fraction, followed by a space, then your chosen arithmetic operation (+, -, *, /), followed by another space, and another fraction \nThen press enter, and your result of the operations on the fractions will be outputted. \nIt's not just fractions too, you can use this to perform arithmetic operations on any whole number that is an integer.";
   }

   // Whenever the implied operation is addition as per user input, the addition method first ensures both fractions are improper, and that
   // their bases are the same, before adding them together. In order to ensure that the fractions are improper, it uses the convertToImproper method (line 280)
   // and adds the two numerators together using the combiningOfWholesAndNumerators method. Before it does that, however, it first checks to see if 
   // the placements of negatives in between fractions causes the fractions to actually subtract instead of add. 
   public static String addition(int numerator, int numerator2, int denominator, int denominator2, int whole, int whole2, String operator){
      //Checking to see which operation must be done
      if (operator == "+" && Integer.toString(whole).charAt(0) == '-' || Integer.toString(numerator).charAt(0) == '-'){
         subtraction(numerator, numerator2, denominator, denominator2, whole, whole2, operator);
      }
      //Converting both fractions to improper
      numerator2 = convertToImproper(numerator2, denominator2, whole2);
      denominator2 = Math.abs(denominator2);
      numerator = convertToImproper(numerator, denominator, whole);
      denominator = Math.abs(denominator);
      if (numerator != 0){
      whole = 0;
      }
      if (numerator2 != 0){
         whole2 = 0;
      } // Setting both denominators equal to each other

      if (denominator == 0 || denominator2 == 0){
         return "Invalid. One denominator is 0";
      }
      if (denominator != denominator2){
         int denominatorTemp = denominator;
         denominator *= denominator2;
         numerator *= denominator2;
         denominator2 *= denominatorTemp;
         numerator2 *= denominatorTemp;
      }
      // Adding both using combiningOfNumeratorsAndWholes method. 
      int newNum = combiningOfNumeratorsAndWholes(numerator, numerator2, whole, whole2, operator);
      String finalFraction = "" + newNum + "/" + denominator;
      finalFraction = fractionConventions(finalFraction);
      return finalFraction;
   }

   // The subtraction method works in similar principles to the addition method, but it doesn't check for the right operation as the addition method
   // and it subtracts the two values instead of adding them.
   public static String subtraction(int numerator, int numerator2, int denominator, int denominator2, int whole, int whole2, String operator){
      // Converting both functions to improper
      numerator2 = convertToImproper(numerator2, denominator2, whole2);
      denominator2 = Math.abs(denominator2);
      numerator = convertToImproper(numerator, denominator, whole);
      denominator = Math.abs(denominator);      
      // setting whole to 0
      if (numerator != 0){
      whole = 0;
      }
      if (numerator2 != 0){
         whole2 = 0;
      } // Setting both denominators equal to each other
      if (denominator != denominator2){
         int denominatorTemp = denominator;
         denominator *= denominator2;
         numerator *= denominator2;
         denominator2 *= denominatorTemp;
         numerator2 *= denominatorTemp;
      }
      // Subtracting both functions
      int newNum = combiningOfNumeratorsAndWholes(numerator, numerator2, whole, whole2, operator);
      String finalFraction = "" + newNum + "/" + denominator;
      finalFraction = fractionConventions(finalFraction);
      return finalFraction;
   }

   // The multiplication method mutliplies both the top and the bottom of the fractions together. However, like the other arithmetic operation
   // methods, it does convert both to improper before solving
    public static String multiplication(int numerator, int numerator2, int denominator, int denominator2, int whole, int whole2){
      // Conversion to improper
      numerator2 = convertToImproper(numerator2, denominator2, whole2);
      denominator2 = Math.abs(denominator2);
      numerator = convertToImproper(numerator, denominator, whole);
      denominator = Math.abs(denominator);
      // setting whole to 0
      if (numerator != 0){
      whole = 0;
      }
      if (numerator2 != 0){
         whole2 = 0;
      }
      // 
      numerator2 *= numerator;
      denominator2 *= denominator;

      String finalFraction = "" + numerator2 + "/" + denominator2;

      finalFraction = fractionConventions(finalFraction);
      return finalFraction;
    }

    // the Division method uses the Keep-Change-Flip strategy, 
    // The method first multiplies the first numerator by the second denominator, and the first denominator
    // with the second numerator
    public static String division(int numerator, int numerator2, int denominator, int denominator2, int whole, int whole2){
      numerator2 = convertToImproper(numerator2, denominator2, whole2);
      numerator = convertToImproper(numerator, denominator, whole);
      if (numerator != 0){
      whole = 0;
      }
      if (numerator2 != 0){
         whole2 = 0;
      }
      // Occurence of multiplication
      numerator2 *= denominator;
      denominator2 *= numerator;
      if (denominator2 != 0){
      String finalFraction = "" + numerator2 + "/" + denominator2;
      finalFraction = fractionConventions(finalFraction);
      return finalFraction;
      }

      else {
         return "Cannot divide by 0. Invalid statement.";
      }

    }
// Fraction Conventions checks to make sure that the fractions are always simplified, and are in Mixed Fraction Form before shooting out the answer
   public static String fractionConventions(String fraction){
      String finalFraction = "";
      int whole = 0;
      int numerator = Integer.parseInt(fraction.substring(0, fraction.indexOf('/')));
      int denominator = Integer.parseInt(fraction.substring(fraction.indexOf('/') + 1));
      if (numerator == denominator){
         numerator = 0;
         denominator = 0;
         finalFraction = "1";
      }
      // if the numerator is divisible by the denominator
      else if (numerator % denominator == 0){
         whole = numerator / denominator;
         finalFraction = "" + whole;
         numerator = 0;
         denominator = 0;
      }
      // If the numerator is bigger than the denominator
      else if (Math.abs(numerator) > denominator){
         whole = numerator / denominator;
         numerator -= (denominator * whole);
         numerator = Math.abs(numerator);
         denominator = Math.abs(denominator);
         finalFraction = "" + whole + " " + numerator + "/" + denominator;
      }
      // If it's a whole number
      else if (denominator == 1){
         whole = numerator;
         numerator = 0;
         denominator = 0;
         finalFraction = "" + whole;
      }
      // If the denominator isn't 0
      if (denominator != 0){
         int gcf = GCF(numerator, denominator);
         numerator /= Math.abs(gcf);
         denominator /= Math.abs(gcf);
         if (whole != 0){
            finalFraction = "" + whole + " " + numerator + "/" + denominator;
         }
         else {
             finalFraction = "" + numerator + "/" + denominator;
         }
      }

      if ((Integer.toString(numerator).charAt(0) == '-' || Integer.toString(whole).charAt(0) == '-') && Integer.toString(denominator).charAt(0) == '-'){
         numerator = Math.abs(numerator);
         denominator = Math.abs(denominator);
         whole = Math.abs(whole);
      } 
      
      
      return finalFraction;
   }

   // The convert to improper method converts a mixed fraction to improper by muliplying the denominator by the whole number, and adding it to the numerator. 
   // It then proceeds to return the new numerator value. 
   // In order to return the correct numerator value, the method goes through a series of tests in order to identify 
   // which fraction is being used, and acts accordingly. 
   public static int convertToImproper(int numerator, int denominator, int whole){
      if (whole < 0){
         numerator += Math.abs(whole) * denominator;
         numerator *= -1;
      }
      
      else if (whole > 0){
         numerator += Math.abs(whole) * denominator;
      }
      if (denominator / Math.abs(denominator) == -1){
            numerator *= -1;
            denominator *= -1;
      }

      return numerator;
   }

   // This method is used foor calculating the Greatest Common Factor(GCF) of the numerator and the denominator/
   // It works by utilizing a simple and efficient procedure that was created by the Greek mathematician Euclid. 
   // The method outputs the GCF value once the fraction has been simplified as much as possible. 
   public static int GCF (int numerator, int denominator){  
      int a = numerator; 
      int b = denominator; 
      while (b != 0){
         int temp = b; 
         b = a % b; 
         a = temp; 
      }
      return a;
   }

   // This method is used to add/subtract the numerators depending on the operator. The whole numbers are not subtracted
   // They have beeen set to 0 when the functions were being converted into improper functions
   public static int combiningOfNumeratorsAndWholes(int numerator, int numerator2, int whole, int whole2, String operator){
      int newNum = 0;
       // if the operator is addition
      if (operator.equals("+")){
         // if two wholes are being added
         if (numerator == 0 && whole != 0){
         newNum = whole + whole2;
      } 
      else {
         newNum = numerator + numerator2;
      }
      }
      else { // subtraction
         if (numerator == 0 && whole != 0){
         newNum = whole - whole2;
      } 
      else {
         newNum = numerator2 - numerator;
      }
      }
   return newNum;
   }

// // The returnOperandWhole method first takes in one of the operands from the input given by the user
// And only then returns the whole number value. Every fraction has different whole numbers, so that is why the 
// operand is put through multiple test cases
public static int returnOperandWhole(String operand){
   int whole = 0;
      // case one: if it's a whole number
      if (operand.contains("/") && operand.contains("_") && operand.length() >= 5){
        whole = Integer.parseInt(operand.substring(0, operand.indexOf('_')));
      } 

      return whole;
   }

// The returnOperandNumerator method first takes in one of the operands from the input given by the user
// And only then returns the numerator value. Every fraction has different numerators, so that is why the 
// operand is put through multiple test cases
public static int returnOperandNumerator(String operand){
   int numerator = 0;   
   // case one: if the fraction is a mixed fraction
      if (operand.contains("/") && operand.contains("_") && operand.length() >= 5){
        numerator = Integer.parseInt(operand.substring(operand.indexOf('_') + 1, operand.indexOf('/')));
      }
      // case two: If it is just a function
      else if (operand.contains("/") && operand.length() <= 5){
            numerator = Integer.parseInt(operand.substring(0, operand.indexOf('/')));
      }
      // Case three: if it is just a whole number
      else {
            numerator = Integer.parseInt(operand);
      }
   return numerator;
}

// The returnOperandDenominator method first takes in one of the operands from the input given by the user
// And only then returns the denominator value. Every fraction has different denominators, so that is why the 
// operand is put through multiple test cases
public static int returnOperandDenominator(String operand){
   int denominator = 0;   
   // case one: operand is a whole
   if ((!operand.contains("/")) ){ 
         denominator = 1;
      }
   // case two: operand is a mixed fraction
   else if (operand.contains("/") && operand.contains("_") && operand.length() >= 5){
        denominator = Integer.parseInt(operand.substring(operand.indexOf('/') + 1));
      }
   // case three: operand isn't mixed fraction, but is a fraction
      else if (operand.contains("/") && operand.length() <= 5){
            denominator = Integer.parseInt(operand.substring(operand.indexOf('/') + 1));
      }


   return denominator;
}


}