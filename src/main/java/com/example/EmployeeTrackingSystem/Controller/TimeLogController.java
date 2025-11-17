package com.example.EmployeeTrackingSystem.Controller;

import com.example.EmployeeTrackingSystem.model.WorkSession;
import com.example.EmployeeTrackingSystem.repository.WorkSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/timelog")
@CrossOrigin(origins = "*")
public class TimeLogController {

    @Autowired
    private WorkSessionRepository workRepo;

    // ✅ Clock In
    @PostMapping("/start/{username}")
    public WorkSession startSession(@PathVariable String username) {

        Optional<WorkSession> active = workRepo.findByUsernameAndClockOutIsNull(username);
        if (active.isPresent()) {
            return active.get();   // already active
        }

        WorkSession ws = new WorkSession();
        ws.setUsername(username);
        ws.setClockIn(LocalDateTime.now());
        ws.setDate(LocalDate.now());

        return workRepo.save(ws);
    }

    // ✅ Clock Out
    @PostMapping("/stop/{username}")
    public WorkSession stopSession(@PathVariable String username) {

        WorkSession ws = workRepo.findByUsernameAndClockOutIsNull(username)
                .orElseThrow(() -> new RuntimeException("No active session"));

        LocalDateTime end = LocalDateTime.now();
        ws.setClockOut(end);

        double hours = Duration.between(ws.getClockIn(), end).toMinutes() / 60.0;
        ws.setHoursWorked(hours);

        return workRepo.save(ws);
    }

    // ✅ Get Active
    @GetMapping("/active/{username}")
    public WorkSession getActiveSession(@PathVariable String username) {
        return workRepo.findByUsernameAndClockOutIsNull(username).orElse(null);
    }

    // ✅ Today’s hours
    @GetMapping("/today/{username}")
    public double getTodayHours(@PathVariable String username) {
        List<WorkSession> today = workRepo.findByUsernameAndDate(username, LocalDate.now());
        return today.stream().mapToDouble(ws -> ws.getHoursWorked() == null ? 0 : ws.getHoursWorked()).sum();
    }

    // ✅ Weekly
    @GetMapping("/weeklyHours")
    public List<Map<String, Object>> getWeeklyHours() {
        List<Map<String, Object>> weekly = new ArrayList<>();

        weekly.add(Map.of("day", "Mon", "hours", 4));
        weekly.add(Map.of("day", "Tue", "hours", 5));
        weekly.add(Map.of("day", "Wed", "hours", 6));
        weekly.add(Map.of("day", "Thu", "hours", 3));
        weekly.add(Map.of("day", "Fri", "hours", 8));
        weekly.add(Map.of("day", "Sat", "hours", 2));
        weekly.add(Map.of("day", "Sun", "hours", 0));

        return weekly;
    }

    // ✅ Fetch All Logs
    @GetMapping("/user/{username}")
    public List<WorkSession> getLogs(@PathVariable String username) {
        return workRepo.findByUsername(username);
    }

    @GetMapping("/test")
    public String test() {
        return "TimeLog Active";
    }
}
