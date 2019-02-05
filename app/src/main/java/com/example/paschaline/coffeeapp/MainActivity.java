package com.example.paschaline.coffeeapp;




import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.net.Uri;
import java.text.NumberFormat;
import android.content.Intent;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /*
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            //Show an error message
            Toast.makeText(this, "You cannot order more than 100 cups", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //Show an error message
            Toast.makeText(this, "You cannot order less than 1 cup", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.quantity_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();


        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, name, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));//only email apps
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for:" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }
    }

    /**
     * Calculates the price of the order
     *
     * @param addChocolateCream is whether a user wants chocolate cream
     * @param addWhippedCream   is whether a user wants whipped cream
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolateCream) {
        int basePrice = 5;
        if (addChocolateCream) {
            basePrice = basePrice + 2;
        }
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        return basePrice * quantity;
    }


    /**
     * Printing summary of order
     *
     * @param addChocolateCream is whether a user wants chocolate cream
     * @param name              of the customer
     * @param addWhippedCream   is whether a user wants whipped cream
     * @param price             of the order
     * @return text summary
     */
    private String createOrderSummary(int price, String name, boolean addWhippedCream, boolean addChocolateCream) {
        String priceMessage = "Name:"  + name;
        priceMessage += "\n Add Whipped Cream?" + addWhippedCream;
        priceMessage += "\n Add Chocolate Cream?" + addChocolateCream;
        priceMessage += "\n Quantity: " + quantity;
        priceMessage += "\n Total: $" + price;
        priceMessage += "\n " + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);

    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}




