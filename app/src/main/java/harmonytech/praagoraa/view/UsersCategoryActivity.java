package harmonytech.praagoraa.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import harmonytech.praagoraa.R;
import harmonytech.praagoraa.controller.util.FirebaseHelper;
import harmonytech.praagoraa.controller.util.Utility;

public class UsersCategoryActivity extends AppCompatActivity implements View.OnClickListener{

    String database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_users_category);

        Button btClient = (Button) findViewById(R.id.category_client);
        btClient.setOnClickListener(this);
        Button btProvider = (Button) findViewById(R.id.category_provider);
        btProvider.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        Intent intent = new Intent(this, SignWithActivity.class);

        switch (id){
            case R.id.category_client:
                database = FirebaseHelper.FIREBASE_DATABASE_USERS;
                intent.putExtra(Utility.KEY_CONTENT_EXTRA_DATABASE, database);
                startActivity(intent);
                finish();
                break;
            case R.id.category_provider:
                database = FirebaseHelper.FIREBASE_DATABASE_PROVIDERS;
                intent.putExtra(Utility.KEY_CONTENT_EXTRA_DATABASE, database);
                startActivity(intent);
                finish();
                break;
        }
    }
}
