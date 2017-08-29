package harmonytech.praagoraa.view;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import harmonytech.praagoraa.R;
import harmonytech.praagoraa.controller.domain.Provider;
import harmonytech.praagoraa.controller.fragment.PublicationFragment;
import harmonytech.praagoraa.controller.util.FirebaseHelper;

public class PublicationsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog dialog;
    Query publication;

    ValueEventListener valueEventListener;
    ValueEventListener singleValueEventListener;

    ArrayList<Provider> publications;

    PublicationFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publications);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        publications = new ArrayList<>();

        setupQuery();

        dialog = ProgressDialog.show(this,"", this.getResources().getString(R.string.loading_publications_pls_wait), true, false);

        loadList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        publication.removeEventListener(valueEventListener);
        publication.removeEventListener(singleValueEventListener);
    }

    private void setupQuery() {
        publication = mDatabase
                .child(FirebaseHelper.FIREBASE_DATABASE_ALL)
                .child(mAuth.getCurrentUser().getUid());
    }

    public void loadList(){

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Provider p;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    p = new Provider();
                    p.setName((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_NAME).getValue());
                    p.setEmail((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_EMAIL).getValue());
                    p.setPhone((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_PHONE).getValue());
                    p.setCity((String) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_CITY).getValue());
                    p.setDescription((String) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_DESCRIPTION).getValue());
                    p.setCpf((String) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_CPF).getValue());
                    p.setBirth((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_BIRTH).getValue());
                    p.setRate((Double) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_RATE).getValue());
                    p.setCategory((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_CATEGORY).getValue());
                    p.setSubcategory((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PROVIDER_SUBCATEGORY).getValue());

                    publications.add(p);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PublicationsActivity.this, R.string.error_loading_providers, Toast.LENGTH_LONG).show();
                finish();
            }
        };

        singleValueEventListener = new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                frag = (PublicationFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
                if(frag == null) {
                    frag = new PublicationFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.providers_fragment_container, frag, "mainFrag");
                    ft.commit();
                }

                Toast.makeText(PublicationsActivity.this, String.valueOf(publications.size()), Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PublicationsActivity.this, R.string.error_loading_providers, Toast.LENGTH_LONG).show();
                finish();
            }
        };

        publication.addValueEventListener(valueEventListener);

        publication.addListenerForSingleValueEvent(singleValueEventListener);
    }

    public List<Provider> getPublicationsList() {
        return publications;
    }
}
