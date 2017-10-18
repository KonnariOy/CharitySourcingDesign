package acp2.ubicomp.charitysourcing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TaskPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
    }

    public void goNextPage(View view) {
        Intent intent = new Intent(TaskPage.this, FinalPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TaskPage.this, TeamPage.class);
        startActivity(intent);
    }
}
