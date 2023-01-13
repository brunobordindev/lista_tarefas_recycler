package com.example.exerciciolistatarefa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.exerciciolistatarefa.helper.TarefaDAO;
import com.example.exerciciolistatarefa.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AddTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarefa);

        editTarefa = findViewById(R.id.editText);

        //recuperar tarefa para edicao
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //config. tarefa na caixa de texto
        if (tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemAdicionar:

                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if (tarefaAtual != null){//edicao

                    String nomeTarefa = editTarefa.getText().toString();

                    if(!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        if (tarefaDAO.atualizar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro ao atualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                }else{//salvar
                    String nomeTarefa = editTarefa.getText().toString();

                    if(!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);

                        if (tarefaDAO.salvar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro ao salvar", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}