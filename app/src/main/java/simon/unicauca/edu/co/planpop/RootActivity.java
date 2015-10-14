package simon.unicauca.edu.co.planpop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseUser;

public class RootActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        getSupportActionBar().hide();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "8ladccmIM9sV7Fj0LMWBj2nIwIbz0ZtPJffGxO8M", "7X6yfM9fkrif2EQ1JytvKJhrreB3MAUkFTGPCnLX");
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            intent = new Intent(RootActivity.this,MainActivity.class);
        } else {
            intent = new Intent(RootActivity.this,LoginActivity.class);
        }
        startActivity(intent);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_root, menu);
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
