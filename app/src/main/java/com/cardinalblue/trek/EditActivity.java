package com.cardinalblue.trek;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends Activity implements View.OnClickListener {

    private EditText titleEditText;
    private Button webpageButton;
    private Button confirmButton;

    private String mTitle;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        titleEditText = (EditText) findViewById(R.id.edit_title_edittext);
        webpageButton = (Button) findViewById(R.id.edit_webpage_button);
        confirmButton = (Button) findViewById(R.id.edit_confirm_button);

        webpageButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);

        Intent intent = getIntent();
//        mTitle = intent.getStringExtra(PreloaderActivity.KEY_LOC_TITLE);
//        mUrl = intent.getStringExtra(PreloaderActivity.KEY_SRC_LINK);

        titleEditText.setText(mTitle);

    }

    @Override
    public void onClick(View v) {
//        Intent intent = new Intent();
//        intent.putExtra(PreloaderActivity.KEY_LOC_TITLE, titleEditText.getEditableText().toString());
//        intent.putExtra(PreloaderActivity.KEY_SRC_LINK, mUrl);
//        switch (v.getId()) {
//            case R.id.edit_confirm_button:
//                setResult(PreloaderActivity.RESULT_CONFIRM, intent);
//                break;
//            case R.id.edit_webpage_button:
//                setResult(PreloaderActivity.RESULT_OPEN_BROWSER, intent);
//                break;
//        }
//        finish();
    }
}
