package harmonytech.praagora.controller.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;

import harmonytech.praagora.R;

public class Utility {

    public static final String URI_PACKAGE = "android.resource://harmonytech.praagora/";

    public static final String SEGMENTO = "segmento";
    public static final String SEGMENTO_FIREBASE = "segmentoFirebase";

    public static final String SEGMENTO_ANIMAIS = "Animais";
    public static final String SEGMENTO_ANIMAIS_FIREBASE = "animais";
    public static final String SEGMENTO_ARTE_CULTURA = "Arte e Cultura";
    public static final String SEGMENTO_ARTE_CULTURA_FIREBASE = "arte_cultura";
    public static final String SEGMENTO_ASSISTENCIA_TECNICA = "Assistência Técnica";
    public static final String SEGMENTO_ASSISTENCIA_TECNICA_FIREBASE = "assistenciaTecnica";
    public static final String SEGMENTO_AULAS = "Aulas";
    public static final String SEGMENTO_AULAS_FIREBASE = "aulas";
    public static final String SEGMENTO_AUTOS = "Autos";
    public static final String SEGMENTO_AUTOS_FIREBASE = "autos";
    public static final String SEGMENTO_BELEZA_ESTETICA = "Beleza e Estética";
    public static final String SEGMENTO_BELEZA_ESTETICA_FIREBASE = "beleza_estetica";
    public static final String SEGMENTO_CONSTRUCAO = "Construção e Reformas";
    public static final String SEGMENTO_CONSTRUCAO_FIREBASE = "construcao_reformas";
    public static final String SEGMENTO_CONSULTORIA = "Consultoria";
    public static final String SEGMENTO_CONSULTORIA_FIREBASE = "consultoria";
    public static final String SEGMENTO_DESIGN = "Design e Tecnologia";
    public static final String SEGMENTO_DESIGN_FIREBASE = "design_tecnologia";
    public static final String SEGMENTO_EVENTOS = "Eventos";
    public static final String SEGMENTO_EVENTOS_FIREBASE = "eventos";
    public static final String SEGMENTO_SAUDE = "Saúde";
    public static final String SEGMENTO_SAUDE_FIREBASE = "saude";
    public static final String SEGMENTO_SERVICOS_DOMESTICOS = "Serviços Domésticos";
    public static final String SEGMENTO_SERVICOS_DOMESTICOS_FIREBASE = "servicos_domesticos";

    public static final String HASH_MAP_TELA = "tela";
    public static final String HASH_MAP_FIREBASE = "firebase";

    public static final String SEGMENT_DETAILS_TITLE = "title";
    public static final String SEGMENT_DETAILS_SUBTITLE = "subtitle";

    public static final String PROVIDER = "provider";

    public static final String KEY_CONTENT_EXTRA_DATABASE = "database";
    public static final String KEY_CONTENT_EXTRA_DATA = "data";

    public static final String LOGIN_SHARED_PREF_NAME = "LoginActivityPreferences";

    public static final String EAGORA_PHONE = "(67)98157–2320";

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean isValidCPF(String cpf) {
        if ((cpf==null) || (cpf.length()!=11)) return false;

        Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
    }

    public static boolean verifyEmptyField(String email, String pass){

        return (email.isEmpty() || pass.isEmpty());
    }

    public static boolean isConnected(Activity activity){
        ConnectivityManager cm =
                (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void showSnackBarErrorMessage(Activity activity, String errorMessage){

        TSnackbar snackbar = TSnackbar.make(activity.findViewById(android.R.id.content), errorMessage, TSnackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(activity.getResources().getColor(R.color.redNoInternetConnection));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        snackbar.show();
    }

    public static void showSnackBarSucessMessage(Activity activity, String sucessMessage){

        TSnackbar snackbar = TSnackbar.make(activity.findViewById(android.R.id.content), sucessMessage, TSnackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(activity.getResources().getColor(R.color.greenSucessMessage));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        snackbar.show();
    }

    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }

    public static TextWatcher insertMask(final String mask, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String str = unmask(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                ediTxt.setText(mascara);
                ediTxt.setSelection(mascara.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }

    public static SpannableString changeActionBarTitle(Activity activity, String title){

        SpannableString spannableString = new SpannableString(title);
        spannableString.setSpan(new harmonytech.praagora.controller.util.TypefaceSpan(activity.getApplicationContext(),
                        "CircularStd-Book.otf"), 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static void callPhone(Activity activity, String phone){

        Uri uri = Uri.parse("tel:" + phone);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);

        activity.startActivity(intent);
    }

    public static void openWhatsApp(Activity activity, String phone) {
        String smsNumber = phone; //without '+'
        try {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix

            activity.startActivity(sendIntent);
        } catch(Exception e) {
            Toast.makeText(activity.getApplicationContext(), "Error/n" + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
