package tn.esprit.fakerni.Controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import tn.esprit.fakerni.R;
import tn.esprit.fakerni.Util.MyExpandableListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {

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
        String[] samsungModels = {"Samsung Galaxy M21", "Samsung Galaxy F41",
                "Samsung Galaxy M51", "Samsung Galaxy A50s"};
        String[] googleModels = {"Pixel 4 XL", "Pixel 3a", "Pixel 3 XL", "Pixel 3a XL",
                "Pixel 2", "Pixel 3"};
        String[] redmiModels = {"Redmi 9i", "Redmi Note 9 Pro Max", "Redmi Note 9 Pro"};
        String[] vivoModels = {"Vivo V20", "Vivo S1 Pro", "Vivo Y91i", "Vivo Y12"};
        String[] nokiaModels = {"Nokia 5.3", "Nokia 2.3", "Nokia 3.1 Plus"};
        String[] motorolaModels = { "Motorola One Fusion+", "Motorola E7 Plus", "Motorola G9"};
        taskCollection = new HashMap<String, List<String>>();
        for(String group : groupList){
            if (group.equals("Samsung")){
                loadChild(samsungModels);
            } else if (group.equals("Google"))
                loadChild(googleModels);
            else if (group.equals("Redmi"))
                loadChild(redmiModels);
            else if (group.equals("Vivo"))
                loadChild(vivoModels);
            else if (group.equals("Nokia"))
                loadChild(nokiaModels);
            else
                loadChild(motorolaModels);
            taskCollection.put(group, childList);
        }
    }

    private void loadChild(String[] mobileModels) {
        childList = new ArrayList<>();
        for(String model : mobileModels){
            childList.add(model);
        }
    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add("Samsung");
        groupList.add("Google");
        groupList.add("Redmi");
        groupList.add("Vivo");
        groupList.add("Nokia");
        groupList.add("Motorola");
    }

}