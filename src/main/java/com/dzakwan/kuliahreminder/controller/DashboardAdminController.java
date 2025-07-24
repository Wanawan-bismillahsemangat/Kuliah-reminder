package com.dzakwan.kuliahreminder.controller;

import com.dzakwan.kuliahreminder.repository.JadwalRepository;
import com.dzakwan.kuliahreminder.model.Jadwal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardAdminController {

    @Autowired
    private JadwalRepository jadwalRepository;

    @GetMapping("/dashboardadmin")
    public String showDashboard(Model model) {
        List<Jadwal> jadwalList = jadwalRepository.findAll();
        model.addAttribute("jadwalList", jadwalList);
        return "dashboardadmin"; // menuju dashboard.html
    }
}
