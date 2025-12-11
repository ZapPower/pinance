package sigma.pinance.src.core.budget;

import lombok.Data;
import sigma.pinance.src.core.data.Contribution;
import sigma.pinance.src.core.data.Member;
import sigma.pinance.src.core.exceptions.ObjectiveException;
import sigma.pinance.src.core.exceptions.Severity;
import sigma.pinance.src.cli.utils.TextColor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * An Objective is a container for a budget. It represents any financial objective that
 * can be modeled by a budget. Members can be added and contribute to the budget.
 * The budget is contained within a special root BudgetItem.
 */
@Data
public class Objective {
    private static final String MAIN_BUDGET_NAME = "Budget";

    private String name;
    private UUID objectiveID;
    // TODO: create special root BudgetItem subclass
    private BudgetItem budget;
    private ArrayList<Contribution> ledger;
    private double totalContribution = 0;
    private double totalExpectedContribution = 0;
    private double completedContribution = 0;
    private boolean completed = false;
    private LocalDate startDate;
    private LocalDate endDate;
    private ArrayList<Member> members;

    public Objective(String name, double startingBudget, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = new BudgetItem(startingBudget, MAIN_BUDGET_NAME, null);
        this.ledger = new ArrayList<>();
        this.members = new ArrayList<>();
        this.objectiveID = UUID.randomUUID();
    }

    /**
     * Adds a new member to this budget and re-validates.
     *
     * @param name Name of the member
     * @param expectedContribution The expected contribution of the member
     * @return The new member
     * @throws ObjectiveException if validation fails
     */
    public Member addMember(String name, double expectedContribution) {
        Member member = new Member(name, expectedContribution);
        members.add(member);
        totalExpectedContribution += expectedContribution;
        validate();
        return member;
    }

    /**
     * Grabs a member by memberID
     *
     * @param memberID ID of the member
     * @return The associated member
     * @throws ObjectiveException If the member does not exist
     */
    public Member getMember(UUID memberID) {
        Member member = members.stream().filter(m -> m.getMemberID().equals(memberID))
                .findFirst().orElse(null);
        if (Objects.isNull(member)) {
            throw new ObjectiveException(Severity.LOW, "Could not find member with ID " + memberID);
        }
        return member;
    }

    /**
     * Creates a new contribution to this objective and re-validates.
     *
     * @param amount Amount for the contribution
     * @param date Date of contribution
     * @param memberID The member associated with the contribution
     * @param desc The description of the contribution
     * @return The new total contribution to this objective.
     * @throws ObjectiveException when validation fails or member doesn't exist
     */
    public double addContribution(double amount, LocalDate date, UUID memberID, String desc) {
        Member member = getMember(memberID);
        Contribution c = new Contribution(date, amount, desc, member);
        member.addContribution(c);
        ledger.add(c);
        totalContribution += amount;
        validate();
        return totalContribution;
    }
    public double addContribution(double amount, LocalDate date) {
        return addContribution(amount, date, null, null);
    }
    public double addContribution(double amount, LocalDate date, UUID memberID) {
        return addContribution(amount, date, memberID, null);
    }

    /**
     * Validates the objective (all member-related fields) and internal budget
     */
    public void validate() {
        double completedAmount = budget.getCompletedItemAmount();
        if (completedAmount > totalContribution) {
            throw new ObjectiveException(Severity.MEDIUM,
                    "You have over-completed the budget! Add contributions or un-complete budget items.");
        } else if (completedAmount > totalExpectedContribution) {
            throw new ObjectiveException(Severity.HIGH, "You have completed the budget beyond what expect to receive!");
        }
        budget.validate();
        completed = budget.isCompleted();
    }

    @Override
    public String toString() {
        return (completed ? TextColor.GREEN : TextColor.RED) +
                name +
                ": " +
                budget.toString() +
                " (" +
                startDate.toString() +
                ", " +
                endDate.toString() +
                ")" + TextColor.RESET;
    }
}
