package ms.customer.blacklist.mapper;

import ms.customer.blacklist.dao.entity.BlacklistEntity;
import ms.customer.blacklist.model.dto.BlacklistDto;

import java.time.LocalDateTime;

import static ms.customer.blacklist.model.enums.Status.ACTIVE;

public abstract class BlacklistMapper {

    public static BlacklistEntity buildBlacklistEntity(BlacklistDto dto) {
        return BlacklistEntity.builder()
                .cif(dto.getCif())
                .status(ACTIVE)
                .riskLevel(dto.getRiskLevel())
                .expireAt(LocalDateTime.now().plusHours(dto.getRiskLevel().getTimeDuration()))
                .build();
    }
}
