package com.wyyc.zqqworkproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.wyyc.mymvpframe.ui.news.MyMvpFrameActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_myview)
    Button mBtnMyview;
    @BindView(R.id.btn_soller)
    Button mBtnSoller;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);

        mBtnMyview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

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

    @OnClick({R.id.btn_myview, R.id.btn_soller, R.id.btn_view, R.id.btn_dragview, R.id.btn_viewgroup
            , R.id.btn_recycleview, R.id.btn_rxjava, R.id.btn_dagger2, R.id.btn_mvp, R.id.btn_dialog, R.id.btn_bottom_view, R.id.btn_test
            , R.id.btn_colorview,R.id.btn_tableview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_myview:
                startActivity(new Intent(MainActivity.this, MyCoustomViewActivity.class));
                break;
            case R.id.btn_soller:
                startActivity(new Intent(MainActivity.this, ScrollerTestActivity.class));
                break;
            case R.id.btn_view:
                startActivity(new Intent(MainActivity.this, ViewTestActivity.class));
                break;
            case R.id.btn_dragview:
                startActivity(new Intent(MainActivity.this, ViewDragHelperActivity.class));
                break;
            case R.id.btn_viewgroup:
                startActivity(new Intent(MainActivity.this, ViewGroup1Activity.class));
                break;
            case R.id.btn_recycleview:
                startActivity(new Intent(MainActivity.this, FlowLayoutActivity.class));
                break;
            case R.id.btn_rxjava:
                startActivity(new Intent(MainActivity.this, RxjavaDemoActivity.class));
                break;
            case R.id.btn_dagger2:
                startActivity(new Intent(MainActivity.this, DaggerTestActivity.class));
                break;
            case R.id.btn_mvp:
                startActivity(new Intent(MainActivity.this, MyMvpFrameActivity.class));
                break;
            case R.id.btn_dialog:
                startActivity(new Intent(MainActivity.this, DialogTestActivity.class));
                break;
            case R.id.btn_bottom_view:
                startActivity(new Intent(MainActivity.this, BottomSheetActivity.class));
                break;
            case R.id.btn_test:
                startActivity(new Intent(MainActivity.this, TestActivity.class));
                break;
            case R.id.btn_colorview:
                startActivity(new Intent(MainActivity.this, MyViewColorActivity.class));
                break;
            case R.id.btn_tableview:
                startActivity(new Intent(MainActivity.this, TableViewActivity.class));
                break;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
