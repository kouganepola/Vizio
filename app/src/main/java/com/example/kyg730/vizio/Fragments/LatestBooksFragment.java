package com.example.kyg730.vizio.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kyg730.vizio.UI.BookListAdapter;
import com.example.kyg730.vizio.Components.Book;
import com.example.kyg730.vizio.Components.BookDao;
import com.example.kyg730.vizio.Components.DaoSession;
import com.example.kyg730.vizio.R;
import com.example.kyg730.vizio.UI.ReaderMainActivity;
import com.example.kyg730.vizio.Users.Reader;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koumudi on 13/03/2018.
 */

public class LatestBooksFragment extends Fragment {

    private DaoSession daoSession;
    private static List<Book> bookList;
    private Reader reader;
    private static BookListAdapter latestBooksAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daoSession =((ReaderMainActivity)getActivity()).getDaoSession();
        reader = ((ReaderMainActivity)getActivity()).getReader(); //*************************************
        bookList= new ArrayList<>();
        reader.getLatestBooks();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_reader_latest_books, container, false);
        latestBooksAdapter = new BookListAdapter((ReaderMainActivity) getActivity(),R.layout.activity_book_item_view,bookList);
        ListView latestBookList = (ListView) view.findViewById(R.id.llist);
        latestBookList.setAdapter(latestBooksAdapter);




        return view;
    }

    public static void setBookList(List<Book> newList){
        for (Book book:newList) {
            bookList.add(book);
        }
        latestBooksAdapter.notifyDataSetChanged();


    }


}
