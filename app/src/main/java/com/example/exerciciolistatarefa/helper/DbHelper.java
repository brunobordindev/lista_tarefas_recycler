package com.example.exerciciolistatarefa.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.exerciciolistatarefa.adapter.TarefaAdapter;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String NOME_TABELA_TAREFAS = "tarefas";


    public DbHelper(Context context ){
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + NOME_TABELA_TAREFAS
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT NOT NULL ); ";

        try{
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + NOME_TABELA_TAREFAS + " ;";

        try{
            db.execSQL(sql);
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao  atualizar App");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao  atualizar App" + e.getMessage());
        }
    }
}
