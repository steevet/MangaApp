package com.example.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class ThemeActivity extends AppCompatActivity implements View.OnClickListener {

    boolean searchClicked = false;
    boolean toolbar_visible = true;
    boolean info_visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_layout);

        //Buttons at top of screen
        Button toggleToolbar= (Button) this.findViewById(R.id.toggleButton);
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

        Button credits= (Button) findViewById(R.id.credits);
        credits.setOnClickListener(this);

        //Search feature elements
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchBar = (SearchView) this.findViewById(R.id.searchBar);
        searchBar.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchBar.setIconifiedByDefault(false);

        themeList();

    }

    private void themeList() {

        ListView listedThemes = (ListView) findViewById(R.id.themes);
        ArrayList<String> availableThemes = new ArrayList<>();
        availableThemes.add("Dark Theme");
        availableThemes.add("Ocean blue Theme");
        availableThemes.add("Hot Theme");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, availableThemes);
        listedThemes.setAdapter(adapter);

        listedThemes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            TextView tv;
            TextView cred;
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String themeName = adapter.getItem(position).toString();
                //setContentView(R.layout.theme_layout);
                //Typeface type = Typeface.createFromAsset(getAssets(),"fonts/lobster_regular");

                //tv = (TextView) findViewById(R.id.themes);
                //cred = (TextView) findViewById(R.id.creditTitle);



                switch (themeName) {
                    case ("Dark Theme"):
                        //tv.setTextColor(Color.BLUE);
                        //cred.setTypeface(type);
                        //findViewById(R.id.toggleButton).setBackgroundColor(Color.DKGRAY);
                        findViewById(R.id.homeButton).setBackgroundColor(Color.BLACK);
                        findViewById(R.id.searchBar).setBackgroundColor(Color.DKGRAY);
                        findViewById(R.id.textView).setBackgroundColor(Color.BLACK);
                        findViewById(R.id.credits).setBackgroundColor(Color.BLACK);
                        findViewById(R.id.favourites).setBackgroundColor(Color.BLACK);
                        //findViewById(R.id.themes).setBackgroundColor(Color.BLACK);
                        //findViewById(R.id.leftArrow).setBackgroundColor(Color.DKGRAY);
                        //findViewById(R.id.rightArrow).setBackgroundColor(Color.DKGRAY);
                        //findViewById(R.id.bottomBar).setBackgroundColor(Color.BLACK);
                        //view.setBackgroundResource(R.color.teal_200);
                        //startActivity(new Intent(MainActivity.this, ));

                        break;

                    case ("Ocean blue Theme"):
                        findViewById(R.id.toggleButton).setBackgroundColor(Color.CYAN);
                        findViewById(R.id.searchBar).setBackgroundColor(Color.CYAN);
                        findViewById(R.id.textView).setBackgroundColor(Color.BLUE);
                        findViewById(R.id.credits).setBackgroundColor(Color.BLUE);
                        findViewById(R.id.favourites).setBackgroundColor(Color.BLUE);
                        findViewById(R.id.themes).setBackgroundColor(Color.BLUE);
                        //findViewById(R.id.leftArrow).setBackgroundColor(Color.CYAN);
                        //findViewById(R.id.rightArrow).setBackgroundColor(Color.CYAN);
                        //findViewById(R.id.bottomBar).setBackgroundColor(Color.BLUE);
                        break;

                    case ("Hot Theme"):
                        findViewById(R.id.toggleButton).setBackgroundColor(Color.YELLOW);
                        findViewById(R.id.searchBar).setBackgroundColor(Color.YELLOW);
                        findViewById(R.id.textView).setBackgroundColor(Color.RED);
                        findViewById(R.id.credits).setBackgroundColor(Color.RED);
                        findViewById(R.id.favourites).setBackgroundColor(Color.RED);
                        findViewById(R.id.themes).setBackgroundColor(Color.RED);
                        //findViewById(R.id.leftArrow).setBackgroundColor(Color.YELLOW);
                        //findViewById(R.id.rightArrow).setBackgroundColor(R.color.teal_200);
                        //findViewById(R.id.bottomBar).setBackgroundColor(Color.RED);
                        //view.setBackgroundResource(R.color.teal_200);
                        break;

                    default:
                        break;
                }

            }

        });

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
                startActivity(new Intent(ThemeActivity.this, MainActivity.class));
                break;

            case R.id.favourites:
                finish();
                startActivity(new Intent(ThemeActivity.this, FavouritesActivity.class));
                break;

            case R.id.credits:
                finish();
                startActivity(new Intent(ThemeActivity.this, CreditsActivity.class));
                break;

            default:
                break;

        }

    }

}
