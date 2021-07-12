package sg.edu.rp.c346.id16014507.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etTask, etDate;
    Button btnInsert, btnGetTasks;
    TextView tvResults;
    ListView lv;
    ArrayList<Task> al;
    ArrayAdapter<Task> aa;
    boolean asc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTask = findViewById(R.id.etTask);
        etDate = findViewById(R.id.etDate);
        tvResults = findViewById(R.id.tvResults);
        lv = findViewById(R.id.lv);

        btnInsert = findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                db.insertTask(etTask.getText().toString(), etDate.getText().toString());
            }
        });

        btnGetTasks = findViewById(R.id.btnGetTasks);
        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper db = new DBHelper(MainActivity.this);
                ArrayList<String> data = db.getTaskContent();
                db.close();
                String txt = "";
                for(int i = 0; i < data.size(); i++) {
                    txt += i + ". " + data.get(i) + "\n";
                }

                tvResults.setText(txt);

                DBHelper db2 = new DBHelper(MainActivity.this);
                al = db2.getTasks(asc);
                db2.close();
                asc = !asc;

                aa = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,al);
                lv.setAdapter(aa);
                
                al.clear();
                al.addAll(db.getTasks(asc));
                aa.notifyDataSetChanged();

            }
        });

    }
}