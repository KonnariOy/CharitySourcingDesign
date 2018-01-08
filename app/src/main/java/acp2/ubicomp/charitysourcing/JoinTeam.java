package acp2.ubicomp.charitysourcing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JoinTeam extends AppCompatActivity {


    String tempContent;
    JSONObject json;

    EditText teamName;
    String dummyTeam = "Blue Panda";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getStuff();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.join_team_v_two);
        teamName = (EditText) findViewById(R.id.team_name);
    }

    public void goNext(View view) throws IOException {
        String team = teamName.getText().toString().trim();
        if (team.equals("")) {
            teamName.setError("write team name");
        } else {
            if (testGet(json,team)) {
                showCreatTeamDialog("Join team", team + " team exists, do you want to join team " + team, team);
            } else {
                showCreatTeamDialog("Create team", team + " named team does not exists, do you want to create team " + team, team);
            }
        }

    }

    private void showCreatTeamDialog(String title, String message, final String teamN) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(JoinTeam.this, TeamPage.class);
                        intent.putExtra("teamName", teamName.getText().toString());
                        intent.putExtra("teamScore", "0");
                        startActivity(intent);
                        if(getTitle().toString().equals("Create team")) {
                            try {
                                sendStuff(teamN);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }

//    public void createTeam(View view) {
//        Intent intent = new Intent(JoinTeam.this, CreateTeam.class);
//        startActivity(intent);
//        finish();
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(JoinTeam.this, FirstPage.class);
        startActivity(intent);
    }

    public boolean testGet(JSONObject stuffA, String alpha) {

        try {
            String tup = stuffA.getString(alpha);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    public void getStuff() throws IOException, JSONException {
        URL url = null;
        StringBuilder responseOutput = new StringBuilder();
        String line = "";
        try {
            url = new URL("https://simohosio.com/study/charity/getteams.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder total = new StringBuilder();
            while ((line = r.readLine()) != null) {
                responseOutput = total.append(line);
            }
            tempContent = responseOutput.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            json = new JSONObject(tempContent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendStuff(String teamNameStuff) throws IOException {
        URL url = null;
        StringBuilder responseOutput = new StringBuilder();
        String line = "";
        try {
            url = new URL("https://simohosio.com/study/charity/putteam.php?team=" + teamNameStuff);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder total = new StringBuilder();
            while ((line = r.readLine()) != null) {
                responseOutput = total.append(line);
            }
            tempContent = responseOutput.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
