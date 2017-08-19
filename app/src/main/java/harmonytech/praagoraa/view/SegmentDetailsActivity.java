package harmonytech.praagoraa.view;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import harmonytech.praagoraa.R;
import harmonytech.praagoraa.controller.domain.Provider;
import harmonytech.praagoraa.controller.fragment.ProviderFragment;
import harmonytech.praagoraa.controller.util.FirebaseHelper;
import harmonytech.praagoraa.controller.util.Utility;

public class SegmentDetailsActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ProgressDialog dialog;
    Query segmentoFirebase;

    ValueEventListener valueEventListener;
    ValueEventListener singleValueEventListener;

    String category, subcategory, title, subtitle;

    ArrayList<Provider> providers;

    ProviderFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment_details);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {
            actionBar.setTitle(Utility.changeActionBarTitle(this, actionBar.getTitle().toString()));
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();

        providers = new ArrayList<>();

        title = getIntent().getStringExtra(Utility.SEGMENT_DETAILS_TITLE);
        subtitle = getIntent().getStringExtra(Utility.SEGMENT_DETAILS_SUBTITLE);
        category = getIntent().getStringExtra(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_CATEGORY);
        subcategory = getIntent().getStringExtra(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_SUBCATEGORY);

        setupUI();

        dialog = ProgressDialog.show(this,"", this.getResources().getString(R.string.loading_providers_pls_wait), true, false);

        loadList();
    }

    public List<Provider> getProvidersList() {
        return providers;
    }

    private void setupUI() {

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null){
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);

            segmentoFirebase = mDatabase
                    .child(category)
                    .child(subcategory)
                    .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        segmentoFirebase.removeEventListener(valueEventListener);
        segmentoFirebase.removeEventListener(singleValueEventListener);
    }

    private void loadList() {

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Provider p;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    p = new Provider();
                    p.setName((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_NAME).getValue());
                    p.setEmail((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_EMAIL).getValue());
                    p.setPhone((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_PHONE).getValue());
                    p.setPostalCode((String) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_CITY).getValue());
                    p.setDescription((String) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_DESCRIPTION).getValue());
                    p.setCpf((String) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_CPF).getValue());
                    p.setBirth((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_BIRTH).getValue());
                    p.setRate((Double) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_RATE).getValue());
                    p.setCategory(subtitle);
                    p.setSubcategory(title);

                    providers.add(p);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SegmentDetailsActivity.this, R.string.error_loading_providers, Toast.LENGTH_LONG).show();
                finish();
            }
        };

        singleValueEventListener = new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                Collections.reverse(providers);

                frag = (ProviderFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
                if(frag == null) {
                    frag = new ProviderFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.providers_fragment_container, frag, "mainFrag");
                    ft.commit();
                }

                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SegmentDetailsActivity.this, R.string.error_loading_providers, Toast.LENGTH_LONG).show();
                finish();
            }
        };

        segmentoFirebase.addValueEventListener(valueEventListener);

        segmentoFirebase.addListenerForSingleValueEvent(singleValueEventListener);
    }
}
