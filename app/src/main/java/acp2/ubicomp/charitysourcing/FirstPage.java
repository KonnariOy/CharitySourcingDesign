package acp2.ubicomp.charitysourcing;

import android.content.Intent;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import java.io.IOException;


public class FirstPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.startingpage);
    }

    public void goNext(View view) {
        Intent intent = new Intent(FirstPage.this, JoinTeam.class);
        startActivity(intent);
        finish();
    }

    public void changeView(View view) {
        if (view.getId() == R.id.one) {
            findViewById(R.id.linone).setVisibility(View.VISIBLE);
            findViewById(R.id.lintwo).setVisibility(View.GONE);
            findViewById(R.id.linthree).setVisibility(View.GONE);
            findViewById(R.id.header).setVisibility(View.VISIBLE);


        } else if (view.getId() == R.id.two) {
            findViewById(R.id.linone).setVisibility(View.GONE);
            findViewById(R.id.lintwo).setVisibility(View.VISIBLE);
            findViewById(R.id.linthree).setVisibility(View.GONE);
            findViewById(R.id.header).setVisibility(View.GONE);

        } else if (view.getId() == R.id.three) {
            findViewById(R.id.linone).setVisibility(View.GONE);
            findViewById(R.id.lintwo).setVisibility(View.GONE);
            findViewById(R.id.linthree).setVisibility(View.VISIBLE);
            findViewById(R.id.header).setVisibility(View.VISIBLE);

        }
    }
}
