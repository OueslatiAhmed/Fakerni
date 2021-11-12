package tn.esprit.fakerni.Controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.esprit.fakerni.Dao.ToDoDao;
import tn.esprit.fakerni.Entity.ToDo;
import tn.esprit.fakerni.R;
import tn.esprit.fakerni.Util.AppDatabase;
import tn.esprit.fakerni.Util.MyExpandableListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {

    AppDatabase db;

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> taskCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_page, container, false);
        db = Room.databaseBuilder(v.getContext().getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        createGroupList();
        createCollection();
        expandableListView = v.findViewById(R.id.expList);
        expandableListAdapter = new MyExpandableListAdapter(v.getContext(), groupList, taskCollection);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i,i1).toString();
                Toast.makeText(getActivity().getApplicationContext(), "Selected: " + selected, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return v;
    }

    private void createCollection() {
        ToDoDao toDoDao = db.toDoDao();
        List<String> list = new ArrayList<String>();
        for (ToDo toDo : toDoDao.getAllUrgent()) {
            list.add(toDo.getName());
        }
        String[] urgent = list.toArray(new String[0]);

        list = new ArrayList<String>();
        for (ToDo toDo : toDoDao.getAllHigh()) {
            list.add(toDo.getName());
        }
        String[] high = list.toArray(new String[0]);

        list = new ArrayList<String>();
        for (ToDo toDo : toDoDao.getAllLow()) {
            list.add(toDo.getName());
        }
        String[] low = list.toArray(new String[0]);
        taskCollection = new HashMap<String, List<String>>();
        for(String group : groupList){
            if (group.equals("Urgent")){
                loadChild(urgent);
            } else if (group.equals("High"))
                loadChild(high);
            else if (group.equals("Low"))
                loadChild(low);
            taskCollection.put(group, childList);
        }
    }

    private void loadChild(String[] taskModels) {
        childList = new ArrayList<>();
        for(String model : taskModels){
            childList.add(model);
        }
    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add("Urgent");
        groupList.add("High");
        groupList.add("Low");
    }

}