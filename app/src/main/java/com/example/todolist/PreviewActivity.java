package com.example.todolist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.squareup.picasso.Picasso;


public class PreviewActivity extends AppCompatActivity {

    public ImageView img_preview;
    public Button btn_add_preview;
    public RatingBar rb_preview;
    public EditText et_name_preview;
    public EditText et_discription_preview;
    public Button btn_chose_photo_preview;
    public Button btn_delete;
    public View view_preview;

    public final int IMAGE_URI_REQUEST_CODE=23;
    static final String TAG="URI";
    public Uri imgUri=MainActivity.uri;

    final   Intent resultIntent = new Intent();
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        viewInitialization();
        imgUri=MainActivity.uri;

      final Intent state = getIntent();
            Bundle taskBundle=state.getBundleExtra("bundle");
      if(taskBundle!=null){
             task= (Task)taskBundle.get("taskToPreview");}

      if (task!=null) {
            btn_delete.setVisibility(Button.VISIBLE);
            btn_add_preview.setText(R.string.save_btn);
            rb_preview.setRating(task.getType());
            et_name_preview.setText(task.getName());
            et_discription_preview.setText(task.getDescription());
          Picasso.get().load(imgUri).into(img_preview);
            //img_preview.setImageURI(imgUri);
        }


    btn_chose_photo_preview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent uriIntent=new Intent(Intent.ACTION_PICK);
            uriIntent.setType("image/*");
            startActivityForResult(uriIntent,IMAGE_URI_REQUEST_CODE);
        }
    });

    btn_add_preview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (task == null) {
                Task task = new Task(et_name_preview.getText().toString(), et_discription_preview.getText().toString(),
                        rb_preview.getRating(),imgUri);
               Bundle newTask= new Bundle();
                  newTask.putParcelable("task",task);
                    resultIntent.putExtra("task",newTask);
                    setResult(RESULT_OK,resultIntent);
                    finish(); }
                else{
                Task task = new Task(et_name_preview.getText().toString(), et_discription_preview.getText().toString(),
                        rb_preview.getRating(),imgUri);
                Bundle savedTask= new Bundle();
                savedTask.putParcelable("savedTask",task);
                resultIntent.putExtra("savedTask",savedTask);
                setResult(RESULT_OK,resultIntent);
                finish();
                }
        }
    });

    btn_delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resultIntent.putExtra("taskDeleted","delete");
            setResult(RESULT_OK,resultIntent);
            finish();
        }
    });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_URI_REQUEST_CODE && resultCode == RESULT_OK) {
         imgUri = data.getData();
         MainActivity.uri=imgUri;
            img_preview.setImageURI(imgUri);
        }
    }

    private void viewInitialization(){
        img_preview=findViewById(R.id.imgView);
        btn_add_preview=findViewById(R.id.add);
        btn_delete=findViewById(R.id.btn_delete);
        btn_chose_photo_preview=findViewById(R.id.pic_chose_btn);
        et_name_preview=findViewById(R.id.et_name);
        et_discription_preview=findViewById(R.id.et_description);
        rb_preview=findViewById(R.id.rb_type);
        view_preview=findViewById(R.id.view);
}

}