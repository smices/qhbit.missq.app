package com.idkin.android.idkclient;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.idkin.android.idkclient.utils.AppUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AboutActivity extends BaseActivity {

    @InjectView(R.id.text_version)
    TextView mVersions;

    @InjectView(R.id.text_content)
    TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        mVersions.setText(AppUtil.getVersions());
        try {
            loadAboutRawFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_about;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadAboutRawFile() throws IOException {
        String str = "";
        final StringBuffer buffer = new StringBuffer();
        InputStream is = getResources().openRawResource(R.raw.about_idkin);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        while ((str = reader.readLine()) != null) {
            buffer.append(str).append("\n");
        }

        is.close();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mContent.setText(buffer.toString());
            }
        });
    }

}
