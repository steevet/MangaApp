package com.example.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Serializable  {

    boolean searchClicked = false;
    boolean toolbar_visible = true;
    boolean info_visible = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        handleIntent(getIntent());

        //Buttons at top of screen
        Button toggleToolbar = (Button) this.findViewById(R.id.toggleButton);
        toggleToolbar.setOnClickListener(this);

        ImageButton homeButton = (ImageButton) this.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);

        ImageButton toggleInfo = (ImageButton) this.findViewById(R.id.infoButton);
        toggleInfo.setOnClickListener(this);

        ImageButton searchButton = (ImageButton) this.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        //Buttons in the toggleable menu

        Button favouritesList= (Button) findViewById(R.id.favourites);
        favouritesList.setOnClickListener(this);

        Button themes= (Button) findViewById(R.id.themes);
        themes.setOnClickListener(this);

        Button credits = (Button) findViewById(R.id.credits);
        credits.setOnClickListener(this);

        //Search feature elements
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchBar = (SearchView) this.findViewById(R.id.searchBar);
        searchBar.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchBar.setIconifiedByDefault(false);

        doSearch();

    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch();
        }
    }

    private void doSearch() {
        ListView searchDisplay = (ListView) findViewById(R.id.searchList);

        //Searches for the required manga in the database from MangaDex class

        MangaDex mangadex = new MangaDex();

        String tagId = mangadex.getTags().get("Romance");
        ArrayList<Manga> mangas = (ArrayList<Manga>) mangadex.searchByTag(tagId);
        mangadex.getCoverInfo(mangas);
        for (Manga manga : mangas) {
            // Just load the first available cover for now.
            MangaCover cover = manga.getCovers().get(0);
            // We don't do anything with the resulting bytes here, but the cover object
            // will have them ready so that MangaAdapter can use it as the results list
            // is populated.
            mangadex.getCoverBytes(cover, 256);
        }

        MangaAdapter mangaAdapter = new MangaAdapter(this, R.layout.custom_list_view_items, mangas);
        searchDisplay.setAdapter(mangaAdapter);

        searchDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("manga", mangas.get(position));
                startActivity(i);

            }
        });

    }

    @Override
    public void onClick(View v) {

        //Initialized elements to configure visibility
        Group toolbar = (Group) this.findViewById(R.id.group);

        LinearLayout infoMenu = (LinearLayout) this.findViewById(R.id.toggleList);

        SearchView searchBar = (SearchView) this.findViewById(R.id.searchBar);

        switch (v.getId()) {
            case R.id.toggleButton:
                if (toolbar_visible == false) {
                    toolbar.setVisibility(View.VISIBLE);
                    toolbar_visible = true;

                } else if (toolbar_visible == true) {
                    toolbar.setVisibility(View.INVISIBLE);
                    toolbar_visible = false;
                    infoMenu.setVisibility(View.INVISIBLE);
                    info_visible = false;

                }
                break;

            case R.id.infoButton:
                if (info_visible == false) {
                    infoMenu.setVisibility(View.VISIBLE);
                    info_visible = true;

                } else if (toolbar_visible == true) {
                    infoMenu.setVisibility(View.INVISIBLE);
                    info_visible = false;

                }
                break;

            case R.id.searchButton:
                if (searchClicked == false) {
                    searchBar.setVisibility(View.INVISIBLE);
                    searchClicked = true;

                } else if (searchClicked == true) {
                    searchBar.setVisibility(View.VISIBLE);
                    searchClicked = false;

                }
                break;

            case R.id.homeButton:
                finish();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                break;

            case R.id.favourites:
                startActivity(new Intent(MainActivity.this, FavouritesActivity.class));
                break;

            case R.id.themes:
                startActivity(new Intent(MainActivity.this, ThemeActivity.class));
                break;

            case R.id.credits:
                startActivity(new Intent(MainActivity.this, CreditsActivity.class));
                break;

            default:
                break;

        }

    }
}
