package com.example.mangareaderapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

public class CreditsActivity extends AppCompatActivity implements View.OnClickListener {

    boolean searchClicked = false;
    boolean toolbar_visible = true;
    boolean info_visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits_layout);

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

        //Search feature elements
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchBar = (SearchView) this.findViewById(R.id.searchBar);
            searchBar.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchBar.setIconifiedByDefault(false);

    }

    @Override
    public void onClick(View v) {

        //Initialized elements to configure visibility
        Group toolbar = (Group) this.findViewById(R.id.group);

        LinearLayout infoMenu = (LinearLayout) this.findViewById(R.id.toggleList);

        SearchView searchBar = (SearchView) this.findViewById(R.id.searchBar);


        //Switch case is used determine what happens when a button is clicked
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
                startActivity(new Intent(CreditsActivity.this, MainActivity.class));
                break;

            case R.id.favourites:
                finish();
                startActivity(new Intent(CreditsActivity.this, FavouritesActivity.class));
                break;

            case R.id.themes:
                finish();
                startActivity(new Intent(CreditsActivity.this, ThemeActivity.class));
                break;

            default:
                break;

        }

    }

}