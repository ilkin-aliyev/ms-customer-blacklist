package ms.customer.blacklist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.customer.blacklist.dao.repository.BlacklistRepository;
import ms.customer.blacklist.model.dto.BlacklistDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static ms.customer.blacklist.mapper.BlacklistMapper.buildBlacklistEntity;
import static ms.customer.blacklist.model.enums.Status.ACTIVE;
import static ms.customer.blacklist.model.enums.Status.INACTIVE;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public void addIntoBlacklist(BlacklistDto dto) {
        log.info("ActionLog.addIntoBlacklist.start cif:{}", dto.getCif());

        if (blacklistRepository.findByCifAndStatus(dto.getCif(), ACTIVE).isPresent()) {
            log.error("ActionLog.addIntoBlacklist.error customer present in blacklist cif:{}", dto.getCif());
            return;
        }

        blacklistRepository.save(buildBlacklistEntity(dto));

        log.info("ActionLog.addIntoBlacklist.success cif:{}", dto.getCif());
    }

    public void updateBlacklistStatus() {
        log.info("ActionLog.updateBlacklistStatus.start");

        var expiredBlacklists = blacklistRepository.findByStatusAndExpireAtBefore(ACTIVE, LocalDateTime.now());

        expiredBlacklists.forEach(blacklist -> blacklist.setStatus(INACTIVE));

        blacklistRepository.saveAll(expiredBlacklists);

        log.info("ActionLog.updateBlacklistStatus.success");
    }
}
