package sg.edu.sutd.grapher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class Graph extends AppCompatActivity {
    Firebase mRootRef;
    LineChartView mChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);
        mRootRef = new Firebase("https://smartbin-16031.firebaseio.com/");
        mChartView = (LineChartView)findViewById(R.id.chart);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Firebase classificationRef = mRootRef.child("Bin Classification For Graph");
        classificationRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Line> lines = new ArrayList<Line>();
                Map<String, Map<String, String>> mapMap = dataSnapshot.getValue(Map.class);
                Object[] strings;
                for (Map<String, String> stringStringMap : mapMap.values()) {
                    List<PointValue> pointValueArrayList = new ArrayList<PointValue>();
                    strings = stringStringMap.keySet().toArray();
                    Arrays.sort(strings);
                    for (Object s : strings) {
                        pointValueArrayList.add(new PointValue(Float.parseFloat(s.toString().substring(11, 13)) * 100 + Float.parseFloat(s.toString().substring(14, 16)), Float.parseFloat(stringStringMap.get(s))));
                    }
                    Line line = new Line(pointValueArrayList);
                    lines.add(line);
                }
                LineChartData lineChartData = new LineChartData();
                lineChartData.setLines(lines);
                Axis axisX = new Axis().setHasTiltedLabels(true);
                Axis axisY = new Axis();
                axisX.setName("Time");
                axisY.setName("Fill");
                lineChartData.setAxisXBottom(axisX);
                lineChartData.setAxisYLeft(axisY);
                mChartView.setLineChartData(lineChartData);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}