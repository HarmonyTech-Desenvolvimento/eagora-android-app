package harmonytech.praagoraa.controller.domain;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ProviderFirebase {

    public String name, email, birth, cpf, state ,city, phone, description, category, categoryScreen, subcategory, subcategoryScreen;
    public double rate;

    public ProviderFirebase() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ProviderFirebase(String name, String email, String birth, String city, String cpf, String phone,
                            double rate, String description, String category, String subcategory, String categoryScreen, String subcategoryScreen, String state) {
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.state = state;
        this.city = city;
        this.cpf = cpf;
        this.phone = phone;
        this.rate = rate;
        this.description = description;
        this.category = category;
        this.subcategory = subcategory;
        this.categoryScreen = categoryScreen;
        this.subcategoryScreen = subcategoryScreen;
    }
}
