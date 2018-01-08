package acp2.ubicomp.charitysourcing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import acp2.ubicomp.charitysourcing.MailClass;

public class TaskPage extends AppCompatActivity {

    Button btn_ok;
    Dialog dialog;
    ImageButton btn_dialog;

    String sessionId;
    String userEmail;
    String teamId;
    String teamName;
    String points;
    String team_score;
    String userId;
    String taskUrl; // Add to row 39, when this object created, url sent from TeamPage, where list of tasks exists.

    Bundle extras;

    WebView wv;
    MailClass sendM;

    javascriptInt javaInt;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        javaInt = new javascriptInt(this);

        btn_dialog = (ImageButton) findViewById(R.id.imageShowDialog);
        btn_dialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(TaskPage.this);
            }
        });

        wv = (WebView)findViewById(R.id.webTaskView);
        CookieManager.getInstance().setAcceptThirdPartyCookies(wv,true);

        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras == null) {
                taskUrl = null;
                team_score = "0";
            } else {
                taskUrl = extras.getString("Task_url");
                teamName = extras.getString("team_name");
            }
        } else {
            taskUrl = (String) savedInstanceState.getSerializable("Task_url");
            teamName = (String) savedInstanceState.getSerializable("team_name");
            team_score = "0";
        }

        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(javaInt, "Android"); // javascriptInt

        wv.setWebViewClient(new WebViewClient() {

            @Override

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(taskUrl.isEmpty() || taskUrl == null)
                    view.loadUrl("https://simohosio.com/study/charity/task1/");
                else
                    view.loadUrl(taskUrl);

                return true;
            }
        });

        wv.loadUrl(taskUrl); // Integrate this into
/*        wv.
    Login screen has only one input: team id / name
    Android app displays: a) leaderboad, team tally (points), user tally (points)
    The values above come from scripts and/or android callback. Callback fires when every single task (as in item of a task) is completed.
    In the android app, there needs to be the following callback-methods:
    setSession(String sessionId, String teamId, String teamName, String userEmail, String userId),
    itemCompleted(String reward).
    These are called from the task when task items are done and when the user first accesses a task during one unique session

    Android.itemCompleted(reward.toString());
    Android.setSession(sessionId.toString(), 	.toString(), userEmail.toString(), teamId.toString(), teamName.toString());
*/
    }

    public void getSessionValues() {
        sessionId = javaInt.getSessionId();
        userEmail = javaInt.getUserEmail();
        teamId = javaInt.getTeamId();
        teamName = javaInt.getTeamName();
        userId = javaInt.getUserId();
        if(sessionId == null || sessionId.isEmpty())
            sessionId = "I am Not Real";
        if(userEmail == null || userEmail.isEmpty())
            userEmail = "I am Not Real";
        if(teamId == null || teamId.isEmpty())
            teamId = "I am Not Real";
        if(teamName == null || teamName.isEmpty())
            teamName = "I am Not Real";
        if(userId == null || userId.isEmpty())
            userId = "I am Not Real";

    }


    public class javascriptInt {

        Context c;

        String sessionId;
        String userEmail;
        String teamId;
        String teamName;
        String userId;
        String points;

        /** Instantiate the interface and set the context */
        javascriptInt(Context c) {
            this.c = c;
        }

        @JavascriptInterface
        public void itemCompleted(String reward) {
            if (points == "0")
                points = reward;
            else {
                try {
                    int myNumA = Integer.parseInt(reward);
                    int myNumB = Integer.parseInt(points);
                    int total = myNumA + myNumB;
                    points = String.valueOf(total);
                } catch (NumberFormatException nfe) {
                }

            }
        }

        @JavascriptInterface
        public void setSession(String sessionId, String userId, String teamName, String userEmail, String teamId)
        {
            this.sessionId = sessionId;
            this.userEmail = userEmail;
            this.teamId = teamId;
            this.teamName = teamName; // No team name on first attempt.
            this.userId = userId;
        }

        // Get Info to Android
        public String getSessionId() {
            return sessionId;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public String getTeamId() {
            return teamId;
        }

        public String getTeamName() {
            return teamName;
        }

        public String getUserId() {
            return userId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
        public void setUserEmail(String userEmail){
            this.userEmail = userEmail;
        }
        public void setTeamId(String teamId){
            this.teamId= teamId;
        }
        public void setTeamName(String teamName){
            this.teamName = teamName;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TaskPage.this, TeamPage.class);
        getSessionValues();
        if(points != "0")
            intent.putExtra("team_score", points);
        intent.putExtra("teamName", teamName);
        intent.putExtra("userEmail", userEmail);
        intent.putExtra("teamId", teamId);
        intent.putExtra("sesId", sessionId);
        startActivity(intent);
        finish();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent intent = new Intent(TaskPage.this, TeamPage.class);
                if(points != "0")
                    intent.putExtra("team_score", points);
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
    }*/

    public void showDialog(Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        Button okButton = dialog.findViewById(R.id.btn_sharelink);
        Button caButton = dialog.findViewById(R.id.btn_ok);

        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendEmail("HUUH");
                dialog.dismiss();
            }
        });

        caButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public boolean sendEmail(String emailAdd) {
        try {
        String[] TO = {"add sent email"};
        sendM = new MailClass("add gmail account","gmail account pass", TO,"Test","Test");
        sendM.createEmailMessage(); /* TODO: java.lang.NoClassDefFoundError: javax.activation.DataHandler, occurs in MailClass.java - emailMessage.setText(emailBody);// for a text email */
        sendM.sendEmail();
        /*Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL  , TO); // new String[]{"Recipient"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT   , "TestMail, please come in. ");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finish email sent...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }*/
        } catch (Exception e) {
            Log.e("SendMailTask", e.getMessage(), e);
            return false;
        }
        return true;
    }
}
