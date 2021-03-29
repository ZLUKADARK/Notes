package com.example.notes;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notes.adapter.ListItem;
import com.example.notes.db.MyConstans;
import com.example.notes.db.MyDbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditActivity extends AppCompatActivity {
    private final int PIC_IMAGE_CODE = 123;
    private ImageView imImg;
    private ConstraintLayout imgCont;
    private FloatingActionButton addImg;
    private ImageButton edImg, delImg;
    private EditText title_id, desc_id;
    private MyDbManager myDbManager;
    private String tempUri = "empty";
    private boolean editState = true;
    private ListItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        getMyIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myDbManager.openDb();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PIC_IMAGE_CODE && data != null) {
            tempUri = data.getData().toString();
            imImg.setImageURI(data.getData());
            getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);

        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        myDbManager = new MyDbManager(this);
        title_id = findViewById(R.id.title_id);
        imImg = findViewById(R.id.imImg);
        edImg = findViewById(R.id.edimg);
        delImg = findViewById(R.id.delimg);
        addImg = findViewById(R.id.addimg);
        imgCont = findViewById(R.id.imgCont);
        desc_id = findViewById(R.id.desc_id);
    }

    public void getMyIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            item = (ListItem) intent.getSerializableExtra(MyConstans.ITEM_INTENT);
            editState = intent.getBooleanExtra(MyConstans.EDIT_STATE, true);
            if (!editState) {
                title_id.setText(item.getTitle());
                desc_id.setText(item.getDesc());
                if (!item.getImguri().equals("empty")) {
                    tempUri = item.getImguri();
                    imgCont.setVisibility(View.VISIBLE);
                    imImg.setImageURI(Uri.parse(item.getImguri()));
                    delImg.setVisibility(View.GONE);
                    edImg.setVisibility(View.GONE);
                }
            }
        }
    }

    public void OnclickSave(View view) {
        String title = title_id.getText().toString();
        String description = desc_id.getText().toString();

        if (title.equals("") || description.equals("")) {
            Toast.makeText(this, R.string.empty_allert, Toast.LENGTH_SHORT).show();
        } else {
            if (editState) {
                myDbManager.insertToDb(title, description, tempUri);
                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            } else {
                myDbManager.updateitem(title, description, tempUri, item.getId());
                Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
            }
            myDbManager.closeDb();
            finish();
        }
    }

    public void onClickDelImg(View view) {
        imImg.setImageResource(R.drawable.defimg);
        tempUri = "empty";
        imgCont.setVisibility(View.GONE);
        addImg.setVisibility(View.VISIBLE);
    }

    public void onClickAddImg(View view) {
        imgCont.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
    }

    public void onClickEditImg(View view) {
        Intent edit = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        edit.setType("image/*");
        startActivityForResult(edit, PIC_IMAGE_CODE);
    }
}