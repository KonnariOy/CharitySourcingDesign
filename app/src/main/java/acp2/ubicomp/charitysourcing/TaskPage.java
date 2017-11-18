package acp2.ubicomp.charitysourcing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class TaskPage extends AppCompatActivity {
    private Button btn_ok;
    private Dialog mDialog;
    private WebView webViewTask;
    private Context mContext;
    private String mUrl = "http://simohosio.com/study/charity/task1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        mContext = this;
        initUi();

        // load url
        webViewTask.loadUrl(mUrl);

    }

    @SuppressLint("JavascriptInterface")
    private void initUi() {
        webViewTask = (WebView) findViewById(R.id.webViewTask);
        WebSettings webSettings = webViewTask.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webViewTask.addJavascriptInterface(new JavaScriptHandler(this), "Android");
        webSettings.setLoadWithOverviewMode(true);
        webViewTask.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(mContext, description, Toast.LENGTH_SHORT).show();
            }
        });

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

    private class JavaScriptHandler {
        Context mContext;

        public JavaScriptHandler(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public void itemCompleted(String reward) {
            Log.v("reward", "" + reward);
            Toast.makeText(mContext, " Reward:" + reward, Toast.LENGTH_SHORT).show();

        }

        @JavascriptInterface
        public void setSession(String sessionId, String userId, String userEmail, String teamId, String teamName) {
            Log.v("tag", "sessionId :" + sessionId);
            Log.v("tag", "userId: " + userId);
            Log.v("tag", "userEmail: " + userEmail);
            Log.v("tag", "teamId: " + teamId);
            Log.v("tag", "teamName: " + teamName);

        }
    }
}
