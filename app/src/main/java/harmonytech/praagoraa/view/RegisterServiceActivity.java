package harmonytech.praagoraa.view;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.MenuItem;
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
import java.util.UUID;

import harmonytech.praagoraa.R;
import harmonytech.praagoraa.controller.domain.Provider;
import harmonytech.praagoraa.controller.domain.ProviderFirebase;
import harmonytech.praagoraa.controller.util.FirebaseHelper;
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

    Provider provider = new Provider();

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_service);

        provider = (Provider) getIntent().getSerializableExtra(Utility.PROVIDER);

        mAuth = FirebaseAuth.getInstance();

        actionBar = getSupportActionBar();

        if(actionBar!=null) {
            actionBar.setTitle(Utility.changeActionBarTitle(this, actionBar.getTitle().toString()));
            actionBar.setDisplayHomeAsUpEnabled(true);
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
        if(provider==null) {
            ArrayAdapter<String> adapterCategorias = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, areas);
            spCategoria.setAdapter(adapterCategorias); // this will set list of values to spinner
            spCategoria.setOnItemSelectedListener(this);
        }else{
            ArrayAdapter<String> adapterCategorias = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, areas);
            spCategoria.setAdapter(adapterCategorias); // this will set list of values to spinner
            spCategoria.setSelection(((ArrayAdapter)spCategoria.getAdapter()).getPosition(provider.getCategoryScreen()));
            spCategoria.setEnabled(false);
        }

        spEspecialidade = (Spinner) findViewById(R.id.spSubCategoria);
        if(provider!=null) {
            adapterSubCategorias = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, subareas.get(provider.getCategoryScreen()).get(Utility.HASH_MAP_TELA));
            spEspecialidade.setAdapter(adapterSubCategorias); // this will set list of values to spinner
            spEspecialidade.setSelection(adapterSubCategorias.getPosition(provider.getSubcategoryScreen()));
            spEspecialidade.setEnabled(false);
        }

        setupFieldMasks();

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(this);

        spinnerState = (Spinner)findViewById(R.id.sp_state);
        spinnerState.setOnItemSelectedListener(this);
        spinnerCity = (Spinner) findViewById(R.id.sp_city);
        spinnerCity.setOnItemSelectedListener(this);
        spinnerCity.setEnabled(false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(provider!=null){
            setupInfosProvider();
        }

        fillCity();
        fillState();
    }

    private void setupInfosProvider() {
        etNome.getEditText().setText(provider.getName());
        etCPF.getEditText().setText(provider.getCpf());
        etTelefone.getEditText().setText(provider.getPhone());
        etEmail.getEditText().setText(provider.getEmail());
        etNascimento.getEditText().setText(provider.getBirth());
        etDescription.setText(provider.getDescription());

        actionBar.setTitle(Utility.changeActionBarTitle(this, "Editar Serviço"));
        btnCadastrar.setText("Salvar Alterações");
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

    public void attemptLogin(){
        String uid, name, email, birth, state ,city, cpf, phone, description;
        double rate;

        boolean allFieldsFilled = true;
        boolean allFilledRight = true;
        boolean allFilledCorrectly = true;

        uid = mAuth.getCurrentUser().getUid();
        name = etNome.getEditText().getText().toString();
        email = etEmail.getEditText().getText().toString();
        birth = etNascimento.getEditText().getText().toString();
        state = spinnerState.getSelectedItem().toString();
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
            writeNewProvider(uid, name, email, birth, city, cpf, phone, rate, description, state);
            if(provider==null) {
                Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Editado com sucesso", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    private void writeNewProvider(String uid, String name, String email, String birth, String city, String cpf, String phone, double rate, String description, String state) {
        String category = segmentosFirebase.get(spCategoria.getSelectedItem().toString());
        String subcategory = subareas.get(spCategoria.getSelectedItem().toString())
                .get(Utility.HASH_MAP_FIREBASE).get(spEspecialidade.getSelectedItemPosition());

        String categoryScreen = spCategoria.getSelectedItem().toString();
        String subcategoryScreen = spEspecialidade.getSelectedItem().toString();

        ProviderFirebase providerFirebase = new ProviderFirebase(name, email, birth, city, cpf, phone, rate, description, category, subcategory, categoryScreen, subcategoryScreen, state);

        mDatabase
                .child(category)
                .child(subcategory)
                .child(uid).setValue(providerFirebase);

        if(provider==null) {
            mDatabase
                    .child(FirebaseHelper.FIREBASE_DATABASE_ALL)
                    .child(uid)
                    .child(UUID.randomUUID().toString()).setValue(providerFirebase);
        }else{
            mDatabase
                    .child(FirebaseHelper.FIREBASE_DATABASE_ALL)
                    .child(uid)
                    .child(provider.getId()).setValue(providerFirebase);
        }
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
                if(provider==null) {
                    adapterSubCategorias = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, subareas.get(spCategoria.getSelectedItem().toString()).get(Utility.HASH_MAP_TELA));
                    spEspecialidade.setAdapter(adapterSubCategorias); // this will set list of values to spinner
                }
                break;
            case R.id.sp_state:
                if(provider==null) {
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

        if(provider!=null){
            spinnerState.setSelection(((ArrayAdapter)spinnerState.getAdapter()).getPosition(provider.getState()));
            spinnerState.setEnabled(false);
        }
    }

    public void fillCity(){
        if(provider==null) {
            ArrayAdapter<String> spinnerCountShoesArrayAdapterState = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.sp));
            spinnerCity.setAdapter(spinnerCountShoesArrayAdapterState);
        }else{
            switch (provider.getState()) {
                case "Mato Grosso do Sul": {
                    ArrayAdapter<String> spinnerCountShoesArrayAdapterState = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ms));
                    spinnerCity.setAdapter(spinnerCountShoesArrayAdapterState);
                    spinnerCity.setSelection(((ArrayAdapter)spinnerCity.getAdapter()).getPosition(provider.getCity()));
                    break;
                }
                case "São Paulo": {
                    ArrayAdapter<String> spinnerCountShoesArrayAdapterState = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.sp));
                    spinnerCity.setAdapter(spinnerCountShoesArrayAdapterState);
                    spinnerCity.setSelection(((ArrayAdapter)spinnerCity.getAdapter()).getPosition(provider.getCity()));
                    break;
                }
            }
        }
    }
}
