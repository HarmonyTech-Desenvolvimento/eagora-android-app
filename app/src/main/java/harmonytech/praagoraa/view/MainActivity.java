package harmonytech.praagoraa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.VideoView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import harmonytech.praagoraa.R;
import harmonytech.praagoraa.controller.domain.Segment;
import harmonytech.praagoraa.controller.fragment.SegmentFragment;
import harmonytech.praagoraa.controller.util.FirebaseHelper;
import harmonytech.praagoraa.controller.util.FontsOverride;
import harmonytech.praagoraa.controller.util.Utility;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    ArrayList<Segment> recipesList = new ArrayList<>();
    SegmentFragment frag;

    Button btnRegisterService;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    Handler handler;
    Runnable myRunnable;

    String database;

    NavigationView navigationView;

    SharedPreferences sharedPreferences;

    public Spinner spinnerState, spinnerCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        verifyUserIsLogged();

        setContentView(R.layout.activity_main);

        database = getIntent().getStringExtra(Utility.KEY_CONTENT_EXTRA_DATABASE);

        //set Custom Typeface
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/CircularStd-Book.otf");

        //set Custom Typeface CollapsingToolbar
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/CircularStd-Book.otf");
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);

        btnRegisterService = (Button) findViewById(R.id.btnRegisterService);
        btnRegisterService.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(Utility.changeActionBarTitle(this, toolbar.getTitle().toString()));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        spinnerState = (Spinner)findViewById(R.id.main_sp_state);
        spinnerState.setOnItemSelectedListener(this);
        spinnerCity = (Spinner) findViewById(R.id.main_sp_city);
        spinnerCity.setOnItemSelectedListener(this);
        spinnerCity.setEnabled(false);

        fillState();
        fillCity();

        setupUI();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();
        String state = spinnerState.getSelectedItem().toString();

        switch (id){
            case R.id.main_sp_state:
                switch (state) {
                    case "Mato Grosso do Sul": {
                        spinnerCity.setEnabled(true);
                        ArrayAdapter<String> spinnerCountShoesArrayAdapterState = new ArrayAdapter<>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ms));
                        spinnerCity.setAdapter(spinnerCountShoesArrayAdapterState);
                        break;
                    }
                    case "São Paulo": {
                        spinnerCity.setEnabled(true);
                        ArrayAdapter<String> spinnerCountShoesArrayAdapterState = new ArrayAdapter<>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.sp));
                        spinnerCity.setAdapter(spinnerCountShoesArrayAdapterState);
                        break;
                    }
                    default:
                        spinnerCity.setSelection(0);
                        spinnerCity.setEnabled(false);
                        break;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //TODO
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler != null) {
            handler.removeCallbacks(myRunnable);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_categorias) {

        } else if (id == R.id.nav_cadastrar) {
            startActivity(new Intent(this, RegisterServiceActivity.class));

        } else if (id == R.id.nav_publicacoes) {
            startActivity(new Intent(this, RegisterServiceActivity.class));

        } else if (id == R.id.nav_termos_uso) {
            startActivity(new Intent(this, TermsConditionsActivity.class));

        } else if (id == R.id.nav_duvidas) {
            startActivity(new Intent(this, DoubtActivity.class));

        } else if (id == R.id.nav_contato) {
            startActivity(new Intent(this, ContactUsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void signOut(View view){
        LoginManager.getInstance().logOut();
        mAuth.signOut();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        finish();
    }

    public void verifyUserIsLogged(){

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // User is signed out
                    startActivity(new Intent(MainActivity.this, UsersCategoryActivity.class));
                    finish();
                }/*else{
                    sharedPreferences = getSharedPreferences(Utility.FAVORITE_SHARED_PREF_NAME, MODE_PRIVATE);
                    String result = sharedPreferences.getString(Utility.FAVORITE_FIRST_TIME,"");
                    if(result.equals("")){
                        insertFirstExecFavorites();
                    }
                }*/
            }
        };
    }

    private void setupUI() {

        sharedPreferences = getSharedPreferences(Utility.LOGIN_SHARED_PREF_NAME, MODE_PRIVATE);
        String result = sharedPreferences.getString("database", "");

        if(database!= null && database.equals(FirebaseHelper.FIREBASE_DATABASE_USERS)){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_cadastrar).setVisible(false);
            btnRegisterService.setVisibility(View.GONE);
        }else{
            if(result.equals(FirebaseHelper.FIREBASE_DATABASE_USERS)){
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_cadastrar).setVisible(false);
                btnRegisterService.setVisibility(View.GONE);
            }
        }

        if(database!= null && database.equals(FirebaseHelper.FIREBASE_DATABASE_USERS)){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_publicacoes).setVisible(false);
            btnRegisterService.setVisibility(View.GONE);
        }else{
            if(result.equals(FirebaseHelper.FIREBASE_DATABASE_USERS)){
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_publicacoes).setVisible(false);
                btnRegisterService.setVisibility(View.GONE);
            }
        }

        final VideoView videoview = (VideoView) findViewById(R.id.video);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.eagora_clip);
        videoview.setVideoURI(uri);
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoview.start();
                mp.setLooping(true);
            }
        });

        Segment servicosDomesticos = new Segment();
        servicosDomesticos.setNameFirebase(Utility.SEGMENTO_SERVICOS_DOMESTICOS_FIREBASE);
        servicosDomesticos.setName(Utility.SEGMENTO_SERVICOS_DOMESTICOS);
        Uri uriServicoDomestico = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_servicos_domesticos);
        servicosDomesticos.setBanner(uriServicoDomestico);
        recipesList.add(servicosDomesticos);

        Segment construcao = new Segment();
        construcao.setNameFirebase(Utility.SEGMENTO_CONSTRUCAO_FIREBASE);
        construcao.setName(Utility.SEGMENTO_CONSTRUCAO);
        Uri uriConstrucao = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_reforma);
        construcao.setBanner(uriConstrucao);
        recipesList.add(construcao);

        Segment assistenciaTecnica = new Segment();
        assistenciaTecnica.setNameFirebase(Utility.SEGMENTO_ASSISTENCIA_TECNICA_FIREBASE);
        assistenciaTecnica.setName(Utility.SEGMENTO_ASSISTENCIA_TECNICA);
        Uri uriAssistenciaTecnica = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_assistencia);
        assistenciaTecnica.setBanner(uriAssistenciaTecnica);
        recipesList.add(assistenciaTecnica);

        Segment belezaEstetica = new Segment();
        belezaEstetica.setNameFirebase(Utility.SEGMENTO_BELEZA_ESTETICA_FIREBASE);
        belezaEstetica.setName(Utility.SEGMENTO_BELEZA_ESTETICA);
        Uri uriBelezaEstetica = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_beleza_estetica);
        belezaEstetica.setBanner(uriBelezaEstetica);
        recipesList.add(belezaEstetica);

        Segment autos = new Segment();
        autos.setNameFirebase(Utility.SEGMENTO_AUTOS_FIREBASE);
        autos.setName(Utility.SEGMENTO_AUTOS);
        Uri uriAutos = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_servicos_carro);
        autos.setBanner(uriAutos);
        recipesList.add(autos);

        Segment aulas = new Segment();
        aulas.setNameFirebase(Utility.SEGMENTO_AULAS_FIREBASE);
        aulas.setName(Utility.SEGMENTO_AULAS);
        Uri uriAulas = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_aulas);
        aulas.setBanner(uriAulas);
        recipesList.add(aulas);

        Segment animal = new Segment();
        animal.setNameFirebase(Utility.SEGMENTO_ANIMAIS_FIREBASE);
        animal.setName(Utility.SEGMENTO_ANIMAIS);
        Uri uriAnimal = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_animais);
        animal.setBanner(uriAnimal);
        recipesList.add(animal);

        Segment arteCultura = new Segment();
        arteCultura.setNameFirebase(Utility.SEGMENTO_ARTE_CULTURA_FIREBASE);
        arteCultura.setName(Utility.SEGMENTO_ARTE_CULTURA);
        Uri uriArteCultura = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_arte_cultura);
        arteCultura.setBanner(uriArteCultura);
        recipesList.add(arteCultura);

        Segment consultoria = new Segment();
        consultoria.setNameFirebase(Utility.SEGMENTO_CONSULTORIA_FIREBASE);
        consultoria.setName(Utility.SEGMENTO_CONSULTORIA);
        Uri uriConsultoria = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_consultoria);
        consultoria.setBanner(uriConsultoria);
        recipesList.add(consultoria);

        Segment design = new Segment();
        design.setNameFirebase(Utility.SEGMENTO_DESIGN_FIREBASE);
        design.setName(Utility.SEGMENTO_DESIGN);
        Uri uriDesign = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_design);
        design.setBanner(uriDesign);
        recipesList.add(design);

        Segment eventos = new Segment();
        eventos.setNameFirebase(Utility.SEGMENTO_EVENTOS_FIREBASE);
        eventos.setName(Utility.SEGMENTO_EVENTOS);
        Uri uriEventos = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_eventos);
        eventos.setBanner(uriEventos);
        recipesList.add(eventos);

        Segment saude = new Segment();
        saude.setNameFirebase(Utility.SEGMENTO_SAUDE_FIREBASE);
        saude.setName(Utility.SEGMENTO_SAUDE);
        Uri uriSaude = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_saude);
        saude.setBanner(uriSaude);
        recipesList.add(saude);

        frag = (SegmentFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(frag == null) {
            frag = new SegmentFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.segments_fragment_container, frag, "mainFrag");
            ft.commit();
        }
    }

    public List<Segment> getRecipesList() {
        return recipesList;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.btnRegisterService:
                startActivity(new Intent(this, RegisterServiceActivity.class));
                break;
        }
    }

    public void fillState(){
        ArrayAdapter<String> spinnerCountShoesArrayAdapterState = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.state));
        spinnerState.setAdapter(spinnerCountShoesArrayAdapterState);
    }

    public void fillCity(){
        ArrayAdapter<String> spinnerCountShoesArrayAdapterState = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.sp));
        spinnerCity.setAdapter(spinnerCountShoesArrayAdapterState);
    }
}
