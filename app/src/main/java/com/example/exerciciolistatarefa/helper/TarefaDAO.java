package com.example.exerciciolistatarefa.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.exerciciolistatarefa.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        escreve = dbHelper.getWritableDatabase();
        le = dbHelper.getReadableDatabase();

    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try{
            escreve.insert(DbHelper.NOME_TABELA_TAREFAS, null, cv);
            Log.i("INFO", "Tarefa salva com sucesso!");
        }catch (Exception e){
           Log.e("INFO", "Erro ao salvar tarefa" + e.getMessage());
           return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        String args[] = {tarefa.getId().toString()};
        try{
            escreve.update(DbHelper.NOME_TABELA_TAREFAS, cv, "id=?", args );
            Log.i("INFO", "Tarefa salva com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar tarefa" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        String args[] = {tarefa.getId().toString()};
        try{
            escreve.delete(DbHelper.NOME_TABELA_TAREFAS, "id=?", args);
            Log.i("INFO", "Tarefa salva com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar tarefa" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> listaTarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.NOME_TABELA_TAREFAS + " ;";
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){

            Tarefa tarefa = new Tarefa();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            listaTarefas.add(tarefa);
        }

        return listaTarefas;
    }
}
