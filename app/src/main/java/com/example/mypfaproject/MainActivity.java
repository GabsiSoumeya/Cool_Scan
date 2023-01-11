package com.example.mypfaproject;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button scanBtn;
    private String urlToRssFeed = "https://world.openfoodfacts.org";
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //this line hide StatusBar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new FirstFragment()).commit();
        navigationView.setSelectedItemId(R.id.firstFragment);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.firstFragment:
                        fragment = new FirstFragment();
                        break;
                    case R.id.secondFragment:
                        fragment = new SecondFragment();
                        break;
                    case R.id.thirdFragment:
                        fragment = new ThirdFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
                return true;
            }
        });
    }
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    private void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);

    }

    @Override
    public void onClick (View v){
        scanCode();
    }

    private void scanCode () {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("ScanningCode");
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String productName = null;
        String response = "";
        JSONObject jsonObj = null;
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                try {
                    URL url = new URL("https://world.openfoodfacts.org/api/v0/product/" + result.getContents() + ".json");
                    Scanner scanner = new Scanner(url.openStream());
                    response = scanner.useDelimiter("\\Z").next();
                    scanner.close();
                    System.out.println("Response Json : \n " + response);

                    jsonObj = new JSONObject(response);
                    if (jsonObj != null) {
                        JSONObject productNode = jsonObj.getJSONObject("product");
                        productName = (String) productNode.get("product_name");
                        //  builder.setMessage(productName);
                        String imageSmallUrl = (String) productNode.get("image_small_url");
                        URL imageUrl = new URL(imageSmallUrl);
                        Bitmap bmp = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                        ImageView imageView = findViewById(R.id.dialog_imageview);

                        if(imageView.getParent() != null) {
                            ((ViewGroup)imageView.getParent()).removeView(imageView);
                        }

                        imageView.setImageBitmap(bmp);
                        //builder.setView(imageView);
                        String nutritionGradeFr  = (String) productNode.get("nutrition_grade_fr");
                        ImageView scoreView = findViewById(R.id.score_imageview);

                        if(scoreView.getParent() != null) {
                            ((ViewGroup)scoreView.getParent()).removeView(scoreView);
                        }
                        switch (nutritionGradeFr) {
                            case "a":
                                scoreView.setImageDrawable(getResources().getDrawable(R.drawable.ic_nutriscore_a));
                                break;
                            case "b":
                                scoreView.setImageDrawable(getResources().getDrawable(R.drawable.ic_nutriscore_b));
                                break;
                            case "c":
                                scoreView.setImageDrawable(getResources().getDrawable(R.drawable.ic_nutriscore_c));
                                break;
                            case "d":
                                scoreView.setImageDrawable(getResources().getDrawable(R.drawable.ic_nutriscore_d));
                                break;
                            case "e":
                                scoreView.setImageDrawable(getResources().getDrawable(R.drawable.ic_nutriscore_e));
                                break;
                        }

                        final TextView scoreText  = new TextView(this);
                        scoreText.setText("Score:  ");
                        scoreText.setTextSize(20);

                        LinearLayout ll=new LinearLayout(this);
                        ll.setOrientation(LinearLayout.VERTICAL);
                        ll.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(140, 398);
                        layoutParams.setMargins(24, 0, 24, 0);

                        imageView.setLayoutParams(layoutParams);

                        scoreText.setLeft(10);

                        ll.addView(imageView);
                        ll.addView(scoreText);
                        ll.addView(scoreView);
                        builder.setView(ll);
                        //builder.setView(scoreView);
                        //builder.setView(findViewById(R.id.layout_popup));
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                builder.setTitle("Nom du produit :" + productName);

                String finalResponse = response;
                builder.setPositiveButton("View Details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //scanCode();
                        // creation de  l'intent => pour afficher les details
                        Intent intent = new Intent(MainActivity.this, ActivityInfo.class);
                        // il faut envoyer les information de cette activity à l'autre (ils se voient pas)
                        //on envoie la reponse json de l'API
                        intent.putExtra("product", finalResponse);
                        startActivity(intent);

                    }
                }).setNegativeButton("finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();



            } else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}


