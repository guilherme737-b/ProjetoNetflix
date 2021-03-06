package com.guilhermefernandes.netflixremake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guilhermefernandes.netflixremake.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtDesc;
    private TextView txtCast;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        txtTitle = findViewById(R.id.text_view_title);
        txtDesc = findViewById(R.id.text_view_desc);
        txtCast = findViewById(R.id.text_view_cast);
        recyclerView = findViewById(R.id.recycler_view_similar);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            getSupportActionBar().setTitle(null);
        }

        LayerDrawable drawable =
                (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.shadows);

        if (drawable != null){
            Drawable movieCover = ContextCompat.getDrawable(this, R.drawable.movie_4);
            drawable.setDrawableByLayerId(R.id.cover_drawble, movieCover);
            ((ImageView) findViewById(R.id.image_cover)).setImageDrawable(drawable);
        }

        txtTitle.setText("Batman Begins");
        txtDesc.setText("O jovem Bruce Wayne cai em um po??o e ?? atacado por morcegos. Bruce acorda de um pesadelo sobre seu passado e descobre que ?? prisioneiro no But??o. ?? apresentado a Henri Ducard, que fala por Ra's Al Ghul, l??der da Liga das Sombras, e o convida para ser treinado pela elite de vigilantes.");
        txtCast.setText(getString(R.string.cast, "Elenco" + "Robert Pattinson" + "Zo?? Kravitz" + "Paul Dano"));

        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            Movie movie = new Movie();
            movies.add(movie);
        }

        recyclerView.setAdapter(new MovieAdapter(movies));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));


    }

    private static class MovieHolder extends RecyclerView.ViewHolder{

        final ImageView imageViewCover;

        public MovieHolder(@NonNull View itemView) {  // contruor para pegar componentes costumisaveis
            super(itemView);
            imageViewCover = itemView.findViewById(R.id.image_view_cover);
        }
    }
    private class MovieAdapter extends RecyclerView.Adapter<MainActivity.MovieHolder>{

        private final List<Movie> movies;

        private MovieAdapter(List<Movie> movies) {
            this.movies = movies;
        }


        @NonNull
        @Override
        public MainActivity.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MainActivity.MovieHolder(getLayoutInflater()
                    .inflate(R.layout.movie_item_similar, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MainActivity.MovieHolder holder, int position) {
            Movie movie = movies.get(position);
            //    holder.imageViewCover.setImageResource(movie.getCoverUrl());
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }

    }
}