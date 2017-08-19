package harmonytech.praagoraa.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import harmonytech.praagoraa.R;
import harmonytech.praagoraa.controller.util.Utility;

public class DoubtActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {
            actionBar.setTitle(Utility.changeActionBarTitle(this, actionBar.getTitle().toString()));
        }

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listDataHeader.add(getResources().getString(R.string.question_1));
        List<String> answer1 = new ArrayList<>();
        answer1.add(getResources().getString(R.string.answer_1));

        listDataHeader.add(getResources().getString(R.string.question_2));
        List<String> answer2 = new ArrayList<>();
        answer2.add(getResources().getString(R.string.answer_2));

        listDataHeader.add(getResources().getString(R.string.question_3));
        List<String> answer3 = new ArrayList<>();
        answer3.add(getResources().getString(R.string.answer_3));

        listDataHeader.add(getResources().getString(R.string.question_4));
        List<String> answer4 = new ArrayList<>();
        answer4.add(getResources().getString(R.string.answer_4));

        listDataHeader.add(getResources().getString(R.string.question_5));
        List<String> answer5 = new ArrayList<>();
        answer5.add(getResources().getString(R.string.answer_5));

        listDataHeader.add(getResources().getString(R.string.question_6));
        List<String> answer6 = new ArrayList<>();
        answer6.add(getResources().getString(R.string.answer_6));

        listDataHeader.add(getResources().getString(R.string.question_7));
        List<String> answer7 = new ArrayList<>();
        answer7.add(getResources().getString(R.string.answer_7));

        listDataHeader.add(getResources().getString(R.string.question_8));
        List<String> answer8 = new ArrayList<>();
        answer8.add(getResources().getString(R.string.answer_8));

        listDataChild.put(getResources().getString(R.string.question_1), answer1);
        listDataChild.put(getResources().getString(R.string.question_2), answer2);
        listDataChild.put(getResources().getString(R.string.question_3), answer3);
        listDataChild.put(getResources().getString(R.string.question_4), answer4);
        listDataChild.put(getResources().getString(R.string.question_5), answer5);
        listDataChild.put(getResources().getString(R.string.question_6), answer6);
        listDataChild.put(getResources().getString(R.string.question_7), answer7);
        listDataChild.put(getResources().getString(R.string.question_8), answer8);

        listAdapter = new harmonytech.praagoraa.controller.util.ExpandableListAdapter(DoubtActivity.this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }
}
