package com.example.taller;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText cantidad;
    TextView valor, total;
    String materiales[], dijes[], tipos[], monedas[];
    Spinner cmbMaterial, cmbDijes, cmbTipos, cmbMonedas;
    int precios[][][]= new int[2][2][4];

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inicializamos Precios

        precios[0][0][0] = 100;
        precios[0][0][1] = 100;
        precios[0][0][2] = 80;
        precios[0][0][3] = 70;

        precios[0][1][0] = 120;
        precios[0][1][1] = 120;
        precios[0][1][2] = 100;
        precios[0][1][3] = 90;

        precios[1][0][0] = 90;
        precios[1][0][1] = 90;
        precios[1][0][2] = 70;
        precios[1][0][3] = 50;

        precios[1][1][0] = 110;
        precios[1][1][1] = 110;
        precios[1][1][2] = 90;
        precios[1][1][3] = 80;


        //capturamos los objetos utilizados
        cantidad = findViewById(R.id.txtCantidad);
        cantidad.setFilters(new InputFilter[]{ new MinMaxFilter("1", "100")});
        valor = findViewById(R.id.txtValor);
        total = findViewById(R.id.txtTotal);
        cmbMaterial = (Spinner)findViewById(R.id.cmbMaterial);
        cmbDijes = findViewById(R.id.cmbDije);
        cmbTipos = findViewById(R.id.cmbTipos);
        cmbMonedas = findViewById(R.id.cmbMonedas);
        //Añadimos el evento a cada spinner
        cmbMaterial.setOnItemSelectedListener(this);
        cmbDijes.setOnItemSelectedListener(this);
        cmbTipos.setOnItemSelectedListener(this);
        cmbMonedas.setOnItemSelectedListener(this);
        //Traemos los materiales y dijes en un Array
        materiales = getResources().getStringArray(R.array.materiales);
        dijes = getResources().getStringArray(R.array.dijes);
        tipos = getResources().getStringArray(R.array.tipos);
        monedas = getResources().getStringArray(R.array.monedas);
        //Creamos los adapter indicando donde se va a colocar como se va a visualizar y que se va a mostrar
        ArrayAdapter<String> adapterMateriales = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, materiales);
        ArrayAdapter<String> adapterDijes = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, dijes);
        ArrayAdapter<String> adapterTipos = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, tipos);
        ArrayAdapter<String> adapterMonedas = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, monedas);
        //pasamos el adapter al combo
        cmbMaterial.setAdapter(adapterMateriales);
        cmbDijes.setAdapter(adapterDijes);
        cmbTipos.setAdapter(adapterTipos);
        cmbMonedas.setAdapter(adapterMonedas);
        valor.setText(""+precios[0][0][0]);
        total.setText(""+0);

    }

    public void calcular(View v){
        int res = 0;
        int cant, precio;
        if (validar()){
            cant = Integer.parseInt(cantidad.getText().toString());
            precio = Integer.parseInt(valor.getText().toString());
            res = cant*precio;
            total.setText(""+res);
        }

    }

    public void limpiar(View v){
        cantidad.setText("");
        total.setText("");
        cmbMaterial.setSelection(0);
        cmbDijes.setSelection(0);
        cmbTipos.setSelection(0);
        cmbMonedas.setSelection(0);
        cantidad.requestFocus();
        cantidad.setError(null);
    }

    public boolean validar(){
        if (cantidad.getText().toString().isEmpty()){
            cantidad.setError(getResources().getString(R.string.error_1));
            cantidad.requestFocus();
            return false;
        }else if(Integer.parseInt(cantidad.getText().toString())==0){
            cantidad.setError(getResources().getString(R.string.error_0));
            cantidad.selectAll();
            return false;
        }if(Integer.parseInt(cantidad.getText().toString())>100){
            cantidad.setError(getResources().getString(R.string.error_0));
            cantidad.selectAll();
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> a, View v, int pos, long l) {
        int posM, posD, posT, resultado, posMoneda;
        total.setText("");
        posM = cmbMaterial.getSelectedItemPosition();
        posD = cmbDijes.getSelectedItemPosition();
        posT = cmbTipos.getSelectedItemPosition();
        posMoneda = cmbMonedas.getSelectedItemPosition();
        if (posMoneda==1){ // pesos colombianos
           resultado = precios[posM][posD][posT]*3200;
           valor.setText(""+resultado);
        }else{ //Dolar
            valor.setText(""+precios[posM][posD][posT]);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
