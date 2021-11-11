package tn.esprit.fakerni.Controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.fakerni.Dao.NotificationDao;
import tn.esprit.fakerni.Entity.Notification;
import tn.esprit.fakerni.R;
import tn.esprit.fakerni.Util.AppDatabase;
import tn.esprit.fakerni.Util.MyRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    AppDatabase db;

    RecyclerView recyclerView;
    ArrayList<String> s1, s2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        db = Room.databaseBuilder(v.getContext().getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
        s1 = new ArrayList<String>();
        s2 = new ArrayList<String>();

        loadData(s1, s2);

        recyclerView = v.findViewById(R.id.myRecyclerView);

        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(v.getContext(), s1, s2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        return v;
    }

    private void loadData(ArrayList<String> ss1, ArrayList<String> ss2) {
        NotificationDao notificationDao = db.notificationDao();
        List<Notification> notifications = notificationDao.getAll();
        for (Notification notif : notifications) {
            if(notif.getDate().getTime() < System.currentTimeMillis()) {
                ss1.add(notif.getTitle());
                ss2.add(notif.getDescription());
            }
        }

    }
}