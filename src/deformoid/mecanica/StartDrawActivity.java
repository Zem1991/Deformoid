package deformoid.mecanica;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import deformoid.mecanica.graficos.*;


public class StartDrawActivity extends Activity {
    DrawView drawView;
    //StartDrawActivity(double comprimentoViga, double larguraViga, double alturaViga, double peso, double GPA, String seccao){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //recupere aqui os extras da intent e passa por parametro no GraficoView
        Intent it = getIntent();
        String largura =  it.getStringExtra("largura");
        String altura =  it.getStringExtra("altura");
        String peso =  it.getStringExtra("peso");
        String gpa =  it.getStringExtra("gpa");
        String seccao = it.getStringExtra("seccao");
        String comprimento = it.getStringExtra("comprimento");
        Log.v("vigas", "Comprimento :"+comprimento+" Largura: "+largura+" Altura: "+altura+" Peso: "+peso+" Gpa: "+gpa);
        
        drawView = new DrawView(this,Double.parseDouble(comprimento),  Double.parseDouble(largura),  Double.parseDouble(altura),  Double.parseDouble(peso),  Double.parseDouble(gpa),seccao);
        drawView.setBackgroundColor(Color.WHITE);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(drawView);
    }
}