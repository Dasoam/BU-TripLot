package com.dadash.butriplot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;

    public Adapter(Context context, ArrayList<RetriveTripLot> retriveTripLotArrayList) {
        this.context = context;
        this.retriveTripLotArrayList = retriveTripLotArrayList;
    }

    ArrayList<RetriveTripLot> retriveTripLotArrayList;
    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrivedharedtriplot,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        RetriveTripLot retriveTripLot=retriveTripLotArrayList.get(position);
        holder.Tadd.setText(retriveTripLot.Addrress);
        holder.Tdat.setText(retriveTripLot.Date);
        holder.Ttim.setText(retriveTripLot.Time);
        holder.Tvehi.setText(retriveTripLot.Vehicle);
        holder.Tnam.setText(retriveTripLot.Name);
        holder.Tphon.setText(retriveTripLot.Phone_Number);

        boolean isExpanded=retriveTripLotArrayList.get(position).isExpanded();
        holder.relativeLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return retriveTripLotArrayList.size();
    }

    //public static class MyViewHolder extends RecyclerView.ViewHolder {

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Tadd,Tdat,Ttim,Tvehi,Tnam,Tphon;
        RelativeLayout relativeLayout;
        TextView showdetails;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Tadd=itemView.findViewById(R.id.Taddrress);
            Tdat=itemView.findViewById(R.id.tdate);
            Ttim=itemView.findViewById(R.id.ttime);
            Tvehi=itemView.findViewById(R.id.tvehicle);
            Tnam=itemView.findViewById(R.id.tname);
            Tphon=itemView.findViewById(R.id.tphone);


            relativeLayout=itemView.findViewById(R.id.rl1);
            showdetails=itemView.findViewById(R.id.showhide);
            showdetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RetriveTripLot rpt= retriveTripLotArrayList.get(getAdapterPosition());
                    rpt.setExpanded(!rpt.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
