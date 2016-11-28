package net.serkanbal.externalcontentproviderlab;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by Serkan on 28/11/16.
 */

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.CalendarViewHolder> {
    private List<CalendarObject> mCalendarObjectList;

    public CalendarRecyclerAdapter(List<CalendarObject> calendarObjectList) {
        mCalendarObjectList = calendarObjectList;
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CalendarViewHolder(inflater.inflate(android.R.layout.simple_list_item_2,parent,false));
    }

    @Override
    public void onBindViewHolder(final CalendarViewHolder holder, int position) {
        holder.mNameTextView.setText(mCalendarObjectList.get(position).getTitle());

        long unixDateStamp = mCalendarObjectList.get(position).getDate();

        String startTime = DateFormat.getDateInstance(DateFormat.SHORT).format(unixDateStamp);

        holder.mDateTextView.setText(startTime);

        holder.mNameTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                long id = mCalendarObjectList.get(holder.getAdapterPosition()).getId();
                Uri uriWithId = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);

                ContentResolver contentResolver = view.getContext().getContentResolver();
                contentResolver.delete(uriWithId, null, null);
                mCalendarObjectList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCalendarObjectList.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView mNameTextView, mDateTextView;

        public CalendarViewHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView)itemView.findViewById(android.R.id.text1);
            mDateTextView = (TextView)itemView.findViewById(android.R.id.text2);
        }
    }

    public void swapData(Cursor cursor){
        mCalendarObjectList.clear();

        if(cursor != null && cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                String title = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
                long date = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
                long id = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events._ID));
                mCalendarObjectList.add(new CalendarObject(id,title,date));
                cursor.moveToNext();
            }
        }
        notifyDataSetChanged();
    }

}
