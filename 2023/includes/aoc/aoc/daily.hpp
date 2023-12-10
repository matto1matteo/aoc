#pragma once
#include "aoc/aoc.hpp"
#include <vector>

namespace mtt {
class Day01 : public DailyProblem {
public:
    Day01(std::istream& stream);
    Day01(const Day01&) = default;
    Day01(Day01&&) = default;
    Day01& operator=(const Day01&) = default;
    Day01& operator=(Day01&&) = default;

    void printSolution() override;

private:
    std::string firstSolution();
    std::string secondSolution();

    std::vector<std::string> data;
};


}
