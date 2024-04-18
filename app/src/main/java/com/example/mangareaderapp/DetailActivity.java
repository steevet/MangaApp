package com.example.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    boolean searchClicked = false;
    boolean toolbar_visible = true;
    boolean info_visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manga_detail);

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

        chapterSelection();

    }

    private void chapterSelection() {

        Intent intent = getIntent();
        MangaDex mangadex = new MangaDex();

        Manga manga = (Manga) intent.getSerializableExtra("manga");

        TextView mangaNameBox = (TextView) findViewById(R.id.mangaName);
        mangaNameBox.setText(manga.getTitle());

        TextView mangaAuthor = (TextView) findViewById(R.id.mangaAuthor);
        mangaAuthor.setText("Author: " + manga.getAuthor());

        TextView mangaDate = (TextView) findViewById(R.id.mangaDate);
        mangaDate.setText("Created on: " + manga.getDate());

        TextView mangaDescription = (TextView) findViewById(R.id.mangaDescription);
        String shortenedDescription = manga.getDescription();
        int patternIndex = shortenedDescription.indexOf("\n");
        if (patternIndex != -1) {
            shortenedDescription = shortenedDescription.substring(0, patternIndex);
        }
        mangaDescription.setText(shortenedDescription);

        ImageView imageView = (ImageView) findViewById(R.id.mangaCover);
        MangaCover cover = manga.getCovers().get(0);
        byte [] coverBytes = cover.getCoverBytes(256);
        Bitmap bitmap = BitmapFactory.decodeByteArray(coverBytes, 0, coverBytes.length);
        imageView.setImageBitmap(bitmap);

        ListView chapterList = (ListView) findViewById(R.id.chapterList);

        mangadex.getChapterInfo(manga);
        ArrayList<MangaChapter> chapters = (ArrayList<MangaChapter>) manga.getChapters();

        MangaChapterAdapter adapter = new MangaChapterAdapter(this,
                android.R.layout.simple_list_item_1, chapters);
        chapterList.setAdapter(adapter);

        chapterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MangaChapter chapter = adapter.getItem(position);
                if (chapter.getExternalUrl() != null) {
                    return;
                }
                Intent i = new Intent(DetailActivity.this, ChapterActivity.class);
                i.putExtra("chapter", chapter);
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
                startActivity(new Intent(DetailActivity.this, MainActivity.class));
                break;

            case R.id.favourites:
                startActivity(new Intent(DetailActivity.this, FavouritesActivity.class));
                break;

            case R.id.themes:
                startActivity(new Intent(DetailActivity.this, ThemeActivity.class));
                break;

            case R.id.credits:
                startActivity(new Intent(DetailActivity.this, CreditsActivity.class));
                break;

            default:
                break;

        }

    }

}
