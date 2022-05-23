package unipiloto.edu.co.appiclaje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class TipoConsulta extends AppCompatActivity {
    private FirebaseAuth firbaseAuth;
    public String nickname="";
    public String tipo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_consulta);
        firbaseAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String id = firbaseAuth.getCurrentUser().getUid();
        nickname = intent.getStringExtra("nickname");
        tipo = intent.getStringExtra("tipo");
    }

    public void consultaID(View view) {
        Intent intent = new Intent(this, ConsultarId.class);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
          }
    public void consultaTipo(View view) {
        Intent intent = new Intent(this, ConsultaTipo.class);
        intent.putExtra("nickname",nickname);
        startActivity(intent);

    }
    public void consultaEstado(View view) {
        Intent intent = new Intent(this, ConsultaEstado.class);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
           }
    public void consultaMapa(View view) {
        Intent intent = new Intent(this, SolicitudesMapa.class);
        intent.putExtra("nickname",nickname);
        intent.putExtra("tipo",tipo);
        startActivity(intent);

    }
}
