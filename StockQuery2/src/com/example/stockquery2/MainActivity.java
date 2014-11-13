package com.example.stockquery2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.net.*;

import org.json.*;

//import edu.temple.myfirstapp.MainActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends Activity {

	EditText userStock;
	TextView companyName, companyPrice;
	Button go;	
	JSONObject stockInfo = null;
	String stockSymbol;
	String URL;
	Message msg;
	long delayTime = 10000;
	Handler showContent = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			try {
				
				JSONObject stock = new JSONObject(msg.obj.toString());
                JSONObject list = stock.getJSONObject("list");
                JSONArray resources = list.getJSONArray("resources");
                JSONObject resourceObject = resources.getJSONObject(0);
                JSONObject resource = resourceObject.getJSONObject("resource");
                JSONObject quote = resource.getJSONObject("fields");

                companyName.setText(quote.getString("name"));
                companyPrice.setText("$" + quote.getString("price"));
               
			} catch (JSONException e1) {
				
				e1.printStackTrace();
			}

			return false;
		}
	});
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
							while(true){
							 stockSymbol = userStock.getText().toString();
							 url2 = new URL("http://finance.yahoo.com/webservice/v1/symbols/" + stockSymbol + "/quote?format=json");						 							
							 BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream(), "UTF-8"));
							String reply = " ", temp = " ";
							temp = reader.readLine();
							while (temp != null) {
								reply = reply + temp;
								temp = reader.readLine();
							}
							msg = Message.obtain();
							msg.obj = reply;
							//Object showContent = null;
						 showContent.sendMessage(msg);	
						 Thread.sleep(delayTime);
							}
						} catch (Exception e) {
							e.printStackTrace();							
						}
					}					
				};
				thread.start();
				
				
			}
		});

	}

}
