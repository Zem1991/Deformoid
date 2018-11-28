package deformoid.mecanica;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity implements OnItemSelectedListener {

	private EditText textLargura;
	private EditText textAltura;
	private EditText textGpa;
	private EditText textPeso;
	private EditText textComprimento;
	private int materialId;
	private int seccaoID;
	private Spinner spinner;
	private Spinner spinnerSeccoes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textLargura = (EditText) findViewById(R.id.EditTextLargura);
		textAltura = (EditText) findViewById(R.id.EditTextAltura);
		textGpa = (EditText) findViewById(R.id.EditTextGpa);
		textPeso = (EditText) findViewById(R.id.EditTextPeso);
		textComprimento = (EditText) findViewById(R.id.EditTextComprimento);

		// inicializa spinner com materiais
		spinner = (Spinner) findViewById(R.id.spinner1);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.materiais_array,
				android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setOnItemSelectedListener(this);

		spinner.setAdapter(adapter);

		// inicializa spinner seccoes

		spinnerSeccoes = (Spinner) findViewById(R.id.spinner2);

		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.seccao_array,
				android.R.layout.simple_spinner_item);

		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerSeccoes.setAdapter(adapter2);

	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// quando um item do spinner é selecionado preencher campo de GPa

		materialId = 1;
		seccaoID = 1;

		Log.v("viga", "ID: " + id + " Pos: " + pos + " String.Valueof(id): "
				+ String.valueOf(id));

		switch (pos) {
		case 1: // concreto
			textGpa.setText("25");

			break;

		case 2: // aço
			textGpa.setText("210");

			break;
		case 3:// ferro fundido
			textGpa.setText("70");

			break;
		case 4:// latao
			textGpa.setText("105");

			break;
		case 5:// pinho
			textGpa.setText("15");

			break;
		case 6:// eucalipto
			textGpa.setText("24");

			break;
		case 7:// poliestireno
			textGpa.setText("3");

			break;
		case 8:// titanio
			textGpa.setText("114");

			break;

		default:
			break;
		}

	}

	public void onNothingSelected(AdapterView<?> parent) {
		// quando nenhum é selecionado
	}	

	public void onClick(View v) {

		int id = v.getId();

		switch (id) {
		case R.id.buttonCriaGrafico:

			Log.v("vigas",
					"Comprimento: " + textComprimento.getText().toString() + " Largura: "
							+ textLargura.getText().toString() + " Altura: "
							+ textAltura.getText().toString() + " Peso: "
							+ textPeso.getText().toString() + " Gpa: " + textGpa.getText().toString()
							+ " Seccao: "
							+ String.valueOf(spinnerSeccoes.getSelectedItem()));

			// verifica se todos elementos estão preenchidos
			if (textLargura.getText().toString().equals("") || textAltura.getText().toString().equals("")
					|| textPeso.getText().toString().equals("") || textGpa.getText().toString().equals("")
					|| textComprimento.getText().toString().equals("")) {

				Log.v("vigas","Erro");
				// mostra alert dialog
				mostraDialogErro();
			} else {
				Log.v("vigas","Carregando Grafico");

				Intent it = new Intent(getApplicationContext(),
						GraficoActivity.class);

				// Log.v("vigas",
				// "MainActivity diz: Largura: "+textLargura.getText()+" Altura: "+textAltura.getText()+" Peso: "+textPeso.getText()+" Gpa: "+textGpa.getText().toString());

				// recuperar os elementos da tela aqui
				// passar todos os parametros para a activity
				it.putExtra("largula", textLargura.getText().toString());
				it.putExtra("altura", textAltura.getText().toString());
				it.putExtra("peso", textPeso.getText().toString());
				it.putExtra("gpa", textGpa.getText().toString());
				it.putExtra("comprimento", textComprimento.getText().toString());

				// it.putExtra("material",
				// String.valueOf(spinner.getSelectedItem()));
				it.putExtra("seccao",
						String.valueOf(spinnerSeccoes.getSelectedItem()));

				startActivity(it);
			}

			break;
			
		case R.id.buttonVerDeformacao:

			Log.v("vigas",
					"Comprimento: " + textComprimento.getText().toString() + " Largura: "
							+ textLargura.getText().toString() + " Altura: "
							+ textAltura.getText().toString() + " Peso: "
							+ textPeso.getText().toString() + " Gpa: " + textGpa.getText().toString()
							+ " Seccao: "
							+ String.valueOf(spinnerSeccoes.getSelectedItem()));

			// verifica se todos elementos estão preenchidos
			if (textLargura.getText().toString().equals("") || textAltura.getText().toString().equals("")
					|| textPeso.getText().toString().equals("") || textGpa.getText().toString().equals("")
					|| textComprimento.getText().toString().equals("")) {

				Log.v("vigas","Erro");
				// mostra alert dialog
				mostraDialogErro();
			} else {
				Log.v("vigas","Carregando Deformação");

				Intent it = new Intent("com.deformoid.mecanica.STARTDRAWACTIVITY");

				// Log.v("vigas",
				// "MainActivity diz: Largura: "+textLargura.getText()+" Altura: "+textAltura.getText()+" Peso: "+textPeso.getText()+" Gpa: "+textGpa.getText().toString());

				// recuperar os elementos da tela aqui
				// passar todos os parametros para a activity
				it.putExtra("largura", textLargura.getText().toString());
				it.putExtra("altura", textAltura.getText().toString());
				it.putExtra("peso", textPeso.getText().toString());
				it.putExtra("gpa", textGpa.getText().toString());
				it.putExtra("comprimento", textComprimento.getText().toString());

				// it.putExtra("material",
				// String.valueOf(spinner.getSelectedItem()));
				it.putExtra("seccao",
						String.valueOf(spinnerSeccoes.getSelectedItem()));

				startActivity(it);
			}

			break;

		default:
			break;
		}

	}

	private void mostraDialogErro() {
		final AlertDialog alertDialog = new AlertDialog.Builder(
				MainActivity.this).create();
		alertDialog.setTitle("Erro");
		alertDialog.setMessage("Insira todos os dados necessários");

		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
				// here you can add functions

			}
		});

		alertDialog.show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.equipe:
			Intent viewIntent =
			new Intent(MainActivity.this, Equipe.class);
			startActivity(viewIntent);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
