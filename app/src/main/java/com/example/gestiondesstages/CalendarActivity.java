package com.example.gestiondesstages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.gestiondesstages.DataBase.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements WeekView.EventClickListener{
    private WeekView mWeekView;
    private DataBaseHelper dataBaseHelper;
    ArrayList<Integer> listTag;
    FloatingActionButton btnQuitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        dataBaseHelper = new DataBaseHelper(this);
        mWeekView = new WeekView(this);
        btnQuitter = findViewById(R.id.btnQuitterCal);
        // Get a reference for the week view in the layout.
        mWeekView = findViewById(R.id.weekView);

        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWeekView.setEmptyViewClickListener(new WeekView.EmptyViewClickListener() {
            @Override
            public void onEmptyViewClicked(Calendar time) {

                Intent intent = new Intent(CalendarActivity.this, AjouterStageActivity.class);
                startActivity(intent);

            }
        });
        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                Toast.makeText(getApplicationContext(), "EventClick" + event.getId() , Toast.LENGTH_SHORT).show();

                //id du event est le meme que celui du stage
                Intent i = new Intent(CalendarActivity.this, ModifierStageDuCalendrier.class);
                startActivity(i);
            }
        });

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                // Populate the week view with some events.
                // List<WeekViewEvent> events = getEvents(newYear, newMonth);

                List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

                Bundle bundle = getIntent().getExtras();
                if (bundle == null) {
                    return events;
                }
                    listTag = (ArrayList<Integer>) bundle.getIntegerArrayList("listTag");


                    int journeeInt = 1;

                    //Boucle pour rajouter les stages dans le calendrier depuis la map
                    for (int i = 0; i < listTag.size(); i++) {

                        String heure = dataBaseHelper.getUneVisite(listTag.get(i)).getHeureDebutVisite();
                        String journee = dataBaseHelper.getUneVisite(listTag.get(i)).getJourneeVisite();


                        if (journee.contains("Mercredi")) {
                            journeeInt = 4;
                        }
                        if (journee.contains("Jeudi")) {
                            journeeInt = 5;
                        }
                        if (journee.contains("Vendredi")) {
                            journeeInt = 6;
                        }


                        Calendar startTime = Calendar.getInstance();
                        startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(heure.substring(0, 2)));
                        startTime.set(Calendar.DAY_OF_WEEK, journeeInt);
                        startTime.set(Calendar.MINUTE, 0);
                        startTime.set(Calendar.MONTH, newMonth - 1);
                        startTime.set(Calendar.YEAR, newYear);
                        Calendar endTime = (Calendar) startTime.clone();
                        endTime.add(Calendar.HOUR, 1);
                        endTime.set(Calendar.MONTH, newMonth - 1);
                        WeekViewEvent event = new WeekViewEvent(listTag.get(i), "STAGE " + (i+1), startTime, endTime);//id du event est le meme que celui du stage grace a listTag.get(i)
                        event.setColor(getResources().getColor(R.color.BlueApp));
                        events.add(event);
                    }


                    return events;
                }

        });

// Set long press listener for events.
        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
                Toast.makeText(getApplicationContext(), "onEventLongPress", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

    }
}