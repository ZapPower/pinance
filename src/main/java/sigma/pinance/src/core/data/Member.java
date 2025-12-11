package sigma.pinance.src.core.data;

import lombok.Data;
import sigma.pinance.src.core.exceptions.ObjectiveException;
import sigma.pinance.src.core.exceptions.Severity;
import sigma.pinance.src.cli.utils.TextColor;
import sigma.pinance.src.cli.utils.TextUtils;

import java.util.ArrayList;
import java.util.UUID;

/**
 * An individual that can be assigned to an Objective.
 * They can be expected to contribute a certain amount and make contributions.
 */
@Data
public class Member {
    private double expectedContribution;
    private double currentContribution;
    private boolean isFulfilled;
    private UUID memberID;
    private String name;
    private ArrayList<Contribution> contributions;

    public Member(String name, double expectedContribution) {
        this.expectedContribution = expectedContribution;
        this.currentContribution = 0;
        this.isFulfilled = false;
        this.name = name;
        this.contributions = new ArrayList<>();
        this.memberID = UUID.randomUUID();
    }

    public double addContribution(Contribution c) {
        contributions.add(c);
        currentContribution += c.amount();
        if (currentContribution >= expectedContribution) {
            isFulfilled = true;
        }
        return currentContribution;
    }

    public double removeContribution(UUID contributionID) {
        Contribution contribution = contributions.stream()
                .filter(c -> c.contributionID().equals(contributionID))
                .findFirst().orElse(null);
        boolean successful = contributions.remove(contribution);
        if (!successful) {
            throw new ObjectiveException(Severity.LOW, "Could not remove contribution " + contributionID);
        }

        return currentContribution;
    }

    @Override
    public String toString() {
        TextColor tc = isFulfilled ? TextColor.GREEN : TextColor.ORANGE;
        StringBuilder sb = new StringBuilder();
        sb.append(TextUtils.color(name, tc)).append(":\n")
                .append("\t- Expected: ").append(expectedContribution).append("\n")
                .append("\t- Current: ").append(currentContribution).append("\n")
                .append("\t- ID: ").append(memberID).append("\n")
                .append("\nContributions:\n");
        if (contributions.isEmpty()) {
            sb.append("\tNone");
        }
        for (Contribution c : contributions) {
            sb.append("\t").append(c).append("\n");
        }
        return sb.toString();
    }
}
