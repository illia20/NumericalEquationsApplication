package com.example.numequationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EquationAdapter extends RecyclerView.Adapter<EquationAdapter.ViewHolder> {

    private final List<Equation> equations;
    private final LayoutInflater layoutInflater;

    interface OnEquationClickListener { void onEquationClick(Equation equation); }

    EquationAdapter(Context context, List<Equation> equations, OnEquationClickListener onEquationClickListener){
        this.equations = equations;
        this.layoutInflater = LayoutInflater.from(context);
        this.onEquationClickListener = onEquationClickListener;
    }

    private final OnEquationClickListener onEquationClickListener;

    @NonNull
    @Override
    public EquationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new EquationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquationAdapter.ViewHolder holder, int position) {
        Equation equation = equations.get(position);
        holder.equationField.setText(equation.getEquation());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEquationClickListener.onEquationClick(equation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return equations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView equationField;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            equationField = (TextView)itemView.findViewById(R.id.equation_text);
        }
    }
}
