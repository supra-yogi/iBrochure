package com.group.ibrochure.i_brochure.Infrastructure;

import android.os.AsyncTask;

import com.group.ibrochure.i_brochure.Common.EntityBase;
import com.group.ibrochure.i_brochure.Common.IRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Yogi on 02/11/2017.
 */

public abstract class BaseAPI<T extends EntityBase> extends AsyncTask<Void,Void,Void> implements IRepository<T> {
    private String uri;
    private HttpsURLConnection con = null;
    protected void API(String destUri) {
         uri = Uri.getUri(destUri);
    }

    public BaseAPI() {
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    public T GetById(int id) {
        return null;
    }

    @Override
    public List<T> GetAll() {
        return null;
    }

    @Override
    public int GetCount() {
        return 0;
    }

    @Override
    public void Save(T entity) {

    }

    @Override
    public void Delete(int id) {

    }
}
