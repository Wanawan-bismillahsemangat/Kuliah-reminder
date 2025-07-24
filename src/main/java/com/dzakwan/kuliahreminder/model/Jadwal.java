package com.dzakwan.kuliahreminder.model;

import jakarta.persistence.*;

@Entity
public class Jadwal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mataKuliah;
    private String hari;
    private String jam;
    private String ruangan;


    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMataKuliah() { return mataKuliah; }
    public void setMataKuliah(String mataKuliah) { this.mataKuliah = mataKuliah; }

    public String getHari() { return hari; }
    public void setHari(String hari) { this.hari = hari; }

    public String getJam() { return jam; }
    public void setJam(String jam) { this.jam = jam; }

    public String getRuangan() { return ruangan; }
    public void setRuangan(String ruangan) { this.ruangan = ruangan; }
}
