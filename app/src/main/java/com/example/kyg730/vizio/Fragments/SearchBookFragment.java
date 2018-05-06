package com.example.kyg730.vizio.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.kyg730.vizio.Components.Book;
import com.example.kyg730.vizio.Components.DaoSession;
import com.example.kyg730.vizio.R;
import com.example.kyg730.vizio.UI.BookListAdapter;
import com.example.kyg730.vizio.UI.ReaderMainActivity;
import com.example.kyg730.vizio.Users.Reader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koumudi on 13/03/2018.
 */

public class SearchBookFragment extends Fragment {

    private DaoSession daoSession;
    private static List <Book> bookList;
    private Reader reader;
    private static BookListAdapter searchBooksAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        daoSession =((ReaderMainActivity)getActivity()).getDaoSession();
        reader = ((ReaderMainActivity)getActivity()).getReader(); //*************************************
        bookList = new ArrayList<Book>();




    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_reader_search_books, container,false);
        searchBooksAdapter = new BookListAdapter( getActivity(),R.layout.activity_book_item_view,bookList);
        ListView searchBookList = (ListView) view.findViewById(R.id.slist);
        searchBookList.setAdapter(searchBooksAdapter);

        final SearchView simpleSearchView = (SearchView) view.findViewById(R.id.rsbSearchView);
// perform set on query text listener event
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
// do something on text submit
                bookList.clear();

                 reader.searchForABook(query);

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
// do something when text changes
                bookList.clear();
                reader.searchForABook(simpleSearchView.getQuery().toString());



                return false;
            }
        });




        return view;
    }

    public static void setBookList(List<Book> newList){
        bookList.clear();
        for (Book book:newList) {
            bookList.add(book);
        }
        searchBooksAdapter.notifyDataSetChanged();
    }

}
