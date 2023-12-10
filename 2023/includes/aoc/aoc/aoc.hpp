#pragma once

#include <cstddef>
#include <string>

namespace mtt {

class DailyProblem {
public:
    /// `print_solution` will print the solution for the problem
    virtual void printSolution() = 0;

    /// getter
    int getDay() const {
        return day + 1;
    }

private:
    int day;
};

class AOC {
public:
    AOC();
    AOC(AOC&&) = default;
    AOC(const AOC&) = default;
    AOC& operator=(AOC&&) = default;
    AOC& operator=(const AOC&) = default;
    ~AOC() = default;

    const DailyProblem* getProblem(std::size_t day) const;
    void printProblemSolutions() const;

private:
    DailyProblem* problems[25];
};
}
