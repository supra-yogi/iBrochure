package com.group.ibrochure.i_brochure.home;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.group.ibrochure.i_brochure.R;
import com.group.ibrochure.i_brochure.adapters.BrochureAdapter;
import com.group.ibrochure.i_brochure.model.Brochure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mBrochureRecyclerView;
    private BrochureAdapter mAdapter;
    private ArrayList<Brochure> mBrochureCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        new FetchDataTask().execute();

    }

    private void init() {
        mBrochureRecyclerView = (RecyclerView) findViewById(R.id.brochure_recycler);
        mBrochureRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBrochureRecyclerView.setHasFixedSize(true);
        mBrochureCollection = new ArrayList<>();
        mAdapter = new BrochureAdapter(mBrochureCollection, this);
        mBrochureRecyclerView.setAdapter(mAdapter);
    }

    public class FetchDataTask extends AsyncTask<Void, Void, Void> {

        private String mIbrochureString;

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            Uri builtUri = Uri.parse(getString(R.string.iBrochure_api));
            URL url;
            try {
                url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("user-key", "");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    //Nothing to do
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                mIbrochureString = buffer.toString();
                JSONObject jsonObject = new JSONObject(mIbrochureString);

                Log.v("Response", jsonObject.toString());

                JSONArray brochuresArray = jsonObject.getJSONArray("brochures");

                for (int i = 0; i < brochuresArray.length(); i++) {
                    Log.v("BRAD_", i + "");

                    String name;
                    String telp;
                    String address;
                    String imgUrl;

                    JSONObject jBrochure = (JSONObject) brochuresArray.get(i);
                    jBrochure = jBrochure.getJSONObject("brochures");
                    JSONObject jLocation = jBrochure.getJSONObject("address");

                    name = jBrochure.getString("name");
                    address = jLocation.getString("address");
                    telp = jBrochure.getString("telp");
                    imgUrl = jBrochure.getString("thumb");

                    Brochure brochure = new Brochure();
                    brochure.setName(name);
                    brochure.setTelp(telp);
                    brochure.setAddress(address);
                    brochure.setImgUrl(imgUrl);

                    mBrochureCollection.add(brochure);


                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("HomeActivity", "Error closing stream", e);
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
