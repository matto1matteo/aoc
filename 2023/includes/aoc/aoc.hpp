#include <cstddef>
#include <string>

namespace mtt {

class DailyProblem {
public:
    DailyProblem() = default;
    DailyProblem(DailyProblem&&) = default;
    DailyProblem(const DailyProblem&) = default;
    DailyProblem& operator=(DailyProblem&&) = default;
    DailyProblem& operator=(const DailyProblem&) = default;
    ~DailyProblem() = default;

    /// Method to override

    /// `read_file` will read and load data from the file located at `file_path`
    virtual void read_file(std::string file_path) = 0;

    /// `print_solution` will print the solution for the problem
    virtual void print_solution() = 0;

private:
};

class AOC {
public:
    AOC() = default;
    AOC(AOC&&) = default;
    AOC(const AOC&) = default;
    AOC& operator=(AOC&&) = default;
    AOC& operator=(const AOC&) = default;
    ~AOC() = default;

    const DailyProblem* get_problem(std::size_t day);
    void print_problems();

private:
    DailyProblem* problems[25];
};
}
