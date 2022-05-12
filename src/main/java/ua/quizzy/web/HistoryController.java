package ua.quizzy.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.quizzy.domain.statistics.HistoryEntry;
import ua.quizzy.domain.statistics.QuizGlobalStatistics;
import ua.quizzy.domain.statistics.QuizStatistics;
import ua.quizzy.service.HistoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/statistics")
    public QuizStatistics getStatistics() {
        log.info("getStatistics");
        return historyService.getStatistics();
    }

    @GetMapping("/statistics/global")
    public List<QuizGlobalStatistics> getGlobalStatistics() {
        log.info("getGlobalStatistics");
        return historyService.getGlobalStatistics();
    }

    //    todo pagination
    @GetMapping
    public List<HistoryEntry> getHistory() {
        log.info("getHistory");
        return historyService.getHistory();
    }
}
