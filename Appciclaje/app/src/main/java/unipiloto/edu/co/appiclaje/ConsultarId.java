package unipiloto.edu.co.appiclaje;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConsultarId extends AppCompatActivity {
    private ListView listView;
    private FirebaseAuth firbaseAuth;
    private String nickname;
    private List<String> listaId;
    public int cont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_solicitud);
        Intent intent = getIntent();
        cont =0;
        nickname = intent.getStringExtra("nickname");
        listView = (ListView) findViewById(R.id.listId);
        listaId = new ArrayList<>();
        AdapterView.OnItemClickListener itemClickListener = new  AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ConsultarId.this, DetalleSolicitud.class);
                intent.putExtra("id",listaId.get(i));
                startActivity(intent);

            }
        };
        listView.setOnItemClickListener(itemClickListener);
    }
    public void consultar(View view) {
        if (cont==0){
            cont++;
            Query query = FirebaseDatabase.getInstance().getReference("solicitudes").orderByChild("nickname").equalTo(nickname);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (!dataSnapshot.getKey().isEmpty()) {
                                listaId.add(dataSnapshot.getKey());
                            }
                        }
                    }
                    ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                            ConsultarId.this,
                            android.R.layout.simple_list_item_1,
                            listaId);
                    listView.setAdapter(listAdapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
    }
        else{
            Toast.makeText(ConsultarId.this, "Ya se listaron sus solicitudes", Toast.LENGTH_LONG).show();
        }
    }
}