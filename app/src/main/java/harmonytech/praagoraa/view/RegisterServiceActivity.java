package harmonytech.praagoraa.view;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import harmonytech.praagoraa.R;
import harmonytech.praagoraa.controller.domain.ProviderFirebase;
import harmonytech.praagoraa.controller.util.Singleton;
import harmonytech.praagoraa.controller.util.Utility;

public class RegisterServiceActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    Button btnCadastrar;
    TextInputLayout etNome, etEmail, etNascimento, etCPF, etTelefone;
    EditText etDescription;
    Spinner spCategoria, spEspecialidade, spinnerState, spinnerCity;

    private FirebaseAuth mAuth;

    DatabaseReference mDatabase;

    TextWatcher cpfMask, phoneMask, birthMask;

    ArrayAdapter<String> adapterSubCategorias;

    HashMap<String, HashMap<String, ArrayList<String>>> subareas;
    HashMap<String, String> segmentosFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_service);

        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {
            actionBar.setTitle(Utility.changeActionBarTitle(this, actionBar.getTitle().toString()));
        }

        ArrayList<String> areas = Singleton.getInstance().getAreas();
        subareas = Singleton.getInstance().getSegmentos();
        segmentosFirebase = Singleton.getInstance().getSegmentosFirebase();

        etNome = (TextInputLayout) findViewById(R.id.etNome);
        etEmail = (TextInputLayout) findViewById(R.id.etEmail);
        etNascimento = (TextInputLayout) findViewById(R.id.etNascimento);
        etCPF = (TextInputLayout) findViewById(R.id.etCpf);
        etTelefone = (TextInputLayout) findViewById(R.id.etTelefone);
        etDescription = (EditText) findViewById(R.id.description);

        spCategoria = (Spinner) findViewById(R.id.spCategorias);
        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, areas);
        spCategoria.setAdapter(adapterCategorias); // this will set list of values to spinner
        spCategoria.setOnItemSelectedListener(this);

        spEspecialidade = (Spinner) findViewById(R.id.spSubCategoria);

        setupFieldMasks();

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(this);

        spinnerState = (Spinner)findViewById(R.id.sp_state);
        spinnerState.setOnItemSelectedListener(this);
        spinnerCity = (Spinner) findViewById(R.id.sp_city);
        spinnerCity.setOnItemSelectedListener(this);
        spinnerCity.setEnabled(false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        fillCity();
        fillState();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.btnCadastrar:
                attemptLogin();
            break;
        }
    }

    public void attemptLogin(){
        String uid, name, email, birth, city, cpf, phone, description;
        double rate;

        boolean allFieldsFilled = true;
        boolean allFilledRight = true;
        boolean allFilledCorrectly = true;

        uid = mAuth.getCurrentUser().getUid();
        name = etNome.getEditText().getText().toString();
        email = etEmail.getEditText().getText().toString();
        birth = etNascimento.getEditText().getText().toString();
        city = spinnerCity.getSelectedItem().toString();
        cpf = etCPF.getEditText().getText().toString();
        phone = etTelefone.getEditText().getText().toString();
        description = etDescription.getText().toString();
        rate = 0.01;

        if(description.equals("")){
            allFieldsFilled = false;
            Toast.makeText(this, "Campo Descrição Profissional é obrigatório", Toast.LENGTH_SHORT).show();
        }

        if(name.equals("")){
            allFieldsFilled = false;
            etNome.setError("Campo obrigatório");
        }else{
            etNome.setErrorEnabled(false);
        }

        if(cpf.equals("")){
            allFieldsFilled = false;
            allFilledRight = false;
            etCPF.setError("Campo obrigatório");
        }else{
            etCPF.setErrorEnabled(false);
        }

        if(phone.equals("")){
            allFieldsFilled = false;
            etTelefone.setError("Campo obrigatório");
        }else{
            etTelefone.setErrorEnabled(false);
        }

        if(email.equals("")){
            allFieldsFilled = false;
            etEmail.setError("Campo obrigatório");
        }else{
            etEmail.setErrorEnabled(false);
        }

        if(birth.equals("")){
            allFieldsFilled = false;
            etNascimento.setError("Campo obrigatório");
        }else{
            etNascimento.setErrorEnabled(false);
        }

        if(city.equals("Cidade")){
            allFieldsFilled = false;
            Toast.makeText(this, "Campo de Estado/Cidade são obrigatórios", Toast.LENGTH_SHORT).show();
        }

        if(allFieldsFilled) {
            if (cpf.length() < 14) {
                allFilledRight = false;
                etCPF.setError("O CPF têm 11 dígitos");
            } else {
                etCPF.setErrorEnabled(false);
            }

            if (phone.length() < 14) {
                allFilledRight = false;
                etTelefone.setError("Telefone inválido");
            } else {
                etTelefone.setErrorEnabled(false);
            }

            if (birth.length() < 10) {
                allFilledRight = false;
                etNascimento.setError("Data de nascimento inválida");
            } else {
                etNascimento.setErrorEnabled(false);
            }
        }

        if(allFilledRight){
            cpf = cpf.replaceAll("[.]", "").replaceAll("[-]","");

            if(!Utility.isValidCPF(cpf)){
                allFilledCorrectly = false;
                etCPF.setError("CPF inválido");
            }else{
                etCPF.setErrorEnabled(false);
            }
        }

        if(allFieldsFilled && allFilledRight && allFilledCorrectly) {
            writeNewProvider(uid, name, email, birth, city, cpf, phone, rate, description);
            Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void writeNewProvider(String uid, String name, String email, String birth, String city, String cpf, String phone, double rate, String description) {
        String category = segmentosFirebase.get(spCategoria.getSelectedItem().toString());
        String subcategory = subareas.get(spCategoria.getSelectedItem().toString())
                .get(Utility.HASH_MAP_FIREBASE).get(spEspecialidade.getSelectedItemPosition());

        ProviderFirebase providerFirebase = new ProviderFirebase(name, email, birth, city, cpf, phone, rate, description, category, subcategory);

        mDatabase
                .child(category)
                .child(subcategory)
                .child(uid).setValue(providerFirebase);
    }

    public void setupFieldMasks(){
        cpfMask = Utility.insertMask(getResources().getString(R.string.cpf_mask), etCPF.getEditText());
        etCPF.getEditText().addTextChangedListener(cpfMask);

        phoneMask = Utility.insertMask(getResources().getString(R.string.phone_mask), etTelefone.getEditText());
        etTelefone.getEditText().addTextChangedListener(phoneMask);

        birthMask = Utility.insertMask(getResources().getString(R.string.birth_mask), etNascimento.getEditText());
        etNascimento.getEditText().addTextChangedListener(birthMask);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();
        String state = spinnerState.getSelectedItem().toString();

        switch (id){
            case R.id.spCategorias:
                adapterSubCategorias = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, subareas.get(spCategoria.getSelectedItem().toString()).get(Utility.HASH_MAP_TELA));
                spEspecialidade.setAdapter(adapterSubCategorias); // this will set list of values to spinner
                break;
            case R.id.sp_state:
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
