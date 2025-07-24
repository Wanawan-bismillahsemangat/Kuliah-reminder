package com.dzakwan.kuliahreminder.controller;

import com.dzakwan.kuliahreminder.model.Jadwal;
import com.dzakwan.kuliahreminder.repository.JadwalRepository;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

@Controller
public class ViewController {

    @Autowired
    private JadwalRepository jadwalRepository;

    // ✅ Admin Dashboard (akses: /web/dashboard)
    @GetMapping("/web/dashboard")
    public String showAdminDashboard(Model model, Principal principal) {
    model.addAttribute("username", principal.getName());
    model.addAttribute("jadwalList", jadwalRepository.findAll());
    model.addAttribute("jadwalBaru", new Jadwal()); // ✅ TAMBAHKAN BARIS INI!
    return "dashboard";
    }


    // ✅ User Dashboard (akses: /web/dashboard_user)
    @GetMapping("/web/dashboard_user")
    public String dashboardUser(Model model, Principal principal) {
        model.addAttribute("jadwalList", jadwalRepository.findAll());
        model.addAttribute("username", principal.getName());
        return "dashboard_user"; // templates/dashboard_user.html
    }

    // ➕ Tambah Jadwal (admin only)
    @PostMapping("/jadwal/tambah")
    public String tambahJadwal(@ModelAttribute Jadwal jadwal) {
        jadwalRepository.save(jadwal);
        return "redirect:/web/dashboard";
    }

    // ❌ Hapus Jadwal (admin only)
   @PostMapping("/jadwal/hapus/{id}")
    public String hapusJadwal(@PathVariable Long id) {
    jadwalRepository.deleteById(id);
    return "redirect:/web/dashboard";
    }


    // ✏️ Form Edit Jadwal (admin only)
    @GetMapping("/jadwal/edit/{id}")
    public String editJadwal(@PathVariable Long id, Model model) {
        Jadwal jadwal = jadwalRepository.findById(id).orElse(null);
        model.addAttribute("jadwal", jadwal);
        return "jadwal_edit";
    }

    // ✅ Update Jadwal (admin only)
    @PostMapping("/jadwal/update")
    public String updateJadwal(@ModelAttribute Jadwal jadwal) {
        jadwalRepository.save(jadwal);
        return "redirect:/web/dashboard";
    }

    @GetMapping("/api/next-jadwal")
@ResponseBody
public Jadwal getNextJadwal() {
    List<Jadwal> semuaJadwal = jadwalRepository.findAll();
    LocalDateTime now = LocalDateTime.now();
    LocalTime timeNow = now.toLocalTime();
    DayOfWeek hariIni = now.getDayOfWeek();

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    for (Jadwal jadwal : semuaJadwal) {
        String hariDb = jadwal.getHari().toUpperCase();
        if (convertHariToEnglish(hariDb).equals(hariIni.name())) {
            try {
                LocalTime jadwalTime = LocalTime.parse(jadwal.getJam(), timeFormatter);
                long selisihMenit = Duration.between(timeNow, jadwalTime).toMinutes();

                if (selisihMenit > 0 && selisihMenit <= 30) {
                    return jadwal;
                }
            } catch (Exception e) {
                System.out.println("Format jam tidak valid: " + jadwal.getJam());
            }
        }
    }

    return null;
}

private String convertHariToEnglish(String hari) {
    return switch (hari.toLowerCase()) {
        case "senin" -> "MONDAY";
        case "selasa" -> "TUESDAY";
        case "rabu" -> "WEDNESDAY";
        case "kamis" -> "THURSDAY";
        case "jumat" -> "FRIDAY";
        case "sabtu" -> "SATURDAY";
        case "minggu" -> "SUNDAY";
        default -> "";
    };
}

}
