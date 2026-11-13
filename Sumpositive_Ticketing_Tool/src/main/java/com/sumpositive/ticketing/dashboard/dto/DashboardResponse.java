
package com.sumpositive.ticketing.dashboard.dto;

public class DashboardResponse {

    private long totalTickets;

    private long todoTickets;
    private long inProgressTickets;
    private long doneTickets;

    private long lowPriorityTickets;
    private long mediumPriorityTickets;
    private long highPriorityTickets;

    public DashboardResponse() {
    }

    public DashboardResponse(long totalTickets,
            long todoTickets,
            long inProgressTickets,
            long doneTickets,
            long lowPriorityTickets,
            long mediumPriorityTickets,
            long highPriorityTickets) {

        this.totalTickets = totalTickets;
        this.todoTickets = todoTickets;
        this.inProgressTickets = inProgressTickets;
        this.doneTickets = doneTickets;
        this.lowPriorityTickets = lowPriorityTickets;
        this.mediumPriorityTickets = mediumPriorityTickets;
        this.highPriorityTickets = highPriorityTickets;
    }

    public long getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(long totalTickets) {
        this.totalTickets = totalTickets;
    }

    public long getTodoTickets() {
        return todoTickets;
    }

    public void setTodoTickets(long todoTickets) {
        this.todoTickets = todoTickets;
    }

    public long getInProgressTickets() {
        return inProgressTickets;
    }

    public void setInProgressTickets(long inProgressTickets) {
        this.inProgressTickets = inProgressTickets;
    }

    public long getDoneTickets() {
        return doneTickets;
    }

    public void setDoneTickets(long doneTickets) {
        this.doneTickets = doneTickets;
    }

    public long getLowPriorityTickets() {
        return lowPriorityTickets;
    }

    public void setLowPriorityTickets(long lowPriorityTickets) {
        this.lowPriorityTickets = lowPriorityTickets;
    }

    public long getMediumPriorityTickets() {
        return mediumPriorityTickets;
    }

    public void setMediumPriorityTickets(long mediumPriorityTickets) {
        this.mediumPriorityTickets = mediumPriorityTickets;
    }

    public long getHighPriorityTickets() {
        return highPriorityTickets;
    }

    public void setHighPriorityTickets(long highPriorityTickets) {
        this.highPriorityTickets = highPriorityTickets;
    }
}
