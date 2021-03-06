package acp2.ubicomp.charitysourcing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class InstructionPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruction_page);
    }

    public void goNext(View view) {
        Intent intent = new Intent(InstructionPage.this, JoinTeam.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(InstructionPage.this, FirstPage.class);
        startActivity(intent);
    }
}
