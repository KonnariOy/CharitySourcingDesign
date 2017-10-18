package acp2.ubicomp.charitysourcing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class JoinTeam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_team);
    }

    public void goNext(View view) {
        Intent intent = new Intent(JoinTeam.this, TeamPage.class);
        startActivity(intent);
        finish();
    }
    public void createTeam(View view) {
        Intent intent = new Intent(JoinTeam.this, CreateTeam.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(JoinTeam.this, InstructionPage.class);
        startActivity(intent);
    }
}
