package com.example.exerciciolistatarefa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.exerciciolistatarefa.adapter.TarefaAdapter;
import com.example.exerciciolistatarefa.helper.RecyclerItemClickListener;
import com.example.exerciciolistatarefa.helper.TarefaDAO;
import com.example.exerciciolistatarefa.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTarefaActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), listaTarefas.get(position).getNomeTarefa(), Toast.LENGTH_SHORT).show();

                        //recuperar tarefa para edicao
                        tarefaSelecionada = listaTarefas.get(position);
                        Intent intent = new Intent(MainActivity.this, AddTarefaActivity.class);
                        intent.putExtra("tarefaSelecionada", tarefaSelecionada);
                        startActivity(intent);

                        Log.i("clique", "onItemClick");
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        tarefaSelecionada = listaTarefas.get(position);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("Excluir Tarefa");
                        dialog.setMessage("Você deseja escluir: \n" + tarefaSelecionada.getNomeTarefa() + "?");

                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                                if (tarefaDAO.deletar(tarefaSelecionada)){
                                    carregarListaTarefa();
                                    Toast.makeText(getApplicationContext(), "Sucesso ao deletar tarefa", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(getApplicationContext(), "Erro ao deletar tarefa", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.setNegativeButton("Não", null);

                        dialog.create();
                        dialog.show();

                        Log.i("cliqueLongo", "onLongItemClick");
                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));


    }

    @Override
    protected void onStart() {
        carregarListaTarefa();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.itemConfiguracao);
        return super.onOptionsItemSelected(item);
    }

    public void carregarListaTarefa(){

        //Lista tarefas
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        listaTarefas = tarefaDAO.listar();



        //configuracao Adapter recyclerView
        tarefaAdapter = new TarefaAdapter(listaTarefas);

        //Configuracao recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(tarefaAdapter);
    }
}