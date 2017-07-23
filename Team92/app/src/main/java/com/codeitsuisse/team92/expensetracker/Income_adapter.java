package com.codeitsuisse.team92.expensetracker;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jay Lohokare on 12-Sep-15.
 */
public class Income_adapter extends ArrayAdapter<ExpenseDetails> {

    AlertDialog alertDialog;

    public static String t;

    // declaring our ArrayList of items
    private List<ExpenseDetails> objects;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * final Cone
    * because it is the list of objects we want to display.
    */

    final Context context = this.getContext();

    public Income_adapter(Context context, int textViewResourceId, List<ExpenseDetails> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */
    public View getView(int position, View convertView, ViewGroup parent){


        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.card_adapter, parent, false);

        }
        final ExpenseDetails i = objects.get(position);



      /*  v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t = i.getCategory().toString();
            }
        });*/
		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */



        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView date = (TextView) v.findViewById(R.id.date);
            TextView amount = (TextView) v.findViewById(R.id.amount);
            TextView to = (TextView) v.findViewById(R.id.to);
            to.setVisibility(View.GONE);
            TextView from = (TextView) v.findViewById(R.id.from);
            from.setVisibility(View.GONE);






            // check to see if each individual textview is null.
            // if not, assign some text!

            ImageView icon = (ImageView) v.findViewById(R.id.imageView);
            //  icon.setImageResource(R.drawable.food_icon);


            String cat = String.valueOf(i.getCategory());
            if(cat == "food")
            {
                icon.setImageResource(R.drawable.food_icon);

            }
            if(cat == "entertainment")
            {
                icon.setImageResource(R.drawable.entertainment_icon);
            }
            if(cat == "shopping")
            {
                icon.setImageResource(R.drawable.shopping_icon);
            }



            if (date != null){
                date.setText( i.getDate() + "-" +  i.getMonth() + "-" +  i.getYear() + " ");
            }
            try{
                if (amount != null){
                    amount.setText("Rs. " + String.valueOf(i.getAmount()));
                }
            }
            catch(Exception e){

            }




        }

        // the view must be returned to our activity
        return v;

    }

}



