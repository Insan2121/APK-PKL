package com.example.ApkPKL;

public class User {

    private String username;
    private String email;
    private String password;
    private String jeniskelamin;
    private String alamat;
    private String kontak;
    private String tgl;
    private String nama;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJenisKelamin() {
        return jeniskelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jeniskelamin = jenisKelamin;
    }

    public String getTanggal() {
        return tgl;
    }

    public void setTanggal(String tanggal) {
        this.tgl = tanggal;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

}
