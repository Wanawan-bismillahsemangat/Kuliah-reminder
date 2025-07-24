package com.dzakwan.kuliahreminder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dzakwan.kuliahreminder.model.Jadwal;
import com.dzakwan.kuliahreminder.repository.JadwalRepository;

import java.util.List;

@RestController
@RequestMapping("/api/jadwal")
public class JadwalController {

    @Autowired
    private JadwalRepository jadwalRepository;

    @GetMapping("/seed")
    public String seedJadwal() {
        Jadwal j = new Jadwal();
        j.setMataKuliah("Pemrograman Web");
        j.setHari("Senin");
        j.setJam("08:00");
        j.setRuangan("Ruang A1");
        jadwalRepository.save(j);
        return "Data berhasil disimpan!";
    }

    @PostMapping
    public Jadwal createJadwal(@RequestBody Jadwal jadwal) {
        return jadwalRepository.save(jadwal);
    }

    @GetMapping
    public List<Jadwal> getAllJadwal() {
        return jadwalRepository.findAll();
    }
}
