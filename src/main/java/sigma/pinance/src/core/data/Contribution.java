package sigma.pinance.src.core.data;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A singular contribution to an objective, may or may not be made by a member
 *
 * @param date
 * @param amount
 * @param contributionID
 * @param description
 * @param contributor
 */
@Builder
public record Contribution(LocalDate date, double amount, UUID contributionID, String description, Member contributor) {
    public Contribution(LocalDate date, double amount, String description, Member contributor) {
        this(date, amount, UUID.randomUUID(), description, contributor);
    }

    @Override
    public String toString() {
        return "$" + amount + (Objects.nonNull(description) ? " - \"" + description + "\"" : "") + " (" + date + ")";
    }
}
