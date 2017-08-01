package harmonytech.praagora.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import harmonytech.praagora.R;
import harmonytech.praagora.controller.domain.Segment;
import harmonytech.praagora.controller.fragment.SegmentFragment;
import harmonytech.praagora.controller.util.FontsOverride;
import harmonytech.praagora.controller.util.Utility;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ArrayList<Segment> recipesList = new ArrayList<>();
    SegmentFragment frag;

    Button btnRegisterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set Custom Typeface
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/CircularStd-Book.otf");

        //set Custom Typeface CollapsingToolbar
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/CircularStd-Book.otf");
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);

        btnRegisterService = (Button) findViewById(R.id.btnRegisterService);
        btnRegisterService.setOnClickListener(this);

        setupUI();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(Utility.changeActionBarTitle(this, toolbar.getTitle().toString()));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    private void setupUI() {

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

        Segment assistenciaTecnica = new Segment();
        assistenciaTecnica.setNameFirebase(Utility.SEGMENTO_ASSISTENCIA_TECNICA_FIREBASE);
        assistenciaTecnica.setName(Utility.SEGMENTO_ASSISTENCIA_TECNICA);
        Uri uriAssistenciaTecnica = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_assistencia);
        assistenciaTecnica.setBanner(uriAssistenciaTecnica);
        recipesList.add(assistenciaTecnica);

        Segment aulas = new Segment();
        aulas.setNameFirebase(Utility.SEGMENTO_AULAS_FIREBASE);
        aulas.setName(Utility.SEGMENTO_AULAS);
        Uri uriAulas = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_aulas);
        aulas.setBanner(uriAulas);
        recipesList.add(aulas);

        Segment autos = new Segment();
        autos.setNameFirebase(Utility.SEGMENTO_AUTOS_FIREBASE);
        autos.setName(Utility.SEGMENTO_AUTOS);
        Uri uriAutos = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_servicos_carro);
        autos.setBanner(uriAutos);
        recipesList.add(autos);

        Segment belezaEstetica = new Segment();
        belezaEstetica.setNameFirebase(Utility.SEGMENTO_BELEZA_ESTETICA_FIREBASE);
        belezaEstetica.setName(Utility.SEGMENTO_BELEZA_ESTETICA);
        Uri uriBelezaEstetica = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_beleza_estetica);
        belezaEstetica.setBanner(uriBelezaEstetica);
        recipesList.add(belezaEstetica);

        Segment construcao = new Segment();
        construcao.setNameFirebase(Utility.SEGMENTO_CONSTRUCAO_FIREBASE);
        construcao.setName(Utility.SEGMENTO_CONSTRUCAO);
        Uri uriConstrucao = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_reforma);
        construcao.setBanner(uriConstrucao);
        recipesList.add(construcao);

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

        Segment servicosDomesticos = new Segment();
        servicosDomesticos.setNameFirebase(Utility.SEGMENTO_SERVICOS_DOMESTICOS_FIREBASE);
        servicosDomesticos.setName(Utility.SEGMENTO_SERVICOS_DOMESTICOS);
        Uri uriServicoDomestico = Uri.parse(Utility.URI_PACKAGE + R.drawable.eagora_image_servicos_domesticos);
        servicosDomesticos.setBanner(uriServicoDomestico);
        recipesList.add(servicosDomesticos);

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
}