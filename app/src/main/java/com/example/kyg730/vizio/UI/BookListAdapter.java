package com.example.kyg730.vizio.UI;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kyg730.vizio.Components.Book;
import com.example.kyg730.vizio.R;

import java.util.List;

/**
 * Created by Koumudi on 13/03/2018.
 */

public class BookListAdapter extends ArrayAdapter<Book>{

    private final Context context;
    private List<Book> books;
    public BookListAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
        this.context = context;
        this.books = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_book_item_view, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.textView_book_name);
        TextView data =(TextView)rowView.findViewById(R.id.textView_data);
        name.setText(books.get(position).getName());
        data.setText(books.get(position).getAuthor());
        return rowView;

    }


}
