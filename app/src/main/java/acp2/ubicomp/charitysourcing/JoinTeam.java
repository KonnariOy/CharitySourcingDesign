package acp2.ubicomp.charitysourcing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class JoinTeam extends AppCompatActivity {

    EditText team_name;
    String dummyTeam = "bluepanda";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_team_v_two);
        team_name = (EditText) findViewById(R.id.team_name);
    }

    public void goNext(View view) {
        String team = team_name.getText().toString().trim();
        if (team.equals("")) {
            team_name.setError("write your team name");
        } else {
            if (dummyTeam.equalsIgnoreCase(team)) {
                showCreatTeamDialog("Join Team", "There is already a team with name " + team + ", Do you want to join with " + team);
            } else {
                showCreatTeamDialog("Create Team", "There is no team with name " + team + ", Do you want create team " + team);
            }
        }

    }

    private void showCreatTeamDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(JoinTeam.this, TeamPage.class);
                        startActivity(intent);
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
}
