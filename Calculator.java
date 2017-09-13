
/**
 * 
 * @author Rob Thomas
 * 
 * Description: This class uses the Java Swing and AWT libraries to create a
 * rudimentary GUI-based calculator. All math is done using Java's double data
 * type, which may lead to inaccuracies when dealing with certain decimal 
 * values. The calculator allows for one value to be stored in memory and 
 * later recalled
 */

/*package calculator;*/

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;


public class Calculator 
{
    //display is the JTextArea that displays the current text.
    public static JTextArea display;
    //displayString is the String shown by the display. 0 by default.
    public static String displayString = "0";
    //firstNumber is the number displayed on the left.
    public static double firstNumber = 0.0;
    //secondNumber is the number displayed on the right.
    public static double secondNumber = 0.0;
    //storedValue is the stored number.
    public static double storedValue;
    /*decimalPosition is the position behind the decimal point at which to 
        append the next number added. It is 0 when appending before the decimal.*/
    public static int decimalPosition = 0;
    /*currentOperation is the operation to be done on the two numbers on 
        display. It's equal to space by default.*/
    public static char currentOperation = ' ';
    /*rewritingFirstNumber records if firstNumber is to be rewritten the next
        time the user enters numbers. It is toggled true every time the 
        calculator's state is resolved and toggled false when the next number 
        button is pressed.*/
    public static boolean rewritingFirstNumber = true;
    //layout is the GridBagLayout used by the calculator frame.
    public static GridBagLayout layout;
    
    public static void main(String[] args) 
    {
        //calculatorFrame is the JFrame representing the calculator itself.
        JFrame calculatorFrame = new JFrame("Calculator");
        calculatorFrame.setSize(330, 450);
        calculatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calculatorFrame.setResizable(false);
        layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        calculatorFrame.setLayout(layout);
        
        display = new JTextArea(18, 1);
        display.setEditable(false);
        updateDisplay();
        display.setVisible(true);
        
        //storeButton is the button for the Store command.
        JButton storeButton = new JButton();
        storeButton.setText("Store");
        storeButton.setSize(60, 60);
        storeButton.addActionListener(new ActionListener()
        {
            /*If storeButton is pressed, resolve the calculator's state and then
                perform the Store command.*/
            @Override
            public void actionPerformed(ActionEvent e)
            {
                resolveState();
                Store();
            }
        });
        storeButton.setVisible(true);
        
        //restoreButton is the button for the Restore command.
        JButton restoreButton = new JButton();
        restoreButton.setText("Restore");
        restoreButton.setSize(60, 60);
        restoreButton.addActionListener(new ActionListener()
        {
            /*If restoreButton is pressed, resolve the calculator's state and
                then perform the Restore command.*/
            @Override
            public void actionPerformed(ActionEvent e)
            {
                resolveState();
                Restore();
            }
            
        });
        restoreButton.setVisible(true);
        
        //memClearButton is the button to clear the stored memory.
        JButton memClearButton = new JButton();
        memClearButton.setText("Mem Clear");
        memClearButton.setSize(60, 60);
        memClearButton.addActionListener(new ActionListener()
        {
            /*If memClearButton is pressed, set the stored value to 0.*/
            @Override
            public void actionPerformed(ActionEvent e)
            {
                storedValue = 0.0;
            }
        });
        memClearButton.setVisible(true);
        
        //divideButton is the button for the division operation.
        JButton divideButton = new JButton();
        divideButton.setText("/");
        divideButton.setSize(60, 60);
        divideButton.addActionListener(new ActionListener()
        {
            /*If divideButton is pressed, resolve the calculator's state and 
                then set the current operation to division.*/
            @Override
            public void actionPerformed(ActionEvent e)
            {
                resolveState();
                setOperation('/');
            }
        });
        
        //multiplyButton is the button for the multiplication operation.
        JButton multiplyButton = new JButton();
        multiplyButton.setText("*");
        multiplyButton.setSize(60, 60);
        multiplyButton.addActionListener(new ActionListener()
        {
            /*If multiplyButton is pressed, resolve the calculator's state and
                then set the current operation to multiplication.*/
            @Override
            public void actionPerformed(ActionEvent e)
            {
                resolveState();
                setOperation('*');
            }
        });
        multiplyButton.setVisible(true);
        
        //subtractButton is the button for the subtraction operation.
        JButton subtractButton = new JButton();
        subtractButton.setText("-");
        subtractButton.setSize(60, 60);
        subtractButton.addActionListener(new ActionListener()
        {
            /*If subtractButton is pressed, resolve the calculator's state and
                then set the current operation to subtraction.*/
            @Override
            public void actionPerformed(ActionEvent e)
            {
                resolveState();
                setOperation('-');
            }
        });
        subtractButton.setVisible(true);
        
        //addButton is the button for the addition operation.
        JButton addButton = new JButton();
        addButton.setText("+");
        addButton.setSize(60, 60);
        addButton.addActionListener(new ActionListener()
        {
            /*If addButton is pressed, resolve the calculator's state and
                then set the current operation to addition.*/
            @Override
            public void actionPerformed(ActionEvent e)
            {
                resolveState();
                setOperation('+');
            }
        });
        addButton.setVisible(true);
        
        //clearButton is the button for clearing the display.
        JButton clearButton = new JButton();
        clearButton.setText("Clear");
        clearButton.setSize(60, 60);
        clearButton.addActionListener(new ActionListener()
        {
            /*If clearButton is pressed, resolve the calculator's state, then
                set firstNumber to 0.*/
            @Override
            public void actionPerformed(ActionEvent e)
            {
                resolveState();
                firstNumber = 0.0;
            }
        });
        clearButton.setVisible(true);
        
        //dotButton is the button for specifying the decimal point's position.
        JButton dotButton = new JButton();
        dotButton.setText(".");
        dotButton.setSize(60, 60);
        dotButton.addActionListener(new ActionListener()
        {
            /*If dotButton is pressed, set decimal position to -1. This begins
                adding numbers behind the decimal point.*/
            @Override
            public void actionPerformed(ActionEvent e)
            {
                decimalPosition = -1;
            }
        });
        dotButton.setVisible(true);
        
        //equalsButton is the button for resolving the calculator's state.
        JButton equalsButton = new JButton();
        equalsButton.setText("=");
        equalsButton.setSize(60, 60);
        equalsButton.addActionListener(new ActionListener()
        {
           /*If equalsButton is pressed, resolve the calculator's state.*/
            @Override
            public void actionPerformed(ActionEvent e)
            {
                resolveState();
            }
        });
        
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(display, constraints);
        calculatorFrame.add(display);

        
        constraints.gridwidth = 1;
        layout.setConstraints(storeButton, constraints);
        layout.setConstraints(restoreButton, constraints);
        layout.setConstraints(memClearButton, constraints);
        calculatorFrame.add(storeButton);
        calculatorFrame.add(restoreButton);
        calculatorFrame.add(memClearButton);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(divideButton, constraints);
        calculatorFrame.add(divideButton);
        
        constraints.gridwidth = 1;
        calculatorFrame.add(new numberButton(7, constraints));
        calculatorFrame.add(new numberButton(8, constraints));
        calculatorFrame.add(new numberButton(9, constraints));
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(multiplyButton, constraints);
        calculatorFrame.add(multiplyButton);
        
        constraints.gridwidth = 1;
        calculatorFrame.add(new numberButton(4, constraints));
        calculatorFrame.add(new numberButton(5, constraints));
        calculatorFrame.add(new numberButton(6, constraints));
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(subtractButton, constraints);
        calculatorFrame.add(subtractButton);
        
        constraints.gridwidth = 1;
        calculatorFrame.add(new numberButton(1, constraints));
        calculatorFrame.add(new numberButton(2, constraints));
        calculatorFrame.add(new numberButton(3, constraints));
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(addButton, constraints);
        calculatorFrame.add(addButton);
        
        constraints.gridwidth = 1;
        layout.setConstraints(clearButton, constraints);
        layout.setConstraints(dotButton, constraints);
        calculatorFrame.add(clearButton);
        calculatorFrame.add(new numberButton(0, constraints));
        calculatorFrame.add(dotButton);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(equalsButton, constraints);
        calculatorFrame.add(equalsButton);
        calculatorFrame.setVisible(true);
    }
    
    public static class numberButton extends JButton
    {
        //int Num is the number this button correlates to.
        private int Num;
        
        public numberButton(int num, GridBagConstraints constraints)
        {
            Num = num;
            setText(Num + "");
            setSize(60, 60);
            addActionListener(new ActionListener()
            {
                /*If a numberButton is pressed, append Num to the right side of
                    firstNumber or secondNumber.*/
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (currentOperation != ' ')
                    {
                        appendNumber(Num, true);
                    }
                    else
                    {
                        appendNumber(Num, false);
                    }
                }
            });
            setVisible(true);
            
            layout.setConstraints(this, constraints);
        }
    }
    
    public static void resolveState()
    {
        //If currentOperation is empty, do nothing.
        if (currentOperation != ' ')
        {
            /*If there is an operation loaded, perform it on firstNumber and
                secondNumber.*/
            firstNumber = performOperation(currentOperation, firstNumber, secondNumber);
                //Set secondNumber to 0.
            secondNumber = 0.0;
                //Set the current operation to space.
            setOperation(' ');
        }
        //Set decimalPosition back to 0, ending appending behind the point.
        decimalPosition = 0;
        //Open firstNumber up to rewriting.
        rewritingFirstNumber = true;
        //Update the display.
        updateDisplay();
    }
    
    public static double performOperation(char op, double first, double second)
    {
        switch (op)
        {
            case '/':
                return first / second;
            case '*':
                return first * second;
            case '-':
                return first - second;
            case '+':
                return first + second;
            default:
                System.err.println("INVALID OPERATION ATTEMPTED. EXITING PROGRAM.");
                System.exit(1);
                return 0;
        }
    }
    
    public static void setOperation(char op)
    {
        //Set currentOperation to op.
        currentOperation = op;
        displayString = displayString + " " + op + " ";
        updateDisplay();
    }
    
    public static void Store()
    {
        //Store firstNumber inside of storedValue.
        storedValue = firstNumber;
    }
    
    public static void Restore()
    {
        //Set displayString to storedValue.
        firstNumber = storedValue;
        updateDisplay();
    }
    
    public static void appendNumber(int num, boolean operationSelected)
    {
        /*If firstNumber is open for rewriting and no operation is selected, set
            firstNumber to 0 first.*/
        if (rewritingFirstNumber && currentOperation == ' ')
        {
            firstNumber = 0.0;
            rewritingFirstNumber = false;
        }
        //If an operation is already selected, then append num to secondNumber.
        if (operationSelected)
        {
            /*If decimalPosition isn't zero, put the number behind the decimal
                point instead, then decrement decimalPosition.*/
            if (decimalPosition < 0)
            {
                secondNumber += ((double)num) * Math.pow(10, decimalPosition);
                decimalPosition--;
            }
            //Otherwise, shift the number left and add in the new number.
            else
            {
                secondNumber *= 10;
                secondNumber += (double)num;
            }
        }
        //Else, append num to firstNumber.
        else
        {
            /*If decimalPosition isn't zero, put the number behind the decimal
                point instead, then decrement decimalPosition.*/
            if (decimalPosition < 0)
            {
                firstNumber += ((double)num) * Math.pow(10, decimalPosition);
                decimalPosition--;
            }
            //Otherwise, shift the number left and add in the new number.
            else
            {
                firstNumber *= 10;
                firstNumber += (double)num;
            }
        }
        updateDisplay();
    }
    
    public static void updateDisplay()
    {
        //Set displayString to the form "firstNumber currentOperation secondNumber".
        if (currentOperation != ' ')
        {
            //If secondNumber is 0, don't display it.
            if (secondNumber != 0.0)
            {
                displayString = firstNumber + " " + currentOperation + " " + 
                        secondNumber;
            }
            else 
            {
                displayString = firstNumber + " " + currentOperation;
            }
        }
        else
        {
            displayString = firstNumber + "";
        }
        //Set display's text to displayString.
        display.setText(displayString);
    }
}
