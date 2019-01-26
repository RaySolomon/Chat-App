package com.example.sahil.chat_app;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private TextInputLayout mStatus;
    private Button savebtn;
    private ProgressDialog mProg;


    private DatabaseReference mStatusdatabase;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mStatusdatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mToolBar = (Toolbar)findViewById(R.id.status_appBar);
       // ActionBar actionBar = getSupportActionBar();
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String status_Value = getIntent().getStringExtra("sts_Value");

        mStatus = (TextInputLayout)findViewById(R.id.status_input);
        savebtn = (Button)findViewById(R.id.status_save_btn);

        mStatus.getEditText().setText(status_Value);

//        mProg = new ProgressDialog(StatusActivity.this);
//        mProg.setTitle("Saving Status");
//        mProg.setMessage("Please,Wait while we save the changes");
//        mProg.show();

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProg = new ProgressDialog(StatusActivity.this);
                mProg.setTitle("Saving Status");
                mProg.setMessage("Please,Wait while we save the changes");
                mProg.show();

                String status = mStatus.getEditText().getText().toString();

                mStatusdatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            mProg.dismiss();
                        }else{
                            Toast.makeText(StatusActivity.this,"There was some error in saving changes",Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
