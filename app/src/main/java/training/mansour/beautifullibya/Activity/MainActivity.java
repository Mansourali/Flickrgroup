package training.mansour.beautifullibya.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import training.mansour.beautifullibya.Fragments.SecondFragment;
import training.mansour.beautifullibya.Fragments.InitialFragment;
import training.mansour.beautifullibya.Fragments.FlickrGroupPhotos;
import training.mansour.beautifullibya.R;
import training.mansour.beautifullibya.Services.MyService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static int INITIAL_FRAGMENT = 0;
    private final static int SECOND_FRAGMENT = 1;
    private final static int FLICKR_FRAGMENT = 2;
    private static final int RUN_SERVICE_TO_DOWNLOAD_FLICKR_JSON = 100;
    private int CurrentFragment ;
    private JobScheduler jobScheduler;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        jobScheduler = JobScheduler.getInstance(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jobConstruction();
            }
        },3000);*/

        if(savedInstanceState == null){
            swithPage(new InitialFragment());
            CurrentFragment = INITIAL_FRAGMENT;
        }else{
            CurrentFragment = savedInstanceState.getInt("currentFragment");
            switch(CurrentFragment){
                case SECOND_FRAGMENT:
                    swithPage(new SecondFragment());
                    break;
                case FLICKR_FRAGMENT:
                    swithPage(new FlickrGroupPhotos());
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("CurrentFragment", CurrentFragment);
        super.onSaveInstanceState(outState, outPersistentState);
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
        getMenuInflater().inflate(R.menu.main, menu);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            swithPage(new InitialFragment());
            toolbar.setTitle ( item.getTitle () );
        } else if (id == R.id.nav_gallery) {
            swithPage(new SecondFragment());
            toolbar.setTitle ( item.getTitle () );
        } else if (id == R.id.nav_slideshow) {
            swithPage(new FlickrGroupPhotos());
            toolbar.setTitle ( item.getTitle () );
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void swithPage(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();

    }

    private void jobConstruction() {

        JobInfo.Builder builder = new JobInfo.Builder(RUN_SERVICE_TO_DOWNLOAD_FLICKR_JSON,
                new ComponentName(this, MyService.class));
        //TODO make the time interval longer for example 12 hours
        builder.setPeriodic(120000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                //.setBackoffCriteria ( 600000, JobInfo.BACKOFF_POLICY_LINEAR )
                .setPersisted(true);
        jobScheduler.schedule(builder.build());

    }
}
