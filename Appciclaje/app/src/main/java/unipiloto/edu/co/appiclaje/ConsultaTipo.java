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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ConsultaTipo extends AppCompatActivity {
    private String id;
    private TextView regId;
    private String nickname;
    private List<String> listTipo;
    private ListView listView;
    private Validation validation;
    private int cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_tipo);
        regId = findViewById(R.id.consulta_id);
        cont=0;
        Intent intent = getIntent();
        nickname = intent.getStringExtra("nickname");
        listView = (ListView) findViewById(R.id.listTipo);
        listTipo = new ArrayList<>();
       AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ConsultaTipo.this, DetalleSolicitud.class);
                intent.putExtra("id", listTipo.get(i));
                startActivity(intent);

            }
        };
        listView.setOnItemClickListener(itemClickListener);
    }
    public boolean validacion(){
        boolean c =true;
        validation = new Validation();
        if (validation.isEmpty(id)) {
            Toast.makeText(this, "No deje este campo vacío", Toast.LENGTH_LONG).show();
            c = false;
        }
        return c;
    }
    public void consultar(View view) {
        if (cont == 0) {
            cont++;
            id = regId.getText().toString();
            if(validacion()){
                Query query = FirebaseDatabase.getInstance().getReference("solicitudes").orderByChild("nickname").equalTo(nickname);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            List<String> values=new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (!dataSnapshot.getKey().isEmpty()) {
                                    String tipo =dataSnapshot.child("tipo").getValue().toString();
                                    if(tipo.equalsIgnoreCase(id)){
                                        listTipo.add(dataSnapshot.getKey());
                                    }
                                }
                            }
                            if(listTipo.isEmpty()){
                                Toast.makeText(ConsultaTipo.this, "No existen solicitudes", Toast.LENGTH_LONG).show();
                                cont=0;
                            }

                        }

                        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                                ConsultaTipo.this,
                                android.R.layout.simple_list_item_1,
                                listTipo);
                        listView.setAdapter(listAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }else{
                Toast.makeText(ConsultaTipo.this, "No deje el campo vacio", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(ConsultaTipo.this, "Ya se listaron sus solicitudes", Toast.LENGTH_LONG).show();
        }
    }


    }

