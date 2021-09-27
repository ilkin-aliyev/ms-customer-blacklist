package ms.customer.blacklist.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RiskLevel {
    LOW(12),
    MEDIUM(24),
    HIGH(36);

    private final int timeDuration;
}
