package ms.customer.blacklist.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ms.customer.blacklist.model.enums.RiskLevel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlacklistDto {
    private String cif;
    private RiskLevel riskLevel;
}
