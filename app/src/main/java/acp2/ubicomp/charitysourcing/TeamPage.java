package acp2.ubicomp.charitysourcing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;



public class TeamPage extends AppCompatActivity {

    protected String externalListUrl = "http://simohosio.com/study/charity/task1/"; // Applied as default.
    private String teamName;
    private String team_score = "0";
    private String url_name_indicator;
    protected String[] externalTaskList; // Use task object here???, fill
    ListView listview;
    Bundle extras;

    private String[] styff;

    //TeamPageListActivityTasks activityTaskObject;

    private void setListView(ListView thisList) {
        /* Here, apply the results fetched from database as a string array. Use JSONobject???. Use JoinTeam as a example. */
        styff = new String[3];
        styff[0] = "abc";
        styff[1] = "def";
        styff[2] = "ghi";
        thisList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, styff));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_window);
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras == null) {
                teamName = null;
                team_score = "0";
            } else {
                teamName = extras.getString("teamName");
            }
        } else {
            teamName = (String) savedInstanceState.getSerializable("teamName");
            team_score = "0";
        }

        TextView t = (TextView) findViewById(R.id.TeamName);
        t.setText(" Team : " + teamName);
        setListView((ListView) findViewById(R.id.TeamList));
        listview = (ListView) findViewById(R.id.TaskList);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterView, View view, int position, long id) {
            url_name_indicator = adapterView.getItemAtPosition(position).toString();
                //Do some more stuff here and launch new activity
                if(url_name_indicator.equals("Find mystery object"))
                    externalListUrl = "https://simohosio.com/study/charity/task1/";
                else if (url_name_indicator.equals("Count people in picture"))
                    externalListUrl = "https://simohosio.com/study/charity/task2/";
                else if (url_name_indicator.equals("Solve captcha"))
                    externalListUrl = "https://simohosio.com/study/charity/task3/";
                    // toast to imply, there is no valid url behind this variable.

                goNext(view);
            }
        });
    }

    public void goNext(View view) {
        Intent intent = new Intent(TeamPage.this, TaskPage.class);
        // Add to putExtra, value from

        intent.putExtra("Task_url", externalListUrl);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TeamPage.this, FirstPage.class);
        startActivity(intent);
    }
}
