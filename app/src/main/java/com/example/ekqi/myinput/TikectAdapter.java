package com.example.ekqi.myinput;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TikectAdapter extends RecyclerView.Adapter<TikectAdapter.ViewHolder> {

    Context context;
    ArrayList<MyTicket>myTickets;

    public TikectAdapter(Context c, ArrayList<MyTicket> p) {
        context = c;
        myTickets = p;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.iten_myticket,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.text_nama_wisata.setText(myTickets.get(i).getNama_wisata());
        viewHolder.text_lokasi.setText(myTickets.get(i).getLokasi());
        viewHolder.text_jumlah_Ticket.setText(myTickets.get(i).getJumlah_tiket()+" Ticket");

        final String getNamaWisata = myTickets.get(i).getNama_wisata();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomyticketdetail = new Intent(context,MyTicketDetailAct.class);
                gotomyticketdetail.putExtra("nama_wisata",getNamaWisata);
                context.startActivity(gotomyticketdetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_nama_wisata, text_lokasi, text_jumlah_Ticket;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_nama_wisata = itemView.findViewById(R.id.text_nama_wisata);
            text_lokasi = itemView.findViewById(R.id.text_lokasi);
            text_jumlah_Ticket = itemView.findViewById(R.id.text_jumlah_Ticket);
        }
    }
}
