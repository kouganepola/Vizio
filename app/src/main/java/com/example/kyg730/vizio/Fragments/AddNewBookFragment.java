package com.example.kyg730.vizio.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.kyg730.vizio.Components.Book;
import com.example.kyg730.vizio.R;
import com.example.kyg730.vizio.UI.PublisherMainActivity;
import com.example.kyg730.vizio.Users.Publisher;

/**
 * Created by Koumudi on 03/05/2018.
 */

public class AddNewBookFragment extends Fragment {

    private Publisher publisher;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        publisher = ((PublisherMainActivity)getActivity()).getPublisher();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_publisher_add_new_book, container,false);
        final EditText inputName = (EditText) view.findViewById(R.id.anbetName);
        final EditText inputAuthor = (EditText) view.findViewById(R.id.anbetAuthor);
        final EditText inputSummary = (EditText) view.findViewById(R.id.anbetSummary);
        final EditText inputCost = (EditText) view.findViewById(R.id.anbetCost);
        Button btnUpload = (Button)view.findViewById(R.id.btnUpload);

        //todo validate inputs
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setName(inputName.getText().toString());
                book.setAuthor(inputAuthor.getText().toString());
                book.setSummary(inputSummary.getText().toString());
                book.setCost(inputCost.getText().toString());
                publisher.addNewBook(book);
            }
        });
        return view;
    }
}
