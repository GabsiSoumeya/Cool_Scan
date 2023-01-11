package com.example.mypfaproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {


    @Nullable
/*******
 // TODO: Rename parameter arguments, choose names that match
 // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
 private static final String ARG_PARAM1 = "param1";
 private static final String ARG_PARAM2 = "param2";

 // TODO: Rename and change types of parameters
 private String mParam1;
 private String mParam2;

 public SecondFragment() {
 // Required empty public constructor
 }

 /**
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 *
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment SecondFragment.
 */
/*******
 // TODO: Rename and change types and number of parameters
 public static SecondFragment newInstance(String param1, String param2) {
 SecondFragment fragment = new SecondFragment();
 Bundle args = new Bundle();
 args.putString(ARG_PARAM1, param1);
 args.putString(ARG_PARAM2, param2);
 fragment.setArguments(args);
 return fragment;
 }*/

 @Override
 public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 if (getArguments() != null) {


 }

        /****
         scanBtn = findViewById(R.id.scanBtn);
         scanBtn.setOnClickListener(this);
         ******/

 }
/*
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {
 // Inflate the layout for this fragment
 return inflater.inflate(R.layout.fragment_second, container, false);
 }******/
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_second, container,
                false);
        scanCode();
        return rootView;
    }

    private void scanCode() {

        IntentIntegrator integrator = new IntentIntegrator(this.getActivity());
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("ScanningCode");
        integrator.initiateScan();

    }


  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                //builder.setMessage(result.getContents());
                System.out.println("ressssuuuuuuuuuult: " + result);
                System.out.println("ressssuuuuuuuuuult get content: " + result.getContents());

                try {
                    final String jsonStr = Jsoup.connect(
                            "https://world.openfoodfacts.org/api/v0/product/" + result.getContents() +  ".json")
                            .ignoreContentType(true)
                            .execute()
                            .body();

                    System.out.println("Result API" + jsonStr);
                    //builder.setMessage(jsonStr);
               //Product name
                    final JSONObject jsonObj = new JSONObject(jsonStr);
                    if (jsonObj.has("product")) {
                        JSONObject productNode = jsonObj.getJSONObject("product");
                        String productName = productNode.getString("product_name");
                        builder.setMessage(productName);
                        System.out.println("ressssuuuuuuuuuult product name: " + productName);
                        /** allergens = productNode.getString("allergens");
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }


                builder.setTitle("ScanningResult");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                   // @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        scanCode();

                    }
                }).setNegativeButton("finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            this.finalize();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            } else {
                Toast.makeText(this.getActivity(), "No Results", Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode , resultCode , data);
        }
    }*/

}