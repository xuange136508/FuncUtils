package com.my.loopiine.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.my.loopiine.R;
import com.my.loopiine.ui.main.widget.fragment.AVNoteFragment;
import com.my.loopiine.ui.main.widget.fragment.NewFragment;
import com.my.loopiine.ui.main.widget.fragment.ShaoNvFragment;
import com.my.loopiine.ui.main.widget.fragment.VideosNoteFragment;
import com.my.loopiine.ui.main.widget.fragment.XinGanFragment;

//直播流 http://s38.316x.com/video2-mp4-m3u8/rh3314-1/rh3314-1.m3u8
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Fragment mCurrentFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        //设置界面
        changeFragment(new NewFragment());

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("最近更新");
        setSupportActionBar(mToolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.open,  R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //处理侧滑菜单
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_new);


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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_new:
                changeFragment(new NewFragment());
                break;
            case R.id.nav_xingan:
                changeFragment(new XinGanFragment());
                break;
            case R.id.nav_shaonv:
                changeFragment(new ShaoNvFragment());
                break;
            case R.id.nav_notes:
                changeFragment(new AVNoteFragment());
                break;
            case R.id.nav_videos:
                changeFragment(new VideosNoteFragment());
                break;

            case R.id.nav_share:
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setTitle("关于应用");
                builder.setMessage("这是 android.support.v7.app.AlertDialog 中的样式");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", null);
                builder.show();
                break;
            case R.id.nav_personal:
                android.support.v7.app.AlertDialog.Builder builder2 = new android.support.v7.app.AlertDialog.Builder(this);
                builder2.setTitle("关于作者");
                builder2.setMessage("这是 android.support.v7.app.AlertDialog 中的样式");
                builder2.setNegativeButton("取消", null);
                builder2.setPositiveButton("确定", null);
                builder2.show();
                break;
        }
        mToolbar.setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    //切换碎片
    private void changeFragment(Fragment fragment) {
        if (mCurrentFragment == null || !fragment.getClass().getName().equals(mCurrentFragment.getClass().getName())) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragent_content, fragment).commit();
            mCurrentFragment = fragment;
        }
    }
}
