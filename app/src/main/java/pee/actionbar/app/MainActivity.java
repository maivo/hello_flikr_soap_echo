package pee.actionbar.app;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SaxAsyncHttpResponseHandler;
import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";


    boolean locName = false;
    boolean lastmodName = false;
    boolean changefreqName = false;

    String elementName = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreated(Bundle) called");


        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "inside onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_go) {
            handleFlickrSoap();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d(TAG, "inside id == R.id.action_settings");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void handleGo(){
        Log.i(TAG, "inside handleGo()");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.google.com", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.i(TAG, "inside onStart()");
            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.i(TAG, "inside onSuccess()");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG, "inside onFailure()");

            }
        });
    }

    protected void handleSax(){
        Log.i(TAG, "inside handleSax()");

        AsyncHttpClient client = new AsyncHttpClient();
        SaxAsyncHttpResponseHandler saxAsyncHttpResponseHandler = null;
        saxAsyncHttpResponseHandler = new SaxAsyncHttpResponseHandler<SiteUrlSet>(new SiteUrlSet()) {

            @Override
            public void onStart() {
                Log.i(TAG, "inside onStart()");
            }

            @Override
            public void onSuccess(int i, Header[] headers, SiteUrlSet siteUrlSet) {
                Log.i(TAG, "inside onSuccess()");
                List<SiteUrl> siteUrlList = siteUrlSet.getSiteUrlList();
                Log.i(TAG, "siteUrlList.size(): "+siteUrlList.size());
                for(SiteUrl siteUrl : siteUrlList){
                    Log.i(TAG, "siteUrl: "+siteUrl);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, SiteUrlSet siteUrlSet) {
                Log.i(TAG, "inside onFailure()");
            }
        };

        client.get("http://bin-iin.com/sitemap.xml", saxAsyncHttpResponseHandler);


    }





    public void handleChunkFromFile(){
        Log.i(TAG, "inside handleChunkFromFile");

        AndroidTemplates loader = new AndroidTemplates(getBaseContext());
        Theme theme = new Theme(loader);
        Chunk chunk = theme.makeChunk("test");
        chunk.set("tags", "glorious tags!");
        String out = chunk.toString();
        Log.i(TAG, "out: " + out);
    }

    public String getTempConvertCelsiusToFahrenheitRequest(){
        String chunkTemplateContent = getAssetContent("tempconvert_celsius_to_fahrenheit_request.chunk");
        Log.i(TAG, "chunkTemplateContent: " + chunkTemplateContent);
        Chunk c = new Chunk();
        c.append(chunkTemplateContent);
        c.set("tempCelcius", "30");
        String out = c.toString();
        return out;
    }



    protected void handleLoopjPostXml(){
        Log.i(TAG, "inside handleLoopjPostXml()");

        AsyncHttpClient client = new AsyncHttpClient();
        Context context = this.getApplicationContext();
        String  url = "http://www.w3schools.com/webservices/tempconvert.asmx";
        String  xml = getTempConvertCelsiusToFahrenheitRequest();
        Log.i(TAG, "xmlRequest: \n"+xml);

        HttpEntity entity;
        try {
            entity = new StringEntity(xml);
            //entity = new StringEntity(xml, "UTF-8");
        } catch (IllegalArgumentException e) {
            Log.d("HTTP", "StringEntity: IllegalArgumentException");
            return;
        } catch (UnsupportedEncodingException e) {
            Log.d("HTTP", "StringEntity: UnsupportedEncodingException");
            return;
        }
        String  contentType = "text/xml; charset=utf-8";

        Log.d("HTTP", "Post...");
        client.addHeader("Content-Type", contentType);
       // client.addHeader("Accept", "text/xml");
       // client.addHeader("SOAPAction", "http://www.w3schools.com/webservices/CelsiusToFahrenheit");
        client.post(context, url, entity, contentType, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String s = new String(bytes);
                Log.i(TAG, "onSuccess06. s: \n" + s);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG, "onFailure. throwable: " + throwable);
            }
        });

    }


    public void handleChunk(){
        Log.i(TAG, "inside handleChunk");
        Chunk c = new Chunk();
        c.append("Hello {$tags}");
        c.set("tags", "glorious tags!");
        String out = c.toString();
        Log.i(TAG, "out: " + out);
    }

    public void handleChunkFromAssetContent(){
        Log.i(TAG, "inside handleChunkFromAssetContent");
        String chunkTemplateContent = getAssetContent("test.chunk");
        Log.i(TAG, "chunkTemplateContent: " + chunkTemplateContent);
        Chunk c = new Chunk();
        c.append(chunkTemplateContent);
        c.set("tags", "glorious tags!");
        String out = c.toString();
        Log.i(TAG, "out: " + out);
    }

    /**
     * eg/ assetPath: "book/contents.json"
     * @param assetPath
     * @return
     */
    public String getAssetContent(String assetPath){
        Log.i(TAG, "inside getAssetContent");

        StringBuilder buf=new StringBuilder();
        BufferedReader in= null;
        String str;
        try {
        InputStream json=getAssets().open(assetPath);

            in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            while ((str=in.readLine()) != null) {
                buf.append(str);
            }
            return buf.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }


    protected void handleFlickrSoap(){
        Log.i(TAG, "inside handleFlickrSoap()");

        AsyncHttpClient client = new AsyncHttpClient();
        Context context = this.getApplicationContext();
        String  url = "https://api.flickr.com/services/soap/";
        String  xml = getAssetContent("flickr_hello.xml");
        Log.i(TAG, "xmlRequest: \n"+xml);

        HttpEntity entity;
        try {
            entity = new StringEntity(xml);
            //entity = new StringEntity(xml, "UTF-8");
        } catch (IllegalArgumentException e) {
            Log.d("HTTP", "StringEntity: IllegalArgumentException");
            return;
        } catch (UnsupportedEncodingException e) {
            Log.d("HTTP", "StringEntity: UnsupportedEncodingException");
            return;
        }
        String  contentType = "text/xml; charset=utf-8";

        Log.d("HTTP", "Post...");
        client.addHeader("Content-Type", contentType);
        // client.addHeader("Accept", "text/xml");
        // client.addHeader("SOAPAction", "http://www.w3schools.com/webservices/CelsiusToFahrenheit");
        client.post(context, url, entity, contentType, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String s = new String(bytes);
                Log.i(TAG, "onSuccess07. s: \n" + s);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG, "onFailure. throwable: " + throwable);
            }
        });

    }
}
