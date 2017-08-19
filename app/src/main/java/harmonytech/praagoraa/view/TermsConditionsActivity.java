package harmonytech.praagoraa.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import harmonytech.praagoraa.R;
import harmonytech.praagoraa.controller.util.Utility;

public class TermsConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {
            actionBar.setTitle(Utility.changeActionBarTitle(this, actionBar.getTitle().toString()));
        }
    }
}
