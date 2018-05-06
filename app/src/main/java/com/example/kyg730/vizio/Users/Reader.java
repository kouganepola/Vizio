package com.example.kyg730.vizio.Users;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import com.example.kyg730.vizio.Components.Book;
import com.example.kyg730.vizio.Components.BookDao;
import com.example.kyg730.vizio.Components.DaoMaster;
import com.example.kyg730.vizio.Components.DaoSession;
import com.example.kyg730.vizio.Fragments.LatestBooksFragment;
import com.example.kyg730.vizio.Fragments.PurchasedBookFragment;
import com.example.kyg730.vizio.Fragments.SearchBookFragment;
import com.example.kyg730.vizio.HttpClient;
import com.example.kyg730.vizio.HttpClientN;
import com.example.kyg730.vizio.UI.BookViewActivity;
import com.example.kyg730.vizio.UI.ReaderMainActivity;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Koumudi on 29/04/2018.
 */

public class Reader extends User implements Parcelable{

    private BookDao bookDao;
    private Query<Book> bookQuery;






    public Reader() {


    }

    @Override
    public void start(Context context) {
        Intent intent = new Intent(context,ReaderMainActivity.class);
        intent.putExtra("ReaderParcel",this);
        context.startActivity(intent);

    }



    protected Reader(Parcel in) {
        //todo add reader variables here
    }

    public static final Creator<Reader> CREATOR = new Creator<Reader>() {
        @Override
        public Reader createFromParcel(Parcel in) {
            return new Reader(in);
        }

        @Override
        public Reader[] newArray(int size) {
            return new Reader[size];
        }
    };

    public List<Book> getPurchasedBooks (DaoSession daoSession) {

                // query all notes, sorted a-z by their text

        bookDao = daoSession.getBookDao();
        bookQuery = bookDao.queryBuilder().orderAsc(BookDao.Properties.Acquired_Date).build();

        return bookQuery.list();
    }

    public List<Book> searchPurchasedBooks (String query){

            bookQuery = bookDao.queryBuilder().whereOr(BookDao.Properties.Name.like(query), BookDao.Properties.Author.like(query), BookDao.Properties.Publisher.like(query)).orderAsc(BookDao.Properties.Acquired_Date).build();
            return bookQuery.list();

    }



  public void searchForABook( String query){

        RequestParams params = new RequestParams();
        params.put("searchQuery", query);


        TextHttpResponseHandler responseHandler = new TextHttpResponseHandler() {
            List<Book> bookList= new ArrayList<>();

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.v("Success","Error status :"+statusCode);
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, String response) {
                    bookList.clear();
                    if (response != null) {
                        try {

                            JSONObject jsonObj = new JSONObject(response);


                            JSONArray books = jsonObj.getJSONArray("available_books");

// looping through All Books
                            for (int i = 0; i < books.length(); i++) {
                                JSONObject book = books.getJSONObject(i);
                                Map<String, String> bookTagMap = Book.getBookTagMap();
                                String bookID = book.getString(bookTagMap.get("TAG_BOOK_ID"));
                                String name = book.getString(bookTagMap.get("TAG_NAME"));
                                String author = book.getString(bookTagMap.get("TAG_AUTHOR"));
                                String updatedDate = book.getString(bookTagMap.get("TAG_UPDATED_DATE"));
                                String summary = book.getString(bookTagMap.get("TAG_SUMMARY"));
                                String coverImagePath =book.getString(bookTagMap.get("TAG_COVER_IMAGE_PATH"));
                                String publisherName =book.getString(bookTagMap.get("TAG_PUBLISHER_NAME"));
                                String cost =book.getString(bookTagMap.get("TAG_COST"));

                                Book searchedBook = new Book(Long.parseLong(bookID),name,author,updatedDate,summary,coverImagePath,publisherName,cost);

// adding books to booklist
                                bookList.add(searchedBook);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    } else {
                        Log.e("ServiceHandler", "No data received from HTTP request");

                    }

                }

            @Override
            public void onFinish() {
                // once request is executed call for changes in UI
                SearchBookFragment.setBookList(bookList);
            }
        };

        try {

            Log.d("HTTP","Params READY!!!");
            HttpClient.get("book/search",params,responseHandler);


        }catch (Exception e){

        }



    }

    public void getLatestBooks(){

            TextHttpResponseHandler responseHandler = new TextHttpResponseHandler() {
            List<Book> bookList= new ArrayList<>();

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.v("Success","Error status :"+statusCode);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                bookList.clear();
                if (response != null) {
                    try {

                        JSONObject jsonObj = new JSONObject(response);


                        JSONArray books = jsonObj.getJSONArray("latest_books");

// looping through All Books
                        for (int i = 0; i < books.length(); i++) {
                            JSONObject book = books.getJSONObject(i);
                            Map<String, String> bookTagMap = Book.getBookTagMap();
                            String bookID = book.getString(bookTagMap.get("TAG_BOOK_ID"));
                            String name = book.getString(bookTagMap.get("TAG_NAME"));
                            String author = book.getString(bookTagMap.get("TAG_AUTHOR"));
                            String updatedDate = book.getString(bookTagMap.get("TAG_UPDATED_DATE"));
                            String summary = book.getString(bookTagMap.get("TAG_SUMMARY"));
                            String coverImagePath =book.getString(bookTagMap.get("TAG_COVER_IMAGE_PATH"));
                            String publisherName =book.getString(bookTagMap.get("TAG_PUBLISHER_NAME"));
                            String cost =book.getString(bookTagMap.get("TAG_COST"));

                            Book searchedBook = new Book(Long.parseLong(bookID),name,author,updatedDate,summary,coverImagePath,publisherName,cost);

// adding books to booklist
                            bookList.add(searchedBook);


                        }
                        Log.v("fff","www"+bookList.size());

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                } else {
                    Log.e("ServiceHandler", "No data received from HTTP request");

                }

            }

            @Override
            public void onFinish() {
                // once request is executed call for changes in UI
                LatestBooksFragment.setBookList(bookList);
            }
        };

        try {

            Log.d("HTTP","Params READY!!!");
            HttpClient.get("book/latest_books",responseHandler);


        }catch (Exception e){

        }



    }

    public void download(Context context, Book book){

        DaoSession daoSession = DaoMaster.newDevSession(context,"Vizio-db");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        //todo download imaeand create pat
        book.setCoverImagePath(book.getName()+".jpg");
        book.setAcquired_Date(dateFormat.format(date));
        daoSession.getBookDao().insertOrReplace(book);



    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }




}
