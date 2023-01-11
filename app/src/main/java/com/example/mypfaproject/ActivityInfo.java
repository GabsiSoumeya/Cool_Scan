package com.example.mypfaproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class ActivityInfo extends AppCompatActivity {

    private JSONObject productNode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Reception de la reponse envoyé par MainActivity
        Intent intent = getIntent();
        String response = intent.getStringExtra("product");

        //Convertir la reponse (string) en Json
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(response);
            productNode = jsonObj.getJSONObject("product");

            //Afficher text (product name)
            String titleText = (String) productNode.get("product_name_fr");
            TextView titleView = findViewById(R.id.textView);
            titleView.setTextSize(20);
            titleView.setText(titleText);

            //Afficher text Brand et poids
            String brandText = (String) productNode.get("brands");
            String poidsText = (String) productNode.get("quantity");
            TextView brandView = findViewById(R.id.textBrand);
            brandView.setTextSize(17);
            brandView.setText("Marque: " + brandText + "\n" + "Poids: " + poidsText);


            //Afficher Image de produit
            String imageUrlText = (String) productNode.get("image_url");
            URL imageUrl = new URL(imageUrlText);
            Bitmap bmp = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            System.out.println("image URL: " + imageUrlText + "  " +  " bmp " + bmp );
            ImageView imageView = findViewById(R.id.imageView2);
            imageView.setImageBitmap(bmp);


            //Afficher text ingredient
            String ingredientsText = (String) productNode.get("ingredients_text_fr");

            //Afficher list ingredients
            ListView listIngredientsView = findViewById(R.id.listView);
            String[] listIng = ingredientsText.split("[,]", 0);
            for(String ingredient: listIng) {
                System.out.println("innnnnnng:" + ingredient);
            }
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_list_item_1,  listIng );

            listIngredientsView.setAdapter(adapter);
            //Afficher text Repères nutritionnels
            JSONObject reperesJson = (JSONObject)  productNode.get("nutrient_levels");
            TextView reperesView = findViewById(R.id.textReperes);
            reperesView.setTextSize(15);
            reperesView.setSingleLine(false);
            //ici on va parcourir la reponse car elle contient plusieurs elements
            //String reperesText = "";
            /*for (int i=0; i <= reperesJson.keys().; i++ ) {
            while (reperesJson.keys().hasNext()) {
                reperesText += reperesJson.keys().next()  + ": " + (String) reperesJson.get(reperesJson.keys().next()) + "\n";
            }
            reperesView.setText("Repères nutritionnels: \n" + reperesText);*/

            callButton();


        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void callButton() {
        Button allergenesButton = findViewById(R.id.button);
        Button additivesButton = findViewById(R.id.button2);
        Button huilesPalmButton = findViewById(R.id.button3);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_list_item_1);

        View.OnClickListener allergensClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String allergenesText = null;
                try {
                    allergenesText = (String) productNode.get("allergens");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*TextView view = findViewById(R.id.buttonText);
                view.setTextSize(15);
                view.setSingleLine(false);
                view.setText(allergenesText);*/

                allergenesText = allergenesText.replace("en:", "");
                allergenesText = allergenesText.replace("fr:", "");
                allergenesText = "* " + allergenesText;
                allergenesText = allergenesText.replace(",", "\n \n * ");
                //popup
                builder2.setTitle("Allergènes");
                builder2.setMessage(allergenesText);
                builder2.setCancelable(true);
                AlertDialog popup = builder2.create();
                popup.show();
            }
        };
        View.OnClickListener additivesClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";
                try {
                    JSONArray additives =  (JSONArray) productNode.get("additives_tags");
                    for (int i = 0; i <= additives.length(); i++ ){
                        text += additives.get(i).toString().toUpperCase() + "\n";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*TextView view = findViewById(R.id.buttonText);
                view.setTextSize(15);
                view.setSingleLine(false);
                view.setText(text);*/


                text = text.replace("EN:", "");
                text = text.replace("FR:", "");
                text = "* " + text;
                text = text.replace(",", "\n \n * ");

                //popup
                builder2.setTitle("Additives");
                builder2.setMessage(text);
                builder2.setCancelable(true);
                AlertDialog popup = builder2.create();
                popup.show();
            }
        };

        View.OnClickListener huilePalmClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = null;
                try {
                    JSONArray ingredients = (JSONArray) productNode.get("ingredients");
                    text =  (String) ingredients.get(4).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*TextView view = findViewById(R.id.buttonText);
                view.setTextSize(15);
                view.setSingleLine(false);
                view.setText(text);*/

                text = text.replace("en:", "");
                text = text.replace("fr:", "");
                text = "* " + text;
                text = text.replace(",", "\n \n * ");
                //popup
                builder2.setTitle("Huile de palm");
                builder2.setMessage(text);
                builder2.setCancelable(true);
                AlertDialog popup = builder2.create();
                popup.show();
            }
        };
        allergenesButton.setOnClickListener(allergensClickListener);
        additivesButton.setOnClickListener(additivesClickListener);
        huilesPalmButton.setOnClickListener(huilePalmClickListener);
    }

}