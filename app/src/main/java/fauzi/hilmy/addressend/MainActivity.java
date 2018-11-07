package fauzi.hilmy.addressend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edtStuff)
    TextInputEditText edtStuff;
    @BindView(R.id.btnAddress)
    Button btnAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAddress)
    public void onViewClicked() {
        String stuff = edtStuff.getText().toString();
        if (stuff != null){
            Intent i = new Intent(this, AddressActivity.class);
            i.putExtra("stuff", stuff);
            startActivity(i);
        } else {
            edtStuff.setError("Please Enter Stuff you will buy here!!");
            edtStuff.requestFocus();
        }
    }
}
