package com.example.android.justjava;
/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String createOrderSummary(int priceOfOrder, boolean iscream, boolean ischocolate, String Name) {
        String order = "";

        order = getString(R.string.order_summary_name, Name);
        order += "\n" + getString(R.string.quantity) + ": " + quantity;
        order += "\n" + getString(R.string.total)+ priceOfOrder;
        order += "\n" + getString(R.string.thank_you);
        return (order);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_cream);
        boolean iscream = checkBox.isChecked();
        checkBox = (CheckBox) findViewById(R.id.checkbox_chocolate);
        boolean ischocolate = checkBox.isChecked();

        int price = calculatePrice(quantity, iscream, ischocolate);

        EditText editText = (EditText) findViewById(R.id.edit_text_name);
        String Name = editText.getText().toString();

        String message = createOrderSummary(price, iscream, ischocolate, Name);
    //    displayMessage(message);

        String subject = "JustJava order for " + Name;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"breart.thomas@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity, boolean iscream, boolean ischocolate) {
       int pricePerCup = 5;

        if (iscream)
            ++pricePerCup;
        if (ischocolate)
            pricePerCup += 2;
        return (quantity * pricePerCup);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, getString(R.string.toast_increment), Toast.LENGTH_SHORT).show();
            return ;
        }
        quantity++;
        displayQuantity(quantity);
    }
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, getString(R.string.toast_decrement), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }
/*
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }*/
}