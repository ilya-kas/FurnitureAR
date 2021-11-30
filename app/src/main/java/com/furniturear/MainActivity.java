package com.furniturear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ArFragment arFragment;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;
    private LinkedList models = new LinkedList();
    private Model selectedModel;
    private DrawerLayout drawer;

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) { //если версия андроида недостаточная
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {//если версия OpenGl недостаточная
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
        setContentView(R.layout.activity_main); //отображение содержимого главной activity

        Toolbar toolbar = findViewById(R.id.toolbar); //toolbar вверху
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout); //получаем ссылку на контейнер с выдвигаемой шторкой
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle); //отлавливание открытия и закрытия
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view); //получаем ссылку на компанент выдвигаемой шторки
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);//сцена, куда добавляем модельки

        loadModels(); //загружаем все модельки в классы
        selectedModel = (Model) models.get(1);
        selectedModel.resetModelRenderable(this); //устанавливаем модельку, которая базово будет отображаться
        selectedModel.setOnTapListener(arFragment);
    }

    //создаёт модели и добавляет их в список
    public void loadModels(){
        models.add(new Model("bedside_table"));
        models.add(new Model("sofa"));
        models.add(new Model("Bookcase"));
        models.add(new Model("Desk"));
        models.add(new Model("Desk_01"));
        models.add(new Model("Sofa_01"));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){ //в зависимости от выбранного пункта меню
            case R.id.m_btable:
                selectedModel = (Model) models.get(0);
                break;
            case R.id.m_sofa:
                selectedModel = (Model) models.get(1);
                break;
            case R.id.m_bookcase:
                selectedModel = (Model) models.get(2);
                break;
            case R.id.m_desk1:
                selectedModel = (Model) models.get(3);
                break;
            case R.id.m_desk2:
                selectedModel = (Model) models.get(4);
                break;
            case R.id.m_sofa2:
                selectedModel = (Model) models.get(5);
                break;
        }
        selectedModel.resetModelRenderable(this); //перезагружаем модельку
        selectedModel.setOnTapListener(arFragment); //устанавливаем модельку, которая базово будет отображаться
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
