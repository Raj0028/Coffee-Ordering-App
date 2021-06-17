package com.example.justjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity = 2;

    @SuppressLint("QueryPermissionsNeeded")
    public void submitOrder(View view){
            CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
            CheckBox chocolate = findViewById(R.id.addChocolate);
            boolean hasChocolate = chocolate.isChecked();
            boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
            int price = calculatePrice(hasChocolate,  hasWhippedCream);
            EditText name = findViewById(R.id.personName);
            String customer = name.getText().toString();
            String priceMessage = createOrderSummary(price , hasWhippedCream , hasChocolate , customer);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT , "Just Java order for " + customer);
            intent.putExtra(Intent.EXTRA_TEXT , priceMessage);
            if(intent.resolveActivity(getPackageManager()) != null ){
                startActivity(intent);
            }

    }
    public void increment(View view){
        if(quantity==100){
            Toast.makeText(this , "You cannot have more than 100 Coffees" , Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }
    public void decrement(View view){
        if(quantity==1){
            Toast.makeText(this , "You cannot have less than 1 Coffees" , Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }


    @SuppressLint("SetTextI18n")
    private void displayQuantity(int number) {
        TextView priceTextView = findViewById(R.id.quantity_text_view);
        priceTextView.setText("" + number);
    }

    private String createOrderSummary(int price , boolean addWhippedCream , boolean addChocolate , String customer){
        String priceMessage = "Name: " + customer;
        priceMessage += "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return  priceMessage;
    }
    private int calculatePrice(boolean hasChocolate , boolean hasWhippedCream){
        int basePrice = 5;
        if(hasChocolate){
            basePrice += 2;
        }
        if(hasWhippedCream){
            basePrice += 1;
        }
        return  quantity * basePrice;
    }

}