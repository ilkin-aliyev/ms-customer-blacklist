package ms.customer.blacklist.scheduler;

import lombok.RequiredArgsConstructor;
import ms.customer.blacklist.service.BlacklistService;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlacklistScheduler {
    private final BlacklistService blacklistService;

    @Scheduled(fixedDelayString = "PT5M")
    @SchedulerLock(name = "update-blacklist-status", lockAtLeastForString = "PT5M", lockAtMostForString = "PT15M")
    public void updateBlacklistStatus() {
        blacklistService.updateBlacklistStatus();
    }
}
