package com.example.ekqi.myinput;

public class MyTicket {
    String nama_wisata;
    String lokasi;
    int jumlah_tiket;

    public MyTicket() {
    }

    public MyTicket(String nama_wisata, String lokasi, int jumlah_tiket) {
        this.nama_wisata = nama_wisata;
        this.lokasi = lokasi;
        this.jumlah_tiket = jumlah_tiket;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public int getJumlah_tiket() {
        return jumlah_tiket;
    }

    public void setJumlah_tiket(int jumlah_tiket) {
        this.jumlah_tiket = jumlah_tiket;
    }
}
