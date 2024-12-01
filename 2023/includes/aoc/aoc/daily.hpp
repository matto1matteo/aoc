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

class Extraction {
public:
    int Red;
    int Green;
    int Blue;

    static Extraction parseLine(std::string line);
};

class Game {
public:
    int Id;

    std::vector<Extraction> Extractions;

    static Game parseLine(std::string line);
};

class Day02 : public DailyProblem {
public:
    Day02(std::istream& s);
    Day02(const Day02&) = default;
    Day02(Day02&&) = default;
    Day02& operator=(const Day02&) = default;
    Day02& operator=(Day02&&) = default;

    void printSolution() override;

private:
    std::string firstSolution();
    std::string secondSolution();

    std::vector<Game> games;
};

}
