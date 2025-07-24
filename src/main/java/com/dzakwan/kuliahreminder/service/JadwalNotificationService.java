package com.dzakwan.kuliahreminder.service;

import com.dzakwan.kuliahreminder.model.Jadwal;
import com.dzakwan.kuliahreminder.repository.JadwalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class JadwalNotificationService {

    @Autowired
    private JadwalRepository jadwalRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Scheduled(fixedRate = 60000) // setiap 1 menit
    public void cekJadwal() {
        List<Jadwal> semua = jadwalRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek hariIni = now.getDayOfWeek();
        LocalTime timeNow = now.toLocalTime();

        for (Jadwal jadwal : semua) {
            try {
                if (convertHari(jadwal.getHari()).equals(hariIni)) {
                    LocalTime jam = LocalTime.parse(jadwal.getJam(), formatter);
                    long selisih = Duration.between(timeNow, jam).toMinutes();

                    if (selisih >= 0 && selisih <= 30) {
                        messagingTemplate.convertAndSend("/topic/jadwal", jadwal);
                    }
                }
            } catch (Exception e) {
                System.out.println("❗ Format jam salah: " + jadwal.getJam());
            }
        }
    }

    private DayOfWeek convertHari(String hari) {
        return switch (hari.toLowerCase()) {
            case "senin" -> DayOfWeek.MONDAY;
            case "selasa" -> DayOfWeek.TUESDAY;
            case "rabu" -> DayOfWeek.WEDNESDAY;
            case "kamis" -> DayOfWeek.THURSDAY;
            case "jumat" -> DayOfWeek.FRIDAY;
            case "sabtu" -> DayOfWeek.SATURDAY;
            case "minggu" -> DayOfWeek.SUNDAY;
            default -> DayOfWeek.MONDAY;
        };
    }
}
