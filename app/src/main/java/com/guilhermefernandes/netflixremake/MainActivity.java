package com.guilhermefernandes.netflixremake;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.guilhermefernandes.netflixremake.model.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycle_view_main);

    }

    private static class MovieHolder extends RecyclerView.ViewHolder{


        public MovieHolder(@NonNull View itemView) {  // contruor para pegar componentes costumisaveis
            super(itemView);
           itemViewUrl = itemView.findViewById(R.id.text_view_title)
        }

        private class MainAdapter extends RecyclerView.Adapter<MovieHolder>{

             final List<Movie> movies;

            private MainAdapter(List<Movie> movies) {
                this.movies = movies;
            }


            @NonNull
            @Override
            public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MovieHolder(getLayoutInflater().inflate(R.layout.movie_item, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
                Movie movie = movies.get(position);
            }

            @Override
            public int getItemCount() {
                return 0;
            }
        }

    }
}