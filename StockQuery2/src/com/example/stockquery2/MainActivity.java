package com.example.stockquery2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.*;

//import edu.temple.myfirstapp.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText userStock;
	TextView companyName, companyPrice;
	Button go;	
	JSONObject stockInfo;
	String stockSymbol;
	String URL, name, price;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		userStock = (EditText) findViewById(R.id.stockSymbol);
		companyName = (TextView) findViewById(R.id.companyName);
		companyPrice = (TextView) findViewById(R.id.price);
		go = (Button) findViewById(R.id.go);

		go.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Thread thread = new Thread() {
					@Override
					public void run() {

						URL url2 = null;

						try {
							 stockSymbol = userStock.getText().toString();
							 //url2 = new URL("http://finance.yahoo.com/webservice/v1/symbols");
							 URL = ("http://finance.yahoo.com/webservice/v1/symbols/" + stockSymbol + "/quote?format=json");

							/*BufferedReader reader = new BufferedReader(
									new InputStreamReader(url2.openStream()));
							String reply = " ", temp = " ";

							temp = reader.readLine();
							while (temp != null) {
								reply = reply + temp;
								temp = reader.readLine();
							}*/
					    stockInfo = new JSONObject(URL);
					    JSONArray jsonArray = stockInfo.getJSONArray("resources");
					    for(int i = 0; i < jsonArray.length(); i++){
					    	 name = stockInfo.getString("name");
					    	 price = stockInfo.getString("price");
					    }
					    companyName.setText(name);
					    companyPrice.setText(price);
						//parseJSON();
							

						} catch (Exception e) {
							e.printStackTrace();
							//Toast.makeText(MainActivity.this,"file not found", 3000).show();
						}

					}

					

				};

				thread.start();

			}
		});

	}

}
