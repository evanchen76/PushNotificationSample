package evan.chen.app.pushnotificationsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.result = (TextView) this.findViewById(R.id.result);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            String action_type = extras.getString("ACTION_TYPE");

            if (action_type.equals("Reply")) {
                this.result.setText("Reply");
            } else if (action_type.equals("Delete")) {
                this.result.setText("Delete");
            } else if (action_type.equals("Detail")) {
                this.result.setText("Detail");
            }
        }
    }
}