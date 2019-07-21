package com.example.todolist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String stringUri="content://";

    private   Task task=new Task("","",0.0f,Uri.parse(stringUri));
    static final int ADD_REQUEST_CODE=123;
    static final int SAVE_REQUEST_CODE=345;

    public static Uri uri;

    public View mainBackground_view;
    public ImageView main_imgView;
    public TextView main_title_tv;
    public TextView main_Discription_tv;
    public RatingBar main_rb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewInitialization();
        main_rb.setEnabled(false);
mainBackground_view.setEnabled(false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent=new Intent(MainActivity.this,PreviewActivity.class);
                startActivityForResult(intent,ADD_REQUEST_CODE);
            }
        });

        mainBackground_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PreviewActivity.class);
               Bundle taskBundle= new Bundle();
              Task task1= new Task(main_title_tv.getText().toString(),main_Discription_tv.getText().toString(),main_rb.getRating(),uri);
               taskBundle.putParcelable("taskToPreview",task1);
               intent.putExtra("bundle",taskBundle);
                startActivityForResult(intent,SAVE_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            mainBackground_view.setEnabled(true);

            switch (requestCode) {
                case ADD_REQUEST_CODE: {
                    Bundle resultBundle = data.getBundleExtra("task");
                    Task task = (Task) resultBundle.get("task");
                    if(task !=null){
                    onViewsSeter(task);}

                    break; }
                case SAVE_REQUEST_CODE: {
                    String delete= data.getStringExtra("taskDeleted");
                    if (delete != null) {
                        Uri uri=Uri.parse(stringUri);
                        Task task = new Task("","",0.0f,uri);
                        onViewsSeter(task);
                        mainBackground_view.setEnabled(false);

                    } else {
                        Bundle resultBundle = data.getBundleExtra("savedTask");
                        Task task = (Task) resultBundle.get("savedTask");
                        if(task !=null){
                            onViewsSeter(task);}
                    }
                    break; }

            }
        }
    }

    private void viewInitialization(){
        mainBackground_view=findViewById(R.id.view_rv);
        main_imgView=findViewById(R.id.imgView_rv);
        main_title_tv=findViewById(R.id.tv_name_rv);;
        main_Discription_tv=findViewById(R.id.tv_description_rv);
        main_rb=findViewById(R.id.tv_type_rv);
    }

    private void onViewsSeter(Task task){
        Picasso.get().load(task.getImgUri()).into(main_imgView);

        // main_imgView.setImageURI(task.getImgUri());
        main_title_tv.setText(task.getName());
        main_Discription_tv.setText(task.getDescription());
        main_rb.setRating(task.getType());
        main_rb.setEnabled(false);

        if(main_rb.getRating()==0.0f){
            mainBackground_view.setBackgroundResource(R.drawable.view_backgroound);}
       else if(main_rb.getRating()==1.0f){
            mainBackground_view.setBackgroundColor(getResources().getColor(R.color.colorLiteYellow));
            }
           else if(main_rb.getRating()==2.0f){
               mainBackground_view.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                }
             else if(main_rb.getRating()==3.0f){
                 mainBackground_view.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                }
        else if(main_rb.getRating()==4.0f) {
            mainBackground_view.setBackgroundColor(getResources().getColor(R.color.colorLiteRed));
            }
        else
            {mainBackground_view.setBackgroundColor(getResources().getColor(R.color.colorRed));
            }
    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
