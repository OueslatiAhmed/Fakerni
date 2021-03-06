package tn.esprit.fakerni.Util;

import static tn.esprit.fakerni.R.color.high;
import static tn.esprit.fakerni.R.color.low;
import static tn.esprit.fakerni.R.color.urgent;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import tn.esprit.fakerni.R;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, List<String>> taskCollection;
    private List<String> groupList;

    public MyExpandableListAdapter(Context context, List<String> groupList,
                                   Map<String, List<String>> taskCollection){
        this.context = context;
        this.taskCollection = taskCollection;
        this.groupList = groupList;
    }

    @Override
    public int getGroupCount() {
        return taskCollection.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return taskCollection.get(groupList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return taskCollection.get(groupList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String level = getGroup(i).toString();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_task, null);
        }
        TextView item = view.findViewById(R.id.task);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(level);
        if (level.equals("Urgent")) {
            item.setTextColor(urgent);
        }
        else if (level.equals("High")) {
            item.setTextColor(high);
        }
        else if (level.equals("Low")) {
            item.setTextColor(low);
        }
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        String model = getChild(i, i1).toString();
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.task, null);
        }
        TextView item = view.findViewById(R.id.txtTask);
      //  ImageView delete = view.findViewById(R.id.delete);

        item.setText(model);

       /* delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        List<String> child = mobileCollection.get(groupList.get(i));
                        child.remove(i1);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });*/
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}