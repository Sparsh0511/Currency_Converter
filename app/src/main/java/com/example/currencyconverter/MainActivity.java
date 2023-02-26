package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    Spinner fromSpinner, toSpinner;
    TextView resultTextView;
    Button submit;
    AutoCompleteTextView fromTextView, toTextView;
    ArrayList<String> currencyIds = new ArrayList<>();
    ArrayList<String> countryName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        resultTextView = findViewById(R.id.resultTextView);
        submit = findViewById(R.id.submitBtn);
        fromTextView = findViewById(R.id.fromTextView);
        toTextView = findViewById(R.id.toTextView);


        addToCurrencyIds();
        ArrayAdapter<String> countryNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item , countryName);

        fromTextView.setAdapter(countryNameAdapter);
        fromTextView.setThreshold(3);
        toTextView.setAdapter(countryNameAdapter);
        toTextView.setThreshold(3);

        AndroidNetworking.initialize(this);

        AtomicReference<String> fromCountry = new AtomicReference<>();
        AtomicReference<String>  toCountry = new AtomicReference<>();

        fromTextView.setOnItemClickListener((parent, view, position, id) -> {
             String selectedItem = (String) parent.getItemAtPosition(position);
             fromCountry.set(selectedItem);
        });

        toTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            toCountry.set(selectedItem);
        });


        submit.setOnClickListener(view -> {

            int fromIdx = countryName.indexOf(fromCountry.toString());
            int toIdx = countryName.indexOf(toCountry.toString());
            String from = currencyIds.get(fromIdx);
            String to = currencyIds.get(toIdx);

            String URL = new String("https://api.exchangerate.host/convert?from=" + from + "&to=" + to);
            AndroidNetworking.get(URL)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject object = response.getJSONObject("info");
                                String rate =  String.valueOf(object.get("rate"));
                                resultTextView.setText("1 " + from + " = " + rate + " " + to);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            anError.printStackTrace();
                        }
                    });

        });
    }

//    private void addToCurrencyIds() {
//        currencyIds.add("AED");
//        currencyIds.add("AFN");
//        currencyIds.add("ALL");
//        currencyIds.add("AMD");
//        currencyIds.add("ANG");
//        currencyIds.add("AOA");
//        currencyIds.add("ARS");
//        currencyIds.add("AUD");
//        currencyIds.add("AWG");
//        currencyIds.add("AZN");
//        currencyIds.add("BAM");
//        currencyIds.add("BBD");
//        currencyIds.add("BDT");
//        currencyIds.add("BGN");
//        currencyIds.add("BHD");
//        currencyIds.add("BIF");
//        currencyIds.add("BMD");
//        currencyIds.add("BND");
//        currencyIds.add("BOB");
//        currencyIds.add("BRL");
//        currencyIds.add("BSD");
//        currencyIds.add("BTC");
//        currencyIds.add("BTN");
//        currencyIds.add("BWP");
//        currencyIds.add("BYN");
//        currencyIds.add("BZD");
//        currencyIds.add("CAD");
//        currencyIds.add("CDF");
//        currencyIds.add("CHF");
//        currencyIds.add("CLF");
//        currencyIds.add("CLP");
//        currencyIds.add("CNH");
//        currencyIds.add("CNY");
//        currencyIds.add("COP");
//        currencyIds.add("CRC");
//        currencyIds.add("CUC");
//        currencyIds.add("CUP");
//        currencyIds.add("CVE");
//        currencyIds.add("CZK");
//        currencyIds.add("DJF");
//        currencyIds.add("DKK");
//        currencyIds.add("DOP");
//        currencyIds.add("DZD");
//        currencyIds.add("EGP");
//        currencyIds.add("ERN");
//        currencyIds.add("ETB");
//        currencyIds.add("EUR");
//        currencyIds.add("FJD");
//        currencyIds.add("FKP");
//        currencyIds.add("GBP");
//        currencyIds.add("GEL");
//        currencyIds.add("GGP");
//        currencyIds.add("GHS");
//        currencyIds.add("GIP");
//        currencyIds.add("GMD");
//        currencyIds.add("GNF");
//        currencyIds.add("GTQ");
//        currencyIds.add("GYD");
//        currencyIds.add("HKD");
//        currencyIds.add("HNL");
//        currencyIds.add("HRK");
//        currencyIds.add("HTG");
//        currencyIds.add("HUF");
//        currencyIds.add("IDR");
//        currencyIds.add("ILS");
//        currencyIds.add("IMP");
//        currencyIds.add("INR");
//        currencyIds.add("IQD");
//        currencyIds.add("IRR");
//        currencyIds.add("ISK");
//        currencyIds.add("JEP");
//        currencyIds.add("JMD");
//        currencyIds.add("JOD");
//        currencyIds.add("JPY");
//        currencyIds.add("KES");
//        currencyIds.add("KGS");
//        currencyIds.add("KHR");
//        currencyIds.add("KMF");
//        currencyIds.add("KPW");
//        currencyIds.add("KRW");
//        currencyIds.add("KWD");
//        currencyIds.add("KYD");
//        currencyIds.add("KZT");
//        currencyIds.add("LAK");
//        currencyIds.add("LBP");
//        currencyIds.add("LKR");
//        currencyIds.add("LRD");
//        currencyIds.add("LSL");
//        currencyIds.add("LYD");
//        currencyIds.add("MAD");
//        currencyIds.add("MDL");
//        currencyIds.add("MGA");
//        currencyIds.add("MKD");
//        currencyIds.add("MMK");
//        currencyIds.add("MNT");
//        currencyIds.add("MOP");
//        currencyIds.add("MRU");
//        currencyIds.add("MUR");
//        currencyIds.add("MVR");
//        currencyIds.add("MWK");
//        currencyIds.add("MXN");
//        currencyIds.add("MYR");
//        currencyIds.add("MZN");
//        currencyIds.add("NAD");
//        currencyIds.add("NGN");
//        currencyIds.add("NIO");
//        currencyIds.add("NOK");
//        currencyIds.add("NPR");
//        currencyIds.add("NZD");
//        currencyIds.add("OMR");
//        currencyIds.add("PAB");
//        currencyIds.add("PEN");
//        currencyIds.add("PGK");
//        currencyIds.add("PHP");
//        currencyIds.add("PKR");
//        currencyIds.add("PLN");
//        currencyIds.add("PYG");
//        currencyIds.add("QAR");
//        currencyIds.add("RON");
//        currencyIds.add("RSD");
//        currencyIds.add("RUB");
//        currencyIds.add("RWF");
//        currencyIds.add("SAR");
//        currencyIds.add("SBD");
//        currencyIds.add("SCR");
//        currencyIds.add("SDG");
//        currencyIds.add("SEK");
//        currencyIds.add("SGD");
//        currencyIds.add("SHP");
//        currencyIds.add("SLL");
//        currencyIds.add("SOS");
//        currencyIds.add("SRD");
//        currencyIds.add("SSP");
//        currencyIds.add("STD");
//        currencyIds.add("STN");
//        currencyIds.add("SVC");
//        currencyIds.add("SYP");
//        currencyIds.add("SZL");
//        currencyIds.add("THB");
//        currencyIds.add("TJS");
//        currencyIds.add("TMT");
//        currencyIds.add("TND");
//        currencyIds.add("TOP");
//        currencyIds.add("TRY");
//        currencyIds.add("TTD");
//        currencyIds.add("TWD");
//        currencyIds.add("TZS");
//        currencyIds.add("UAH");
//        currencyIds.add("UGX");
//        currencyIds.add("USD");
//        currencyIds.add("UYU");
//        currencyIds.add("UZS");
//        currencyIds.add("VES");
//        currencyIds.add("VND");
//        currencyIds.add("VUV");
//        currencyIds.add("WST");
//        currencyIds.add("XAF");
//        currencyIds.add("XAG");
//        currencyIds.add("XAU");
//        currencyIds.add("XCD");
//        currencyIds.add("XDR");
//        currencyIds.add("XOF");
//        currencyIds.add("XPD");
//        currencyIds.add("XPF");
//        currencyIds.add("XPT");
//        currencyIds.add("YER");
//        currencyIds.add("ZAR");
//        currencyIds.add("ZMW");
//        currencyIds.add("ZWL");
//
//    }

    private void addToCurrencyIds() {
        Locale[] locales = Locale.getAvailableLocales();

        // Iterate over each locale and print the country name and currency code
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (!country.isEmpty()) {
                try {
                    Currency currency = Currency.getInstance(locale);
                    String currencyCode = currency.getCurrencyCode();
                    if(!currencyIds.contains(currencyCode) && !countryName.contains(country)) {
                        currencyIds.add(currencyCode);
                        countryName.add(country);
                        Log.d("CTY", country + "  ->  " + currencyCode);
                    }
                } catch (IllegalArgumentException e) {
                    // Ignore invalid locales
                }
            }
        }

//        for(int i = 0; i < countryName.size(); i++) {
//            Log.d("CTY", countryName.get(i) + " ->  " + currencyIds.get(i));
//            Log.d("CUR", currencyIds.get(i));


    }
}