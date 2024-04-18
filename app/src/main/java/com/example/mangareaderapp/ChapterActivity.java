package com.example.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChapterActivity extends AppCompatActivity implements View.OnClickListener {

    final int pageStart = 0;
    int currentPage = pageStart;
    int maxPage;

    boolean searchClicked = false;
    boolean toolbar_visible = false;
    boolean info_visible = false;
    boolean topbar_visible = false;

    ArrayList<String> usePages;
    MangaChapter readingChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter_reading);

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

        //Down below, ive already added onclick listeners for the left and right arrow buttons
        ImageButton leftArrow = (ImageButton) findViewById(R.id.leftArrow);
        leftArrow.setOnClickListener(this);

        ImageButton rightArrow = (ImageButton) findViewById(R.id.rightArrow);
        rightArrow.setOnClickListener(this);

        Button bottomButton = (Button) findViewById(R.id.toggleMenu);
        bottomButton.setOnClickListener(this);


        //This imageview is used to display the selected page from chapter
        ImageView mangaPageHolder = (ImageView) this.findViewById(R.id.mangaPageDisplay);
        mangaPageHolder.setVisibility(View.VISIBLE);


        //This is the id for the textview that is between the buttons, can be used to display page number
        TextView pageNumber = (TextView) this.findViewById(R.id.pageNumber);
        pageNumber.setText((currentPage+1) + "/" + maxPage);

        Group entireBar = (Group) this.findViewById(R.id.entireTop);
        entireBar.setVisibility(View.INVISIBLE);

        chapterReading();

    }

    private void chapterReading() {

        ImageView mangaPageHolder = (ImageView) this.findViewById(R.id.mangaPageDisplay);

        Intent intent = getIntent();
        MangaDex mangadex = new MangaDex();

        readingChapter = (MangaChapter) intent.getSerializableExtra("chapter");

        mangadex.getPagesInfo(readingChapter);

        usePages = new ArrayList<String>();
        usePages.addAll(readingChapter.getPages().keySet());

        mangadex.streamPage(readingChapter, usePages.get(currentPage));

        maxPage = usePages.size();

        flipPage();

    }

    private void flipPage () {
        MangaDex mangadex = new MangaDex();

        ImageView mangaPageHolder = (ImageView) this.findViewById(R.id.mangaPageDisplay);

        byte [] pageBytes = mangadex.getPageBytes(readingChapter, usePages.get(currentPage));
        //System.out.println("cover for " + mangas.get(position).getTitle() + " is " + coverBytes.length + " bytes long");
        //System.out.println("cover bytes: " + String.format("%02X%02X%02X", coverBytes[0], coverBytes[1], coverBytes[2]));
        //System.out.println("Cover details: " + cover);
        Bitmap bitmap = BitmapFactory.decodeByteArray(pageBytes, 0, pageBytes.length);
        mangaPageHolder.setImageBitmap(bitmap);

        changePageText();

    }

    private void changePageText() {
        TextView pageNumber = (TextView) this.findViewById(R.id.pageNumber);
        pageNumber.setText((currentPage+1) + "/" + maxPage);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    if (currentPage >= pageStart && currentPage < maxPage) {
                        currentPage++;
                        flipPage();
                    }

                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    if (currentPage > pageStart && currentPage < maxPage-1) {
                        currentPage--;
                        flipPage();
                    }

                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onClick(View v) {

        //Initialized elements to configure visibility
        Group toolbar = (Group) this.findViewById(R.id.group);

        Group entireBar = (Group) this.findViewById(R.id.entireTop);

        LinearLayout infoMenu = (LinearLayout) this.findViewById(R.id.toggleList);

        SearchView searchBar = (SearchView) this.findViewById(R.id.searchBar);

        TextView pageNumber = (TextView) this.findViewById(R.id.pageNumber);

        //Switch case is used determine what happens when a button is clicked
        switch (v.getId()) {
            case R.id.toggleMenu:
                if (topbar_visible == false) {
                    entireBar.setVisibility(View.VISIBLE);
                    topbar_visible = true;
                    toolbar_visible= true;

                } else if (topbar_visible == true) {
                    entireBar.setVisibility(View.INVISIBLE);
                    topbar_visible = false;
                    toolbar.setVisibility(View.INVISIBLE);
                    toolbar_visible = false;
                    infoMenu.setVisibility(View.INVISIBLE);
                    info_visible = false;

                }
                break;

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

            case R.id.leftArrow:
                if (currentPage > pageStart && currentPage < maxPage) {
                    currentPage--;
                    flipPage();
                }
                break;

            case R.id.rightArrow:
                if (currentPage >= pageStart && currentPage < maxPage-1) {
                    currentPage++;
                    flipPage();
                }
                break;

            case R.id.homeButton:
                finish();
                startActivity(new Intent(ChapterActivity.this, MainActivity.class));
                break;

            case R.id.favourites:
                finish();
                startActivity(new Intent(ChapterActivity.this, FavouritesActivity.class));
                break;

            case R.id.themes:
                finish();
                startActivity(new Intent(ChapterActivity.this, ThemeActivity.class));
                break;

            case R.id.credits:
                finish();
                startActivity(new Intent(ChapterActivity.this, CreditsActivity.class));
                break;

            default:
                break;

        }

    }

}
