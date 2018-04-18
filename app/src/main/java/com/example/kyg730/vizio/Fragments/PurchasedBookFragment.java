package com.example.kyg730.vizio.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import org.greenrobot.greendao.query.Query;

/**
 * Created by Koumudi on 13/03/2018.
 */

public class PurchasedBookFragment extends Fragment {

    private BookDao bookDao;
    private Query<Book> bookQuery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        DaoSession daoSession =((ReaderMainActivity)getActivity()).getDaoSession();
        bookDao = daoSession.getBookDao();

        // query all notes, sorted a-z by their text
        bookQuery = bookDao.queryBuilder().orderAsc(BookDao.Properties.Acquired_Date).build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_reader_purchased_books, container,false);
        BookListAdapter latestBooksAdapter = new BookListAdapter((ReaderMainActivity) getActivity(),R.layout.activity_book_item_view,bookQuery.list());
        ListView latestBookList = (ListView) view.findViewById(R.id.plist);
        latestBookList.setAdapter(latestBooksAdapter);


        return view;
    }
}
