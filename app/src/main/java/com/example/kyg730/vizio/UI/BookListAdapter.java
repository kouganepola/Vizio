package com.example.kyg730.vizio.UI;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyg730.vizio.Components.Book;
import com.example.kyg730.vizio.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView_bookcover);
        TextView name = (TextView) rowView.findViewById(R.id.textView_book_name);
        TextView data =(TextView)rowView.findViewById(R.id.textView_data);

        try {
            //TODO
            File f=new File(getContext().getFilesDir(), "Aladin.jpg");


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f),null,options);
            b.createScaledBitmap(b, 70, 70, true);


            imageView.setImageBitmap(b);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        name.setText(books.get(position).getName());
        data.setText(books.get(position).getAuthor());
        return rowView;

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
