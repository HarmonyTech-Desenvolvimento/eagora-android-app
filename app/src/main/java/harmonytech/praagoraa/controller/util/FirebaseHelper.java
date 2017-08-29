package harmonytech.praagoraa.controller.util;

import com.google.firebase.database.DatabaseReference;

import harmonytech.praagoraa.controller.domain.User;

public class FirebaseHelper {

    public static final String FIREBASE_DATABASE_PROVIDER_NAME = "name";
    public static final String FIREBASE_DATABASE_PROVIDER_EMAIL = "email";
    public static final String FIREBASE_DATABASE_PROVIDER_PHONE = "phone";
    public static final String FIREBASE_DATABASE_PROVIDER_CITY = "city";
    public static final String FIREBASE_DATABASE_PROVIDER_DESCRIPTION = "description";
    public static final String FIREBASE_DATABASE_PROVIDER_CPF = "cpf";
    public static final String FIREBASE_DATABASE_PROVIDER_BIRTH = "birth";
    public static final String FIREBASE_DATABASE_PROVIDER_CATEGORY = "category";
    public static final String FIREBASE_DATABASE_PROVIDER_SUBCATEGORY = "subcategory";
    public static final String FIREBASE_DATABASE_PROVIDER_RATE = "rate";

    public static final String FIREBASE_DATABASE_ORDERBY= "city";

    public static final String FIREBASE_DATABASE_USERS = "users";
    public static final String FIREBASE_DATABASE_PROVIDERS = "providers";
    public static final String FIREBASE_DATABASE_ALL = "all";

    public static void writeNewUser(DatabaseReference mDatabase, String database, String uid, String name, String email, String profile_pic) {
        User user = new User(name, email, profile_pic);

        mDatabase.child(database).child(uid).setValue(user);
    }
}
