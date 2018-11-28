package deformoid.mecanica;

import deformoid.mecanica.graficos.GraficoView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;


public class GraficoActivity extends Activity {
	GraficoView mView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //recupere aqui os extras da intent e passa por parametro no GraficoView
        Intent it = getIntent();
        String largura =  it.getStringExtra("largula");
        String altura =  it.getStringExtra("altura");
        String peso =  it.getStringExtra("peso");
        String gpa =  it.getStringExtra("gpa");
        String seccao = it.getStringExtra("seccao");        
        String comprimento = it.getStringExtra("comprimento");
        Log.v("vigas", "Comprimento :"+comprimento+" Largura: "+largura+" Altura: "+altura+" Peso: "+peso+" Gpa: "+gpa);
        
        mView = new GraficoView(this,  Double.parseDouble(comprimento),  Double.parseDouble(largura),  Double.parseDouble(altura),  Double.parseDouble(peso),  Double.parseDouble(gpa),seccao);
        mView.setBackgroundColor(Color.WHITE);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(mView);
    }
}
