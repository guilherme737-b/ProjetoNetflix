package com.guilhermefernandes.netflixremake.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.guilhermefernandes.netflixremake.model.Category;
import com.guilhermefernandes.netflixremake.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class JsonDownloadTask extends AsyncTask <String, Void, List<Category>> {

    private final WeakReference<Context> context;
    // para mostrar a requisição para o json vamos usar o ProgressDialog
    private ProgressDialog dialog;
    private CategoryLoader categoryLoader;

    public JsonDownloadTask(Context context){
        this.context = new WeakReference<> (context);
    }

    public void setCategoryLoader(CategoryLoader categoryLoader){
            this.categoryLoader = categoryLoader;
    }



    // vamos precisar destes 3 metodos, um execute, um pos execute (esta no terceiro metodo) e o doinBackgrount que vai acontecer em outra Thread


    // main - thread
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = this.context.get();
        if (context != null)
        dialog = ProgressDialog.show(context, "Carregando", "", true); // true para não ficar a progress bar rodando por tempo indeterminado
    }

    // thread - background
    @Override
    protected List<Category> doInBackground(String... params) { // implementamos para rodar na thread nova, e não na principal
        String ulr = params[0];

        try {
            URL requestUrl = new URL(ulr);

            HttpsURLConnection urlConnection = (HttpsURLConnection) requestUrl.openConnection();
            urlConnection.setReadTimeout(2000); //
            urlConnection.setConnectTimeout(2000);

            int responseCode = urlConnection.getResponseCode();
            if (responseCode > 400) {
                throw new IOException("Error na comunicação do servidor");
            }

            InputStream inputStream = urlConnection.getInputStream(); //InputStream faz parte da leitura de dados, ou seja, está conectado a alguma fonte de dados: arquivo, conexão de internet, vídeo, imagem e etc. O InputStream nos possibilita ler esse Stream em byte, um byte por vez ou seja precisamos converter ela para algo que conseguimos ler(entender), neste caso vamos colocar em um Array para nos retornar em formato de  String

            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());

            String jsonAsString = toString(in);

            List<Category> categories = getCategories(new JSONObject(jsonAsString));
            in.close();

            return categories;

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Category> getCategories(JSONObject jsonObject) throws JSONException {
        List<Category> categories = new ArrayList<>();


        JSONArray categoryArray = jsonObject.getJSONArray("category");
        for (int i = 0; i < categoryArray.length(); i++) {
            JSONObject category = categoryArray.getJSONObject(i);
            String title = category.getString("title");

            List<Movie> movies = new ArrayList<>();
            JSONArray movieArray = category.getJSONArray("movie");
            for (int j = 0; j < movieArray.length() ; j++) {
                JSONObject movie = movieArray.getJSONObject(j);

                String coverUrl = movie.getString("cover_url");

                Movie movieObj = new Movie();
                movieObj.setCoverUrl(coverUrl);

                movies.add(movieObj);
            }

            Category categoryObj = new Category();
            categoryObj.setName(title);
            categoryObj.setMovies(movies);

            categories.add(categoryObj);

        }

        return categories;

    }

    // main - thread
    @Override
    protected void onPostExecute(List<Category> categories) {
        super.onPostExecute(categories);
        dialog.dismiss();
        // listener

        if (categoryLoader != null)
            categoryLoader.onResult(categories);


    }

    private String toString(InputStream is) throws IOException{
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int lidos;
        while ((lidos = is.read(bytes)) > 0){
            baos.write(bytes, 0, lidos);
        }

        return new String(baos.toByteArray());
    }

    public interface CategoryLoader {
            void onResult (List<Category> categories);
    }


}

