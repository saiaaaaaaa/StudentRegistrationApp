package com.salazarisaiahnoel.studentregistrationapp.fragments;

import static android.widget.LinearLayout.VERTICAL;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.saiaaaaaaa.mywebsite_androiddependency.EasySQL;
import com.github.saiaaaaaaa.mywebsite_androiddependency.RoundedAlertDialog;
import com.salazarisaiahnoel.studentregistrationapp.R;

public class HomeFragment extends Fragment {

    EditText studentId, lastName, firstName, middleName, birthday;
    ImageView studentIdSelector, bdaySelector;
    RadioButton rbMale, rbFemale;
    Button btnRegister, btnUpdate, btnDelete;
    
    final String db = "sra_db";
    final String table = "sra_table";
    final String[] columns = {"student_id:text", "last_name:text", "first_name:text", "middle_name:text", "birthday:text", "gender:text"};

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        studentId = view.findViewById(R.id.student_id);
        lastName = view.findViewById(R.id.last_name);
        firstName = view.findViewById(R.id.first_name);
        middleName = view.findViewById(R.id.middle_name);
        birthday = view.findViewById(R.id.birthday);
        studentIdSelector = view.findViewById(R.id.student_id_selector);
        bdaySelector = view.findViewById(R.id.bday_selector);
        rbMale = view.findViewById(R.id.rb_male);
        rbFemale = view.findViewById(R.id.rb_female);
        btnRegister = view.findViewById(R.id.btn_register);
        btnUpdate = view.findViewById(R.id.btn_update);
        btnDelete = view.findViewById(R.id.btn_delete);

        EasySQL easySQL = new EasySQL(requireContext());
        if (!easySQL.doesTableExist(db, table)){
            easySQL.createTable(db, table, columns);
        }

        DisplayMetrics dm = requireContext().getResources().getDisplayMetrics();
        int padding24 = (int)(24 * dm.density);
        int padding8 = (int)(8 * dm.density);
        TypedValue typedValue = new TypedValue();
        requireContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedValue, true);
        int drawableResId = typedValue.resourceId;

        studentIdSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = new LinearLayout(requireContext());
                ll.setOrientation(VERTICAL);

                RoundedAlertDialog rad = new RoundedAlertDialog(requireContext());

                for (String[] a : easySQL.getTableValuesAsArray(db, table)){
                    TextView tv = new TextView(requireContext());
                    tv.setText(a[0].split(":")[1].replace("'", ""));
                    tv.setTextColor(requireContext().getColor(R.color.black));
                    tv.setBackgroundResource(drawableResId);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            studentId.setText(a[0].split(":")[1].replace("'", ""));
                            lastName.setText(a[1].split(":")[1].replace("'", ""));
                            firstName.setText(a[2].split(":")[1].replace("'", ""));
                            middleName.setText(a[3].split(":")[1].replace("'", ""));
                            birthday.setText(a[4].split(":")[1].replace("'", ""));
                            if (a[5].split(":")[1].replace("'", "").equals("Male")){
                                rbMale.setChecked(true);
                                rbFemale.setChecked(false);
                            } else {
                                rbMale.setChecked(false);
                                rbFemale.setChecked(true);
                            }
                            rad.hide();
                        }
                    });
                    tv.setPadding(padding24, padding8, padding24, padding8);
                    ll.addView(tv);
                }

                rad.setTitle("Student ID")
                        .addView(ll)
                        .show();
            }
        });

        bdaySelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(requireContext());
                dpd.show();

                dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        birthday.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easySQL.insertToTable(db, table, new String[]{"student_id:" + studentId.getText().toString(), "last_name:" + lastName.getText().toString(), "first_name:" + firstName.getText().toString(), "middle_name:" + middleName.getText().toString(), "birthday:" + birthday.getText().toString(), "gender:" + (rbMale.isChecked() ? "Male" : "Female")});
                Toast.makeText(requireContext(), "Register success.", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easySQL.deleteFromTable(db, table, "student_id:" + studentId.getText().toString());
                easySQL.insertToTable(db, table, new String[]{"student_id:" + studentId.getText().toString(), "last_name:" + lastName.getText().toString(), "first_name:" + firstName.getText().toString(), "middle_name:" + middleName.getText().toString(), "birthday:" + birthday.getText().toString(), "gender:" + (rbMale.isChecked() ? "Male" : "Female")});
                Toast.makeText(requireContext(), "Update success.", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easySQL.deleteFromTable(db, table, "student_id:" + studentId.getText().toString());
                Toast.makeText(requireContext(), "Delete success.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}