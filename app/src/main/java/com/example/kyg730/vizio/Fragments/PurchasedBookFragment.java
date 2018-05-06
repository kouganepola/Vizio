package com.example.kyg730.vizio.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.kyg730.vizio.Users.Reader;
import com.example.kyg730.vizio.UI.BookListAdapter;
import com.example.kyg730.vizio.Components.Book;
import com.example.kyg730.vizio.Components.DaoSession;
import com.example.kyg730.vizio.R;
import com.example.kyg730.vizio.UI.ReaderMainActivity;

import java.util.List;

/**
 * Created by Koumudi on 13/03/2018.
 */

public class PurchasedBookFragment extends Fragment {


    private DaoSession daoSession;
    private List <Book> bookList;
    private Reader reader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        daoSession =((ReaderMainActivity)getActivity()).getDaoSession();
        reader = ((ReaderMainActivity)getActivity()).getReader(); //*************************************

        bookList = reader.getPurchasedBooks(daoSession) ;


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_reader_purchased_books, container,false);
        final BookListAdapter purBooksAdapter = new BookListAdapter( getActivity(),R.layout.activity_book_item_view,bookList);
        ListView purBookList = (ListView) view.findViewById(R.id.plist);
        purBookList.setAdapter(purBooksAdapter);

        final SearchView simpleSearchView = (SearchView) view.findViewById(R.id.rpbsearchView);
// perform set on query text listener event
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
// do something on text submit
                bookList.clear();

                List<Book> newList = reader.searchPurchasedBooks(query);
                for (Book book:newList) {
                    bookList.add(book);
                }
                purBooksAdapter.notifyDataSetChanged();

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
// do something when text changes
                bookList.clear();
                List<Book> newList = reader.searchPurchasedBooks("%"+simpleSearchView.getQuery()+"%");
                for (Book book:newList) {
                    bookList.add(book);
                     }
                purBooksAdapter.notifyDataSetChanged();

                return false;
            }
        });


        return view;
    }
}
