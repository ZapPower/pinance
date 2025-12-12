# pinance

`pinance` is an in-development budgeting tool for Greek life.

## How it works

### Objectives
An `objective` is any financial goal. For most use cases, this will represent
a single fiscal term. An `objective` contains a singular parent `budget item`
as well as some number of `members` that can `contribute` to the budget.

### Budget Items
A `budget item` is the primary unit for budget tracking. A budget item contains
a budget amount (the amount intended to be allocated), and can also
contain any number of nested `budget items`. The total internal budget of a
`budget item` *should never* reach above the parent budget.

An example tree may look like this:
```aiignore
Budget (100)
 - item1 (10)
    - item2 (5)
    - item3 (5)
 - item4 (50)
 - item5 (20)
```

### Members
A `member` is a registered individual that may `contribute` to
an `objective`. A `member` contains an `expected contribution`
that represents how much they are expected to give towards the
`objective` they are assigned to, and can make `contributions` that will
increase their `total contribution`.

### Contributions
A `contribution` is a single "payment" to an `objective`. It may or may not
be assigned to a `member`, and increases the `total contribution` towards an
`objective`.

## CLI
The CLI is currently **in development**, however what is available now
can be run from `src/main/java/sigma/pinance/src/poctesting/POCTest.java`