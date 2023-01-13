package com.example.exerciciolistatarefa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciciolistatarefa.R;
import com.example.exerciciolistatarefa.model.Tarefa;

import java.util.List;
import java.util.zip.Inflater;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.MyViewHolder> {

    private List<Tarefa> listaTarefas;

    public TarefaAdapter(List<Tarefa> lista) {
        this.listaTarefas = lista;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_tarefa_adapter, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Tarefa tarefa1 = listaTarefas.get(position);
        holder.textoTarefa.setText(tarefa1.getNomeTarefa());
    }

    @Override
    public int getItemCount() {
        return listaTarefas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textoTarefa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textoTarefa = itemView.findViewById(R.id.textTarefa);
        }
    }
}
