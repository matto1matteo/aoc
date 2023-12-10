#include "env.hpp"
#include <aoc/aoc.hpp>
#include <aoc/daily.hpp>
#include <cstddef>
#include <fstream>
#include <iostream>

namespace mtt {
AOC::AOC()
    : problems()
{
    std::fstream file(BASE_PATH + "/" + "day01.txt");
    problems[0] = new Day01(file);
}

const DailyProblem* AOC::getProblem(std::size_t day) const
{
    if (day > 25) {
        throw "expected index from 1 to 25";
    }

    if (problems[day - 1] == nullptr) {
        throw "problem not found";
    }

    return problems[day];
}

void AOC::printProblemSolutions() const
{
    for(auto problem : problems) {
        if (problem != nullptr)
        {
            std::cout << "Solution for problem " << problem->getDay() << "\n";
            problem->printSolution();
        }
    }
}

}
