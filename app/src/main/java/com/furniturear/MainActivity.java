package com.furniturear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ArFragment arFragment;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;
    private List<Model> models = new ArrayList<>();
    private Model selectedModel;
    private DrawerLayout drawer;

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);//это сцена, куда суём модельки

        loadModels(); //загружаем все модельки в классы
        selectedModel = models.get(1);
        selectedModel.resetModelRenderable(this); //устанавливаем модельку, которая базово будет отображаться
        selectedModel.setOnTapListener(arFragment);
    }

    public void loadModels(){
        models.add(new Model("bedside_table"));
        models.add(new Model("sofa"));
        models.add(new Model("Bookcase"));
        models.add(new Model("Desk"));
        models.add(new Model("Desk_01"));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.m_btable:
                selectedModel = models.get(0);
                selectedModel.resetModelRenderable(this); //устанавливаем модельку, которая базово будет отображаться
                selectedModel.setOnTapListener(arFragment);
                break;
            case R.id.m_sofa:
                selectedModel = models.get(1);
                selectedModel.resetModelRenderable(this); //устанавливаем модельку, которая базово будет отображаться
                selectedModel.setOnTapListener(arFragment);
                break;
            case R.id.m_bookcase:
                selectedModel = models.get(2);
                selectedModel.resetModelRenderable(this); //устанавливаем модельку, которая базово будет отображаться
                selectedModel.setOnTapListener(arFragment);
                break;
            case R.id.m_desk1:
                selectedModel = models.get(3);
                selectedModel.resetModelRenderable(this); //устанавливаем модельку, которая базово будет отображаться
                selectedModel.setOnTapListener(arFragment);
                break;
            case R.id.m_desk2:
                selectedModel = models.get(4);
                selectedModel.resetModelRenderable(this); //устанавливаем модельку, которая базово будет отображаться
                selectedModel.setOnTapListener(arFragment);
                break;
        }
        return false;
    }
}
