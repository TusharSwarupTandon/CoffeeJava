package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
{
    int numberOfCupsOfCoffee = 2;    
    int extraToppingsCost = 0;
    final int costPerItem = 50;
    final int toppingWhippedCreamCost = 10;    //Whipped Cream
    final int toppingChocolateCost = 20;       //Chocolate
    final String greetings = "Thank You!";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayQuantity(numberOfCupsOfCoffee);
        displayPrice(getPrice());
    }

    /**
     * this methods returns price
     */
    private int getPrice()
    {
        extraToppingsCost = 0;
        //Adds value to the final cost if Whipped Cream is selected

        CheckBox toppingWhippedCream = findViewById(R.id.toppingWhippedCream);
        boolean currentStateTopping1 = toppingWhippedCream.isChecked();
        if(currentStateTopping1)    extraToppingsCost += toppingWhippedCreamCost;

        //Adds value to the final cost if Chocolate is selected
        CheckBox toppingChocolate = findViewById(R.id.toppingChocolate);
        boolean currentStateTopping2 = toppingChocolate.isChecked();
        if(currentStateTopping2)    extraToppingsCost += toppingChocolateCost;

        return numberOfCupsOfCoffee * (costPerItem + extraToppingsCost);
    }
    /**
     * This method increases the quantity by 1
     */
    public void increment(View view)
    {
        if(numberOfCupsOfCoffee < 100)
        {
            displayQuantity(++numberOfCupsOfCoffee);
            displayPrice(getPrice());
        }
        else
        {
            Toast.makeText(this, "You can not have more than 100 cups of coffee.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method decreases the quantity by 1
     */
    public void decrement(View view)
    {
        if(numberOfCupsOfCoffee>1)
       {
           displayQuantity(--numberOfCupsOfCoffee);
           displayPrice(getPrice());
       }
        else
        {
            Toast.makeText(this, "You can not have less than 1 cup of coffee!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method displays the current number of items
     */
    private void displayQuantity(int number)
    {
        TextView quantity = findViewById(R.id.quantity_text_view);
        quantity.setText((getString(R.string.blank,number)));
    }


    private void displayPrice(int number)
    {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    public void toppingAction(View view)
    {
        displayPrice(getPrice());
    }


    /**
     *This method displays message when orderButton is pressed
     */
    public void orderButton(View view)
    {
        EditText userName = findViewById(R.id.userName);
        String name = userName.getText().toString();
        if(name.equals(""))
        {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
        }
        else
        {

            CheckBox toppingWhippedCream = findViewById(R.id.toppingWhippedCream);
            boolean currentStateTopping1 = toppingWhippedCream.isChecked();

            CheckBox toppingChocolate = findViewById(R.id.toppingChocolate);
            boolean currentStateTopping2 = toppingChocolate.isChecked();

            String result = "Name : " + name + "\nQuantity : " + numberOfCupsOfCoffee + "\nAdd whipped cream ? " + currentStateTopping1 + "\nAdd chocolate ? " + currentStateTopping2 + "\nTotal : " + NumberFormat.getCurrencyInstance().format(getPrice()) + "\n" + greetings;

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for " + name);
            intent.putExtra(Intent.EXTRA_TEXT, result);
            if (intent.resolveActivity(getPackageManager()) != null)
            {
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "No Email services found on the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * This method adds toppings
     */
    public void checkToppings(View view)
    {
        CheckBox toppingWhippedCream = findViewById(R.id.toppingWhippedCream);
        if(toppingWhippedCream.isChecked())    extraToppingsCost+=1;
    }
}