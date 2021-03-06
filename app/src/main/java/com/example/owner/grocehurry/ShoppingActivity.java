package com.example.owner.grocehurry;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class ShoppingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BarCodeScannerFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (findViewById(R.id.frame_layout) != null) {
            if (savedInstanceState != null) {
                return;
            }

            BarCodeScannerFragment barCodeFrag = new BarCodeScannerFragment();
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.frame_layout,barCodeFrag)
                                       .commit();
        }
    }

    public void selectDrawItem(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = BarCodeScannerFragment.class;

        switch(item.getItemId()) {
            case R.id.nav_bar_code_scanner:
                fragmentClass = BarCodeScannerFragment.class;
                break;
            case R.id.nav_shopping_list:
                fragmentClass = ShoppingListFragment.class;
                break;
            default: break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch(Exception e) {
            e.printStackTrace();
        }

        getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment);

        item.setChecked(true);
        setTitle(item.getTitle());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shopping, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bar_code_scanner) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new BarCodeScannerFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_shopping_list) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        selectDrawItem(item);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
