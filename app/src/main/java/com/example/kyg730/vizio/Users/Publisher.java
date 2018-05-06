package com.example.kyg730.vizio.Users;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.kyg730.vizio.Components.Book;
import com.example.kyg730.vizio.Fragments.SearchBookFragment;
import com.example.kyg730.vizio.HttpClient;
import com.example.kyg730.vizio.UI.PublisherMainActivity;
import com.example.kyg730.vizio.UI.ReaderMainActivity;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Koumudi on 03/05/2018.
 */

public class Publisher extends User implements Parcelable {


    public Publisher(){}
    protected Publisher(Parcel in) {
    }

    public static final Creator<Publisher> CREATOR = new Creator<Publisher>() {
        @Override
        public Publisher createFromParcel(Parcel in) {
            return new Publisher(in);
        }

        @Override
        public Publisher[] newArray(int size) {
            return new Publisher[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    @Override
    public void start(Context context) {
        Intent intent = new Intent(context,PublisherMainActivity.class);
        intent.putExtra("PublisherParcel",this);
        context.startActivity(intent);

    }

    public void addNewBook(Book book){

        RequestParams params = new RequestParams();
        Map<String, String> bookTagMap = Book.getBookTagMap();
        params.put(bookTagMap.get("TAG_NAME"), book.getName());
        params.put(bookTagMap.get("TAG_AUTHOR"), book.getAuthor());
        params.put(bookTagMap.get("TAG_SUMMARY"), book.getSummary());
        params.put(bookTagMap.get("TAG_COST"), book.getCost().toString());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        params.put(bookTagMap.get("TAG_UPDATED_DATE"),dateFormat.format(date));




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


                        String books = jsonObj.getString("Data");
                        if(books.equals("Duplicate value.")){

                            //direct to update content
                        }else {
                            //give msg successfully updated

                        }
                    }catch (JSONException e){

                    }

                } else {
                    Log.e("ServiceHandler", "No data received from HTTP request");

                }

            }

            @Override
            public void onFinish() {

            }
        };

        try {

            Log.d("HTTP","Params READY!!!");
            HttpClient.post("book/add",params,responseHandler);


        }catch (Exception e){

        }


    }

    public void updateBookDetails(){

        //todo
    }
}
