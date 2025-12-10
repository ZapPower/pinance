package sigma.pinance.src.core.data;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

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
