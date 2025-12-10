package sigma.pinance.src.core.budget;

import lombok.Data;
import sigma.pinance.src.core.data.Contribution;
import sigma.pinance.src.core.data.Member;
import sigma.pinance.src.core.exceptions.ObjectiveException;
import sigma.pinance.src.core.exceptions.Severity;
import sigma.pinance.src.cli.utils.TextColor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Data
public class Objective {
    private static final String MAIN_BUDGET_NAME = "Budget";

    private String name;
    private UUID objectiveID;
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

    public Member addMember(String name, double expectedContribution) {
        Member member = new Member(name, expectedContribution);
        members.add(member);
        totalExpectedContribution += expectedContribution;
        validate();
        return member;
    }

    public Member getMember(UUID memberID) {
        return members.stream().filter(m -> m.getMemberID().equals(memberID))
                .findFirst().orElse(null);
    }

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
