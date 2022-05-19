package com.guilhermefernandes.netflixremake.util;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class MovieDetail extends AsyncTask<String, Void, MovieDetail> {


    
    private final WeakReference<Context> context;

    public MovieDetail(WeakReference<Context> context) {
        this.context = context;
    }


    @Override
    protected MovieDetail doInBackground(String... strings) {
        return null;
    }
}
