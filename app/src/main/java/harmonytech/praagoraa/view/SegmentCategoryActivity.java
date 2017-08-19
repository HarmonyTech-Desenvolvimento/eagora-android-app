package harmonytech.praagoraa.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import harmonytech.praagoraa.R;
import harmonytech.praagoraa.controller.util.FirebaseHelper;
import harmonytech.praagoraa.controller.util.Singleton;
import harmonytech.praagoraa.controller.util.Utility;

public class SegmentCategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    HashMap<String, HashMap<String, ArrayList<String>>> subcategorias;
    String segmento, segmentoFirebase;
    HashMap<String, ArrayList<String>> arraySegmento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment_category);

        segmento = getIntent().getStringExtra(Utility.SEGMENTO);

        segmentoFirebase = getIntent().getStringExtra(Utility.SEGMENTO_FIREBASE);

        subcategorias = Singleton.getInstance().getSegmentos();

        arraySegmento = subcategorias.get(segmento);

        setupUI();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if(Utility.isConnected(this)){
            Intent intent = new Intent(this, SegmentDetailsActivity.class);
            intent.putExtra(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_CATEGORY, segmentoFirebase);
            intent.putExtra(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_SUBCATEGORY, arraySegmento.get(Utility.HASH_MAP_FIREBASE).get(i));
            intent.putExtra(Utility.SEGMENT_DETAILS_TITLE, arraySegmento.get(Utility.HASH_MAP_TELA).get(i));
            intent.putExtra(Utility.SEGMENT_DETAILS_SUBTITLE, segmento);
            startActivity(intent);
        }else{
            Utility.showSnackBarErrorMessage(this, getResources().getString(R.string.no_internet_connection));
        }
    }

    private void setupUI() {

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null){
            actionBar.setTitle(segmento);
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.custom_list_view, arraySegmento.get(Utility.HASH_MAP_TELA));

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }
}
