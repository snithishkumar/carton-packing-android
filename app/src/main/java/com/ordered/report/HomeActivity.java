package com.ordered.report;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ordered.report.fragment.AddProductFragment;
import com.ordered.report.fragment.HomeFragment;
import com.ordered.report.fragment.ProductListFragment;
import com.ordered.report.fragment.SubitemsFragment;

public class HomeActivity extends AppCompatActivity{

    private static final String TAG = HomeActivity.class.getSimpleName();

    private FragmentManager fragmentManager;
    private Fragment fragment = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new HomeFragment();
        fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showProductList(){
        ProductListFragment productListFragment = new ProductListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper,productListFragment).addToBackStack(null).commit();
    }
    public void showSubProductList(){
        SubitemsFragment subitemsFragment = new SubitemsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper,subitemsFragment).addToBackStack(null).commit();
    }
    public void showAddProductList(){
        AddProductFragment addProductFragment = new AddProductFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper,addProductFragment).addToBackStack(null).commit();
    }

    public void backClicked(View view){
        int size = getSupportFragmentManager().getBackStackEntryCount();
        if(size > 0){
            getSupportFragmentManager().popBackStack();
        }else{
            finish();
        }
    }

}
