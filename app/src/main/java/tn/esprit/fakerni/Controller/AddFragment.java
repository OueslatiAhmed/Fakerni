package tn.esprit.fakerni.Controller;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tn.esprit.fakerni.Dao.NotificationDao;
import tn.esprit.fakerni.Dao.ToDoDao;
import tn.esprit.fakerni.Entity.Notification;
import tn.esprit.fakerni.Entity.ToDo;
import tn.esprit.fakerni.R;
import tn.esprit.fakerni.Util.AppDatabase;
import tn.esprit.fakerni.Util.ReminderBroadcast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    Button button;
    AppDatabase db;
    Spinner spinner;
    EditText etDate;
    TextView tvDate;
    TextView etTask;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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
        View v = inflater.inflate(R.layout.fragment_add, container, false);

        spinner = v.findViewById(R.id.spTask);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(), tn.esprit.fakerni.R.array.spinner_task, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        etTask = v.findViewById(R.id.etTask);

        tvDate = v.findViewById(R.id.tvDate);
        etDate = v.findViewById(R.id.etDate);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        etDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        etDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        createNotificationChannel(v.getContext());
        db = Room.databaseBuilder(v.getContext().getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
        NotificationDao notificationDao = db.notificationDao();
        ToDoDao toDoDao = db.toDoDao();

        button = v.findViewById(R.id.btnTest);
        button.setOnClickListener(v1 -> {
            Toast.makeText(v.getContext(), getString(R.string.task_added), Toast.LENGTH_SHORT).show();

            boolean skip = true;
            String sDate1 = etDate.getText().toString();
            Date date1 = new Date();
            Date newDate = new Date();
            try {
                if(!sDate1.equals("")) {
                    skip = false;
                    date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!skip) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(date1);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(new Date());
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String currentdate =  sdf.format(calendar2.getTime());
                String hh = currentdate.split(":")[0];
                String mm = currentdate.split(":")[1];
                String ss = currentdate.split(":")[2];
                calendar1.add(Calendar.HOUR_OF_DAY, Integer.parseInt(hh));
                calendar1.add(Calendar.MINUTE, Integer.parseInt(mm));
                calendar1.add(Calendar.SECOND, Integer.parseInt(ss));
                newDate = calendar1.getTime();
            }

            String level = spinner.getSelectedItem().toString();
            if(level.equals("Haut")) {
                level = "High";
            }
            else if (level.equals("Faible")) {
                level = "Low";
            }

            ToDo toDo = new ToDo();
            toDo.setName(etTask.getText().toString());
            toDo.setLevel(level);
            toDo.setDate(newDate);
            toDoDao.insert(toDo);

            Intent intent = new Intent(getActivity(), ReminderBroadcast.class);
            Notification notification = new Notification();
            notification.setDate(newDate);
            notification.setTitle(getString(R.string.notification_title));
            notification.setDescription(getString(R.string.task_string)+": "+toDo.getName());
            int notificationId = (int) notificationDao.insert(notification);
            intent.putExtra("NOTIFICATION_ID", notificationId);
            intent.putExtra("CONTENT_TITLE", notification.getTitle());
            intent.putExtra("CONTENT_TEXT", notification.getDescription());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(v.getContext(), 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) v.getContext().getSystemService(Context.ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, notification.getDate().getTime()+1000, pendingIntent);
        });
        return v;
    }

    private void createNotificationChannel(Context context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LembitReminderChannel";
            String description = "Channel for Lemubit Reminder";
            int importation = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLembit", name, importation);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}