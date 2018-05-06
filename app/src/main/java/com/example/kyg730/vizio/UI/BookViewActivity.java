package com.example.kyg730.vizio.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyg730.vizio.Components.Book;
import com.example.kyg730.vizio.R;
import com.example.kyg730.vizio.Users.Reader;

public class BookViewActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);

        //initialize UI components
        ImageView imageView = (ImageView) findViewById(R.id.bkViewImageView);
        TextView tbName = (TextView)findViewById(R.id.bkViewtbBookName);
        TextView tbAuthor = (TextView)findViewById(R.id.bkViewtbAuthor);
        TextView tbPublisher = (TextView)findViewById(R.id.bkViewtbPublisher);
        TextView tbCost = (TextView)findViewById(R.id.bkViewtbCost);
        TextView tbDesc = (TextView)findViewById(R.id.bkViewtbdesc);
        Button download = (Button)findViewById(R.id.btnDownload);

        //get data to fill UI
        final Book book = getIntent().getParcelableExtra("bookParcel");
        tbName.setText(book.getName());
        tbAuthor.setText(book.getAuthor());
        tbPublisher.setText(book.getPublisher());
        tbDesc.setText(book.getSummary());
        final Reader reader = getIntent().getParcelableExtra("readerParcel");

        //TODO set image
        //tbName.setText(intent.getStringExtra("CoverImagePath"));
        tbCost.setText("Cost : $"+book.getCost());

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showProgressDialog();
                //todo add downloading ar content and images
                //try adding a progress bar
                reader.download(getBaseContext(),book);

            }
        });




    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }

    }
}
