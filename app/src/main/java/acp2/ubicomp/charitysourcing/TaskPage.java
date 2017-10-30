package acp2.ubicomp.charitysourcing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class TaskPage extends AppCompatActivity {
    Button btn_ok;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
    }

    public void goNextPage() {
        Intent intent = new Intent(TaskPage.this, FinalPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TaskPage.this, TeamPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent intent = new Intent(TaskPage.this, TeamPage.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_change_device:
                showDialog(TaskPage.this);
                break;
            case R.id.action_quit:
                goNextPage();
                break;
            default:
                break;
        }

        return true;
    }

    public void showDialog(Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        Button okButton = (Button) dialog.findViewById(R.id.btn_ok);

        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
