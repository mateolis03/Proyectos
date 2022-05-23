package unipiloto.edu.co.appiclaje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetalleSolicitud extends AppCompatActivity {
    private String id;
    private List<String> listaSolicitud;
    private  ListView listView;
    private String nickname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_solicitud);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        listView = (ListView) findViewById(R.id.listaSolicitud);
        listaSolicitud = new ArrayList<>();
        detalleSolicitud();

    }

    public void detalleSolicitud() {
        Query query = FirebaseDatabase.getInstance().getReference("solicitudes").child(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String tipo =snapshot.child("tipo").getValue().toString();
                    String peso =snapshot.child("peso").getValue().toString();
                    String direccion =snapshot.child("address").getValue().toString();
                    listaSolicitud.add(0,"Tipo: "+tipo);
                    listaSolicitud.add(1,"Peso: " +peso);
                    listaSolicitud.add(2,"Dirección: "+direccion);
                    ArrayAdapter<String> adapterSolicitud = new ArrayAdapter<>(
                            DetalleSolicitud.this,
                            android.R.layout.simple_list_item_1,
                            listaSolicitud);
                    listView.setAdapter(adapterSolicitud);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}