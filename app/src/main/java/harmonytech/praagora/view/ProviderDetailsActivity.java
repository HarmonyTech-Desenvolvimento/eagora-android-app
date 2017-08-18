package harmonytech.praagora.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import harmonytech.praagora.R;
import harmonytech.praagora.controller.domain.Provider;
import harmonytech.praagora.controller.util.Utility;

public class ProviderDetailsActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvPhone, tvCategory, tvSubcategory, tvWpp, tvDescriptionProvider;
    RatingBar rbRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_details);

        Provider provider = (Provider) getIntent().getSerializableExtra(Utility.PROVIDER);

        tvDescriptionProvider = (TextView) findViewById(R.id.descriptionProvider);
        tvDescriptionProvider.setText(provider.getDescription());

        tvCategory = (TextView) findViewById(R.id.detailsCategory);
        tvCategory.setText(provider.getCategory());

        tvSubcategory = (TextView) findViewById(R.id.detailsSpecialty);
        tvSubcategory.setText(provider.getSubcategory());

        tvName = (TextView) findViewById(R.id.detailsName);
        tvName.setText(provider.getName());

        tvEmail = (TextView) findViewById(R.id.detailsEmail);
        tvEmail.setText(provider.getEmail());

        tvPhone = (TextView) findViewById(R.id.detailsPhone);
        tvPhone.setText(provider.getPhone());

        tvWpp = (TextView) findViewById(R.id.detailsWpp);
        tvWpp.setText(provider.getPhone());

        rbRate = (RatingBar) findViewById(R.id.ratingBar);
        rbRate.setRating((float) provider.getRate());

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
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


    public void callProvider(View view) {
        String phone;
        phone = tvPhone.getText().toString();
        Utility.callPhone(this, phone);
    }

    public void chamarWpp(View view) {
        String wpp;
        wpp = removeCharactersPhone();
        Utility.openWhatsApp(this, wpp);
    }

    public String removeCharactersPhone(){
        String phone;
        String newPhone;
        phone = tvPhone.getText().toString();
        newPhone = phone.replaceAll("\\(","").replaceAll("\\)","").replace("-","");
        newPhone = "55"+newPhone;
        StringBuilder sb = new StringBuilder(newPhone);
        sb.deleteCharAt(4);
        newPhone = sb.toString();

        return newPhone;
    }
}
