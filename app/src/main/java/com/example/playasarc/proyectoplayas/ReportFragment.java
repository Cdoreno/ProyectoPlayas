package com.example.playasarc.proyectoplayas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by Ramon on 28/02/2018.
 */

public class ReportFragment extends Fragment {

    private static final String ARG_BeachReport_ID = "BeachReport_id";

    private BeachReport mBeachReport;


    public static ReportFragment newInstance(UUID BeachReportId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BeachReport_ID, BeachReportId);
        ReportFragment fragment = new ReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID beachReportId = (UUID) getArguments().getSerializable(ARG_BeachReport_ID);
        mBeachReport = BeachReportManager.get(getActivity()).getOneBeachReports(beachReportId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_beach, container, false);
        TextView tvDate = v.findViewById(R.id.textViewDate);
        TextView tvTemp = v.findViewById(R.id.textView);
        TextView tvFlag = v.findViewById(R.id.textView2);
        TextView tvAflu = v.findViewById(R.id.textView3);

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
        String dateCorrect = formatter.format(mBeachReport.getmDate());

        tvDate.setText(dateCorrect);
        tvTemp.setText(mBeachReport.getmTemperature() + " ºC");
        tvFlag.setText(mBeachReport.getmColorFlag());
        tvAflu.setText(mBeachReport.getmAfluencia());

        return v;
    } 
}
