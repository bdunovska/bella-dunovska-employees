package com.sirma.service;

import com.sirma.model.Employee;
import com.sirma.model.Team;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private final List<Employee> allEmployees;

    public EmployeeService(List<Employee> allEmployees) {
        this.allEmployees = allEmployees;
    }

    /**
     * Collects all pairs of employees who have worked together on a project over a period of time.
     * @return
     */
    public List<Team> collectTeams() {

        List<Team> teams = new ArrayList<>();

        for (int i = 0; i < allEmployees.size() - 1; i++) {
            for (int j = i + 1; j < allEmployees.size(); j++) {
                Employee firstEmployee = allEmployees.get(i);
                Employee secondEmployee = allEmployees.get(j);

                if (haveCollaboratedOnAProject(firstEmployee, secondEmployee)) {
                    long overlapDays = calculateTeamCollaborationPeriod(firstEmployee, secondEmployee);
                    if (overlapDays > 0) {
                        addTeam(teams, firstEmployee, secondEmployee, overlapDays);
                    }
                }
            }
        }
        return teams;
    }

    /**
     * Calculates the collaboration time period of two employees on a single project in days.
     * @param firstEmployee
     * @param secondEmployee
     * @return
     */
    private long calculateTeamCollaborationPeriod(Employee firstEmployee, Employee secondEmployee) {
        return Math.abs(ChronoUnit.DAYS.between(getEarlierDate(firstEmployee.getDateFrom(), secondEmployee.getDateFrom()),
                getEarlierDate(firstEmployee.getDateTo(), secondEmployee.getDateTo())));
    }

    private LocalDate getEarlierDate(LocalDate firstDate, LocalDate secondDate){
        return firstDate.isBefore(secondDate) ? firstDate : secondDate;
    }

    /**
     * Method to determine if two employees have worked together on a project
     * @param firstEmployee
     * @param secondEmployee
     * @return true if an employee's time on a project spans over the entire time period of work on the same project of another employee
     */
    private boolean haveCollaboratedOnAProject(Employee firstEmployee, Employee secondEmployee) {
        if(firstEmployee.getProjectId() != secondEmployee.getProjectId()){
            return false;
        }
        // firstEmployee start date is before or on the date of the second employee's start date on the project
        // and the end date is on or after end date of the second employee's end date.
        return (firstEmployee.getDateFrom().isBefore(secondEmployee.getDateTo())
                || firstEmployee.getDateFrom().isEqual(secondEmployee.getDateTo()))
                && (firstEmployee.getDateTo().isAfter(secondEmployee.getDateFrom())
                || firstEmployee.getDateTo().isEqual(secondEmployee.getDateFrom()));
    }

    private void addTeam(List<Team> teams, Employee firstEmployee, Employee secondEmployee, long overlapDays) {
            Team newTeam = new Team(
                    firstEmployee.getEmployeeId(),
                    secondEmployee.getEmployeeId(),
                    firstEmployee.getProjectId(),
                    overlapDays);

            //relays on the override of the equals method in the teams class
            if(!teams.contains(newTeam)) {
                teams.add(newTeam);
            }
    }

    public Team getTeamWithLongestCollaboration() {
        Team teamWithLongestCollaboration = null;
        List<Team> teams = collectTeams();
        if (teams.size() != 0) {
             teams.sort((team1, team2) ->
                    (int) (team2.getTotalDuration() - team1.getTotalDuration()));
             teamWithLongestCollaboration = teams.get(0);
        }
        return teamWithLongestCollaboration;
    }
}

